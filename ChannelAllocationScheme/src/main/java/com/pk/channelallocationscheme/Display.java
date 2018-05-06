/*
 * To change pc license header, choose License Headers in Project Properties.
 * To change pc template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.channelallocationscheme;

import java.text.DecimalFormat;
import java.util.Date;

/**
 *
 * @author Prashanthi
 */
public class Display {
    private static DecimalFormat df2 = new DecimalFormat(".##");
    public synchronized void displayLog(ProcessCall pc){
        
            System.out.print("\nNew Call: Number = "+pc.serial+" Time : "+pc.time+" Cell : "+pc.cell+" Duration : "+pc.duration);
        System.out.println(" Call started "+pc.dateFormat.format(new Date()));
        if(pc.isAccepted){
        System.out.print("Accepted ");
        System.out.print("Channel = "+ pc.channel);
        System.out.println(" SIR = "+df2.format(pc.sirDb));
        if(pc.totalInteferers>0){
            System.out.print("Inteferers: ");
            for(int i=0;i<pc.interferers.size();i++){
                System.out.print((pc.interferers.get(i).cell+1)+"/"+pc.interferers.get(i).distance+", ");
            }
            System.out.println("");
            pc.cancel();
        }
            
        }else{
            System.out.println("Rejected ");
            System.out.print("Reasons : ");
            for(int i=0;i<5;i++){
                System.out.print(i+1+"/");
                if(pc.channelStatuses.get(i)>0.0){
                    System.out.print(df2.format(pc.channelStatuses.get(i)));
                }else{
                    System.out.print("In Use");
                }
            }
            System.out.println("");
            pc.cancel();
        }
        
    }
    public synchronized void deallocatedDisplayLog(DeallocateProcess dc){
        System.out.println("Disconnect: Number = "+dc.serial+" Start Time = "+dc.time+" Endtime = "+(dc.time+dc.duration)+"Duration = "+dc.duration+"Channel = "+(dc.channel+1));
        dc.cancel();
    }
}
