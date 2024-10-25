package fr.twiloo.iut.sockets.server;

import fr.twiloo.iut.sockets.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {
    private static int clientCounter = 1;
    private final int id;
    private final Server server;
    private final Socket socket;
    private final ObjectOutputStream out;
    private ObjectInputStream in;

    public ConnectedClient(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.id = clientCounter++;
        System.out.println("Nouvelle connexion, id : " + id);
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            Message message;
            try {
                message = (Message) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if (message == null) {
                try {
                    server.disconnectClient(this);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
            message.setSenderId(this.id);
            try {
                server.broadcastMessage(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessage(Message message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public void closeClient() throws IOException {
        in.close();
        out.close();
        socket.close();
    }
}
