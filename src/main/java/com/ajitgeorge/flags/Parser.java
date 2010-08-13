package com.ajitgeorge.flags;

public interface Parser<T> {
    T parse(String stringValue) throws IllegalAccessException;
}

