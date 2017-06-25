package com.cherkasov;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/*
В метод main первым параметром приходит имя файла, тело которого в кодировке Windows-1251.
В метод main вторым параметром приходит имя файла, в который необходимо записать содержимое первого файла в кодировке UTF-8.
*/
public class CharsetCoding {
    static String win1251TestString = "РќР°СЂСѓС€РµРЅРёРµ РєРѕРґРёСЂРѕРІРєРё РєРѕРЅСЃРѕР»Рё?"; //only for your testing

    public static void main(String[] args) throws IOException {
        String fileIn = args[0];
        String fileOut = args[1];

        FileInputStream fi = new FileInputStream(fileIn);
        FileOutputStream fo = new FileOutputStream(fileOut);


        byte[] buffer = new byte[fi.available()];

        fi.read(buffer);

        Charset utf8 = Charset.forName("UTF-8");
        Charset win1251 = Charset.forName("Windows-1251");


        String sIn = new String(buffer,utf8);
        String sOut= new String(sIn.getBytes(win1251));

        fo.write(sOut.getBytes());

        fi.close();
        fo.close();

        //System.out.println(sOut);
    }
}
