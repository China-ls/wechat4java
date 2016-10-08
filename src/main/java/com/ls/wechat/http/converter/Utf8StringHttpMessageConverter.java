package com.ls.wechat.http.converter;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

public class Utf8StringHttpMessageConverter extends StringHttpMessageConverter {

    public static final Charset DEFAULT_CHARSET_UTF8 = Charset.forName("UTF-8");

    private boolean isWriteAcceptCharset = true;

    public Utf8StringHttpMessageConverter() {
        this(DEFAULT_CHARSET);
    }

    public Utf8StringHttpMessageConverter(Charset defaultCharset) {
        super(defaultCharset);
    }

    public void setWriteAcceptCharset(boolean writeAcceptCharset) {
        super.setWriteAcceptCharset(writeAcceptCharset);
        isWriteAcceptCharset = writeAcceptCharset;
    }

    @Override
    protected void writeInternal(String s, HttpOutputMessage outputMessage) throws IOException {
        if (isWriteAcceptCharset) {
            outputMessage.getHeaders().setAcceptCharset(getAcceptedCharsets());
        }
        Charset charset = getContentTypeCharset(outputMessage.getHeaders().getContentType());
        FileCopyUtils.copy(s, new OutputStreamWriter(outputMessage.getBody(), charset));
    }

    private Charset getContentTypeCharset(MediaType contentType) {
        if (contentType != null && contentType.getCharSet() != null) {
            return contentType.getCharSet();
        } else {
            return DEFAULT_CHARSET_UTF8;
        }
    }
}
