package com.nrmyw.hud_manager_lib.util;

import com.nrmyw.hud_manager_lib.HudManager;

public class HudBrightnessUtil {


    public static void setMinimumIsElectricity(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HudManager.getInstance().getHudEvent().setLuminanceTestMode(0,15);
                            new Thread().sleep(555);
                            HudManager.getInstance().getHudEvent().setLuminanceTestMode(1,99);
                        }catch (Exception e){

                        }
                    }
                }
        ).start();
    }

    public static void setMinimumIsSoftware(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HudManager.getInstance().getHudEvent().setLuminanceTestMode(0,55);
                            new Thread().sleep(555);
                            HudManager.getInstance().getHudEvent().setLuminanceTestMode(1,10);
                        }catch (Exception e){

                        }
                    }
                }
        ).start();
    }

    public static void setNowIsElectricity(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HudManager.getInstance().getHudEvent().setLuminanceTestMode(0,30);
                            new Thread().sleep(555);
                            HudManager.getInstance().getHudEvent().setLuminanceTestMode(1,55);
                        }catch (Exception e){

                        }
                    }
                }
        ).start();
    }

    public static void setNowIsSoftware(){
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HudManager.getInstance().getHudEvent().setLuminanceTestMode(0,55);
                            new Thread().sleep(555);
                            HudManager.getInstance().getHudEvent().setLuminanceTestMode(1,30);
                        }catch (Exception e){

                        }
                    }
                }
        ).start();
    }

    public static void setElectricity(int v){
        if (v<15) {
            v=15;
        }
        if(v>99){
            v=99;
        }
        HudManager.getInstance().getHudEvent().setLuminanceTestMode(0,v);
    }


    public static void setSoftware(int v){
        if (v<1) {
            v=1;
        }
        if(v>99){
            v=99;
        }

        HudManager.getInstance().getHudEvent().setLuminanceTestMode(1,v);
    }

}
