package ch.zhaw.init.its.labs.publickey;

import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

class TestEncoding {
	private static final String[] stringTests = {
			"Hello", "Hello, World", "This is not a drill",
			"Etwas mit Ümlauten", "Nücht ASCII"
	};
	
	@Test
	void testEncoding() {
		for (String s : stringTests) {
			BigInteger encoded = BigIntegerEncoder.encode(s);
			String newS = BigIntegerEncoder.decode(encoded);
			
			if (!s.equals(newS)) {
				fail("Decoded encoding of \"" + s + "\" is \"" + newS + "\"");
			}
		}
	}

}
