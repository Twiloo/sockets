package fr.twiloo.iut.sockets.server;

import java.io.IOException;

import static java.lang.System.out;

public class MainServer {

    public static void main(String[] args) {
        out.println("Starting server...");
        out.println();
        try {
            if (args.length != 1) {
                return;
            }

            int port = Integer.parseInt(args[0]);
            new Server(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
