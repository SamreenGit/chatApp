package com.development.chatapp;


import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@Service
public class Server {

    Socket socket=null;//used to create client
ServerSocket serverSocket;//used to create server
BufferedReader br;
PrintWriter printWriter;
    public Server()  {
        try {
            serverSocket = new ServerSocket(8081);
            System.out.println("System started running ......");
            socket= serverSocket.accept();
            br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printWriter=new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();
        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

    private void startWriting() {
        Runnable r2=()->{
            System.out.println("Writing started....");
            try {
            while(true){
                String msg= null;

                  BufferedReader br1= new BufferedReader((new InputStreamReader(System.in)));
msg=br1.readLine();
printWriter.println(msg);
printWriter.flush();;


            }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        new Thread(r2).start();
    }

    private void startReading() {

        Runnable r1=()->{
            System.out.println("Reading started....");
            try {
        while(true){
            String msg= null;

                msg = br.readLine();

            if(msg.equals("exit")){
                System.out.println("Client terminated the chat.");
                socket.close();
                break;
            }
            System.out.println("Client: "+msg);

        }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        new Thread(r1).start();
    }


}
