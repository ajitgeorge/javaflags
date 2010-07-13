package com.drw.flags;

import org.reflections.Reflections;

import java.util.Set;

public class Flags {
    public Set<Class<? extends Flags>> subclasses() {
        Reflections reflections = new Reflections("com.drw.flags");
        return reflections.getSubTypesOf(Flags.class);        
    }
}
