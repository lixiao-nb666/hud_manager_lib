package com.nrmyw.hud_manager_lib.hud.util;

import com.newbee.ble_lib.NewBeeBleManager;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDisplayMold;
import com.nrmyw.hud_manager_lib.type.HudDisplayReflectorMold;

public class HudLuminaceManager {
    private final String shareKey="HudLuminaceManager";
    private int lowLuminaceV,hightLuminaceV;
    private static HudLuminaceManager hudLuminaceManager;
    private HudLuminaceManager(){

    }

    public static HudLuminaceManager getInstance(){
        if(null==hudLuminaceManager){
            synchronized (HudLuminaceManager.class){
                if(null==hudLuminaceManager){
                    hudLuminaceManager=new HudLuminaceManager();
                }
            }
        }
        return hudLuminaceManager;
    }
    public void cearlData(){
        lowLuminaceV=0;
        hightLuminaceV=0;
    }


    private boolean checkDataCanUse(int newLowV,int newHightV){

        if(!NewBeeBleManager.getInstance().isConnect()){
            return false;
        }
        if(null==NewBeeBleManager.getInstance().getNowUseBleDevice()){
            return false;
        }


        if(newLowV==lowLuminaceV&&newHightV==hightLuminaceV){
            return false;
        }
        return true;
    }
    public void setHudDisplayMold(HudDisplayMold hudDisplayMold){
        if(null==hudDisplayMold){
            return;
        }
        if(!checkDataCanUse(hudDisplayMold.getLowLuminance(),hudDisplayMold.getHightLuminance())){
            return;
        }

        lowLuminaceV=hudDisplayMold.getLowLuminance();
        hightLuminaceV=hudDisplayMold.getHightLuminance();
        HudManager.getInstance().getHudEvent().setLuminancePercent(lowLuminaceV,hightLuminaceV);
    }


    public void setHudDisplayReflectorMold(HudDisplayReflectorMold hudDisplayReflectorMold){
        if(null==hudDisplayReflectorMold){
            return;
        }
        if(!checkDataCanUse(hudDisplayReflectorMold.getLowLuminance(),hudDisplayReflectorMold.getHightLuminance())){
            return;
        }
        lowLuminaceV=hudDisplayReflectorMold.getLowLuminance();
        hightLuminaceV=hudDisplayReflectorMold.getHightLuminance();
        HudManager.getInstance().getHudEvent().setLuminancePercent(lowLuminaceV,hightLuminaceV);
    }



}
