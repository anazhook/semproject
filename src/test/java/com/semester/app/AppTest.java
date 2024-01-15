package com.semester.app;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {
    @Test // archiving test
    public void testArchiveAndDearchive() throws Exception {
        Path filepath = Path.of("C:\\Users\\User\\Desktop\\semproject\\somefile.txt");
        Path dearchpath = Path.of("C:\\Users\\User\\Desktop\\semproject\\dearch.txt");

        String filename = "somefile.txt";
        String archiveFilename = "som.zip";
        String dearch = "dearch.txt";
        // Archive the file
        Archiving.archive(filename, archiveFilename);

        // Dearchive the file
        Archiving.dearchive(archiveFilename, dearch);

        assertTrue(Files.mismatch(filepath, dearchpath) == -1);
    }

    @Test
    void testEncryptAndDecryptFile() {
        String inputFile = "input.txt";
        String encryptedFile = "encrypted.enc";
        String decryptedFile = "decrypted.txt";
        String encryptionKey = "MyEncryptionKey1";

        try {
            // Generate a test input file
            generateInputFile(inputFile);

            // Encrypt the input file
            Encrypting.encryptFile(inputFile, encryptedFile, encryptionKey);

            // Decrypt the encrypted file
            Encrypting.decryptFile(encryptedFile, decryptedFile, encryptionKey);

            // Compare the original and decrypted files
            assertTrue(compareFiles(inputFile, decryptedFile));
        } catch (Exception e) {
            fail("An exception occurred during testing: " + e.getMessage());
        } finally {
            // Clean up the generated files
            deleteFile(inputFile);
            deleteFile(encryptedFile);
            deleteFile(decryptedFile);
        }
    }

    @Test
    void testEncryptFileWithInvalidKey() {
        String inputFile = "input.txt";
        String encryptedFile = "encrypted.enc";
        String encryptionKey = "MyEncryptionKey1";

        try {
            // Generate a test input file
            generateInputFile(inputFile);

            // Encrypt the input file
            Encrypting.encryptFile(inputFile, encryptedFile, encryptionKey);

            // Try decrypting with an invalid key
            assertThrows(IllegalArgumentException.class,
                    () -> Encrypting.decryptFile(encryptedFile, "invalid.txt", "InvalidKey123456"));
        } catch (Exception e) {
            fail("An exception occurred during testing: " + e.getMessage());
        } finally {
            // Clean up the generated files
            deleteFile(inputFile);
            deleteFile(encryptedFile);
        }
    }

    @Test
    void testEncryptAndDecryptEmptyFile() {
        String inputFile = "empty.txt";
        String encryptedFile = "encrypted.enc";
        String decryptedFile = "decrypted.txt";
        String encryptionKey = "MyEncryptionKey1";

        try {
            // Generate an empty input file
            Files.createFile(Path.of(inputFile));

            // Encrypt the empty file
            Encrypting.encryptFile(inputFile, encryptedFile, encryptionKey);

            // Decrypt the encrypted file
            Encrypting.decryptFile(encryptedFile, decryptedFile, encryptionKey);

            // Compare the original and decrypted files
            assertTrue(compareFiles(inputFile, decryptedFile));
        } catch (Exception e) {
            fail("An exception occurred during testing: " + e.getMessage());
        } finally {
            // Clean up the generated files
            deleteFile(inputFile);
            deleteFile(encryptedFile);
            deleteFile(decryptedFile);
        }
    }

    @Test
    void testEncryptAndDecryptLargeFile() {
        String inputFile = "largeInput.txt";
        String encryptedFile = "encrypted.enc";
        String decryptedFile = "decrypted.txt";
        String encryptionKey = "MyEncryptionKey1";

        try {
            // Generate a large input file (e.g., 100 MB)
            generateLargeInputFile(inputFile, 100 * 1024 * 1024);

            // Encrypt the large input file
            Encrypting.encryptFile(inputFile, encryptedFile, encryptionKey);

            // Decrypt the encrypted file
            Encrypting.decryptFile(encryptedFile, decryptedFile, encryptionKey);

            // Compare the original and decrypted files
            assertTrue(compareFiles(inputFile, decryptedFile));
        } catch (Exception e) {
            fail("An exception occurred during testing: " + e.getMessage());
        } finally {
            // Clean up the generated files
            deleteFile(inputFile);
            deleteFile(encryptedFile);
            deleteFile(decryptedFile);
        }
    }

    private void generateInputFile(String inputFile) throws IOException {
        // Create a sample input file for testing
        String content = "This is a sample input file.";
        Files.writeString(Path.of(inputFile), content);
    }

    private boolean compareFiles(String file1, String file2) throws IOException {
        // Compare the contents of two files
        String content1 = Files.readString(Path.of(file1));
        String content2 = Files.readString(Path.of(file2));
        return content1.equals(content2);
    }

    private void deleteFile(String file) {
        // Delete a file
        try {
            Files.deleteIfExists(Path.of(file));
        } catch (IOException e) {
            System.err.println("An error occurred while deleting the file: " + e.getMessage());
        }
    }

    private static void generateLargeInputFile(String fileName, long fileSizeInBytes) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            byte[] buffer = new byte[8192];
            long bytesWritten = 0;
            while (bytesWritten < fileSizeInBytes) {
                int bytesToWrite = (int) Math.min(buffer.length, fileSizeInBytes - bytesWritten);
                outputStream.write(buffer, 0, bytesToWrite);
                bytesWritten += bytesToWrite;
            }
        }
    }
}
