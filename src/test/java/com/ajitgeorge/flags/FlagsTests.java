package com.ajitgeorge.flags;

import com.ajitgeorge.flags.doublydefined.First;
import com.ajitgeorge.flags.doublydefined.Second;
import com.ajitgeorge.flags.sample.Sample;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class FlagsTests {
    @Test
    public void shouldParseArgumentsForFlagValues() {
        assertEquals("default", Sample.stringFlag);
        assertEquals("default", Sample.unsetStringFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        List<String> arguments = flags.parse(new String[] {
                "--string=something",
                "anonflagvalue",
                "--int=42",
                "--integer=612"
        });

        assertEquals(1, arguments.size());
        assertEquals("anonflagvalue", getOnlyElement(arguments));

        assertEquals("something", Sample.stringFlag);
        assertEquals("default", Sample.unsetStringFlag);
        assertNull(Sample.undefaultedStringFlag);

        assertEquals(42, Sample.intFlag);
        assertEquals(0, Sample.unsetIntFlag);
        assertEquals(new Integer(612), Sample.integerFlag);
        assertNull(Sample.unsetIntegerFlag);
    }

    @Test
    public void shouldAllowMultipleInstances() {
        Flags flags = new Flags("com.ajitgeorge.flags.doublydefined");
        flags.parse(new String[]{"--doublydefined=something"});

        assertEquals("something", First.doublyDefined);
        assertEquals("something", Second.doublyDefined);
    }
}
