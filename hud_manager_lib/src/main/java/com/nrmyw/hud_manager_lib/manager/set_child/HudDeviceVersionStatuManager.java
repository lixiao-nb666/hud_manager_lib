package com.nrmyw.hud_manager_lib.manager.set_child;

import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_manager_lib.HudManager;

public class HudDeviceVersionStatuManager {

    private static HudDeviceVersionStatuManager hudDeviceSoundStatuManager;
    private  String nowDeviceVersion;
    public HudDeviceVersionStatuManager(){
    }

    public static HudDeviceVersionStatuManager getInstance(){
        if(null==hudDeviceSoundStatuManager){
            synchronized (HudDeviceVersionStatuManager.class){
                if(null==hudDeviceSoundStatuManager){
                    hudDeviceSoundStatuManager=new HudDeviceVersionStatuManager();
                }
            }
        }
        return hudDeviceSoundStatuManager;
    }


    public void connectedToQue(){
        HudEventManager.getInstance().getHudEvent().queDeviceVersion();
    }


    public void clear(){
        nowDeviceVersion="";
    }

    public String getNowDeviceVersion() {
        return nowDeviceVersion;
    }

    public void returnNowDeviceVersion(String nowDeviceVersion){
        this.nowDeviceVersion=nowDeviceVersion;
    }


}
