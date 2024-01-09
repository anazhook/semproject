package com.semester.app;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    private static final String MESSAGE = "Hello World!";

    public App() {
    }

    public static void main(String[] args) throws Exception {
        encrypting ac = new encrypting(); // ac --- asymmetric cryptography bebe
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // String inpath = reader.readLine();
        // String crpath = reader.readLine();
        // String decrpath = reader.readLine();

        String inpath = "somefile.txt";
        String crpath = "cr.txt";
        String decrpath = "dcr.txt";

        // Generate a new key pair
        KeyPair keyPair = ac.generateKeyPair();

        // Read the input file into a byte array
        byte[] inputBytes = Files.readAllBytes(Paths.get(inpath));

        // Encrypt the input bytes
        byte[] encryptedBytes = ac.encrypt(inputBytes, keyPair.getPublic());

        // Write the encrypted bytes to the output file
        Files.write(Paths.get(crpath), encryptedBytes);

        // Read the encrypted file into a byte array
        byte[] encryptedBytesFromFile = Files.readAllBytes(Paths.get(crpath));

        // Decrypt the encrypted bytes
        byte[] decryptedBytes = ac.decrypt(encryptedBytesFromFile, keyPair.getPrivate());

        // Write the decrypted bytes to the output file
        Files.write(Paths.get(decrpath), decryptedBytes);
    }

    public String getMessage() {
        return MESSAGE;
    }
}
