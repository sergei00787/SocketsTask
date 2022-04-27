package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class Server {

    public Server(int portNumber) {
        try( ServerSocket serverSocket = new ServerSocket(portNumber)){
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("New connection accepted");
            final String name = in.readLine();
            out.println(String.format("Hi %s, your port is %d", name, clientSocket.getPort()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try (Socket socket  = new Socket("localhost",5555);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                if (socket.isConnected()) {
                    out.println("Sergey");
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Server server = new Server(5555);
    }
}
