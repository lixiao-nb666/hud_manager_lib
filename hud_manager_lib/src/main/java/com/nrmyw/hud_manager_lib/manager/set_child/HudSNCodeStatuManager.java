package com.nrmyw.hud_manager_lib.manager.set_child;

import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_manager_lib.HudManager;

public class HudSNCodeStatuManager {

    private static HudSNCodeStatuManager hudDeviceSoundStatuManager;
    private  String snCode;
    public HudSNCodeStatuManager(){
    }

    public static HudSNCodeStatuManager getInstance(){
        if(null==hudDeviceSoundStatuManager){
            synchronized (HudSNCodeStatuManager.class){
                if(null==hudDeviceSoundStatuManager){
                    hudDeviceSoundStatuManager=new HudSNCodeStatuManager();
                }
            }
        }
        return hudDeviceSoundStatuManager;
    }


    public void connectedToQue(){
        HudManager.getInstance().getHudEvent().queSN();
    }


    public void clear(){
        snCode="";
    }

    public String getsnCode() {
        return snCode;
    }

    public void returnsnCode(String snCode){
        this.snCode=snCode;
    }


}
