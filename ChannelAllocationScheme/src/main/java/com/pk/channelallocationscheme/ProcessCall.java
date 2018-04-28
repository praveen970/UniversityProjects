/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.channelallocationscheme;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 *
 * @author PraveenKumar
 */
public class ProcessCall extends TimerTask{

    int duration,serial;

    public ProcessCall(int duration,int serial) {
        this.duration = duration;
        this.serial = serial;
    }

    @Override
    public void run() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(serial + "Call started"+dateFormat.format(new Date()));
    }
   
    
}
