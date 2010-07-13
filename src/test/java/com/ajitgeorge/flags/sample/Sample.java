package com.ajitgeorge.flags.sample;

import com.ajitgeorge.flags.Flag;

public class Sample {

    @Flag("string")
    public static String stringFlag = "default";

    @Flag("unsetString")
    public static String unsetStringFlag = "default";
}
