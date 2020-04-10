package com.aimbra.paralellus.utils;

import org.springframework.util.Base64Utils;

public abstract class FileUtils {

    public static String getImageString(String stringB64) {
        String[] split = stringB64.split(",");
        return split[split.length -1];
    }

    public static byte[] decodeImage(String imageDataString) {
        return Base64Utils.decodeFromString(imageDataString);
    }

    public static String encodeImage(byte[] arrayOfBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("data:image/jpeg;base64,");
        stringBuffer.append(Base64Utils.encodeToString(arrayOfBytes));
        return stringBuffer.toString();
    }

}
