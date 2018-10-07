package clientsocket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Scanner entradaDades = new Scanner(System.in);
            //podeu provar de conectar la IP 132.163.4.101
            //que correspon al National Institute of Standards
            //and Technology, en Boulder Colorado, i ofereix
            //la mesura d'un rellotge atòmic de Cesi
            System.out.println("Introdueix la IP del host");
            String IP_Address = entradaDades.next();
            //el port al que ens conectem és el 13
            System.out.println("introdueix el port");
            int port = entradaDades.nextInt();
            Socket socket = new Socket(IP_Address, port);
            try {
                DataInputStream entrada = new DataInputStream(socket.getInputStream());
                DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String msgin = "", msgout = "";
                msgin = entrada.readUTF();
                System.out.println(msgin);
                while (!msgout.equals("BYE")) {
                    msgout = br.readLine();
                    salida.writeUTF(msgout);
                    if (msgout.equals("BYE")) {
                        socket.close();
                    } else {
                        msgin = entrada.readUTF();
                        System.out.println("Servidor: " + msgin);
                    }
                }
            } finally {
                socket.close();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
