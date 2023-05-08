import java.io.*;
import java.net.*;
public class TCPPacketServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1024);
            System.out.println("Server started, waiting for a client to connect...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected from " + clientSocket.getInetAddress());

            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                // Read the packet sent by the client
                Packet packet = (Packet) inputStream.readObject();
                System.out.println("Received packet with serial number " + packet.getSerialNo() + " and message " + packet.getData());

                // Send an acknowledgement to the client
                outputStream.writeObject("ACK");

                // If the client sent a CLOSE message, break out of the loop and terminate the connection
                if (packet.getData().equals("CLOSE")) {
                    break;
                }

                // Convert the message to uppercase and send it back to the client
                String response = packet.getData().toUpperCase();
                Packet responsePacket = new Packet(packet.getSerialNo() + 1, response);
                outputStream.writeObject(responsePacket);
            }

            // Close the streams and sockets
            inputStream.close();
            outputStream.close();
            clientSocket.close();
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
