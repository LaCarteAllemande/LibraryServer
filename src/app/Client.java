package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 3000);
            
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);


            writer.println(25);


            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));


            String response = reader.readLine();
            System.out.println("Server response: " + response);


            socket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

