package com.ajitgeorge.flags;

import com.google.common.collect.Lists;
import org.slf4j.Logger;

import java.util.List;

public class Slf4jDeferredLogger implements DeferredLogger {

    private static Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(Flags.class);

    private final List<Event> events = Lists.newArrayList();

    @Override
    public void debug(String fmt, Object... params) {
        events.add(new Event(Level.DEBUG, fmt, params));
    }

    @Override
    public void info(String fmt, Object... params) {
        events.add(new Event(Level.DEBUG, fmt, params));
    }

    @Override
    public void undeferLogging() {
        for (Event event : events) {
            switch (event.level) {
                case DEBUG:
                    slf4jLogger.debug(event.fmt, event.params);
                    break;
                case INFO:
                    slf4jLogger.info(event.fmt, event.params);
                    break;
            }
        }
    }

    private static enum Level {
        DEBUG,
        INFO
    }

    private static class Event {

        public final Level level;
        public final String fmt;
        public final Object[] params;

        public Event(Level level, String fmt, Object[] params) {
            this.level = level;
            this.fmt = fmt;
            this.params = params;
        }
    }
}
