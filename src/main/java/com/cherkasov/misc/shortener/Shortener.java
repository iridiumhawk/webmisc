package com.cherkasov.misc.shortener;


import com.cherkasov.misc.shortener.strategies.StorageStrategy;

/**
 * Created by hawk on 08.10.2016.
 */
public class Shortener {


    private StorageStrategy storageStrategy;
    private Long lastId = 0L;

    public Shortener(StorageStrategy storageStrategy) {

        this.storageStrategy = storageStrategy;
    }


    public synchronized Long getId(String string) {

        if (storageStrategy.containsValue(string)) {
            return storageStrategy.getKey(string);
        }
        lastId++;
        storageStrategy.put(lastId, string);
        return lastId;
    }

    public synchronized String getString(Long id) {

        return storageStrategy.getValue(id);
    }
}
