package com.nrmyw.hud_manager_lib.manager.set_child;

import com.nrmyw.hud_data_event_lib.HudEventManager;
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
        if(lastSendSpeedTime!=0&&lastSpeed==nowSpeed&&System.currentTimeMillis()-lastSendSpeedTime<2000){
            //数据一致的情况下，先不做处理
        }else {
            this.lastSpeed=nowSpeed;
            HudManager.getInstance().getHudEvent().sendNowSpeed(this.lastSpeed);
            lastSpeed=nowSpeed;
            lastSendSpeedTime=System.currentTimeMillis();
        }
        countShowSpeedBj();
    }


    public void setLimitSpeedToCountShowSpeedBj(int limitSpeed1){
        if(this.limitSpeed1==limitSpeed1&&this.limitSpeed2==0){
            //说明数据一致先不处理
        }else {
            this.limitSpeed1=limitSpeed1;
            this.limitSpeed2=0;
            HudManager.getInstance().getHudEvent().sendNowSpeed(lastSpeed,this.limitSpeed1,this.limitSpeed2);
        }
        countShowSpeedBj();
    }

    public void setLimitSpeedToCountShowSpeedBj(int limitSpeed1,int limitSpeed2){
        if(this.limitSpeed1==limitSpeed1&&this.limitSpeed2==limitSpeed2){
            //说明数据一致先不处理
        }else {
            this.limitSpeed1=limitSpeed1;
            this.limitSpeed2=limitSpeed2;
            HudManager.getInstance().getHudEvent().sendNowSpeed(lastSpeed,this.limitSpeed1,this.limitSpeed2);
        }
        countShowSpeedBj();
    }


    private HudSpeedingTextType speedingTextType=HudSpeedingTextType.WRITE;
    private HudSpeedingShowBJType speedingShowBJType=HudSpeedingShowBJType.NONE;
    private long lastSendSpeedingTime;
    private void countShowSpeedBj(){
        if(!HudUserSetConfig.getInstance().getUserConfigBean().isCheckSpeeding()){
            return;
        }
        HudSpeedingTextType nowSpeedingTextType=HudSpeedingTextType.WRITE;
        HudSpeedingShowBJType nowSpeedingShowBJType=HudSpeedingShowBJType.NONE;
        if(limitSpeed1==0){
            //默认参数即可
        }else {
            if(lastSpeed<limitSpeed1){
                //默认参数即可
            }else {
                if(lastSpeed<limitSpeed1*1.2){
                    nowSpeedingTextType=HudSpeedingTextType.RED;
                    nowSpeedingShowBJType=HudSpeedingShowBJType.NONE;
                }else if(lastSpeed<limitSpeed1*1.3){
                    nowSpeedingTextType=HudSpeedingTextType.RED;
                    nowSpeedingShowBJType=HudSpeedingShowBJType.RED_40;
                }else if(lastSpeed<limitSpeed1*1.4){
                    nowSpeedingTextType=HudSpeedingTextType.RED;
                    nowSpeedingShowBJType=HudSpeedingShowBJType.RED_55;
                }else {
                    nowSpeedingTextType=HudSpeedingTextType.RED;
                    nowSpeedingShowBJType=HudSpeedingShowBJType.RED_80;

                }
            }
        }
        if(speedingTextType==nowSpeedingTextType&&speedingShowBJType==nowSpeedingShowBJType&&System.currentTimeMillis()-lastSendSpeedingTime<2000){
            return;
        }
        this.speedingTextType=nowSpeedingTextType;
        this.speedingShowBJType=nowSpeedingShowBJType;
        this.lastSendSpeedingTime=System.currentTimeMillis();
        HudManager.getInstance().getHudEvent().sendSpeeding(nowSpeedingTextType, nowSpeedingShowBJType);
    }


    public void setIntervalSpeed(int intervalSpeed,int interval ,int averageSpeed){
        this.limitSpeed1=intervalSpeed;
        double km=interval/1000;
        double needTimeH=km/averageSpeed;
        int needM= (int) (needTimeH*60);
//            int timeM=km/averageSpeed*60;
        int timeHours=needM/60;
        int timeMin=needM%60;
        HudEventManager.getInstance().getHudEvent().sendIntervalSpeed(intervalSpeed,interval,averageSpeed,timeHours,timeMin);
        countShowSpeedBj();
//            if(interval<50){
//                needCheckIntervalSpeed=true;
//                lastNeedCheckTime=System.currentTimeMillis();
//            }else {
//                needCheckIntervalSpeed=false;
//            }
    }


}
