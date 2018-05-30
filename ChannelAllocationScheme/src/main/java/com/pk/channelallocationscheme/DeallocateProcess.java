/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.channelallocationscheme;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 *
 * @author Prashanthi
 */
public class DeallocateProcess extends TimerTask{

    ArrayList<Boolean> availablleChannels;
    int channel;
    Display display;
    int serial,time,duration;

    public DeallocateProcess(ArrayList<Boolean> availablleChannels, int channel, Display display, int serial,int time, int duration) {
        this.availablleChannels = availablleChannels;
        this.channel = channel;
        this.display = display;
        this.serial = serial;
        this.time = time;
        this.duration = duration;
    }
    
    @Override
    public void run() {
        availablleChannels.set(channel, Boolean.TRUE);
        display.deallocatedDisplayLog(this);
    }
    
}
