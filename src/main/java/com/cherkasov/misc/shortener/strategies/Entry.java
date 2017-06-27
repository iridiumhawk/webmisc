package com.cherkasov.misc.shortener.strategies;

import java.io.Serializable;

/**
 * Created by hawk on 04.11.2016.
 */
public class Entry implements Serializable {
    Long key;
    String value;
    Entry next;
    int hash;

    public Entry(int hash, Long key, String value, Entry next) {

        this.key = key;
        this.value = value;
        this.next = next;
        this.hash = hash;
    }

    public Long getKey() {

        return key;
    }

    public String getValue() {

        return value;
    }

    @Override
    public String toString() {

        return "Entry{" +
                "key=" + key +
                ", value='" + value + '\'' +
                ", next=" + next +
                ", hash=" + hash +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        if (hash != entry.hash) return false;
        if (key != null ? !key.equals(entry.key) : entry.key != null) return false;
        if (value != null ? !value.equals(entry.value) : entry.value != null) return false;
        return next != null ? next.equals(entry.next) : entry.next == null;

    }

    @Override
    public int hashCode() {

        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (next != null ? next.hashCode() : 0);
        result = 31 * result + hash;
        return result;
    }
}

/*7.1.	Разберись как работает стандартный HashMap, посмотри его исходники или
погугли статьи на эту тему.
7.2.	Если ты честно выполнил предыдущий пункт, то ты знаешь для чего используется
класс Entry внутри HashMap. Создай свой аналог класса Entry внутри пакета
strategies. Это должен быть обычный, не вложенный, не generic класс. Сделай его публичным.
В отличии от класса Entry  из HashMap, наш класс будет поддерживать только
интерфейс Serializable.
7.3.	Добавь в Entry следующие поля: Long key, String value, Entry next, int hash. Как
видишь, наша реализация будет поддерживать только тип Long для ключа и только
String для значения. Область видимости полей оставь по умолчанию.
7.4.	Добавь и реализуй конструктор Entry(int hash, Long key, String value, Entry next).
7.5.	Добавь и реализуй методы: Long getKey(), String getValue(), int hashCode() и String
toString(). Реализовывать остальные методы оригинального Entry не нужно, мы
пишем упрощенную версию.
*/