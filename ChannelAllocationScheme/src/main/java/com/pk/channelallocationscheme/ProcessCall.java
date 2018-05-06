/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.channelallocationscheme;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author PraveenKumar
 */
public class ProcessCall extends TimerTask{

    int duration,cell;
    ArrayList<Cell>cellList;
    ArrayList<Boolean> availablleChannels;
    ArrayList<Integer> coChannels;
    ArrayList<Double>channelStatuses;
    ArrayList<Interferers> interferers;
    int[][] distanceMatrix;
    int serial,time,channel,totalInteferers;
    double sirDb;
    boolean isAccepted = false;
    DateFormat dateFormat;
    Display display;
    public ProcessCall(int duration,int cell,ArrayList<Cell>cellList,int[][] distanceMatrix,int time,int serial,Display display) {
        this.duration = duration;
        this.cell = cell;
        this.cellList = cellList;
        this.distanceMatrix = distanceMatrix;
        this.serial = serial;
        this.time = time;
        this.display = display;
        channelStatuses = new ArrayList<>(Collections.nCopies(5, 0.0));
        interferers = new ArrayList<>();
    }

    @Override
    public void run() {
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Cell currentCell = cellList.get(cell-1);
        availablleChannels = currentCell.getChannels();
        coChannels = currentCell.getCoChannels();
        
        if(didfindBestChannel()){
            AllocationProcess.avgSNR+=this.sirDb;
            if (AllocationProcess.maxTimer<(time+duration)){
                AllocationProcess.maxTimer = time+duration;
            }
            new Timer().schedule(new DeallocateProcess(availablleChannels, channel-1,display,serial,time,duration), this.duration*1000);
            display.displayLog(this);
        }else{
            AllocationProcess.rejectedCalls++;
            display.displayLog(this);
        }
    }
    public boolean didfindBestChannel(){
        switch(cell){
            case 1:
            case 2:
            case 3:
                for(int i=0;i<availablleChannels.size();i++){
                    if(availablleChannels.get(i) == Boolean.TRUE){
                        if(isValidSIR(i)){
                            isAccepted = true;
                            availablleChannels.set(i, Boolean.FALSE);
                            this.channel = i+1;
                           //display.displayLog(this);
                            //this.displayLog();
                            return true;
                        }
                    }
                    
                }
//                if(isAccepted == false){
//                    display.displayLog(this);
//                    //this.displayLog();
//                }
                return false;
            case 4:
            case 5:
            case 6:
                for(int i=availablleChannels.size()-1;i>=0;i--){
                    if(availablleChannels.get(i) == Boolean.TRUE){
                        if(isValidSIR(i)){
                            isAccepted = true;
                            availablleChannels.set(i, Boolean.FALSE);
                            this.channel = i+1;
                            //display.displayLog(this);
                            //this.displayLog();
                            return true;
                        }
                    }
                }
//                if(isAccepted == false){
//                    display.displayLog(this);
//                   // this.displayLog();
//                }
                return false;
            case 7:
            case 8:
            case 9:
                for(int i=availablleChannels.size()-1;i>=0;i--){
                    if(availablleChannels.get(i) == Boolean.TRUE){
                        if(isValidSIR(i)){
                            isAccepted = true;
                            availablleChannels.set(i, Boolean.FALSE);
                            this.channel = i+1;
                            //display.displayLog(this);
                            //this.displayLog();
                            return true;
                        }
                    }
                }
//                if(isAccepted == false){
//                    display.displayLog(this);
//                    //this.displayLog();
//                }
                return false;
        }
        
        return false;
    }
    public boolean isValidSIR(int channel){
        float denominator = 0,sum=0;
        this.totalInteferers = 0;
        if(cellList.get(coChannels.get(0)).channels.get(channel)==false){
            interferers.add(new Interferers(distanceMatrix[cell-1][coChannels.get(0)], coChannels.get(0)));
            this.totalInteferers++;
            denominator+=Math.pow(distanceMatrix[cell-1][coChannels.get(0)],-4);
        }if(cellList.get(coChannels.get(1)).channels.get(channel)==false){
            interferers.add(new Interferers(distanceMatrix[cell-1][coChannels.get(1)], coChannels.get(1).intValue()));
            this.totalInteferers++;
            denominator+=Math.pow(distanceMatrix[cell-1][coChannels.get(1)],-4);
        }
        if(denominator!=0){
        sum = (float) (Math.pow(1000, -4)/denominator);
        this.sirDb = Math.log10(sum)*10;
        this.channelStatuses.set(channel, sirDb);
        if(sirDb>22){
            return true;
        }else{
            return false;
        }
        }
        else {
            this.sirDb = 35;
            return true;
        }
        
    }
    public synchronized void displayLog(){
        
        System.out.print("\nNew Call: Number = "+this.serial+"Time : "+this.time+" Cell : "+this.cell+" Duration : "+this.duration);
        System.out.println(" Call started"+this.dateFormat.format(new Date()));
        if(this.isAccepted){
        System.out.print("Accepted ");
        System.out.print("Channel = "+ this.channel);
        System.out.println("SIR = "+this.sirDb);
        }else{
            System.out.println("Rejected");
        }
        
    }
   
    
}
