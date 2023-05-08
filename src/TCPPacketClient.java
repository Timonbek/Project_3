import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPPacketClient {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the hostname or IP address of the server: ");
            String hostname = scanner.nextLine();

            Socket serverSocket = new Socket(hostname, 1024);
            System.out.println("Connected to server at " + serverSocket.getInetAddress());

            ObjectOutputStream outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(serverSocket.getInputStream());

            while (true) {
                // Read a message from the user and create a packet to send to the server
                System.out.print("Enter a message to send to the server: ");
                String message = scanner.nextLine();
                Packet packet = new Packet(1, message);

                // Send the packet to the server
                outputStream.writeObject(packet);

                // Wait for an acknowledgement from the server
                String ack = (String) inputStream.readObject();
                System.out.println("Received acknowledgement from server: " + ack);

                // If the message was CLOSE, break out of the loop and terminate the connection
                if (message.equals("CLOSE")) {
                    break;
                }

                // Read the response packet from the server and print its contents
                Packet responsePacket = (Packet) inputStream.readObject();
                System.out.println("Received response packet with serial number " + responsePacket.getSerialNo() + " and message " + responsePacket.getData());
            }

            // Close the streams and socket
            inputStream.close();
            outputStream.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

