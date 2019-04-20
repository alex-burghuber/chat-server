package main;

import endpoints.ChatEndpoint;
import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Alexander Burghuber
 */
public class Main {

    public static void main(String[] args) {
        runServer();
    }

    private static void runServer() {
        Server server = new Server("0.0.0.0",
                8025,
                "/websockets",
                ChatEndpoint.class
        );
        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please press a key to stop the chat-server.");
            reader.readLine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            server.stop();
        }
    }
}
