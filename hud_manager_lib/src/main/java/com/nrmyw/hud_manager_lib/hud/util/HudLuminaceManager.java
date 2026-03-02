package com.nrmyw.hud_manager_lib.hud.util;

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

    public void setHudDisplayMold(HudDisplayMold hudDisplayMold){
        if(null==hudDisplayMold){
            return;
        }
        if(hudDisplayMold.getLowLuminance()==lowLuminaceV&&hudDisplayMold.getHightLuminance()==hightLuminaceV){
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
        if(hudDisplayReflectorMold.getLowLuminance()==lowLuminaceV&&hudDisplayReflectorMold.getHightLuminance()==hightLuminaceV){
            return;
        }
        lowLuminaceV=hudDisplayReflectorMold.getLowLuminance();
        hightLuminaceV=hudDisplayReflectorMold.getHightLuminance();
        HudManager.getInstance().getHudEvent().setLuminancePercent(lowLuminaceV,hightLuminaceV);
    }



}
