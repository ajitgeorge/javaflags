package com.ajitgeorge.flags;

import com.ajitgeorge.flags.doublydefined.First;
import com.ajitgeorge.flags.doublydefined.Second;
import com.ajitgeorge.flags.sample.Sample;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;

public class FlagsTests {
    @Test
    public void shouldParseArgumentsForFlagValues() {
        assertEquals("default", Sample.stringFlag);
        assertEquals("default", Sample.unsetStringFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        List<String> arguments = flags.parse(new String[]{"--string=something", "anonflagvalue"});

        assertEquals(1, arguments.size());
        assertEquals("anonflagvalue", getOnlyElement(arguments));

        assertEquals("something", Sample.stringFlag);
        assertEquals("default", Sample.unsetStringFlag);
    }

    @Test
    public void shouldAllowMultipleInstances() {
        Flags flags = new Flags("com.ajitgeorge.flags.doublydefined");
        flags.parse(new String[]{"--doublydefined=something"});

        assertEquals("something", First.doublyDefined);
        assertEquals("something", Second.doublyDefined);
    }
}
