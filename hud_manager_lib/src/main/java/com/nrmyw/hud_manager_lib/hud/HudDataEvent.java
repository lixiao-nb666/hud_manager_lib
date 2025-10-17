package com.nrmyw.hud_manager_lib.hud;


import com.nrmyw.hud_data_lib.bean.HudLaneCountBean;
import com.nrmyw.hud_data_lib.bean.HudLaneHiPassCountBean;
import com.nrmyw.hud_data_lib.type.lane.HudNowLaneStrType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnBjType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_manager_lib.hud.bean.HudDataInfoBean;

public class HudDataEvent {
    private static HudDataEvent hudDataEvent;
    private HudDataInfoBean hudDataInfoBean=new HudDataInfoBean();
    private HudDataEvent(){}

    public static HudDataEvent getInstance(){
        if(null==hudDataEvent){
            synchronized (HudDataEvent.class){
                if(null==hudDataEvent){
                    hudDataEvent=new HudDataEvent();
                }
            }
        }
        return hudDataEvent;
    }

    public void setTime(long time){
        hudDataInfoBean.getTimeBean().setNowTime(time);
    }

    private boolean nowCheckIng;
    private void startCheck(){
        nowCheckIng=true;
    }

    private void stopCheck(){
        nowCheckIng=false;
    }
    private void check(){
        if(nowCheckIng){
            hudDataInfoBean.getIntervalSpeedBean().check();
        }

    }

    public void start(int ss){
        startCheck();
    }

    public void setEnd(){
        stopCheck();
    }


    public void setNowSpeed(int nowSpeed){
        hudDataInfoBean.getSpeedBean().setNowSpeed(nowSpeed);
    }

    public void setNowSpeed(int nowSpeed,int limitedSpeed1 ,int limitedSpeed2){
        hudDataInfoBean.getSpeedBean().setNowSpeed(nowSpeed,limitedSpeed1,limitedSpeed2);
    }

    public void setIntervalSpeed(int intervalSpeed,int interval ,int averageSpeed){
        hudDataInfoBean.getIntervalSpeedBean().setIntervalSpeed(intervalSpeed,interval,averageSpeed);
    }

    public void setIntervalSpeedOver(){
        hudDataInfoBean.getIntervalSpeedBean().setIntervalSpeedOver();
    }

    public void sendWarningPoint(HudWarningPointType type1, int distance1){
        hudDataInfoBean.getWarningPointBean().setWarningPoint(type1,distance1);
    }

    public void sendWarningPoint(HudWarningPointType type1,int distance1,HudWarningPointType type2,int distance2){
        hudDataInfoBean.getWarningPointBean().setWarningPoint(type1,distance1,type2,distance2);
    }

    public void setReachInfo(int reachDis,int reachTime){
        hudDataInfoBean.getReachInfoBean().setReachInfo(reachDis,reachTime);
        check();
    }

    public void setLaneHide(){
        hudDataInfoBean.getLaneInfoBean().setLaneHide();
    }

    public void setLaneCountBean(HudLaneCountBean hudLaneCountBean){
        hudDataInfoBean.getLaneInfoBean().setLaneCountBean(hudLaneCountBean);
    }

    public void setLaneHiPassCountBean(HudLaneHiPassCountBean hudLaneHiPassCountBean){
        hudDataInfoBean.getLaneInfoBean().setLaneHiPassCountBean(hudLaneHiPassCountBean);
    }

    public void setTurnType(HudTurnType type1, int distance1){
        hudDataInfoBean.getTurnBean().setTurnType(type1,distance1);
    }

    public void setTurnType(HudTurnType type1, int distance1,HudTurnType type2, int distance2){
        hudDataInfoBean.getTurnBean().setTurnType(type1,distance1,type2,distance2);
    }

    public void setTurnBj(HudTurnBjType turnBj){
        hudDataInfoBean.getTurnBean().setTurnBj(turnBj);
    }

    public void setNextLaneName(String nextLaneName){
        hudDataInfoBean.getTurnBean().setNextLaneName(nextLaneName);
    }

    public void setNowLane(HudNowLaneStrType nowLaneStrType, String showString){
        hudDataInfoBean.getNowLaneBean().setNowLane(nowLaneStrType,showString);
    }




}
