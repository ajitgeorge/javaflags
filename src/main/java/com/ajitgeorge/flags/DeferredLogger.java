package com.ajitgeorge.flags;

public interface DeferredLogger {
    void debug(String fmt, Object ... params);
    void info(String fmt, Object ... params);

    void undeferLogging();
}
