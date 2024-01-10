package com.semester.app;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

public class encrypting {
    private static final String ALGORITHM = "RSA";

    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(2048);
        return keyGen.generateKeyPair();
    }

    void encrypt(String inputFilePath, String encryptedFilePath, PublicKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] keyBytes = key.getEncoded();

        try (FileInputStream fis = new FileInputStream(inputFilePath);
                FileOutputStream fos = new FileOutputStream(encryptedFilePath)) {

            // Write the key bytes to the beginning of the file
            fos.write(keyBytes);

            // Encrypt the file content and write it to the file
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] encryptedBytes = cipher.doFinal(buffer, 0, bytesRead);
                fos.write(encryptedBytes);
            }
        }
    }

    void decrypt(String encryptedFilePath, String decryptedFilePath, PrivateKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(encryptedFilePath);
                FileOutputStream fos = new FileOutputStream(decryptedFilePath)) {

            // Read the key bytes from the beginning of the file
            byte[] keyBytes = new byte[2048];
            fis.read(keyBytes);

            // Initialize the decryption cipher with the loaded key bytes
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PublicKey publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            // Decrypt the file content and write it to the file
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                byte[] decryptedBytes = cipher.doFinal(buffer, 0, bytesRead);
                fos.write(decryptedBytes);
            }
        }
    }
}
