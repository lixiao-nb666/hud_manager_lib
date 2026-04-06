package com.nrmyw.hud_manager_lib.manager.set_child;

import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_manager_lib.HudManager;

public class HudDeviceSoundStatuManager {

    private static HudDeviceSoundStatuManager hudDeviceSoundStatuManager;
    private     int nowDeviceSoundStatu=-1;
    public HudDeviceSoundStatuManager(){
    }

    public static HudDeviceSoundStatuManager getInstance(){
        if(null==hudDeviceSoundStatuManager){
            synchronized (HudDeviceSoundStatuManager.class){
                if(null==hudDeviceSoundStatuManager){
                    hudDeviceSoundStatuManager=new HudDeviceSoundStatuManager();
                }
            }
        }
        return hudDeviceSoundStatuManager;
    }


    public void connectedToQue(){
        HudManager.getInstance().getHudEvent().queDeviceSound();
    }


    public void clear(){
        nowDeviceSoundStatu=-1;
    }

    public int getNowDeviceSoundStatu() {
        return nowDeviceSoundStatu;
    }

    public void setNowDeviceSoundStatu(int nowDeviceSoundStatu) {
        setnowDeviceSoundStatu(nowDeviceSoundStatu,false);
    }

    public void setnowDeviceSoundStatu(int nowDeviceSoundStatu,boolean needQue) {
        this.nowDeviceSoundStatu = nowDeviceSoundStatu;
        nowSendV();
        if(needQue){
            connectedToQue();
        }
    }



    public void update(){
        update(false);
    }

    public void update(boolean needQue){
        if(nowDeviceSoundStatu==-1){
            connectedToQue();
            return;
        }
        if(nowDeviceSoundStatu==0){
            nowDeviceSoundStatu=1;
        }else {
            nowDeviceSoundStatu=0;
        }
        nowSendV();
        if(needQue){
            connectedToQue();
        }
    }

    public void returnNowDeviceSoundStatuV(int nowDeviceSoundStatuV){
        this.nowDeviceSoundStatu=nowDeviceSoundStatuV;
    }




    private void nowSendV(){
        boolean isOpen=nowDeviceSoundStatu==0?false:true;
        if(isOpen){
            HudEventManager.getInstance().getHudEvent().deviceSoundOpen();
        }else {
            HudEventManager.getInstance().getHudEvent().deviceSoundClose();
        }
    }
}
