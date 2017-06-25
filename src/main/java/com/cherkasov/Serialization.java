package com.cherkasov;

import java.io.*;

/* Переопределение сериализации в потоке

*/
public class Serialization implements Serializable, AutoCloseable {
    transient  private FileOutputStream stream;
    private  String fileName;

    public Serialization(String fileName) throws FileNotFoundException {
        this.stream = new FileOutputStream(fileName);
        this.fileName = fileName;
    }

    public void writeObject(String string) throws IOException {

        stream.write(string.getBytes());
        stream.write("\n".getBytes());
        stream.flush();
        System.out.println("writeFile");

    }

     private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
         System.out.println("writeStream");

//        out.close();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {

        in.defaultReadObject();
        System.out.println("readStream");
     //  this.stream = new FileOutputStream(fileName, true);
//        in.close();
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closing everything!");
        stream.close();
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String fileName = "c:/java/test4.txt";

            FileOutputStream fileOut = new FileOutputStream(fileName);


            Serialization sol = new Serialization(fileName);


            sol.writeObject("Test String 1");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);


            out.writeObject(sol);

        FileInputStream fileIn = new FileInputStream(fileName);

        ObjectInputStream in = new ObjectInputStream(fileIn);
            Serialization loadObj = (Serialization)in.readObject();
        System.out.println(loadObj.fileName);
            loadObj.writeObject("Test string 2");
        out.writeObject(sol);
        loadObj.writeObject("Test string 3");


    }
}
