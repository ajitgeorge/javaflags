package com.ajitgeorge.flags.logger;

import com.ajitgeorge.flags.Flags;

public class ImmediateLogger implements Logger {

    private static org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(Flags.class);

    @Override
    public void debug(String fmt, Object... params) {
        slf4jLogger.debug(fmt, params);
    }

    @Override
    public void info(String fmt, Object... params) {
        slf4jLogger.info(fmt, params);
    }

    @Override
    public void undeferLogging() {
    }
}
