package com.nrmyw.hud_manager_lib.type;

import com.nrmyw.ble_event_lib.bean.BleDeviceBean;

import java.util.ArrayList;
import java.util.List;

public enum HudDevice {
    T800("T800","data",HudDeviceSendDataType.T800),
    T700("T800","DATA",HudDeviceSendDataType.T700),
    ;
    private String title;
    private String body;
    private HudDeviceSendDataType sendDataType;
    HudDevice(String title, String body,HudDeviceSendDataType hudDeviceSendDataType){
        this.title=title;
        this.body=body;
        this.sendDataType=hudDeviceSendDataType;
    }

    public BleDeviceBean getBleDevice(){
        BleDeviceBean bleDeviceBean=new BleDeviceBean();
        bleDeviceBean.setDeviceName(name());
        bleDeviceBean.setDeviceType(ordinal());
        bleDeviceBean.setDeviceTitle(title);
        bleDeviceBean.setDeviceBody(body);
        return bleDeviceBean;
    }

    public HudDeviceSendDataType getSendDataType() {
        return sendDataType;
    }



    public static List<BleDeviceBean> getBleDeviceTypeList(){
        List<BleDeviceBean> typeList=new ArrayList<>();
        for(HudDevice deviceType:HudDevice.values()){
            typeList.add(deviceType.getBleDevice());
        }
        return typeList;
    }

    public static HudDevice getBleDeviceType(BleDeviceBean bleDeviceBean){
        try {
            return HudDevice.values()[bleDeviceBean.getDeviceType()];
        }catch (Exception e){
            return null;
        }
    }
}
