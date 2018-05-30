/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedservers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author PraveenKumar
 */
public class DistributedServers {

    /**
     * @param args the command line arguments
     */
    public static int MAIN_SERVER_PORT = 20000;
    public static int ADDITION_SERVER_PORT = 22000;
    public static int MULTIPLICATION_SERVER_PORT = 26000;
    public static int SUBTRACTION_SERVER_PORT = 24000;
    public static int DIVISION_SERVER_PORT = 28000;
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> portNumbers = new ArrayList<>();
        portNumbers.add(ADDITION_SERVER_PORT);
        portNumbers.add(SUBTRACTION_SERVER_PORT);
        portNumbers.add(MULTIPLICATION_SERVER_PORT);
        portNumbers.add(DIVISION_SERVER_PORT);
        InetAddress IPAddress = InetAddress.getByName("localhost");
        
        MainServer leader = new MainServer(MAIN_SERVER_PORT,portNumbers);
        SubServer additionServer = new SubServer(ADDITION_SERVER_PORT, "+");
        new Thread(additionServer).start();
        SubServer subtractionServer = new SubServer(SUBTRACTION_SERVER_PORT, "-");
        new Thread(subtractionServer).start();
        SubServer multiplicationServer = new SubServer(MULTIPLICATION_SERVER_PORT, "*");
        new Thread(multiplicationServer).start();
        SubServer divisionServer = new SubServer(DIVISION_SERVER_PORT, "/");
        new Thread(divisionServer).start();
        new Thread(leader).start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String expression;
        byte[] sendData = new byte[1024];
        System.out.println("Enter ann expression to caluclate : ");
        DatagramSocket clientSocket = new DatagramSocket();
        while(true){
            
                expression = reader.readLine();
                if(expression.equals("exit"))
                    break;
                sendData = expression.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, MAIN_SERVER_PORT); 
                clientSocket.send(sendPacket);
                System.out.println("DataSent to MainServer");
            }
    }
    
}
