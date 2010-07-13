package com.drw.flags.sample;

import com.drw.flags.Flag;

public class Sample {

    @Flag("string")
    public static String stringFlag = "default";
    public static String unsetStringFlag = "default";
}
