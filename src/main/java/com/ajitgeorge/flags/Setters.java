package com.ajitgeorge.flags;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class Setters {
    public static Map<Class, Setter> all() {
        Map<Class, Setter> all = newHashMap();

        all.put(String.class, new Setter() {
            @Override
            public void set(Field field, String stringValue) throws IllegalAccessException {
                field.set(null, stringValue);
            }
        });

        all.put(int.class, new Setter() {
            @Override
            public void set(Field field, String stringValue) throws IllegalAccessException {
                field.set(null, Integer.parseInt(stringValue));
            }
        });

        all.put(Integer.class, new Setter() {
            @Override
            public void set(Field field, String stringValue) throws IllegalAccessException {
                field.set(null, new Integer(stringValue));
            }
        });

        all.put(BigDecimal.class, new Setter() {
            @Override
            public void set(Field field, String stringValue) throws IllegalAccessException {
                field.set(null, new BigDecimal(stringValue));
            }
        });

        return all;
    }
}
