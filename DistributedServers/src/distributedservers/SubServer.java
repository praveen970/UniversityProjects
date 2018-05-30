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

/**
 *
 * @author PraveenKumar
 */
public class SubServer implements Runnable{
    int portNumber;
    String operation;

    public SubServer(int portNumber, String operation) {
        this.portNumber = portNumber;
        this.operation = operation;
    }
    public void run(){
        try
     {
       
        DatagramSocket serverSocket = new DatagramSocket(portNumber); 
        byte[] receiveData = new byte[1024]; 
  
        while(true) 
        { 
          receiveData = new byte[1024]; 
          DatagramPacket receivePacket = 
             new DatagramPacket(receiveData, receiveData.length); 
          serverSocket.receive(receivePacket); 
          String sentence = new String(receivePacket.getData()); 
          sentence.replaceAll("\\s", "");
          String[] operands;
          int value = 0;
          switch(operation){
            case"+":
                System.out.print("Addition Subserver: Value = ");
                operands = sentence.split("\\+");
                for(int i=0;i<operands.length;i++){
                    value+=Integer.parseInt(operands[i].trim());
                }
                System.out.println(value);
                break;
            case"-":
                System.out.print("Subtraction Subserver: Value = ");
                operands = sentence.split("\\-");
                value = Integer.parseInt(operands[0]);
                for(int i=1;i<operands.length;i++){
                    value-=Integer.parseInt(operands[i].trim());
                }
                System.out.println(value);
                break;
            case"*":
                System.out.print("Multiplication Subserver: Value = ");
                operands = sentence.split("\\*");
                value = 1;
                for(int i=0;i<operands.length;i++){
                    value*=Integer.parseInt(operands[i].trim());
                }
                System.out.println(value);
                break;
            case"/":
                System.out.print("Division Subserver: Value = ");
                operands = sentence.split("\\/");
                value = Integer.parseInt(operands[0]);
                for(int i=1;i<operands.length;i++){
                    value/=Integer.parseInt(operands[i].trim());
                }
                System.out.println(value);
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
