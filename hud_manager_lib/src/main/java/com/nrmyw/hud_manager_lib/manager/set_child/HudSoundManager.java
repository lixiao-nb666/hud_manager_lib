package com.nrmyw.hud_manager_lib.manager.set_child;

import com.nrmyw.hud_manager_lib.HudManager;

public class HudSoundManager {

    private static HudSoundManager hudSoundManager;
    private int nowSound=-1;

    private int minV=0;
    private int maxV=10;
    private HudSoundManager(){}

    public static HudSoundManager getInstance(){
        if(null==hudSoundManager){
            synchronized (HudSoundManager.class) {
                if(null==hudSoundManager){
                    hudSoundManager=new HudSoundManager();
                }
            }
        }
        return hudSoundManager;
    }

    public void connectedToQue(){
        HudManager.getInstance().getHudEvent().queSound();
    }


    public void clear(){
        nowSound=-1;
    }

    public int getNowSound() {
        return nowSound;
    }

    public void setNowSound(int nowSound) {
        setNowSound(nowSound,false);
    }

    public void setNowSound(int nowSound,boolean needQue) {
        this.nowSound = nowSound;
        nowSendV();
        if(needQue){
            connectedToQue();
        }
    }

    public void add(){
        add(false);
    }

    public void add(boolean needQue){
        if(nowSound==-1){
            connectedToQue();
            return;
        }
        nowSound++;
        if(nowSound>maxV){
            nowSound=minV;
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
        if(nowSound==-1){
            connectedToQue();
            return;
        }
        nowSound--;
        if(nowSound<minV){
            nowSound=maxV;
        }
        nowSendV();
        if(needQue){
            connectedToQue();
        }
    }

    public void returnSoundV(int soundV){
        nowSound=soundV;
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
        if(nowSound<minV){
            nowSound=minV;
        }else if(nowSound>maxV){
            nowSound=maxV;
        }
        HudManager.getInstance().getHudEvent().setSound(nowSound);
    }


}
