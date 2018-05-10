package com.brokepal.nighty.login.core.util;

import java.io.IOException;
import java.util.Base64;

/**
 * Created by chenchao on 2018/5/10.
 */
public class Base64Util {
    final static Base64.Decoder decoder = Base64.getDecoder();
    final static Base64.Encoder encoder = Base64.getEncoder();

    public static byte[] decode(String msg) throws IOException {
        return decoder.decode(msg);
    }
    public static String encode(byte[] msg) {
        return encoder.encodeToString(msg);
    }
}
