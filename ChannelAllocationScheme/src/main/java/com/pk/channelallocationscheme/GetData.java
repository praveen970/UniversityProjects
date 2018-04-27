/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.channelallocationscheme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PraveenKumar
 */
public class GetData {
    public static ArrayList<ArrayList<Integer>> getFileData(File file){
        String strLine;
        ArrayList<ArrayList<Integer>> fileData = new ArrayList<>();
        FileInputStream fstream;
        String[] instructions;
        try {
            fstream = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            br.readLine();
            while ((strLine = br.readLine()) != null)   {
                // Print the content on the console
                instructions = strLine.split("\t");
                ArrayList<Integer> rows = new ArrayList<>();
                rows.add(new Integer(Integer.parseInt(instructions[1])));
                rows.add(new Integer(Integer.parseInt(instructions[2])));
                rows.add(new Integer(Integer.parseInt(instructions[3])));
                fileData.add(rows);
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException io){
            Logger.getLogger(GetData.class.getName()).log(Level.SEVERE, null, io);
        }
        return fileData;
    }
}
