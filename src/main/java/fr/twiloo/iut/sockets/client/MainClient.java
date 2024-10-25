package fr.twiloo.iut.sockets.client;

import java.io.IOException;

import static java.lang.System.out;

public class MainClient {

    public static void main(String[] args) throws IOException {
        out.println("Starting client...");
        out.println();

        if (args.length != 2) {
            return;
        }
        String address = args[0];
        int port = Integer.parseInt(args[1]);
        new Client(address, port);
    }
}
