package com.ajitgeorge.flags;

import com.ajitgeorge.flags.doublydefined.First;
import com.ajitgeorge.flags.doublydefined.Second;
import com.ajitgeorge.flags.properties.Properties;
import com.ajitgeorge.flags.sample.Argv;
import com.ajitgeorge.flags.sample.Both;
import com.ajitgeorge.flags.sample.UnknownType;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FlagsTests {

    @Test
    public void shouldHandleStringsFromArgv() {
        assertEquals("default", Argv.stringFlag);
        assertEquals("default", Argv.unsetStringFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        List<String> arguments = flags.parse("--string=something", "anonflagvalue");

        assertEquals(1, arguments.size());
        assertEquals("anonflagvalue", getOnlyElement(arguments));

        assertEquals("something", Argv.stringFlag);
        assertEquals("default", Argv.unsetStringFlag);
        assertNull(Argv.undefaultedStringFlag);

        assertEquals("something", flags.getProperty("string"));
    }

    @Test
    public void shouldHandleStringsFromProperties() {
        assertEquals("default", Properties.stringFlag);
        assertEquals("default", Properties.unsetStringFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.properties");
        flags.parse(properties("string", "something"));

        assertEquals("something", Properties.stringFlag);
        assertEquals("default", Properties.unsetStringFlag);
        assertNull(Properties.undefaultedStringFlag);

        assertEquals("something", flags.getProperty("string"));
    }

    @Test
    public void shouldHandleIntegersFromArgv() {
        assertEquals(0, Argv.intFlag);
        assertEquals(0, Argv.unsetIntFlag);
        assertNull(Argv.integerFlag);
        assertNull(Argv.unsetIntegerFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        flags.parse("--int=42", "--integer=612");

        assertEquals(42, Argv.intFlag);
        assertEquals(0, Argv.unsetIntFlag);
        assertEquals(new Integer(612), Argv.integerFlag);
        assertNull(Argv.unsetIntegerFlag);
    }

    @Test
    public void shouldHandleIntegersFromProperties() {
        assertEquals(0, Properties.intFlag);
        assertEquals(0, Properties.unsetIntFlag);
        assertNull(Properties.integerFlag);
        assertNull(Properties.unsetIntegerFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.properties");
        flags.parse(properties("int", "42", "integer", "612"));

        assertEquals(42, Properties.intFlag);
        assertEquals(0, Properties.unsetIntFlag);
        assertEquals(new Integer(612), Properties.integerFlag);
        assertNull(Properties.unsetIntegerFlag);
    }

    @Test
    public void shouldHandleDoublesFromArgv() {
        assertEquals(0, Argv.doubleFlag, 0.0);
        assertEquals(0, Argv.unsetDoubleFlag, 0.0);
        assertNull(Argv.doubleClassFlag);
        assertNull(Argv.unsetDoubleClassFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        flags.parse("--double=31.42", "--doubleClass=545.612");

        assertEquals(31.42, Argv.doubleFlag, 0.0);
        assertEquals(0.0, Argv.unsetDoubleFlag, 0.0);
        assertEquals(new Double(545.612), Argv.doubleClassFlag);
        assertNull(Argv.unsetDoubleClassFlag);
    }

    @Test
    public void shouldHandleDoublesFromProperties() {
        assertEquals(0, Properties.doubleFlag, 0.0);
        assertEquals(0, Properties.unsetDoubleFlag, 0.0);
        assertNull(Properties.doubleClassFlag);
        assertNull(Properties.unsetDoubleClassFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.properties");
        flags.parse(properties("double", "31.42", "doubleClass", "545.612"));

        assertEquals(31.42, Properties.doubleFlag, 0.0);
        assertEquals(0.0, Properties.unsetDoubleFlag, 0.0);
        assertEquals(new Double(545.612), Properties.doubleClassFlag);
        assertNull(Properties.unsetDoubleClassFlag);
    }

    @Test
    public void shouldHandleBigDecimalsFromArgv() {
        assertNull(Argv.bigDecimalFlag);
        assertNull(Argv.unsetBigDecimalFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        flags.parse("--bigdecimal=42.480");

        assertEquals(new BigDecimal("42.480"), Argv.bigDecimalFlag);
        assertNull(Argv.unsetBigDecimalFlag);
    }

    @Test
    public void shouldHandleBigDecimalsFromProperties() {
        assertNull(Properties.bigDecimalFlag);
        assertNull(Properties.unsetBigDecimalFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.properties");
        flags.parse(properties("bigdecimal", "42.480"));

        assertEquals(new BigDecimal("42.480"), Properties.bigDecimalFlag);
        assertNull(Properties.unsetBigDecimalFlag);
    }

    @Test
    public void shouldHandleBooleansFromArgv() {
        assertFalse(Argv.trueFlag);
        assertFalse(Argv.mixedCaseTrueFlag);
        assertTrue(Argv.falseFlag);
        assertTrue(Argv.mixedCaseFalseFlag);
        assertFalse(Argv.unsetBooleanFlag);
        assertNull(Argv.booleanClassFlag);
        assertNull(Argv.unsetBooleanClassFlag);
        assertFalse(Argv.yesFlag);
        assertTrue(Argv.noFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        flags.parse("--true=true", "--mixedCaseTrue=tRuE", "--false=false", "--mixedCaseFalse=fAlSe", "--booleanClass=true", "--yes=YeS", "--no=nO");

        assertTrue(Argv.trueFlag);
        assertTrue(Argv.mixedCaseTrueFlag);
        assertFalse(Argv.falseFlag);
        assertFalse(Argv.mixedCaseFalseFlag);
        assertFalse(Argv.unsetBooleanFlag);
        assertEquals(Boolean.TRUE, Argv.booleanClassFlag);
        assertNull(Argv.unsetBooleanClassFlag);
        assertTrue(Argv.yesFlag);
        assertFalse(Argv.noFlag);
    }

    @Test
    public void shouldHandleBooleansFromProperties() {
        assertFalse(Properties.trueFlag);
        assertFalse(Properties.mixedCaseTrueFlag);
        assertTrue(Properties.falseFlag);
        assertTrue(Properties.mixedCaseFalseFlag);
        assertFalse(Properties.unsetBooleanFlag);
        assertNull(Properties.booleanClassFlag);
        assertNull(Properties.unsetBooleanClassFlag);
        assertFalse(Properties.yesFlag);
        assertTrue(Properties.noFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.properties");
        flags.parse(properties("true", "true", "mixedCaseTrue", "tRuE", "false", "false", "mixedCaseFalse", "fAlSe", "booleanClass", "true", "yes", "yEs", "no", "No"));

        assertTrue(Properties.trueFlag);
        assertTrue(Properties.mixedCaseTrueFlag);
        assertFalse(Properties.falseFlag);
        assertFalse(Properties.mixedCaseFalseFlag);
        assertFalse(Properties.unsetBooleanFlag);
        assertEquals(Boolean.TRUE, Properties.booleanClassFlag);
        assertNull(Properties.unsetBooleanClassFlag);
        assertTrue(Properties.yesFlag);
        assertFalse(Properties.noFlag);
    }

    @Test
    public void shouldAllowMultipleInstances() {
        Flags flags = new Flags("com.ajitgeorge.flags.doublydefined");
        flags.parse("--doublydefined=something");

        assertEquals("something", First.doublyDefined);
        assertEquals("something", Second.doublyDefined);
    }

    @Test
    public void shouldHandleUnknownTypesGracefully() {
        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        try {
            flags.parse("--unknownType=value");
            fail("should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains(UnknownType.class.getName()));
        }
    }

    @Test
    public void shouldParseEverythingInOneCall() {
        assertNull(Both.fromArgvFlag);
        assertNull(Both.fromPropertiesFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        List<String> arguments = flags.parse(new java.util.Properties[] {properties("fromproperties", "properties")},
                new String[]{"--fromargv=argv", "anonflagvalue"});

        assertEquals(1, arguments.size());
        assertEquals("anonflagvalue", getOnlyElement(arguments));

        assertEquals("argv", Both.fromArgvFlag);
        assertEquals("properties", Both.fromPropertiesFlag);
        
    }

    @Test
    public void shouldParseFilenamesAsPropertiesFiles() {
        assertNull(Both.fromFileFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        List<String> arguments = flags.parse("src/test/test.properties", "anonflagvalue");

        assertEquals(1, arguments.size());
        assertEquals("anonflagvalue", getOnlyElement(arguments));

        assertEquals("loaded from file", Both.fromFileFlag);
    }

    @Test
    public void successiveDefsShouldOverride() {
        assertNull(Both.overriddenFlag);

        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        flags.parse("src/test/underride.properties", "--overridden=new value");

        assertEquals("new value", Both.overriddenFlag);

    }

    private java.util.Properties properties(String... defs) {
        java.util.Properties properties = new java.util.Properties();

        for (int i = 0; i < defs.length; i += 2) {
            properties.setProperty(defs[i], defs[i + 1]);
        }

        return properties;
    }
}
