package com.nrmyw.hud_manager_lib;

import android.content.Context;

import com.newbee.ble_lib.NewBeeBleManager;

import com.nrmyw.ble_event_lib.config.NewBeeBleConfig;
import com.nrmyw.ble_event_lib.send.BleEventObserver;
import com.nrmyw.hud_data_event_lib.HudEventImp;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_event_lib.config.HudSetConfig;
import com.nrmyw.hud_data_lib.config.HudConfig;
import com.nrmyw.hud_manager_lib.type.HudDevice;

public class HudManager {
    private static HudManager hudManager;
    private HudDevice hudDevice;


    private HudManager(){
    }

    public static HudManager getInstance(){
        if(null==hudManager){
            synchronized (HudManager.class){
                if(null==hudManager){
                    hudManager=new HudManager();
                }
            }
        }
        return hudManager;
    }

    public void init(Context context){
        NewBeeBleConfig.getInstance().init(HudConfig.isAutomatic, HudConfig.mtu,HudConfig.serviceID,HudConfig.writeID,HudConfig.noticeID, HudDevice.getBleDeviceTypeList());
        NewBeeBleManager.getInstance().init(context);
        HudEventManager.getInstance().init(context);

    }



    public void close(){
        NewBeeBleManager.getInstance().close();
        HudEventManager.getInstance().close();
    }

    public HudSetConfig getHudSetConfig(){
        return HudSetConfig.getInstance();
    }

    public HudDevice getHudDevice(){
        return HudDevice.getBleDeviceType(NewBeeBleManager.getInstance().getNowUseBleDevice());
    }

    public BleEventObserver getBleEvent(){
       return NewBeeBleManager.getInstance().getEventImp();
    }

    public HudEventImp getHudEvent(){

        return HudEventManager.getInstance().getHudEvent();
    }




}
