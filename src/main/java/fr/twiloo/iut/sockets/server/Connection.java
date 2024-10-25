package fr.twiloo.iut.sockets.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection implements Runnable {
    private final Server server;
    private final ServerSocket serverSocket;

    public Connection(Server server) throws IOException {
        this.server = server;
        this.serverSocket = new ServerSocket(server.getPort());
    }

    @Override
    public void run() {
        while (true) {
            Socket socketNewClient;
            try {
                socketNewClient = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ConnectedClient newClient;
            try {
                newClient = new ConnectedClient(server, socketNewClient);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                server.addClient(newClient);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Thread threadNewClient = new Thread(newClient);
            threadNewClient.start();
        }
    }
}
