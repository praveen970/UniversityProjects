/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package mobileip;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PraveenKumar
 */
public class DataSource {
    public static void main(String args[]) throws Exception 
    { 
        String serverHostname = new String ("localhost");
        int portNumber = 20000;
        if (args.length > 0){
            serverHostname = args[0]; 
            portNumber = Integer.parseInt(args[1]);
        }  
      InetAddress IPAddress = InetAddress.getByName(serverHostname); 
      System.out.println ("DataSource connected to " + IPAddress + 
                          ") via UDP port"+portNumber);
      sendPackets(IPAddress,portNumber);
    } 
    
    public static void sendPackets(InetAddress IPAddress,int portNumber){
        try {
            int sequenceNumber = 1;
            byte[] sendData = new byte[1024];
            DatagramSocket clientSocket = new DatagramSocket();
            while(true){
                if(sequenceNumber>100){
                    break;
                }
                sendData = Integer.toString(sequenceNumber).getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, portNumber); 
                clientSocket.send(sendPacket);
                System.out.println("Sequence Nmber = "+sequenceNumber+" Time = "+LocalTime.now()+
                        " Destination = "+IPAddress+"/"+portNumber);
                sequenceNumber++;
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
                Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
                    Logger.getLogger(DataSource.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
