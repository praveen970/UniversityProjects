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
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PraveenKumar
 */
public class MobileNode extends Thread{
    
    static int homeAgentPort = 20000;
    static String homeAgentaddress = "localhost";
    static String foreignAgentIP = "localhost";
    static DatagramSocket serverSocket;
    DatagramPacket registrationpacket;
    static int currentForeignPort;
    static int foreignAgent1 = 22000;
    static int foreignAgent2 = 24000;
    byte[] sendRegistration = new byte[1024];
    public static void main(String args[]) throws Exception 
    { 
        int portNumber = 5900;
        if(args.length>0){
             portNumber = Integer.parseInt(args[0]);
             foreignAgentIP = args[1];
             foreignAgent1 = Integer.parseInt(args[2]);
             foreignAgent2 = Integer.parseInt(args[3]);
             homeAgentaddress = args[4];
             homeAgentPort = Integer.parseInt(args[5]);
        }
        serverSocket = new DatagramSocket(portNumber);
        Thread newLink = new Thread(new MobileNode());
        newLink.start();
     try
     {        
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        
  
        while(true) 
        { 
          DatagramPacket receivePacket = 
             new DatagramPacket(receiveData, receiveData.length); 
          serverSocket.receive(receivePacket); 
          String sentence = new String(receivePacket.getData()); 
          InetAddress IPAddress = receivePacket.getAddress(); 
          int port = receivePacket.getPort(); 
          if(port == currentForeignPort){
            System.out.println ("Sequence Number = "+sentence+" Time = "+LocalTime.now()+" FA = "+IPAddress+"/"+port);
          }else{
            System.out.println ("Sequence Number = "+sentence+" Rejected. Time = "+LocalTime.now());
          }
          
        } 

     }
      catch (SocketException ex) {
        System.out.println("UDP Port 9876 is occupied.");
        System.exit(1);
      }

    }
    public void run(){
        boolean flag = true;
        
        
        while(true){
            try {
                if(flag){
                    currentForeignPort = foreignAgent1;
                    sendRegistration = ("R-"+foreignAgentIP+"/"+currentForeignPort).getBytes();
                    flag = false;
                }else{
                    currentForeignPort = foreignAgent2;
                    sendRegistration = ("R-"+foreignAgentIP+"/"+currentForeignPort).getBytes();
                    flag = true;
                }
                registrationpacket = new DatagramPacket(sendRegistration, sendRegistration.length,
                    InetAddress.getByName(homeAgentaddress),homeAgentPort);
                serverSocket.send(registrationpacket);
                System.out.println("Registration sent "+ LocalTime.now()+" Changing Care of Address to "+foreignAgentIP+"/"+currentForeignPort);
                Thread.sleep(5000);
            } catch (Exception ex) {
                Logger.getLogger(MobileNode.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
