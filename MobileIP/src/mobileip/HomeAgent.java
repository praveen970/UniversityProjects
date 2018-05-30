/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package mobileip;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.time.LocalTime;

/**
 *
 * @author PraveenKumar
 */
public class HomeAgent {
    public static void main(String args[]) throws Exception 
    { 
     try
     {
        int portNumber = 20000;
        String foreignAgent="";
        int foreignPort = 22000;
        if(args.length>0){
             portNumber = Integer.parseInt(args[0]); // HomeAgents portnumber to open the socket
        }
        DatagramSocket serverSocket = new DatagramSocket(portNumber); //Opening socket at the given portNumner
        byte[] receiveData = new byte[1024]; 
        byte[] sendData  = new byte[1024]; 
        String newAddress;
        String[] newIpAndPort;
        while(true) 
        { 
          receiveData = new byte[1024]; 
          DatagramPacket receivePacket = 
             new DatagramPacket(receiveData, receiveData.length); 
          serverSocket.receive(receivePacket); //Receiveing packet from DataSource
          String sentence = new String(receivePacket.getData()); 
          InetAddress IPAddress = receivePacket.getAddress(); 
          int port = receivePacket.getPort();
          if(sentence.contains("R")){ //Check if it is registrationPacket
              newAddress = sentence.substring(2);
              newIpAndPort = sentence.split("/");
              foreignPort = Integer.parseInt(newIpAndPort[1].trim());
              foreignAgent = newIpAndPort[0].substring(2);
              System.out.println("Registration packet received. Time = "+LocalTime.now()+"Changing Care-of-Address to "+sentence.substring(2));
          }else{//Check if it is a data apcket
              System.out.print("Sequence Number = "+sentence+" Time = "+LocalTime.now());
              if(!foreignAgent.equals("")){
                  DatagramPacket sendPacket = new DatagramPacket(sentence.getBytes(), sentence.length(), 
                  InetAddress.getByName(foreignAgent), foreignPort);
                  serverSocket.send(sendPacket); //Forwarding to the foreign agent specified in registration packet
                  System.out.print(" Forwarded to "+InetAddress.getByName(foreignAgent)+"/"+foreignPort);
              }
              System.out.println("");
          }
          
          
        } 

     }
      catch (SocketException ex) {
        System.out.println("UDP Port 9876 is occupied.");
        System.exit(1);
      }

    } 
  
}
