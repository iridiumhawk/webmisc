package com.cherkasov;

import java.io.ByteArrayOutputStream;

/* Генератор паролей
Требования к паролю:
1) 8 символов
2) только цифры и латинские буквы разного регистра
3) обязательно должны присутствовать цифры, и буквы разного регистра
Все сгенерированные пароли должны быть уникальные.

*/
public class PassGenerator {
    public static void main(String[] args) {


            ByteArrayOutputStream password = getPassword();
            System.out.println(password.toString());


    }

    public static ByteArrayOutputStream getPassword() {
        ByteArrayOutputStream arr = new ByteArrayOutputStream();
        final String ALPHABET = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
        boolean correctPassword = false;

        while (!correctPassword) {

            for (int i = 0; i < 8; i++) {

                char simbol = ALPHABET.charAt((int) (Math.random() * ALPHABET.length()));

                arr.write(simbol);
            }

            String pass = arr.toString();

            int cipher = 0;
            int lowCase = 0;
            int upperCase = 0;

            for (int i = 0; i < pass.length(); i++) {
                if (Character.isLowerCase(pass.charAt(i))) lowCase++;
                if (Character.isUpperCase(pass.charAt(i))) upperCase++;
                if (Character.isDigit(pass.charAt(i))) cipher++;
            }

            if (cipher == 0 || lowCase == 0 || upperCase == 0) {
                arr.reset();
            } else {
                correctPassword = true;
            }

        }

        return arr;
    }

}
