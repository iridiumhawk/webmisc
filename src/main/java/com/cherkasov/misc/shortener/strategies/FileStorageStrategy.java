package com.cherkasov.misc.shortener.strategies;

/**
 * Created by hawk on 05.11.2016.
 */
public class FileStorageStrategy implements StorageStrategy {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
//    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    int size;
//    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
//    float loadFactor = DEFAULT_LOAD_FACTOR;

    long bucketSizeLimit = 1000L;

    public FileStorageStrategy() {

        for (int i = 0 ; i < DEFAULT_INITIAL_CAPACITY; i++) {
            table[i] = new FileBucket();
        }
//        System.exit(0);
    }

    public long getBucketSizeLimit() {

        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {

        this.bucketSizeLimit = bucketSizeLimit;
    }


    @Override
    public boolean containsKey(Long key) {

        return getValue(key) != null;
    }

    @Override
    public boolean containsValue(String value) {

        return getKey(value) != null;
    }

    @Override
    public void put(Long key, String value) {

        int hash = hash(key);
        int i = indexFor(hash, table.length);

        for (Entry entry = getEntryFromBacket(key); entry != null; entry = entry.next) {
            if (entry.hash == hash && key.equals(entry.key)) {
                entry.value = value;
                return;
            }
        }
        addEntry(hash, key, value, i);
    }

    private void addEntry(int hash, Long key, String value, int bucketIndex) {

        if ((null != table[bucketIndex]) && (table[bucketIndex].getFileSize() >= getBucketSizeLimit())) {
            resize(2 * table.length);
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }

        createEntry(hash, key, value, bucketIndex);
    }

    private void createEntry(int hash, Long key, String value, int bucketIndex) {

        Entry entry = null;

        if (table[bucketIndex] == null) {
            table[bucketIndex] = new FileBucket();
        } else {
            entry = table[bucketIndex].getEntry();
        }
        table[bucketIndex].putEntry(new Entry(hash, key, value, entry));
        size++;
    }

    private void resize(int newCapacity) {

        FileBucket[] oldTable = table;
//        int oldCapacity = oldTable.length;

        FileBucket[] newTable = new FileBucket[newCapacity];
        for (int i = 0 ; i < newTable.length; i++) {
            newTable[i] = new FileBucket();
        }
        transfer(newTable);
        for (int i = 0 ; i < table.length; i++) {
            table[i].remove();
        }

        table = newTable;
//        threshold = (int) (newCapacity * loadFactor);
    }


    private void transfer(FileBucket[] newTable) {

        int newCapacity = newTable.length;
        for (FileBucket fileBucket : table) {
            Entry entry = null;

            if (fileBucket == null) {
                continue;
            } else {
                entry = fileBucket.getEntry();
            }

            while (entry != null) {
                Entry next = entry.next;

                int i = indexFor(entry.hash, newCapacity);
                if (newTable[i] == null) {
                    newTable[i] = new FileBucket();
                }
                entry.next = newTable[i].getEntry();

                newTable[i].putEntry(entry);

                entry = next;

            }
        }
    }

    @Override
    public Long getKey(String value) {

        if (value == null) return null; //really?

        for (FileBucket fileBucket : table) {
            if (fileBucket == null) {
                continue;
            }

            for (Entry e = fileBucket.getEntry(); e != null; e = e.next) {
                if (e.getValue().equals(value)) {
                    return e.getKey();
                }
            }
        }
        return null;
    }

    @Override
    public String getValue(Long key) {

        Entry e;
        return (e = getEntryFromBacket(key)) != null ? e.getValue() : null;
    }

    private Entry getEntryFromBacket(Long key) {

        if (size == 0) {
            return null;
        }

        int hash = (key == null) ? 0 : hash(key);
        int i = indexFor(hash, table.length);
        FileBucket fileBucket = table[i];

        if (fileBucket == null) {
            return null;
        }

        for (Entry e = fileBucket.getEntry(); e != null; e = e.next) {
            if ((e.hash == hash) && (e.key.equals(key))) return e; //check null?
        }

        return null;
    }

    private int indexFor(int hash, int length) {

        return hash & (length - 1);
    }


    private int hash(Long k) {

        int h = 0;
        h ^= k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

}

/*10.2.	Использовать FileBucket в качестве ведер (англ. bucket). Подсказка: класс
должен содержать поле FileBucket[] table.
10.3.	Работать аналогично тому, как это делает OurHashMapStorageStrategy, но
удваивать количество ведер не когда количество элементов size станет больше
какого-то порога, а когда размер одного из ведер (файлов) стал больше
bucketSizeLimit.
10.3.1.	Добавь в класс поле long bucketSizeLimit.
10.3.2.	Проинициализируй его значением по умолчанию, например, 10000 байт.
10.3.3.	Добавь сеттер и геттер для этого поля.

10.4.	При реализации метода resize(int newCapacity) проследи, чтобы уже не нужные
файлы были удалены (вызови метод  remove()).

Проверь новую стратегию в методе main(). Учти, что стратегия FileStorageStrategy гораздо
более медленная, чем остальные. Не используй большое количество элементов для теста,
это может занять оооочень много времени.
Запусти программу и сравни скорость работы всех 3х стратегий.*/