/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.channelallocationscheme;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

/**
 *
 * @author PraveenKumar
 */
public class AllocationScheme {

    public static void main(String args[]){
        File file = new File("input.txt");
         ArrayList<ArrayList<Integer>> fileData = GetData.getFileData(file);
    }
    public void startEventQueue(ArrayList<ArrayList<Integer>> fileData){
        Timer timer;
        ArrayList<Integer> row = new ArrayList<>();
        for(int i=0;i<fileData.size();i++){
            row = fileData.get(i);
            new Timer().schedule(new ProcessCall(row.get(2), i), 0,row.get(0));
        }
    }
}
