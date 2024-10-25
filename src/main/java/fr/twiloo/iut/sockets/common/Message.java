package fr.twiloo.iut.sockets.common;

import java.io.Serializable;

public class Message implements Serializable {
    private final String message;
    private int senderId;

    public Message(String message) {
        this.message = message;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    @Override
    public String toString() {
        return senderId + " : " + message;
    }
}
