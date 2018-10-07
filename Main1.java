package serversocket;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int contador = 0;
        try {
            ServerSocket server = new ServerSocket(8189);
            while (true) {
                Socket socket = server.accept();
                System.out.println("Client numero " + contador);
                Runnable r = new ServerManagement(socket, contador);
                Thread t = new Thread(r);
                t.start();
                contador++;
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}

class ServerManagement implements Runnable {

    public ServerManagement(Socket i, int c) {
        socket = i;
        contador = c;
    }

    public void run() {
        try {
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String msgin = "", msgout = "";
            salida.writeUTF("Conectat al servidor");
            try {
                while (!msgin.equals("BYE")) {
                    msgin = entrada.readUTF();
                    if (msgin.equals("BYE")) {
                        System.out.println("Client desconectat");
                        socket.close();
                    } else {
                        System.out.println("Client: " + msgin);
                        msgout = br.readLine();
                        salida.writeUTF(msgout);
                    }
                }
            } finally {
                socket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private Socket socket;
    private int contador;
}
