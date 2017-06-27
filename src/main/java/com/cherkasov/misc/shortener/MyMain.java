package com.cherkasov.misc.shortener;


import com.cherkasov.misc.shortener.strategies.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hawk on 04.11.2016.
 */
public class MyMain {
    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {

        Set<Long> result = new HashSet<>();

        for (String value : strings) {

            result.add(shortener.getId(value));
        }

        return result;
    }


    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {

        Set<String> result = new HashSet<>();

        for (Long key : keys) {

            result.add(shortener.getString(key));
        }

        return result;

    }


    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {

        Helper.printMessage(strategy.getClass().getSimpleName());

        Set<String> values = new HashSet<>();

        for (long i = 0; i < elementsNumber; i++) {
            values.add(Helper.generateRandomString());
        }

        Shortener shorter = new Shortener(strategy);

        Long timeCounter = new Date().getTime();

        Set<Long> ids = getIds(shorter, values);

        Helper.printMessage("" + (new Date().getTime() - timeCounter));

        timeCounter = new Date().getTime();

        Set<String> strings = getStrings(shorter, ids);

        Helper.printMessage("" + (new Date().getTime() - timeCounter));

        if (strings.equals(values)) {
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");

        }

    }

    public static void main(String[] args) {
        int dataSize = 10000;

        testStrategy(new HashMapStorageStrategy(), dataSize);
        testStrategy(new OurHashMapStorageStrategy(), dataSize);
        testStrategy(new OurHashBiMapStorageStrategy(), dataSize);
        testStrategy(new HashBiMapStorageStrategy(), dataSize);
        testStrategy(new DualHashBidiMapStorageStrategy(), dataSize);

        testStrategy(new FileStorageStrategy(), 10);

    }
}
