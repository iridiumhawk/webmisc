package com.cherkasov.misc.shortener.tests;

import com.cherkasov.misc.shortener.Shortener;
import com.cherkasov.misc.shortener.strategies.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hawk on 07.11.2016.
 */
public class FunctionalTest {

    public void testStorage(Shortener shortener) {

        String s1 = "qwertyuiop";
        String s2 = "asdfghjkl";
        String s3 = "qwertyuiop";

        Long id1 = shortener.getId(s1);
        Long id2 = shortener.getId(s2);
        Long id3 = shortener.getId(s3);

        Assert.assertNotEquals(id2,id1);
        Assert.assertNotEquals(id2,id3);
        Assert.assertEquals(id1,id3);

        String s1Expected = shortener.getString(id1);
        String s2Expected = shortener.getString(id2);
        String s3Expected = shortener.getString(id3);

        Assert.assertEquals(s1Expected,s1);
        Assert.assertEquals(s2Expected,s2);
        Assert.assertEquals(s3Expected,s3);

    }

    @Test
    public void testHashMapStorageStrategy() {

        HashMapStorageStrategy strategy = new HashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashMapStorageStrategy() {
        OurHashMapStorageStrategy strategy = new OurHashMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testFileStorageStrategy() {
        FileStorageStrategy strategy = new FileStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testHashBiMapStorageStrategy() {
        HashBiMapStorageStrategy strategy = new HashBiMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testDualHashBidiMapStorageStrategy() {
        DualHashBidiMapStorageStrategy strategy = new DualHashBidiMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }

    @Test
    public void testOurHashBiMapStorageStrategy() {
        OurHashBiMapStorageStrategy strategy = new OurHashBiMapStorageStrategy();
        Shortener shortener = new Shortener(strategy);
        testStorage(shortener);
    }
}
