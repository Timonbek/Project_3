import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main (String [] args) {
        try {
            Socket socket = new Socket("localhost", 1024 );
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            Scanner scanSocket = new Scanner(socket.getInputStream());
            Scanner scanSystem = new Scanner(System.in);
            boolean running = true;
            System.out.println("Request sent successfully!");

            while(running){
                System.out.print("Client: " );
                writer.println(new String(scanSystem.nextLine()));
                writer.flush();
                System.out.println("Server: " + new String(scanSocket.nextLine()));
                if (scanSystem.equals("CLOSE"))
                    running =  false;
            }
            writer.close();
            scanSystem.close();
            scanSocket.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}