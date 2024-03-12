package io.siggi.configurationskipper;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationSkipperMeta {
    ConfigurationSkipperMeta() {
    }

    final Map<Integer, byte[]> hashedPackets = new HashMap<>();

    boolean processPacket(ByteBuf buf, int packetId) {
        int reset = buf.readerIndex();
        try {
            int size = buf.writerIndex() - reset;
            byte[] data = new byte[size];
            byte[] existingBytes = hashedPackets.get(packetId);
            if (existingBytes == null || !Arrays.equals(data, existingBytes)) {
                hashedPackets.put(packetId, data);
                return true;
            }
            return false;
        } finally {
            buf.readerIndex(reset);
        }
    }
}
