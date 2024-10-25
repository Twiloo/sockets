package fr.twiloo.iut.sockets.server;

import fr.twiloo.iut.sockets.common.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private final int port;
    private final List<ConnectedClient> connectedClients = new ArrayList<>();

    public Server(int port) throws IOException {
        this.port = port;
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();
    }

    public int getPort() {
        return port;
    }

    public void addClient(ConnectedClient newClient) throws IOException {
        connectedClients.add(newClient);
        broadcastMessage(new Message("Le client " + newClient.getId() + " vient de se connecter."));
    }

    public void broadcastMessage(Message message) throws IOException {
        System.out.println(message);
        int senderId = message.getSenderId();
        for (ConnectedClient client : connectedClients) {
            if (client.getId() != senderId) {
                client.sendMessage(message);
            }
        }
    }

    public void disconnectClient(ConnectedClient client) throws IOException {
        client.closeClient();
        connectedClients.remove(client);
        broadcastMessage(new Message("Le client " + client.getId() + " vient de se déconnecter."));
    }
}
