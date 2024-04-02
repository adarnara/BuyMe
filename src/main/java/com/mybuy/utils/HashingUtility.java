package com.mybuy.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashingUtility {
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        return bytesToHex(salt);
    }



    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(hexStringToByteArray(salt));

        byte[] hashedPassword = md.digest(password.getBytes());
        return bytesToHex(hashedPassword);
    }




    public static boolean checkPassword(String providedPassword, String storedHash, String storedSalt) throws NoSuchAlgorithmException {
        String providedPasswordHashed = hashPassword(providedPassword, storedSalt);
        return providedPasswordHashed.equals(storedHash);
    }

    private static byte[] hexStringToByteArray(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(hex.substring(index, index + 2), 16);
            bytes[i] = (byte) v;
        }
        return bytes;
    }


    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
