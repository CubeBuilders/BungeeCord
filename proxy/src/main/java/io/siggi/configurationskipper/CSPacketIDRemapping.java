package io.siggi.configurationskipper;

import net.md_5.bungee.protocol.ProtocolConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public enum CSPacketIDRemapping {
    MINECRAFT_1_20_2(
            (remapping) -> {
                remapping.addClientboundMapping(0x00, 0x18); // Plugin Message
                remapping.addClientboundMapping(0x01, 0x1B); // Disconnect
                remapping.addClientboundMapping(0x02, -2);   // Finish Configuration
                remapping.addClientboundMapping(0x03, 0x24); // Keep Alive
                remapping.addClientboundMapping(0x04, 0x33); // Ping
                remapping.addClientboundMapping(0x05, -3);   // Registry Data
                remapping.addClientboundMapping(0x06, 0x42); // Resource Pack
                remapping.addClientboundMapping(0x07, -3);   // Feature Flags
                remapping.addClientboundMapping(0x08, 0x70); // Update Tags

                remapping.addServerboundMapping(0x00, 0x09); // Client Settings
                remapping.addServerboundMapping(0x01, 0x0F); // Plugin Message
                remapping.addServerboundMapping(0x02, -2);   // Finish Configuration
                remapping.addServerboundMapping(0x03, 0x14); // Keep Alive
                remapping.addServerboundMapping(0x04, 0x23); // Pong
                remapping.addServerboundMapping(0x05, 0x27); // Resource Pack Response
            },
            ProtocolConstants.MINECRAFT_1_20_2
    ),
    MINECRAFT_1_20_3(
            (remapping) -> {
                remapping.addClientboundMapping(0x00, 0x18); // Plugin Message
                remapping.addClientboundMapping(0x01, 0x1B); // Disconnect
                remapping.addClientboundMapping(0x02, -2);   // Finish Configuration
                remapping.addClientboundMapping(0x03, 0x24); // Keep Alive
                remapping.addClientboundMapping(0x04, 0x33); // Ping
                remapping.addClientboundMapping(0x05, -3);   // Registry Data
                remapping.addClientboundMapping(0x06, 0x43); // Remove Resource Pack
                remapping.addClientboundMapping(0x07, 0x44); // Add Resource Pack
                remapping.addClientboundMapping(0x08, -3);   // Feature Flags
                remapping.addClientboundMapping(0x09, 0x74); // Update Tags

                remapping.addServerboundMapping(0x00, 0x09); // Client Settings
                remapping.addServerboundMapping(0x01, 0x10); // Plugin Message
                remapping.addServerboundMapping(0x02, -2);   // Finish Configuration
                remapping.addServerboundMapping(0x03, 0x15); // Keep Alive
                remapping.addServerboundMapping(0x04, 0x24); // Pong
                remapping.addServerboundMapping(0x05, 0x28); // Resource Pack Response
            },
            ProtocolConstants.MINECRAFT_1_20_3
    ),
    MINECRAFT_1_20_5(
            (remapping) -> {
                remapping.addClientboundMapping(0x00, 0x16); // Cookie Request
                remapping.addClientboundMapping(0x01, 0x19); // Plugin Message
                remapping.addClientboundMapping(0x02, 0x1D); // Disconnect
                remapping.addClientboundMapping(0x03, -2);   // Finish Configuration
                remapping.addClientboundMapping(0x04, 0x26); // Keep Alive
                remapping.addClientboundMapping(0x05, 0x35); // Ping
                remapping.addClientboundMapping(0x07, -3);   // Registry Data
                remapping.addClientboundMapping(0x08, 0x45); // Remove Resource Pack
                remapping.addClientboundMapping(0x09, 0x46); // Add Resource Pack
                remapping.addClientboundMapping(0x0A, 0x6B); // Store Cookie
                remapping.addClientboundMapping(0x0B, 0x73); // Transfer
                remapping.addClientboundMapping(0x0C, -3);   // Feature Flags
                remapping.addClientboundMapping(0x0D, 0x78); // Update Tags
                remapping.addClientboundMapping(0x0E, -3);   // Known Packs

                remapping.addServerboundMapping(0x00, 0x0A); // Client Settings
                remapping.addServerboundMapping(0x01, 0x11); // Cookie Response
                remapping.addServerboundMapping(0x02, 0x12); // Plugin Message
                remapping.addServerboundMapping(0x03, -2);   // Finish Configuration
                remapping.addServerboundMapping(0x04, 0x18); // Keep Alive
                remapping.addServerboundMapping(0x05, 0x27); // Pong
                remapping.addServerboundMapping(0x06, 0x2B); // Resource Pack Response
                remapping.addServerboundMapping(0x07, -3);   // Known Packs
            },
            ProtocolConstants.MINECRAFT_1_20_5
    ),
    MINECRAFT_1_21(
            (remapping) -> {
                remapping.addClientboundMapping(0x00, 0x16); // Cookie Request
                remapping.addClientboundMapping(0x01, 0x19); // Plugin Message
                remapping.addClientboundMapping(0x02, 0x1D); // Disconnect
                remapping.addClientboundMapping(0x03, -2);   // Finish Configuration
                remapping.addClientboundMapping(0x04, 0x26); // Keep Alive
                remapping.addClientboundMapping(0x05, 0x35); // Ping
                remapping.addClientboundMapping(0x07, -3);   // Registry Data
                remapping.addClientboundMapping(0x08, 0x45); // Remove Resource Pack
                remapping.addClientboundMapping(0x09, 0x46); // Add Resource Pack
                remapping.addClientboundMapping(0x0A, 0x6B); // Store Cookie
                remapping.addClientboundMapping(0x0B, 0x73); // Transfer
                remapping.addClientboundMapping(0x0C, -3);   // Feature Flags
                remapping.addClientboundMapping(0x0D, 0x78); // Update Tags
                remapping.addClientboundMapping(0x0E, -3);   // Known Packs

                remapping.addServerboundMapping(0x00, 0x0A); // Client Settings
                remapping.addServerboundMapping(0x01, 0x11); // Cookie Response
                remapping.addServerboundMapping(0x02, 0x12); // Plugin Message
                remapping.addServerboundMapping(0x03, -2);   // Finish Configuration
                remapping.addServerboundMapping(0x04, 0x18); // Keep Alive
                remapping.addServerboundMapping(0x05, 0x27); // Pong
                remapping.addServerboundMapping(0x06, 0x2B); // Resource Pack Response
                remapping.addServerboundMapping(0x07, -3);   // Known Packs
            },
            ProtocolConstants.MINECRAFT_1_21
    );

    private static final Map<Integer, CSPacketIDRemapping> protocolMap = new HashMap<>();

    static {
        for (CSPacketIDRemapping remapping : values()) {
            for (int version : remapping.protocolVersions) {
                protocolMap.put(version, remapping);
            }
        }
    }

    public static CSPacketIDRemapping getByProtocolVersion(int version) {
        return protocolMap.get(version);
    }

    private final int[] protocolVersions;
    private final Map<Integer, Integer> clientboundC2P = new HashMap<>();
    private final Map<Integer, Integer> clientboundP2C = new HashMap<>();
    private final Map<Integer, Integer> serverboundC2P = new HashMap<>();
    private final Map<Integer, Integer> serverboundP2C = new HashMap<>();

    CSPacketIDRemapping(Consumer<CSPacketIDRemapping> filler, int... protocolVersions) {
        this.protocolVersions = protocolVersions;
        filler.accept(this);
    }

    private void addClientboundMapping(int configuration, int play) {
        clientboundC2P.put(configuration, play);
        if (play >= 0) {
            clientboundP2C.put(play, configuration);
        }
    }

    private void addServerboundMapping(int configuration, int play) {
        serverboundC2P.put(configuration, play);
        if (play >= 0) {
            serverboundP2C.put(play, configuration);
        }
    }

    public int clientboundC2P(int packetId) {
        return clientboundC2P.getOrDefault(packetId, -1);
    }

    public int clientboundP2C(int packetId) {
        return clientboundP2C.getOrDefault(packetId, -1);
    }

    public int serverboundC2P(int packetId) {
        return serverboundC2P.getOrDefault(packetId, -1);
    }

    public int serverboundP2C(int packetId) {
        return serverboundP2C.getOrDefault(packetId, -1);
    }
}
