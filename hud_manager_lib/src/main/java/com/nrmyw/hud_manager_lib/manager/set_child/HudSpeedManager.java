package com.nrmyw.hud_manager_lib.manager.set_child;

import com.nrmyw.hud_data_lib.type.speed.HudSpeedingShowBJType;
import com.nrmyw.hud_data_lib.type.speed.HudSpeedingTextType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.config.HudUserSetConfig;

public class HudSpeedManager {
    private static HudSpeedManager hudSpeedManager;

    private HudSpeedManager(){}

    public static HudSpeedManager getInstance(){
        if(null==hudSpeedManager){
            synchronized (HudSpeedManager.class){
                if(null==hudSpeedManager){
                    hudSpeedManager=new HudSpeedManager();
                }
            }
        }
        return hudSpeedManager;
    }

    private int lastSpeed;
    private long lastSendSpeedTime;
    private int limitSpeed1;
    private int limitSpeed2;
    public void setNowSpeed(int nowSpeed){
        if(lastSendSpeedTime!=0&&lastSpeed==nowSpeed&&System.currentTimeMillis()-lastSendSpeedTime<3000){
            return;
        }
        this.lastSpeed=nowSpeed;
        HudManager.getInstance().getHudEvent().sendNowSpeed(this.lastSpeed);
        lastSpeed=nowSpeed;
        lastSendSpeedTime=System.currentTimeMillis();
        countShowSpeedBj();
    }


    public void setLimitSpeedToCountShowSpeedBj(int limitSpeed1){
        if(this.limitSpeed1==limitSpeed1&&this.limitSpeed2==0){
            return;
        }
        this.limitSpeed1=limitSpeed1;
        this.limitSpeed2=0;
        HudManager.getInstance().getHudEvent().sendNowSpeed(lastSpeed,this.limitSpeed1,this.limitSpeed2);
        countShowSpeedBj();
    }

    public void setLimitSpeedToCountShowSpeedBj(int limitSpeed1,int limitSpeed2){
        if(this.limitSpeed1==limitSpeed1&&this.limitSpeed2==limitSpeed2){
            return;
        }
        this.limitSpeed1=limitSpeed1;
        this.limitSpeed2=limitSpeed2;
        HudManager.getInstance().getHudEvent().sendNowSpeed(lastSpeed,this.limitSpeed1,this.limitSpeed2);
        countShowSpeedBj();
    }

    private void countShowSpeedBj(){
        if(!HudUserSetConfig.getInstance().getUserConfigBean().isCheckSpeeding()){
            return;
        }
        if(limitSpeed1==0||lastSpeed==0){
            return;
        }
        if(lastSpeed>limitSpeed1){
            HudManager.getInstance().getHudEvent().sendSpeeding(HudSpeedingTextType.WRITE, HudSpeedingShowBJType.NONE);
        }else {
            if(lastSpeed<limitSpeed1*1.2){
                HudManager.getInstance().getHudEvent().sendSpeeding(HudSpeedingTextType.RED, HudSpeedingShowBJType.NONE);
            }else if(lastSpeed<limitSpeed1*1.3){
                HudManager.getInstance().getHudEvent().sendSpeeding(HudSpeedingTextType.RED, HudSpeedingShowBJType.RED_40);
            }else if(lastSpeed<limitSpeed1*1.4){
                HudManager.getInstance().getHudEvent().sendSpeeding(HudSpeedingTextType.RED, HudSpeedingShowBJType.RED_55);
            }else {
                HudManager.getInstance().getHudEvent().sendSpeeding(HudSpeedingTextType.RED, HudSpeedingShowBJType.RED_80);
            }
        }
    }


}
