package com.ls.wechat.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class StreamUtils {

    public static void writeToStream(OutputStream os, String text) {
        writeToStream(os, text, "UTF-8");
    }

    public static void writeToStream(OutputStream os, String text, String charsetname) {
        try {
            os.write(text.getBytes(charsetname));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
