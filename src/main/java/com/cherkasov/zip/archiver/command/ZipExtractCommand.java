package com.cherkasov.zip.archiver.command;


import com.cherkasov.zip.archiver.ConsoleHelper;
import com.cherkasov.zip.archiver.ZipFileManager;
import com.cherkasov.zip.archiver.exception.*;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Распаковка архива.");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите путь для распаковки:");
            Path destinationPath = Paths.get(ConsoleHelper.readString());
            zipFileManager.extractAll(destinationPath);

            ConsoleHelper.writeMessage("Архив был распакован.");

        } catch (PathIsNotFoundException e) {
            ConsoleHelper.writeMessage("Неверный путь для распаковки.");
        }
    }
}
