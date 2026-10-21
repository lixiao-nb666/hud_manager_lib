package com.nrmyw.hud_manager_lib.config;

public class HudUrlConfig {


    public static String getHudT700UpdateUrl(boolean isDebug){
        if(isDebug){
            return "https://hud-map.oss-cn-hongkong.aliyuncs.com/update/hut_700_gui_3_debug";
        }else {
            return "https://hud-map.oss-cn-hongkong.aliyuncs.com/update/hut_700_gui_3";
        }
    }

    public static String getHudT900UpdateUrl(boolean isDebug){
        if(isDebug){
            return "https://hud-map.oss-cn-hongkong.aliyuncs.com/update/hud_850_gui_3_debug";
        }else {
            return "https://hud-map.oss-cn-hongkong.aliyuncs.com/update/hud_850_gui_3";
        }
    }
}
