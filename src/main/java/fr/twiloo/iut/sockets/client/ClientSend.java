package fr.twiloo.iut.sockets.client;

import fr.twiloo.iut.sockets.common.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClientSend implements Runnable {
    private final ObjectOutputStream out;
    private final Scanner scanner = new Scanner(System.in);

    public ClientSend(ObjectOutputStream out) {
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            System.out.print(">> ");
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                if (input.equals("exit")) {
                    break;
                }
                try {
                    out.writeObject(new Message(input));
                    out.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void incomingMessageHandler(String message) {
        System.out.println(message);
    }
}
