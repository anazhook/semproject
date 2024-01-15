package com.semester.app;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class Encrypting {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 128;

    public static void encryptFile(String inputFile, String outputFile, String encryptionKey) throws Exception {
        byte[] key = encryptionKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        try (InputStream inputStream = new FileInputStream(inputFile);
                OutputStream outputStream = Files.newOutputStream(Path.of(outputFile), StandardOpenOption.CREATE)) {
            outputStream.write(key);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] encryptedBytes = cipher.update(buffer, 0, bytesRead);
                outputStream.write(encryptedBytes);
            }
            byte[] finalBytes = cipher.doFinal();
            outputStream.write(finalBytes);
        }
    }

    public static void decryptFile(String inputFile, String outputFile, String encryptionKey) throws Exception {
        byte[] key = encryptionKey.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, ALGORITHM);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        try (InputStream inputStream = Files.newInputStream(Path.of(inputFile));
                OutputStream outputStream = new FileOutputStream(outputFile)) {
            byte[] fileKey = new byte[key.length];
            inputStream.read(fileKey);
            if (!encryptionKey.equals(new String(fileKey))) {
                throw new IllegalArgumentException("Incorrect encryption key!");
            }

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byte[] decryptedBytes = cipher.update(buffer, 0, bytesRead);
                outputStream.write(decryptedBytes);
            }
            byte[] finalBytes = cipher.doFinal();
            outputStream.write(finalBytes);
        }
    }
}
