package ch.zhaw.init.its.labs.publickey;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class BigIntegerEncoder {
	public static BigInteger encode(String message) {
		byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
		
		BigInteger ret = BigInteger.ZERO;
		
		for (int i = 0; i < bytes.length; i++) {
			// & 0xFF is necessary because in Java, byte is a signed type(!).
			ret = ret.shiftLeft(8).add(BigInteger.valueOf(bytes[i] & 0xFF));
		}
		
		return ret;
	}
	
	public static String decode(BigInteger encoded) {
		byte[] bytes = new byte[(encoded.bitLength() + 7) / 8];
		
		for (int i = (encoded.bitLength() + 7) / 8 - 1; i >= 0; i--) {
			bytes[i] = (byte) encoded.and(BigInteger.valueOf(255)).intValue();
			encoded = encoded.shiftRight(8);
		}
		
		return new String(bytes, StandardCharsets.UTF_8);
	}
}
