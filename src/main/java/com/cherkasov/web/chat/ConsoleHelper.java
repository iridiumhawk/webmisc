package com.cherkasov.web.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by hawk on 14.07.2016.
 */
public class ConsoleHelper {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() {
//        String out = "";

        while (true) {

            try {
                String out = reader.readLine();
                return out;
            } catch (IOException e) {
                writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            }

        }

    }

    public static int readInt() {

        writeMessage("Введите число:");

        while (true) {

            try {
                int intInput = Integer.parseInt(readString());
                return intInput;
            } catch (NumberFormatException e) {
                writeMessage("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
            }

        }


    }


}
