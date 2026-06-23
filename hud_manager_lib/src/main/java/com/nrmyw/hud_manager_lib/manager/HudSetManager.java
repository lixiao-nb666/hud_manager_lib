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
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxW(200);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxH(260);
                BleSendBitmapQualityType.HIGH.setZoomScaling(0.7f);
                BleSendBitmapQualityType.HIGH.setQualityV(30);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setBleSendBitmapQualityType(BleSendBitmapQualityType.HIGH);
                //2进度条不能显示
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setCanNotShowProgress(true);
                //3设置不能自动显示新旧转向图标
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setAutoChangerTrunTypeOldAndNew(false);
                //4.设置能显示大警示图
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setNeedBigWarningPoint(true);
                //5.一个单图标的时候，显示大图标
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setOneShowBigWarningPoint(false);
                //6.设置隐藏区间限速，使用警示图指令，而不是单独指令
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(true);
                //7.设置自动隐藏区间限速
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
                break;
            case T900:
            case T850:
            case H5000:
                //1设置图片画面
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxW(155);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxH(240);//320
                BleSendBitmapQualityType.ULTRA_HIGH.setZoomScaling(0.8f);
                BleSendBitmapQualityType.ULTRA_HIGH.setQualityV(66);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setBleSendBitmapQualityType(BleSendBitmapQualityType.ULTRA_HIGH);
                //2设置进度条
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setProgressMaxW(14);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setProgressMaxH(238);
                BleSendBitmapQualityType.PROGRESS.setQualityV(8);
                BleSendBitmapQualityType.PROGRESS.setZoomScaling(1f);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setBleSendProgressQualityType(BleSendBitmapQualityType.PROGRESS);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setCanNotShowProgress(false);
                //3设置不能自动显示新旧转向图标
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setAutoChangerTrunTypeOldAndNew(false);
                //4.设置能显示大警示图
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setNeedBigWarningPoint(true);
                //5.一个单图标的时候，显示大图标
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setOneShowBigWarningPoint(false);
                //6.设置隐藏区间限速，使用警示图指令，而不是单独指令,只有这里和T800不一样
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
                //7.设置自动隐藏区间限速
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
                break;
            case T700_GUI3:
                //1设置图片画面
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxW(360);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxH(320);//320
                BleSendBitmapQualityType.ULTRA_HIGH.setZoomScaling(0.8f);
                BleSendBitmapQualityType.ULTRA_HIGH.setQualityV(66);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setBleSendBitmapQualityType(BleSendBitmapQualityType.ULTRA_HIGH);
                //2设置进度条
                HudManager.getInstance().getHudSetConfig().setProgressMaxW(22);
                HudManager.getInstance().getHudSetConfig().setProgressMaxH(320);
                BleSendBitmapQualityType.PROGRESS.setQualityV(8);
                BleSendBitmapQualityType.PROGRESS.setZoomScaling(1f);
                HudManager.getInstance().getHudSetConfig().setBleSendProgressQualityType(BleSendBitmapQualityType.PROGRESS);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setCanNotShowProgress(false);
//        HudManager.getInstance().getHudSetConfig().setTimeDifference(-60*60);
                //3设置不能自动显示新旧转向图标
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setAutoChangerTrunTypeOldAndNew(true);
                //4.设置能显示大警示图
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setNeedBigWarningPoint(false);
                //5.一个单图标的时候，显示大图标
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setOneShowBigWarningPoint(false);
                //6.设置隐藏区间限速，使用警示图指令，而不是单独指令,只有这里和T800不一样
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
                //7.设置自动隐藏区间限速
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
                break;
            case T700:
            default:
                //1设置图片画面
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxW(360);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setImageMaxH(320);//320
                BleSendBitmapQualityType.ULTRA_HIGH.setZoomScaling(0.7f);
                BleSendBitmapQualityType.ULTRA_HIGH.setQualityV(30);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setBleSendBitmapQualityType(BleSendBitmapQualityType.ULTRA_HIGH);
                //2设置进度条
                HudManager.getInstance().getHudSetConfig().setProgressMaxW(22);
                HudManager.getInstance().getHudSetConfig().setProgressMaxH(320);
                BleSendBitmapQualityType.PROGRESS.setQualityV(8);
                BleSendBitmapQualityType.PROGRESS.setZoomScaling(1f);
                HudManager.getInstance().getHudSetConfig().setBleSendProgressQualityType(BleSendBitmapQualityType.PROGRESS);
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setCanNotShowProgress(false);
//        HudManager.getInstance().getHudSetConfig().setTimeDifference(-60*60);
                //3设置不能自动显示新旧转向图标
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setAutoChangerTrunTypeOldAndNew(true);
                //4.设置能显示大警示图
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setNeedBigWarningPoint(false);
                //5.一个单图标的时候，显示大图标
                HudManager.getInstance().getHudSetConfig().getHudSetBean().setOneShowBigWarningPoint(false);
                //6.设置隐藏区间限速，使用警示图指令，而不是单独指令,只有这里和T800不一样
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(false);
                //7.设置自动隐藏区间限速
                HudManager.getInstance().getHudSetConfig().setHideIntervalSpeedUseWarningPointCmd(true);
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
