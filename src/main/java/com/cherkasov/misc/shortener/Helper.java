package com.cherkasov.misc.shortener;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by hawk on 04.11.2016.
 */
public class Helper {
    private static SecureRandom random = new SecureRandom();

    public static String generateRandomString() {

        return new BigInteger(130, random).toString(32);
    }

    public static void printMessage(String message) {

        System.out.println(message);
    }

/*    public static void main(String[] args) {

        for (int i=0; i < 10; i++) {
            printMessage(generateRandomString());
        }
    }*/
}

