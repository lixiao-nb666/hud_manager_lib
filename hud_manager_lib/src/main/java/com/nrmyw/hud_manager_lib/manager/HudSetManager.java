package com.nrmyw.hud_manager_lib.manager;

import com.newbee.ble_lib.NewBeeBleManager;
import com.nrmyw.ble_event_lib.bean.BleDeviceBean;
import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

public class HudSetManager {

    private static HudSetManager hudSetManager;
    private HudSetConfigListen hudSetConfigListen;
    private HudSetManager(){}

    public static HudSetManager getInstance(){
        if(null==hudSetManager){
            synchronized (HudSetManager.class){
                if(null==hudSetManager){
                    hudSetManager=new HudSetManager();
                }
            }
        }
        return hudSetManager;
    }

    public void setSetConfigListen(HudSetConfigListen hudSetConfigListen){
        this.hudSetConfigListen=hudSetConfigListen;
        if(null!=this.hudSetConfigListen){
            this.hudSetConfigListen.publicConfigInit();
        }
    }

    public void nowConnectedSetConfig(){
        HudDevice hudDevice=HudManager.getInstance().getHudDevice();
        if(null==hudDevice||null==hudDevice.getSendDataType()||null==hudSetConfigListen){
            return;
        }
        switch (hudDevice.getSendDataType()){
            case T800:
                //1设置图片画面
                HudManager.getInstance().getHudSetConfig().setImageMaxW(156);
                HudManager.getInstance().getHudSetConfig().setImageMaxH(232);
                BleSendBitmapQualityType.HIGH.setZoomScaling(0.9f);
                BleSendBitmapQualityType.HIGH.setQualityV(30);
                HudManager.getInstance().getHudSetConfig().setBleSendBitmapQualityType(BleSendBitmapQualityType.HIGH);

                //2进度条不用管
                //3设置基本参数
                HudManager.getInstance().getHudSetConfig().setAutoChangerTrunTypeOldAndNew(false);
                HudManager.getInstance().getHudSetConfig().setNeedBigWarningPoint(true);
                HudManager.getInstance().getHudSetConfig().setOneShowBigWarningPoint(true);
                HudManager.getInstance().getHudSetConfig().setCanNotShowProgress(true);
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(true);
                break;
            case T900:
                //1设置图片画面
                HudManager.getInstance().getHudSetConfig().setImageMaxW(150);
                HudManager.getInstance().getHudSetConfig().setImageMaxH(238);//320
                BleSendBitmapQualityType.HIGH.setZoomScaling(0.8f);
                BleSendBitmapQualityType.HIGH.setQualityV(30);
                HudManager.getInstance().getHudSetConfig().setBleSendBitmapQualityType(BleSendBitmapQualityType.HIGH);
                //2设置进度条
                HudManager.getInstance().getHudSetConfig().setProgressMaxW(18);
                HudManager.getInstance().getHudSetConfig().setProgressMaxH(238);
                BleSendBitmapQualityType.PROGRESS.setQualityV(8);
                BleSendBitmapQualityType.PROGRESS.setZoomScaling(1f);
                HudManager.getInstance().getHudSetConfig().setBleSendProgressQualityType(BleSendBitmapQualityType.PROGRESS);
//        HudManager.getInstance().getHudSetConfig().setTimeDifference(-60*60);

                //3设置基础参数
                HudManager.getInstance().getHudSetConfig().setAutoChangerTrunTypeOldAndNew(false);
                HudManager.getInstance().getHudSetConfig().setNeedBigWarningPoint(true);
                HudManager.getInstance().getHudSetConfig().setOneShowBigWarningPoint(true);
                HudManager.getInstance().getHudSetConfig().setCanNotShowProgress(false);
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
                break;
            case T700_GUI3:
                //1设置图片画面
                HudManager.getInstance().getHudSetConfig().setImageMaxW(360);
                HudManager.getInstance().getHudSetConfig().setImageMaxH(320);//320
                BleSendBitmapQualityType.ULTRA_HIGH.setZoomScaling(0.7f);
                BleSendBitmapQualityType.ULTRA_HIGH.setQualityV(30);
                HudManager.getInstance().getHudSetConfig().setBleSendBitmapQualityType(BleSendBitmapQualityType.ULTRA_HIGH);
                //2设置进度条
                HudManager.getInstance().getHudSetConfig().setProgressMaxW(22);
                HudManager.getInstance().getHudSetConfig().setProgressMaxH(320);
                BleSendBitmapQualityType.PROGRESS.setQualityV(8);
                BleSendBitmapQualityType.PROGRESS.setZoomScaling(1f);
                HudManager.getInstance().getHudSetConfig().setBleSendProgressQualityType(BleSendBitmapQualityType.PROGRESS);
//        HudManager.getInstance().getHudSetConfig().setTimeDifference(-60*60);

                //3设置基础参数
                HudManager.getInstance().getHudSetConfig().setAutoChangerTrunTypeOldAndNew(false);
                HudManager.getInstance().getHudSetConfig().setNeedBigWarningPoint(false);
                HudManager.getInstance().getHudSetConfig().setOneShowBigWarningPoint(false);
                HudManager.getInstance().getHudSetConfig().setCanNotShowProgress(false);
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
                break;
            case T700:
            default:
                //1设置图片画面
                HudManager.getInstance().getHudSetConfig().setImageMaxW(360);
                HudManager.getInstance().getHudSetConfig().setImageMaxH(320);//320
                BleSendBitmapQualityType.HIGH.setZoomScaling(0.7f);
                BleSendBitmapQualityType.HIGH.setQualityV(30);
                HudManager.getInstance().getHudSetConfig().setBleSendBitmapQualityType(BleSendBitmapQualityType.HIGH);
                //2设置进度条
                HudManager.getInstance().getHudSetConfig().setProgressMaxW(22);
                HudManager.getInstance().getHudSetConfig().setProgressMaxH(320);
                BleSendBitmapQualityType.PROGRESS.setQualityV(8);
                BleSendBitmapQualityType.PROGRESS.setZoomScaling(1f);
                HudManager.getInstance().getHudSetConfig().setBleSendProgressQualityType(BleSendBitmapQualityType.PROGRESS);
//        HudManager.getInstance().getHudSetConfig().setTimeDifference(-60*60);

                //3设置基础参数
                HudManager.getInstance().getHudSetConfig().setAutoChangerTrunTypeOldAndNew(false);
                HudManager.getInstance().getHudSetConfig().setNeedBigWarningPoint(true);
                HudManager.getInstance().getHudSetConfig().setOneShowBigWarningPoint(false);
                HudManager.getInstance().getHudSetConfig().setCanNotShowProgress(false);
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
                break;
        }
        hudSetConfigListen.nowConnectedResetConfig(hudDevice, NewBeeBleManager.getInstance().getNowUseBleDevice());
    }


    public void setConfigDef(){
        HudManager.getInstance().getHudSetConfig().setAutoChangerTrunTypeOldAndNew(false);
        HudManager.getInstance().getHudSetConfig().setNeedBigWarningPoint(true);
        HudManager.getInstance().getHudSetConfig().setOneShowBigWarningPoint(false);
        HudManager.getInstance().getHudSetConfig().setCanNotShowProgress(false);
        HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
    }

    public interface HudSetConfigListen{

        public void publicConfigInit();

        public void nowConnectedResetConfig(HudDevice hudDevice, BleDeviceBean bleDeviceBean);

    }
}
