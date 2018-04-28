/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.channelallocationscheme;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

/**
 *
 * @author PraveenKumar
 */
public class AllocationProcess {

    public static void main(String args[]){
        File file = new File("input.txt");
         ArrayList<ArrayList<Integer>> fileData = GetData.getFileData(file);
         AllocationProcess process = new AllocationProcess();
         process.startEventQueue(fileData);
    }
    public void startEventQueue(ArrayList<ArrayList<Integer>> fileData){
        Timer timer;
        ArrayList<Integer> row = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	System.out.println(dateFormat.format(new Date()));
        for(int i=0;i<fileData.size();i++){
            row = fileData.get(i);
            new Timer().schedule(new ProcessCall(row.get(2), i),row.get(0)*1000);
        }
        System.out.println(dateFormat.format(new Date()));
    }
}
