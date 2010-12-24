package com.ajitgeorge.flags;

public class DeferredLoggingNonTest {
    public static void main(String[] args) {
        System.out.println("no logging yet");
        Flags flags = new Flags("com.ajitgeorge.flags.sample");
        flags.parse("--string=value", "src/test/test.properties");
        System.out.println("out comes the evil");
        flags.undeferLogging();
    }
}
