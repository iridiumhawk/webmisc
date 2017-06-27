package com.cherkasov.misc.shortener.strategies;

/**
 * Created by hawk on 08.10.2016.
 */
public interface StorageStrategy {
    	boolean containsKey(Long key);
    	boolean containsValue(String value);
    	void put(Long key, String value);
    	Long getKey(String value);
    	String getValue(Long key);
}
