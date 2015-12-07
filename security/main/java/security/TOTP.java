package main.java.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.UndeclaredThrowableException;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;


public class TOTP {

    private static final int[] DIGITS_POWER
            // 0 1 2 3 4 5 6 7 8
            = { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

    private TOTP() {
    }


    private static byte[] hmacSha(String crypto, byte[] keyBytes, byte[] text) {
        try {
            Mac hmac;
            hmac = Mac.getInstance(crypto);
            SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
            hmac.init(macKey);
            return hmac.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }


    public static int generateTOTP(String secret, long unixTime, int digits) {
    	byte[] key = secret.getBytes();
    	long timeSlice = unixTime / 30000;
        byte[] msg = ByteBuffer.allocate(8).putLong(timeSlice).array();
        byte[] hash = hmacSha("HmacSHA1", key, msg);

        // put selected bytes into result int
        int offset = hash[hash.length - 1] & 0xf;

        int binary = ((hash[offset] & 0x7f) << 24) | ((hash[offset + 1] & 0xff) << 16) | ((hash[offset + 2] & 0xff) << 8) | (hash[offset + 3] & 0xff);

        int otp = binary % DIGITS_POWER[digits];

        return otp;
    }

    
    public static int[] generateCurrentTOTPValues(String secret) {
    	int[] result = new int[3];
    	long now = System.currentTimeMillis();
    	result[0] = TOTP.generateTOTP(secret, now - 30000 - 1, 6);
    	result[1] = TOTP.generateTOTP(secret, now , 6);
    	result[2] = TOTP.generateTOTP(secret, now + 30000 + 1, 6);
		return result;
    }
}
