package io.siggi.configurationskipper;

import io.netty.buffer.ByteBuf;
import net.md_5.bungee.UserConnection;
import net.md_5.bungee.protocol.DefinedPacket;
import net.md_5.bungee.protocol.PacketWrapper;

public class ConfigurationSkipper {

    private static ConfigurationSkipperMeta get(UserConnection connection) {
        Object meta = connection.getConfigurationSkipperMeta();
        if (meta == null) {
            connection.setConfigurationSkipperMeta(meta = new ConfigurationSkipperMeta());
        }
        return (ConfigurationSkipperMeta) meta;
    }

    public static boolean supportsVersion(int version) {
        return CSPacketIDRemapping.getByProtocolVersion(version) != null;
    }

    public static void handleClientbound(PacketWrapper packet, UserConnection userConnection) {
        // Handle a clientbound packet where the client is in the Configuration state

        int protocolVersion = userConnection.getPendingConnection().getVersion();
        CSPacketIDRemapping remapping = CSPacketIDRemapping.getByProtocolVersion(protocolVersion);
        int packetId = getPacketId(packet.buf);
        int remapped = remapping.clientboundC2P(packetId);
        if (remapped == -3) {
            get(userConnection).processPacket(packet.buf, packetId);
        }
    }
    public static PacketWrapper handleClientbound(PacketWrapper packet, UserConnection userConnection, Runnable switchToConfiguration, Runnable exitConfiguration) {
        // Handle a clientbound packet where the client is in the Play state

        int protocolVersion = userConnection.getPendingConnection().getVersion();
        CSPacketIDRemapping remapping = CSPacketIDRemapping.getByProtocolVersion(protocolVersion);
        int packetId = getPacketId(packet.buf);
        int remapped = remapping.clientboundC2P(packetId);
        switch (remapped) {
            case -1: // packet has no mapping
                return null;
            case -2: // Finish Configuration packet
                exitConfiguration.run();
                return null;
            case -3: // Configuration-only packet
                if (get(userConnection).processPacket(packet.buf, packetId)) {
                    switchToConfiguration.run();
                    return packet;
                } else {
                    return null;
                }
            default: // packet is remapped
                rewritePacketId(packet.buf, remapped);
                return packet;
        }
    }

    public static PacketWrapper handleServerbound(PacketWrapper packet, UserConnection userConnection) {
        int protocolVersion = userConnection.getPendingConnection().getVersion();
        CSPacketIDRemapping remapping = CSPacketIDRemapping.getByProtocolVersion(protocolVersion);
        int remapped = remapping.serverboundP2C(getPacketId(packet.buf));
        if (remapped == -1) return null; // packet has no mapping
        rewritePacketId(packet.buf, remapped);
        return packet;
    }

    private static int getPacketId(ByteBuf packet) {
        int startOfPacketId = packet.readerIndex();
        int id = DefinedPacket.readVarInt(packet);
        packet.readerIndex(startOfPacketId);
        return id;
    }

    private static void rewritePacketId(ByteBuf packet, int newId) {
        int startOfPacketId = packet.readerIndex();
        DefinedPacket.readVarInt(packet);
        ByteBuf data = packet.copy();
        packet.readerIndex(startOfPacketId);
        packet.writerIndex(startOfPacketId);
        DefinedPacket.writeVarInt(newId, packet);
        packet.writeBytes(data);
        data.release();
    }
}
