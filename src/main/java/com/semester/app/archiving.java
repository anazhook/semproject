package com.semester.app;

import java.io.*;
import java.util.zip.*;

public class archiving {
    public static void compress(String inputFileName, String outputFileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFileName);
                FileOutputStream fos = new FileOutputStream(outputFileName);
                GZIPOutputStream gzipOS = new GZIPOutputStream(fos)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                gzipOS.write(buffer, 0, len);
            }
        }
    }

    public static void decompress(String inputFileName, String outputFileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(inputFileName);
                GZIPInputStream gzipIS = new GZIPInputStream(fis);
                FileOutputStream fos = new FileOutputStream(outputFileName)) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipIS.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        }
    }
}
