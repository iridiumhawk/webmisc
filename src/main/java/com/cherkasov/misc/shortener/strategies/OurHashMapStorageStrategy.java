package com.cherkasov.misc.shortener.strategies;

/**
 * Created by hawk on 04.11.2016.
 */
public class OurHashMapStorageStrategy implements StorageStrategy {

    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
    int size;
    int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
    float loadFactor = DEFAULT_LOAD_FACTOR;

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
        for (Entry e = table[i]; e != null; e = e.next) {
            if (e.hash == hash && key.equals(e.key)) {
                e.value = value;
               return;
            }
        }


        addEntry(hash, key, value, i);

    }

    @Override
    public Long getKey(String value) {

        if (value == null) return null; //really?

        for (Entry entry : table) {
            for (Entry e = entry; e != null; e = e.next) {
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
        return (e = getEntry(key)) != null ? e.getValue() : null;
    }

    private int hash(Long k) {
        int h = 0;
        h^=k.hashCode();

        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    private int indexFor(int hash, int length) {

        return hash & (length - 1);
    }

    private Entry getEntry(Long key) {

        if (size == 0) {
            return null;
        }

        int hash = (key == null) ? 0 : hash(key);
        for (Entry e = table[indexFor(hash, table.length)]; e != null; e = e.next) {
//            Object k;
            if ((e.hash == hash) && (e.key.equals(key))) return e; //check null?
        }

        return null;

    }

    private void resize(int newCapacity) {

        Entry[] oldTable = table;
        int oldCapacity = oldTable.length;
/*        if (oldCapacity == MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return;
        }*/

        Entry[] newTable = new Entry[newCapacity];
        transfer(newTable);
        table = newTable;
        threshold = (int) (newCapacity * loadFactor);
    }

    private void transfer(Entry[] newTable) {

        int newCapacity = newTable.length;
        for (Entry e : table) {
            while (null != e) {
                Entry next = e.next;

                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
    }

    private void addEntry(int hash, Long key, String value, int bucketIndex) {

        if ((size >= threshold) && (null != table[bucketIndex])) {
            resize(2 * table.length);
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }

        createEntry(hash, key, value, bucketIndex);
    }

    private void createEntry(int hash, Long key, String value, int bucketIndex) {

        Entry entry = table[bucketIndex];
        table[bucketIndex] = new Entry(hash, key, value, entry);
        size++;
    }
}

/*8.1.	Добавь в класс следующие поля:
 static final int DEFAULT_INITIAL_CAPACITY = 16;
static final float DEFAULT_LOAD_FACTOR = 0.75f;
 Entry[] table = new Entry[DEFAULT_INITIAL_CAPACITY];
int size;
int threshold = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
float loadFactor = DEFAULT_LOAD_FACTOR;
8.2.	Реализуй в классе следующие вспомогательные методы:
int hash(Long k)
int indexFor(int hash, int length)
Entry getEntry(Long key)
void resize(int newCapacity)
void transfer(Entry[] newTable)
void addEntry(int hash, Long key, String value, int bucketIndex)
void createEntry(int hash, Long key, String value, int bucketIndex)
8.3.	Добавь в класс публичные методы, которые требует интерфейс StorageStrategy.
Какие-либо дополнительные поля класса не использовать. Методы, не описанные в
задании, реализовывать не нужно. Если возникнут вопросы как реализовать какой-то
метод или что он должен делать, то ты всегда можешь посмотреть, как работает
похожий метод в HashMap.
Можешь добавить в метод main класса MyMain тестирование новой стратегии. Запусти
и сравни время работы двух стратегий на одинаковом количестве элементов.*/