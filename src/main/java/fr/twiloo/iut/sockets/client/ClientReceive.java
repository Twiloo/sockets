package fr.twiloo.iut.sockets.client;

import fr.twiloo.iut.sockets.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;

public class ClientReceive implements Runnable {
    private final Client client;
    private final ObjectInputStream in;

    public ClientReceive(Client client, ObjectInputStream in) {
        this.client = client;
        this.in = in;
    }

    @Override
    public void run() {
        while (true) {
            Message message;
            try {
                message = (Message) in.readObject();

                if (message == null) {
                    break;
                }
                this.client.showMessage(message);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            client.disconnectServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
