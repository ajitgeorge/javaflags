package com.ajitgeorge.flags;

interface Parser<T> {
    T parse(String stringValue) throws IllegalAccessException;
}

