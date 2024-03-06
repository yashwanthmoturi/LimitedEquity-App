package com.yash.MYAPP;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordHashingUtil {

    // Generate a salt using SecureRandom
    public static String generateSalt() {

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash password with salt using SHA-256
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((password + salt).getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Verify password by rehashing and comparing with stored hash
    public static boolean verifyPassword(String password, String salt, String storedHash) {
        String generatedHash = hashPassword(password, salt);
        return generatedHash != null && generatedHash.equals(storedHash);
    }

    public static void main(String[] args) {
        String password = "mySecretPassword";

        // Generate salt and hash the password
        String salt = generateSalt();
        String hashedPassword = hashPassword(password, salt);

        System.out.println("Password: " + password);
        System.out.println("Salt: " + salt);
        System.out.println("Hashed Password: " + hashedPassword);

        // Verify password
        boolean passwordMatch = verifyPassword(password, salt, hashedPassword);
        System.out.println("Password Match: " + passwordMatch);
    }
}
