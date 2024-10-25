package fr.twiloo.iut.sockets.client;

import fr.twiloo.iut.sockets.common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final ClientSend clientSend;

    public Client(String address, int port) throws IOException {
        socket = new Socket(address, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        this.clientSend = new ClientSend(out);
        Thread clientSend = new Thread(this.clientSend);
        clientSend.start();
        Thread clientReceive = new Thread(new ClientReceive(this, in));
        clientReceive.start();
    }

    public void showMessage(Message message) {
        clientSend.incomingMessageHandler(message.toString());
    }

    public void disconnectServer() throws IOException {
        socket.close();
        in.close();
        out.close();
    }
}
