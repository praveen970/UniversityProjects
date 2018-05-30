/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package mobileip;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalTime;

/**
 *
 * @author PraveenKumar
 */
public class ForeignAgent {
    public static void main(String args[]) throws Exception 
    { 
     try
     {//Values are for example and not hard coded
        int portNumber = 24000;
        String foerignAgent = "localhost";
        int mobilePort = 5900;
        if(args.length>0){
             portNumber = Integer.parseInt(args[0]); //port and host address of FA
             mobilePort = Integer.parseInt(args[1]);
        }
        DatagramSocket serverSocket = new DatagramSocket(portNumber); //opening UDP socket at port
        byte[] receiveData = new byte[1024]; 
        byte[] sendData  = new byte[1024]; 
  
        while(true) 
        { 
          receiveData = new byte[1024]; 
          DatagramPacket receivePacket = 
             new DatagramPacket(receiveData, receiveData.length); 
          serverSocket.receive(receivePacket); //recevie USP packet from HA
          String sentence = new String(receivePacket.getData()); 
          InetAddress IPAddress = receivePacket.getAddress(); 
          int port = receivePacket.getPort(); 
          System.out.print ("Sequence Number = "+sentence+" Time = "+LocalTime.now());
          DatagramPacket sendPacket = new DatagramPacket(sentence.getBytes(), sentence.length(), 
                  InetAddress.getByName(foerignAgent), mobilePort);
          serverSocket.send(sendPacket); //FOrwrd UPD packet to MN
          System.out.println(" Forwarded to "+InetAddress.getByName(foerignAgent)+"/"+mobilePort);
        } 

     }
      catch (SocketException ex) {
        System.out.println("UDP Port 9876 is occupied.");
        System.exit(1);
      }

    } 
}
