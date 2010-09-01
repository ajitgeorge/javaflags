package com.ajitgeorge.flags.sample;

import com.ajitgeorge.flags.Flag;

public class Both {
    @Flag("fromargv")
    public static String fromArgvFlag;

    @Flag("fromproperties")
    public static String fromPropertiesFlag;

    @Flag("fromfile")
    public static String fromFileFlag;

    @Flag("overridden")
    public static String overriddenFlag;
}
