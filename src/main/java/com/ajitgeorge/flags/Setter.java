package com.ajitgeorge.flags;

import java.lang.reflect.Field;

public interface Setter {
    void set(Field field, String stringValue) throws IllegalAccessException;
}

