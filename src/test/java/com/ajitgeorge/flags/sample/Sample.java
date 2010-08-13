package com.ajitgeorge.flags.sample;

import com.ajitgeorge.flags.Flag;

public class Sample {

    @Flag("string")
    public static String stringFlag = "default";

    @Flag("unsetString")
    public static String unsetStringFlag = "default";

    @Flag("undefaultedString")
    public static String undefaultedStringFlag;

    @Flag("int")
    public static int intFlag;

    @Flag("unsetInt")
    public static int unsetIntFlag;

    @Flag("integer")
    public static Integer integerFlag;

    @Flag("unsetInteger")
    public static Integer unsetIntegerFlag;
}
