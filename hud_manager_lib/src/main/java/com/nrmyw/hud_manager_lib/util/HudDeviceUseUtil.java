package com.nrmyw.hud_manager_lib.util;

import com.nrmyw.ble_event_lib.bean.BleDeviceBean;
import com.nrmyw.hud_manager_lib.type.HudDevice;

import java.util.ArrayList;
import java.util.List;

public class HudDeviceUseUtil {

    public static List<BleDeviceBean> getAllList(){
        return HudDevice.getBleDeviceTypeList();
    }


    public static List<BleDeviceBean> getH5000List(){
        List<BleDeviceBean> typeList=new ArrayList<>();
        typeList.add(HudDevice.H5000.getBleDevice());
        typeList.add(HudDevice.H5000_1.getBleDevice());
        typeList.add(HudDevice.H5000_2.getBleDevice());
        return typeList;
    }

    public static List<BleDeviceBean> getT850List(){
        List<BleDeviceBean> typeList=new ArrayList<>();
        typeList.add(HudDevice.T850.getBleDevice());
        typeList.add(HudDevice.T850_1.getBleDevice());
        typeList.add(HudDevice.T850_2.getBleDevice());
        return typeList;
    }
}
