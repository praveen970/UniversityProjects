/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.channelallocationscheme;

import java.util.ArrayList;

/**
 *
 * @author PraveenKumar
 */
public class Cell {

    int numberOfChannels;
    int numberOfCoChannels;
    ArrayList<Boolean>channels;
    ArrayList<Integer>coChannels;

    public Cell(int numberOfChannels, int numberOfCoChannels) {
        this.numberOfChannels = numberOfChannels;
        this.numberOfCoChannels = numberOfCoChannels;
        channels= new ArrayList<>();
        coChannels = new ArrayList<>();
        for(int i=1;i<=numberOfChannels;i++){
            channels.add(true);
        }
    }

    public ArrayList<Boolean> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Boolean> channels) {
        this.channels = channels;
    }

    public ArrayList<Integer> getCoChannels() {
        return coChannels;
    }

    public void setCoChannels(ArrayList<Integer> coChannels) {
        this.coChannels = coChannels;
    }
    
    
}
class Interferers{
    int distance;
    int cell;

    public Interferers(int distance, int cell) {
        this.distance = distance;
        this.cell = cell;
    }
    
}
