/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distributedservers;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author PraveenKumar
 */
public class MainServer implements Runnable{
    
    int portNumber;
    ArrayList<Integer> portNumbers;
    public MainServer(int portNumber,ArrayList<Integer> ports) {
        this.portNumber = portNumber;
        this.portNumbers = ports;
    }
    
    public void run(){
        try
     {
        String foerignAgent = "localhost";
        int foreignPort = 22000;
        DatagramSocket serverSocket = new DatagramSocket(portNumber); 
        DatagramSocket clientSocket = new DatagramSocket();
        byte[] receiveData = new byte[1024]; 
        byte[] sendData  = new byte[1024]; 
  
        while(true) 
        { 
          receiveData = new byte[1024]; 
          DatagramPacket receivePacket = 
             new DatagramPacket(receiveData, receiveData.length); 
          serverSocket.receive(receivePacket); 
          String sentence = new String(receivePacket.getData()); 
          sendData = sentence.getBytes();
          String[] expressionValues = sentence.split("(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)");
          DatagramPacket sendPacket;
          switch(expressionValues[1]){
              case"+":
                sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), portNumbers.get(0)); 
                clientSocket.send(sendPacket);
                System.out.println("Forwarded to Addition SubServer");
                break;
            case"-":
                sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), portNumbers.get(1)); 
                clientSocket.send(sendPacket);
                System.out.println("Forwarded to Subtraction SubServer");
                break;
            case"*":
                sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), portNumbers.get(2)); 
                clientSocket.send(sendPacket);
                System.out.println("Forwarded to Multiplication SubServer");
                break;
            case"/":
                sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("localhost"), portNumbers.get(3)); 
                clientSocket.send(sendPacket);
                System.out.println("Forwarded to Division SubServer");
                break;
          }
         
          
        } 

     }
      catch (Exception ex) {
        System.out.println(ex.getMessage());
        System.exit(1);
      }
    }
}
