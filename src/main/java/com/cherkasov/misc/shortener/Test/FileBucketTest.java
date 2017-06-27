package com.cherkasov.misc.shortener.Test;

import com.cherkasov.misc.shortener.strategies.Entry;
import com.cherkasov.misc.shortener.strategies.FileBucket;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by hawk on 05.11.2016.
 */
public class FileBucketTest {
    static final FileBucket fb = new FileBucket();

    @Test
    public void putEntry() throws Exception {

        Entry test1 = new Entry(1, 55550L, "111", null);
        Entry test2 = new Entry(2, 55551L, "222", test1);
        Entry test3 = new Entry(3, 55552L, "333", test2);

        fb.putEntry(test3);

        Entry readFromFile = fb.getEntry();

        System.out.println(readFromFile.toString());

        assertEquals(test3,readFromFile);
       /* fb.putEntry(test);
        fb.putEntry(test);*/

    }

}