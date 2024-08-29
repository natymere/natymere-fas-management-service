package com.example.fms.configuration;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Getter
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {
    private final String body;

    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        this.body = readBody(request);
    }

    private String readBody(HttpServletRequest request) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            return reader.lines().collect(Collectors.joining("\n"));
        }
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new StringReader(body));
    }

    @Override
    public ServletInputStream getInputStream() {
        return new CachedBodyServletInputStream(body.getBytes(StandardCharsets.UTF_8));
    }

    private static class CachedBodyServletInputStream extends ServletInputStream {
        private final InputStream inputStream;

        public CachedBodyServletInputStream(byte[] body) {
            this.inputStream = new ByteArrayInputStream(body);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            try {
                return inputStream.available() == 0;
            } catch (IOException e) {
                return true;
            }
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            // No-op
        }
    }
}
