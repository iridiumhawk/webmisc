package com.cherkasov.misc.shortener;

/**
 * Created by hawk on 04.11.2016.
 */
public class ExceptionHandler {
    public static void log(Exception e) {

        Helper.printMessage(e.getMessage());
    }
}
