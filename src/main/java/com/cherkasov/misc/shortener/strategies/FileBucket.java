package com.cherkasov.misc.shortener.strategies;


import com.cherkasov.misc.shortener.Helper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by hawk on 04.11.2016.
 */


public class FileBucket {
    Path path;

    public FileBucket() {

        init();
    }


    private void init() {

        String property = "java.io.tmpdir";

        String tempDir = System.getProperty(property);
//        String tempDir = "c:\\java\\tmp";

        try {
            this.path = Files.createTempFile(Paths.get(tempDir), Helper.generateRandomString(), ".tmp");
        } catch (IOException e) {

            Helper.printMessage(e.getMessage());
        }

        File file = path.toFile();
        if (file.exists()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                Helper.printMessage(e.getMessage());
            }
        }

        file.deleteOnExit();

    }

    public long getFileSize() {

        try {
            return Files.size(path);
        } catch (IOException e) {
            Helper.printMessage(e.getMessage());
        }
        return 0;
    }

    public void putEntry(Entry entry) {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()));

            //  for (Entry e = entry; e != null; e = e.next)
            {
                oos.writeObject(entry);
                oos.flush();

            }
            oos.close();
        } catch (IOException ex) {
            Helper.printMessage(ex.getMessage());
        }

    }

    public Entry getEntry() {

        if (getFileSize() == 0) return null;

        Entry entry = null;

        try {

            ObjectInputStream oin = new ObjectInputStream(new FileInputStream(path.toFile()));
            entry = (Entry) oin.readObject();
            oin.close();

        } catch (ClassNotFoundException | IOException e) {
            Helper.printMessage(e.getMessage());
        }

        return entry;
    }

    public void remove() {

        try {
            Files.delete(path);
        } catch (IOException e) {
            Helper.printMessage(e.getMessage());
        }
    }
}

/*9.1.	Создай класс FileBucket в пакете strategies.
9.2.	Добавь в класс поле Path path. Это будет путь к файлу.
9.3.	Добавь в класс конструктор, он должен:
9.3.1.	Инициализировать path временным файлом. Файл должен быть размещен
в директории для временных файлов и иметь случайное имя. Подсказка:
Files.createTempFile.
9.3.2.	Создавать новый файл, используя path. Если такой файл уже есть, то
заменять его.
9.3.3.	Обеспечивать удаление файла при выходе из программы. Подсказка:
deleteOnExit().

9.4.	Добавь в класс методы:
9.4.1.	long getFileSize(), он должен возвращать размер файла на который
указывает path.
9.4.2.	void putEntry(Entry entry) - должен сериализовывать переданный entry в
файл. Учти, каждый entry может содержать еще один entry.
9.4.3.	Entry getEntry() - должен забирать entry из файла. Если файл имеет нулевой
размер, вернуть null.
9.4.4.	void remove() – удалять файл на который указывает path.
Конструктор и методы не должны кидать исключения.*/