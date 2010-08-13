package com.ajitgeorge.flags;

import com.google.common.base.Predicate;
import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Lists.newArrayList;

/**
 * TODO - generate usage
 * TODO - don't require '='
 * TODO - support booleans
 * TODO - support yes/no, true/false for booleans
 * TODO = support unspecified value for boolean flag means true
 * TODO - RequiredFlag
 */
public class Flags {
    private Map<Class, Parser> parsers = Parsers.all();
    private Set<Field> flaggedFields;

    public Flags(String packagePrefix) {
        Reflections reflections = new Reflections(packagePrefix, new FieldAnnotationsScanner());
        flaggedFields = reflections.getFieldsAnnotatedWith(Flag.class);
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
            } else {
                nonFlagArguments.add(s);
            }
        }
        return nonFlagArguments;
    }

    public void parse(Properties... propertiesInstances) {
        for (Properties properties : propertiesInstances) {
            for (String name : properties.stringPropertyNames()) {
                set(name, properties.getProperty(name));
            }
        }
    }

    private void set(final String name, String value) {
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
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
