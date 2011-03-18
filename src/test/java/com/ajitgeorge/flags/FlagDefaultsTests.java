package com.ajitgeorge.flags;

import com.ajitgeorge.flags.sample.Argv;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FlagDefaultsTests {
	@Test
	public void shouldSaveAndRestoreDefaultValues() throws Exception {
		assertEquals("default", Argv.stringFlag);
		FlagDefaults saved = new FlagDefaults("com.ajitgeorge.flags.sample").save();

		Argv.stringFlag = "purple toupee";

		saved.restore();
		assertEquals("default", Argv.stringFlag);
	}
}
