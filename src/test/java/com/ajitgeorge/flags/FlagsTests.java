package com.ajitgeorge.flags;

import com.ajitgeorge.flags.doublydefined.First;
import com.ajitgeorge.flags.doublydefined.Second;
import com.ajitgeorge.flags.sample.Sample;
import com.ajitgeorge.flags.sample.UnknownType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.*;

public class FlagsTests {
    @Test
    public void shouldHandleStrings() {
        assertEquals("default", Sample.stringFlag);
        assertEquals("default", Sample.unsetStringFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        List<String> arguments = flags.parse(new String[] {
                "--string=something",
                "anonflagvalue",
        });

        assertEquals(1, arguments.size());
        assertEquals("anonflagvalue", getOnlyElement(arguments));

        assertEquals("something", Sample.stringFlag);
        assertEquals("default", Sample.unsetStringFlag);
        assertNull(Sample.undefaultedStringFlag);
    }

    @Test
    public void shouldHandleIntegers() {
        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        flags.parse(new String[] {
                "--int=42",
                "--integer=612"
        });

        assertEquals(42, Sample.intFlag);
        assertEquals(0, Sample.unsetIntFlag);
        assertEquals(new Integer(612), Sample.integerFlag);
        assertNull(Sample.unsetIntegerFlag);
    }

    @Test
    public void shouldHandleBigDecimals() {
        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        flags.parse(new String[] {
                "--bigdecimal=42.480",
        });

        assertEquals(new BigDecimal("42.480"), Sample.bigDecimalFlag);
        assertNull(Sample.unsetBigDecimalFlag);        
    }

    @Test
    public void shouldAllowMultipleInstances() {
        Flags flags = new Flags("com.ajitgeorge.flags.doublydefined");
        flags.parse(new String[]{"--doublydefined=something"});

        assertEquals("something", First.doublyDefined);
        assertEquals("something", Second.doublyDefined);
    }

    @Test
    public void shouldHandleUnknownTypesGracefully() {
        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        try {
            flags.parse(new String[]{"--unknownType=value"});
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(UnknownType.class.getName()));
        }
    }
}
