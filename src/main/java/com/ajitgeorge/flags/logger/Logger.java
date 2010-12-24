package com.ajitgeorge.flags.logger;

public interface Logger {
    void debug(String fmt, Object ... params);
    void info(String fmt, Object ... params);

    void undeferLogging();
}
