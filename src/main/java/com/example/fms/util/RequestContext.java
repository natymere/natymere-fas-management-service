package com.example.fms.util;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class RequestContext {
    private static final ThreadLocal<Map<String, Object>> contextHolder = ThreadLocal.withInitial(HashMap::new);

    public static <T> void set(String key, T value) {
        contextHolder.get().put(key, value);
    }

    public static <T> T get(String key, Class<T> type) {
        Object o = contextHolder.get().get(key);
        return type.cast(o);
    }

    public static void remove(String key) {
        contextHolder.get().remove(key);
    }

    public static void clear() {
        contextHolder.get().clear();
    }

    public static void setRequestId(String requestId) {
        set("requestId", requestId);
    }

    public static String getRequestId() {
        return get("requestId", String.class);
    }

    public static void setRequestStartTime() {
        set("startTime", Instant.now());
    }

    public static Instant getRequestStartTime() {
        return get("startTime", Instant.class);
    }

    public static Long getRequestDuration() {
        Instant requestStartTime = getRequestStartTime();
        if (requestStartTime == null) return null;

        return Duration.between(requestStartTime, Instant.now()).toMillis();
    }

    public static void setBody(String body) {
        set("requestBody", body);
    }

    public static String getBody() {
        return get("requestBody", String.class);
    }
}
