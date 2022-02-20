package agh.biblioteka.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

public class PasswordManagement {

    private static final Random random = new Random();

    public static String hashPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        String hashedPassword;

        md.update(salt.getBytes());
        byte[] bytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        hashedPassword = sb.toString();

        return hashedPassword;
    }

    public static String createSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    public static String generateRandomPassword() {
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            if (random.nextBoolean()) {
                password.append((char) ((int) (Math.random() * 1000) % 26 + 97));
            } else {
                password.append(random.nextInt(10));
            }
        }

        return password.toString();
    }

}
