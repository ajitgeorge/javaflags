package com.drw.flags;

import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Iterables.getOnlyElement;
import static junit.framework.Assert.assertEquals;

public class FlagsTests {
    @Test
    public void shouldParseArgumentsForFlagValues() {
        assertEquals("default", Sample.stringFlag);

        Flags flags = new Flags("com.drw.flags");
        List<String> arguments = flags.parse(new String[]{"--string=something", "anonflagvalue"});

        assertEquals(1, arguments.size());
        assertEquals("anonflagvalue", getOnlyElement(arguments));

        assertEquals("something", Sample.stringFlag);
    }
}
