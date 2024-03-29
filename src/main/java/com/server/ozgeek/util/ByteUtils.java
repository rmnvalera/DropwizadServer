package com.server.ozgeek.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteUtils {

    public static byte[] combine(byte[]... elements) {
        try {
            int sum = 0;

            for (byte[] element : elements) {
                sum += element.length;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream(sum);

            for (byte[] element : elements) {
                baos.write(element);
            }

            return baos.toByteArray();
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }

    public static byte[] truncate(byte[] element, int length) {
        byte[] result = new byte[length];
        System.arraycopy(element, 0, result, 0, result.length);

        return result;
    }

}