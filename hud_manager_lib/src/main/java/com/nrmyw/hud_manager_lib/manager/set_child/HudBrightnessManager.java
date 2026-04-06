package com.nrmyw.hud_manager_lib.manager.set_child;

import com.nrmyw.hud_manager_lib.HudManager;

public class HudBrightnessManager {

    private static HudBrightnessManager hudBrightnessManager;
    public static final int AUTO_V=0;
    private int nowBrightness=-1;
    private int minV=0;
    private int maxV=10;
    private HudBrightnessManager(){}

    public static HudBrightnessManager getInstance(){
        if(null==hudBrightnessManager){
            synchronized (HudBrightnessManager.class){
                if(null==hudBrightnessManager){
                    hudBrightnessManager=new HudBrightnessManager();
                }
            }
        }
        return hudBrightnessManager;
    }

    public void connectedToQue(){
        HudManager.getInstance().getHudEvent().queBrightness();
    }
    public void clear(){
        nowBrightness=-1;
    }

    public int getNowBrightness() {
        return nowBrightness;
    }

    public void setNowBrightness(int nowBrightness) {
        setNowBrightness(nowBrightness,false);
    }

    public void setNowBrightness(int nowBrightness,boolean needQue) {

        this.nowBrightness = nowBrightness;
        nowSendV();
        if(needQue){
            connectedToQue();
        }
    }

    public void add(){
        add(false);
    }

    public void add(boolean needQue){
        if(nowBrightness==-1){
            connectedToQue();
            return;
        }
        nowBrightness++;
        if(nowBrightness>maxV){
            nowBrightness=minV;
        }
        nowSendV();
        if(needQue){
            connectedToQue();
        }
    }

    public void reduce(){
        reduce(false);
    }

    public void reduce(boolean needQue){
        if(nowBrightness==-1){
            connectedToQue();
            return;
        }
        nowBrightness--;
        if(nowBrightness<minV){
            nowBrightness=maxV;
        }
        nowSendV();
        if(needQue){
            connectedToQue();
        }
    }

    public void returnBrightneesV(int mode, int autoV, int handV){
        if(mode==AUTO_V){
            nowBrightness=AUTO_V;
        }else {
            nowBrightness=handV;
        }
    }

    public int getMaxV() {
        return maxV;
    }

    public void setMaxV(int maxV) {
        this.maxV = maxV;
    }

    public int getMinV() {
        return minV;
    }

    public void setMinV(int minV) {
        this.minV = minV;
    }


    private void nowSendV(){

        if(nowBrightness<minV){
            nowBrightness=minV;
        }else if(nowBrightness>maxV){
            nowBrightness=maxV;
        }
        if(nowBrightness==AUTO_V){
            HudManager.getInstance().getHudEvent().sendBrightnessAuto();
        }else {
            HudManager.getInstance().getHudEvent().sendBrightnessHand(nowBrightness);
        }
    }
}
