import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket ServerSocket = new ServerSocket(1024);
        System.out.println("Waiting for client request...");
        Socket input =  ServerSocket.accept();
        PrintWriter writer = new PrintWriter(input.getOutputStream());
        Scanner scanSocket = new Scanner(input.getInputStream());
        Scanner scanSystem = new Scanner(System.in);
        Boolean running = true;
        System.out.println("Client connected!");
        while (running){
            System.out.println("Client: " + scanSocket.nextLine());
            System.out.print("Server: ");
            String s = scanSystem.nextLine();
            writer.println(s);
            writer.flush();
        }
        input.close();
        writer.close();
        scanSocket.close();
        scanSystem.close();
    }
}

