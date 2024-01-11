package com.semester.app;

import java.io.*;
import java.util.zip.*;

public class Archiving {
    public static void archive(String filename, String archiveFilename) throws IOException {
        FileOutputStream fos = new FileOutputStream(archiveFilename);
        ZipOutputStream zos = new ZipOutputStream(fos);
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        zos.closeEntry();
        fis.close();
        zos.close();
        fos.close();
    }

    public static void dearchive(String archiveFilename, String outpath) throws IOException {
        ZipInputStream zis = new ZipInputStream(new FileInputStream(archiveFilename));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = new File(outpath);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = zis.read(bytes)) >= 0) {
                fos.write(bytes, 0, length);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.close();
    }
}
