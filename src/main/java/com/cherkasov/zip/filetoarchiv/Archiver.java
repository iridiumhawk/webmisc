package com.cherkasov.zip.filetoarchiv;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* Добавление файла в архив

*/
public class Archiver {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) return;

        String fileToZipPath = args[0]; //Первый аргумент - полный путь к файлу fileName.
        String zipArchiveFile = args[1];  //Второй аргумент - путь к zip-архиву.

        String fileToZipName = new File(fileToZipPath).getName();

        FileInputStream zipInputStream = new FileInputStream(zipArchiveFile);
        ZipInputStream zipInputFile = new ZipInputStream(zipInputStream);

        Map<ZipEntry, ByteArrayOutputStream> mapZip = new HashMap<>();

        ZipEntry entry = null;

        while ((entry = zipInputFile.getNextEntry()) != null) {
//            if (entry.getSize() == 0) continue;
            final byte[] buf = new byte[(int) entry.getSize()];
            int length;

            // Создать поток вывода
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            if (entry.getSize() != 0) {
                while ((length = zipInputFile.read(buf, 0, buf.length)) >= 0) {

                    outputStream.write(buf);
                }
            }
            outputStream.close();

            mapZip.put(entry, outputStream);

            zipInputFile.closeEntry();
        }

        zipInputFile.close();

/*
        for (ZipEntry name : mapZip.keySet()) {
            System.out.println(name.getName());
        }
*/

//        return;

        FileOutputStream zipOutputStream = new FileOutputStream(zipArchiveFile);
        ZipOutputStream zipOutputFile = new ZipOutputStream(zipOutputStream);

        for (ZipEntry entryOut : mapZip.keySet()) {

            if (entryOut.getName().equals(fileToZipName)) {
                zipOutputFile.putNextEntry(new ZipEntry(fileToZipName));
                Files.copy(new File(fileToZipPath).toPath(), zipOutputFile);
                zipOutputFile.closeEntry();
                continue;
            }

            zipOutputFile.putNextEntry(entryOut);
//                zipOutputFile.putNextEntry(new ZipEntry(entryOut.getName()));


            zipOutputFile.write(mapZip.get(entryOut).toByteArray());

            zipOutputFile.closeEntry();

        }


        zipOutputFile.flush();
        zipOutputFile.close();
    }
}
