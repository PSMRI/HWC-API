package com.iemr.hwc.utils;

public class UserAgentContext {
	private static final ThreadLocal<String> userAgentHolder = new ThreadLocal<>();

    public static void setUserAgent(String userAgent) {
        userAgentHolder.set(userAgent);
    }

    public static String getUserAgent() {
        return userAgentHolder.get();
    }

    public static void clear() {
        userAgentHolder.remove();
    }

}

