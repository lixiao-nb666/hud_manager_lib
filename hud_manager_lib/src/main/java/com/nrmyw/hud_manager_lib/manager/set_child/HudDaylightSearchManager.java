package com.nrmyw.hud_manager_lib.manager.set_child;

import com.nrmyw.hud_manager_lib.HudManager;

public class HudDaylightSearchManager {
    private static HudDaylightSearchManager hudDaylightSearchManager;
    private boolean isDaylightSearch;
    private HudDaylightSearchManager(){}

    public static HudDaylightSearchManager getInstance(){
        if(null==hudDaylightSearchManager){
            synchronized (HudDaylightSearchManager.class){
                if(null==hudDaylightSearchManager){
                    hudDaylightSearchManager=new HudDaylightSearchManager();
                }
            }
        }
        return hudDaylightSearchManager;
    }

    public void update(){
        isDaylightSearch=!isDaylightSearch;
        if(isDaylightSearch){
            HudManager.getInstance().getHudEvent().daylightingStatuOpen();
        }else {
            HudManager.getInstance().getHudEvent().daylightingStatuClose();
        }
    }

    public void setDaylightSearch(boolean isDaylightSearch){
        this.isDaylightSearch=isDaylightSearch;
    }

    public boolean isDaylightSearch(){
        return isDaylightSearch;
    }


}
