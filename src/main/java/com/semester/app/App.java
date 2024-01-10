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

        System.out.println("enter input and output files names:");
        String inpath = reader.readLine();
        String outpath = reader.readLine();

        String format = "";
        int i = inpath.lastIndexOf('.');
        if (i > 0) {
            format = inpath.substring(i + 1);
        }
        String temppath = "temp." + format;
        // Generate a new key pair
        KeyPair keyPair = ac.generateKeyPair();

        System.out.println("type of input file (1 - plain, 2 - encrypted, 3 - archived):");
        int command = Integer.parseInt(reader.readLine());

        byte[] inputBytes;

        if (command == 1) {
            // Read the input file into a byte array
            inputBytes = Files.readAllBytes(Paths.get(inpath));
            Files.write(Paths.get(temppath), inputBytes);

        }

        else if (command == 2) {
            // Read the encrypted file into a byte array
            byte[] encryptedBytesFromFile = Files.readAllBytes(Paths.get(inpath));
            // Decrypt the encrypted bytes
            ac.decrypt(inpath, outpath, keyPair.getPrivate());
        }

        else if (command == 3) {

        }

        else {
            System.out.println("wrong command!");
        }

        System.out.println("type of output file (1 - plain, 2 - encrypted, 3 - archived):");
        command = Integer.parseInt(reader.readLine());
        inputBytes = Files.readAllBytes(Paths.get(temppath));

        if (command == 1) {
            Files.write(Paths.get(outpath), inputBytes);
        }

        else if (command == 2) {
            // Encrypt the input bytes
            ac.encrypt(temppath, outpath, keyPair.getPublic());
        }

        else if (command == 3) {

        }

        else {
            System.out.println("wrong command!");
        }
    }

    public String getMessage() {
        return MESSAGE;
    }
}