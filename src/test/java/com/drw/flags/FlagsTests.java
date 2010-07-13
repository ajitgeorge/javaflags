package com.drw.flags;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class FlagsTests {
    @Test
    public void someTest() {
        assertTrue(new Flags().subclasses().isEmpty());        
    }
}
