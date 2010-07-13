package com.drw.flags;

import com.drw.flags.doublydefined.First;
import com.drw.flags.doublydefined.Second;
import com.drw.flags.sample.Sample;
import org.junit.Test;

import java.util.List;

import static com.google.common.collect.Iterables.getOnlyElement;
import static junit.framework.Assert.assertEquals;

public class FlagsTests {
    @Test
    public void shouldParseArgumentsForFlagValues() {
        assertEquals("default", Sample.stringFlag);
        assertEquals("default", Sample.unsetStringFlag);

        Flags flags = new Flags("com.drw.flags.sample");
        List<String> arguments = flags.parse(new String[]{"--string=something", "anonflagvalue"});

        assertEquals(1, arguments.size());
        assertEquals("anonflagvalue", getOnlyElement(arguments));

        assertEquals("something", Sample.stringFlag);
        assertEquals("default", Sample.unsetStringFlag);
    }

    @Test
    public void shouldAllowMultipleInstances() {
        Flags flags = new Flags("com.drw.flags.doublydefined");
        flags.parse(new String[]{"--doublydefined=something"});

        assertEquals("something", First.doublyDefined);
        assertEquals("something", Second.doublyDefined);
    }
}
