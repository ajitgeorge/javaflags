package com.ajitgeorge.flags;

import com.google.common.base.Predicate;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.io.Files.newReader;

public class Flags {
    private final Map<Class, Parser> parsers = Parsers.all();
    private final Set<Field> flaggedFields;
    private final Map<String, String> properties = new HashMap<String, String>();
    private final DeferredLogger log;

    public Flags(String packagePrefix) {
        Reflections reflections = new Reflections(packagePrefix, new FieldAnnotationsScanner());
        flaggedFields = reflections.getFieldsAnnotatedWith(Flag.class);
        initializePropertiesMapWithDefaults();
        log = new Slf4jDeferredLogger();
    }

    private void initializePropertiesMapWithDefaults() {
        for (Field field : flaggedFields) {
            try {
                Object value = field.get(null);

                if (value != null) {
                    properties.put(field.getName(), field.get(null).toString());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Field " + field.getName() + " cannot be accessed statically", e);
            }
        }
    }

    public List<String> parse(Properties[] propertiesInstances, String[] argv) {
        parse(propertiesInstances);
        return parse(argv);
    }

    public List<String> parse(String... argv) {
        List<String> nonFlagArguments = newArrayList();

        for (String s : argv) {
            if (s.startsWith("--")) {
                String[] parts = s.split("=", 2);
                final String name = parts[0].substring(2);
                String value = parts[1];

                set(name, value);
            } else if (new File(s).isFile()) {
                parse(loadProperties(s));
            } else {
                nonFlagArguments.add(s);
            }
        }
        return nonFlagArguments;
    }

    private Properties loadProperties(String filename) {
        try {
            Properties properties = new Properties();
            properties.load(newReader(new File(filename), Charset.defaultCharset()));
            for (String property : properties.stringPropertyNames()) {
                log.debug("(from {}) property {}={}", new Object[] { filename, property, properties.getProperty(property)});
            }
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("couldn't load properties from file " + filename, e);
        }
    }

    public void parse(Properties... propertiesInstances) {
        for (Properties properties : propertiesInstances) {
            for (String name : properties.stringPropertyNames()) {
                set(name, properties.getProperty(name));
            }
        }
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public Map<String, String> getAllProperties() {
        return Collections.unmodifiableMap(properties);
    }

    public String getProperty(String key) {
        return properties.get(key);
    }

    public void undeferLogging() {
        log.undeferLogging();
    }

    private void set(final String name, String value) {
        properties.put(name, value);

        Iterable<Field> fields = filter(flaggedFields, new Predicate<Field>() {
            @Override
            public boolean apply(Field elem) {
                return elem.getAnnotation(Flag.class).value().equals(name);
            }
        });
        for (Field field : fields) {
            try {
                Parser parser = parsers.get(field.getType());

                if (parser == null) {
                    throw new IllegalArgumentException("flagged field is of unknown type " + field.getType());
                }

                field.set(null, parser.parse(value));
                log.info("set {} to {}", field, value);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
