package com.cherkasov.misc.shortener.Test;

import com.cherkasov.misc.shortener.strategies.FileStorageStrategy;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by hawk on 06.11.2016.
 */
public class FileStorageStrategyTest {
    FileStorageStrategy fs = new FileStorageStrategy();


    @Test
    public void put() throws Exception {

        fs.put(55552L, "asdfghjkl");

        String value= fs.getValue(55552L);

//        System.out.println(readFromFile.toString());

        assertEquals(value,"asdfghjkl");

    }

}