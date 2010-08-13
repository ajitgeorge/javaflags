package com.ajitgeorge.flags.sample;

import com.ajitgeorge.flags.Flag;

public class Both {
    @Flag("fromargv")
    public static String fromArgvFlag;

    @Flag("fromproperties")
    public static String fromPropertiesFlag;
}
