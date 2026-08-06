package com.nrmyw.hud_manager_lib.config;

import com.nrmyw.hud_manager_lib.bean.HudUserConfigBean;

public class HudUserSetConfig {
    private static HudUserSetConfig hudUserSetConfig;

    public static final int TURN_M_CHA_CAN_USE=66;
    public static final int TURN_M_CHA_MAX=66;
    public static final int TURN_M_CHA_MIN=0;




    private HudUserConfigBean userConfigBean;
    private HudUserSetConfig(){

    }

    public static HudUserSetConfig getInstance(){
        if(null==hudUserSetConfig){
            synchronized (HudUserSetConfig.class){
                if(null==hudUserSetConfig){
                    hudUserSetConfig=new HudUserSetConfig();
                }
            }
        }
        return hudUserSetConfig;
    }

    public void setUserConfigBean(HudUserConfigBean hudUserConfigBean){
        this.userConfigBean=hudUserConfigBean;
    }


    public HudUserConfigBean getUserConfigBean(){
        if(null==userConfigBean){
            userConfigBean=new HudUserConfigBean();
        }
        return userConfigBean;
    }


}
