package com.nrmyw.hud_manager_lib;

import android.content.Context;

import com.newbee.ble_lib.NewBeeBleManager;

import com.nrmyw.ble_event_lib.config.NewBeeBleConfig;
import com.nrmyw.ble_event_lib.send.BleEventObserver;

import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
import com.nrmyw.hud_data_event_lib.HudEventImp;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_event_lib.config.HudSetConfig;
import com.nrmyw.hud_data_lib.config.HudConfig;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventObserver;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventSubscriptionSubject;
import com.nrmyw.hud_manager_lib.hud.HudDataEvent;
import com.nrmyw.hud_manager_lib.manager.set_child.HudBrightnessManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudDeviceSoundStatuManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudDeviceVersionStatuManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudSNCodeStatuManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudSoundManager;

import com.nrmyw.hud_manager_lib.manager.HudSetManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

public class HudManager {
    private static HudManager hudManager;
    private BleStatuEventObserver bleStatuEventObserver=new BleStatuEventObserver() {
        @Override
        public void sendBleStatu(BleStatu bleStatu, Object... object) {
            switch (bleStatu){
                case CONNECTED:
                    HudSetManager.getInstance().nowConnectedSetConfig();
                    HudBrightnessManager.getInstance().connectedToQue();
                    HudDeviceSoundStatuManager.getInstance().connectedToQue();
                    HudDeviceVersionStatuManager.getInstance().connectedToQue();
                    HudSNCodeStatuManager.getInstance().connectedToQue();
                    HudSoundManager.getInstance().connectedToQue();
                    break;

                case DISCONNECTED:
                    HudBrightnessManager.getInstance().clear();
                    HudDeviceSoundStatuManager.getInstance().clear();
                    HudDeviceVersionStatuManager.getInstance().clear();
                    HudSNCodeStatuManager.getInstance().clear();
                    HudSoundManager.getInstance().clear();
                    break;
            }
        }



    };
    private HudRetrunEventObserver hudRetrunEventObserver=new HudRetrunEventObserver() {
        @Override
        public void none(byte[] bytes) {
            if(!canBackgroundDoRetrun){
                return;
            }
        }

        @Override
        public void getBrightnessV(int mode, int autoV, int handV) {
            if(!canBackgroundDoRetrun){
                return;
            }
            HudBrightnessManager.getInstance().returnBrightneesV(mode,autoV,handV);
        }



        @Override
        public void getSoundV(int v) {
            if(!canBackgroundDoRetrun){
                return;
            }
            HudSoundManager.getInstance().returnSoundV(v);
        }

        @Override
        public void getDeviceSoundStatu(int deviceSoundStatu) {
            if(!canBackgroundDoRetrun){
                return;
            }
            HudDeviceSoundStatuManager.getInstance().returnNowDeviceSoundStatuV(deviceSoundStatu);
        }

        @Override
        public void getSN(String sn) {
            if(!canBackgroundDoRetrun){
                return;
            }
            HudSNCodeStatuManager.getInstance().returnsnCode(sn);
        }

        @Override
        public void getDeviceVersion(String deviceVersion) {
            if(!canBackgroundDoRetrun){
                return;
            }
            HudDeviceVersionStatuManager.getInstance().returnNowDeviceVersion(deviceVersion);
        }

        @Override
        public void getGPSSpeed(int v) {
            if(!canBackgroundDoRetrun){
                return;
            }
        }
    };



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
        init(context,null);
    }


    public void init(Context context, HudSetManager.HudSetConfigListen hudSetConfigListen){
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
        HudRetrunEventSubscriptionSubject.getInstence().attach(hudRetrunEventObserver);
        NewBeeBleConfig.getInstance().init(true, HudConfig.mtu,HudConfig.serviceID,HudConfig.writeID,HudConfig.noticeID, HudDevice.getBleDeviceTypeList());
        NewBeeBleManager.getInstance().init(context);
        HudEventManager.getInstance().init(context);
        HudSetManager.getInstance().setSetConfigListen(hudSetConfigListen);
    }



    public void close(){
        NewBeeBleManager.getInstance().close();
        HudEventManager.getInstance().close();
        HudRetrunEventSubscriptionSubject.getInstence().detach(hudRetrunEventObserver);
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
    }


    public void nowGetAllPermissions(){
        NewBeeBleManager.getInstance().nowGetAllPermissions();

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

    public boolean deviceIsConnected(){
        return NewBeeBleManager.getInstance().isConnect();
    }

    public HudEventImp getHudEvent(){
        return HudEventManager.getInstance().getHudEvent();
    }

    public HudDataEvent getDataEvent(){
        return HudDataEvent.getInstance();
    }


    public String getUpdateUrl(boolean isDebug){
        return getUpdateUrl(getHudDevice(),isDebug);
    }

    public String getUpdateUrl(HudDevice hudDevice,boolean isDebug){
        if(null==hudDevice){
            return "";
        }
        switch (hudDevice){
            case T700:
                return HudConfig.getHudT700UpdateUrl(isDebug);
            case T900:
            case T900_1:
            case T900_2:
                return HudConfig.getHudT900UpdateUrl(isDebug);
            default:
                return "";
        }
    }

    private boolean canBackgroundDoRetrun;
    public void setCanBackgroundDoRetrun(boolean canBackgroundDoRetrun){
        this.canBackgroundDoRetrun=canBackgroundDoRetrun;
    }


}
