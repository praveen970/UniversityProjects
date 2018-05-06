/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.channelallocationscheme;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PraveenKumar
 */
public class AllocationProcess {
    static int totalCalls;
    static int rejectedCalls;
    static int avgSNR;
    static int maxTimer;
    ArrayList<Cell> cellsList;
    int[][] distanceMatrix;
    public static void main(String args[]){
        AllocationProcess startProcess = new AllocationProcess();
        startProcess.initialize();
    }
    public void initialize(){
        cellsList = new ArrayList<>();
        for(int i=0;i<9;i++){
            cellsList.add(new Cell(5, 2));
        }
        initializeDistanceMatrix();
        cellsList.get(0).getCoChannels().add(5);
        cellsList.get(0).getCoChannels().add(8);
        cellsList.get(1).getCoChannels().add(4);
        cellsList.get(1).getCoChannels().add(6);
        cellsList.get(2).getCoChannels().add(3);
        cellsList.get(2).getCoChannels().add(7);
        cellsList.get(3).getCoChannels().add(2);
        cellsList.get(3).getCoChannels().add(7);
        cellsList.get(4).getCoChannels().add(1);
        cellsList.get(4).getCoChannels().add(6);
        cellsList.get(5).getCoChannels().add(0);
        cellsList.get(5).getCoChannels().add(8);
        cellsList.get(6).getCoChannels().add(1);
        cellsList.get(6).getCoChannels().add(4);
        cellsList.get(7).getCoChannels().add(2);
        cellsList.get(7).getCoChannels().add(3);
        cellsList.get(8).getCoChannels().add(0);
        cellsList.get(8).getCoChannels().add(5);    
           
        File file = new File("input.txt");
         ArrayList<ArrayList<Integer>> fileData = GetData.getFileData(file);
         startEventQueue(fileData);
    }
    public void initializeDistanceMatrix(){
        distanceMatrix = new int[9][9];
        distanceMatrix[0][0] = 0;
        distanceMatrix[0][1] = 2000;
        distanceMatrix[0][2] = 2000;
        distanceMatrix[0][3] = 4000;
        distanceMatrix[0][4] = 6000;
        distanceMatrix[0][5] = 4000;
        distanceMatrix[0][6] = 4000;
        distanceMatrix[0][7] = 6000;
        distanceMatrix[0][8] = 6000;
        distanceMatrix[1][0] = 2000;
        distanceMatrix[1][1] = 0;
        distanceMatrix[1][2] = 2000;
        distanceMatrix[1][3] = 2000;
        distanceMatrix[1][4] = 4000;
        distanceMatrix[1][5] = 2000;
        distanceMatrix[1][6] = 4000;
        distanceMatrix[1][7] = 4000;
        distanceMatrix[1][8] = 6000;
        distanceMatrix[2][0] = 2000;
        distanceMatrix[2][1] = 2000;
        distanceMatrix[2][2] = 0;
        distanceMatrix[2][3] = 4000;
        distanceMatrix[2][4] = 4000;
        distanceMatrix[2][5] = 2000;
        distanceMatrix[2][6] = 2000;
        distanceMatrix[2][7] = 4000;
        distanceMatrix[2][8] = 4000;
        distanceMatrix[3][0] = 4000;
        distanceMatrix[3][1] = 2000;
        distanceMatrix[3][2] = 4000;
        distanceMatrix[3][3] = 0;
        distanceMatrix[3][4] = 2000;
        distanceMatrix[3][5] = 2000;
        distanceMatrix[3][6] = 4000;
        distanceMatrix[3][7] = 4000;
        distanceMatrix[3][8] = 6000;
        distanceMatrix[4][0] = 6000;
        distanceMatrix[4][1] = 4000;
        distanceMatrix[4][2] = 4000;
        distanceMatrix[4][3] = 2000;
        distanceMatrix[4][4] = 0;
        distanceMatrix[4][5] = 2000;
        distanceMatrix[4][6] = 4000;
        distanceMatrix[4][7] = 2000;
        distanceMatrix[4][8] = 4000;
        distanceMatrix[5][0] = 4000;
        distanceMatrix[5][1] = 2000;
        distanceMatrix[5][2] = 2000;
        distanceMatrix[5][3] = 2000;
        distanceMatrix[5][4] = 2000;
        distanceMatrix[5][5] = 0;
        distanceMatrix[5][6] = 2000;
        distanceMatrix[5][7] = 2000;
        distanceMatrix[5][8] = 4000;
        distanceMatrix[6][0] = 4000;
        distanceMatrix[6][1] = 4000;
        distanceMatrix[6][2] = 2000;
        distanceMatrix[6][3] = 4000;
        distanceMatrix[6][4] = 4000;
        distanceMatrix[6][5] = 2000;
        distanceMatrix[6][6] = 0;
        distanceMatrix[6][7] = 2000;
        distanceMatrix[6][8] = 2000;
        distanceMatrix[7][0] = 6000;
        distanceMatrix[7][1] = 4000;
        distanceMatrix[7][2] = 4000;
        distanceMatrix[7][3] = 4000;
        distanceMatrix[7][4] = 2000;
        distanceMatrix[7][5] = 2000;
        distanceMatrix[7][6] = 2000;
        distanceMatrix[7][7] = 0;
        distanceMatrix[7][8] = 2000;
        distanceMatrix[8][0] = 6000;
        distanceMatrix[8][1] = 6000;
        distanceMatrix[8][2] = 4000;
        distanceMatrix[8][3] = 6000;
        distanceMatrix[8][4] = 4000;
        distanceMatrix[8][5] = 4000;
        distanceMatrix[8][6] = 2000;
        distanceMatrix[8][7] = 2000;
        distanceMatrix[8][8] = 0;
    }
    public void startEventQueue(ArrayList<ArrayList<Integer>> fileData){
        Timer timer;
        ArrayList<Timer> eventsQueue = new ArrayList<>();
        ArrayList<Integer> row = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	System.out.println(dateFormat.format(new Date()));
        Display display = new Display();
        totalCalls = fileData.size();
        for(int i=0;i<fileData.size();i++){
            row = fileData.get(i);
            timer = new Timer();
            eventsQueue.add(timer);
            timer.schedule(new ProcessCall(row.get(2), row.get(1),cellsList,distanceMatrix,row.get(0),i+1,display),row.get(0)*1000);
        }
        System.out.println(dateFormat.format(new Date()));
        int lastProcessTime = fileData.get(fileData.size()-1).get(0)+fileData.get(fileData.size()-1).get(2);
        new Timer().schedule(new StopProcess(lastProcessTime,fileData.size()), lastProcessTime*1000);
    }

    class StopProcess extends TimerTask{

        int startTimer;
        int size;

        public StopProcess(int startTimer,int size) {
            this.startTimer = startTimer;
            this.size = size;
        }

        @Override
        public void run() {
            DecimalFormat df2 = new DecimalFormat(".##");
            try {
                //new Timer().schedule(this, (AllocationProcess.maxTimer-startTimer)*1000);
                Thread.sleep((AllocationProcess.maxTimer-startTimer)*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(AllocationProcess.class.getName()).log(Level.SEVERE, null, ex);
            }
            double gos = ((double)AllocationProcess.rejectedCalls/AllocationProcess.totalCalls)*100;
            double avg = ((double)AllocationProcess.avgSNR/(AllocationProcess.totalCalls-AllocationProcess.rejectedCalls));
            System.out.println("Totals : "+(AllocationProcess.totalCalls-AllocationProcess.rejectedCalls)+" calls accepted, "
                    +AllocationProcess.rejectedCalls+" calls rejected, "
                    +df2.format(gos)
                    +"%GOS, AverageSIR = "+df2.format(avg));
        }

    }
}