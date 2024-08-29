package com.example.fms.util;

import java.util.UUID;

public class LoggerUtil {
    public static String generateRequestId() {
        return UUID.randomUUID().toString();
    }
}

