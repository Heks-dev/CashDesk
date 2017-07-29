package ua.org.goservice.cashdesk.model.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    private static final String MD5_ALGORITHM = "MD5";

    public String generate(String arg) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(MD5_ALGORITHM);
            messageDigest.reset();
            messageDigest.update(arg.getBytes());
            byte[] digest = messageDigest.digest();
            BigInteger dst = new BigInteger(1, digest);
            String hex = dst.toString(16);
            System.out.println(hex);
            StringBuilder builder = new StringBuilder();
            while (builder.length() < 32) {
                builder.insert(0, "0" + hex);
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException();
        }
    }
}
