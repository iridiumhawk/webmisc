package com.cherkasov.zip.filetoarchiv;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/* Разархивируем файл

*/
public class UnArchiver {

    public static void main(String[] args) throws Exception {
        String resultFileName = args[0];
        Path resultFileNamePath = Paths.get(resultFileName);

        String[] fileNamePart = Arrays.copyOfRange(args, 1, args.length);
        Arrays.sort(fileNamePart);

        Path tempZipFile = Files.createTempFile(resultFileNamePath.getParent(),null,null);

        try (FileOutputStream zipOutputStream = new FileOutputStream(tempZipFile.toString()) )
        {
            for (String aFileNamePart : fileNamePart) {

                try (FileInputStream zipInputStream = new FileInputStream(aFileNamePart)) {
                    copyData(zipInputStream, zipOutputStream);
                }
            }

        }

        extractAll(tempZipFile,Paths.get(resultFileName));

//        System.out.println("Ready");
    }

    public static void extractAll(Path inputFile,Path outputFile) throws Exception {

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(inputFile))) {

            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {

                try (OutputStream outputStream = Files.newOutputStream(outputFile)) {
                    copyData(zipInputStream, outputStream);
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        }
    }
    public static void copyData(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }
}
