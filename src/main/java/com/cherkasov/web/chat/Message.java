
package com.cherkasov.web.chat;


import java.io.Serializable;

/**
 * Created by hawk on 14.07.2016.
 */
public class Message implements Serializable {
    private final MessageType type;
    private final String data;

    public Message(MessageType type, String data) {
        this.type = type;
        this.data = data;
    }

    public Message(MessageType type) {
        this.type = type;
        data = null;

    }

    public MessageType getType() {

        return type;
    }

    public String getData() {
        return data;
    }
}
