package Kalorienzahler;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Hash {
    private final String hash;

    Hash(char[] passwort) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] messageDigest = new byte[0];
        if (md != null) {
            messageDigest = md.digest(Arrays.toString(passwort).getBytes());
        }
        BigInteger bigInt = new BigInteger(1, messageDigest);
        hash = bigInt.toString(16);
    }

    public String getHash() {
        return hash;
    }
}