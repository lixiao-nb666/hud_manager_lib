package com.nrmyw.hud_manager_lib.bean;

import java.io.Serializable;

public class HudUserConfigBean implements Serializable {

        private int nowBrightnessV;
        private int brightnessMaxV=10;
        private int brightnessMinV=0;

        private int nowSoundV;

        private int deviceSoundStatu;

        private int turnMCha=10;
    public int getNowBrightnessV() {
        return nowBrightnessV;
    }

    public void setNowBrightnessV(int nowBrightnessV) {
        this.nowBrightnessV = nowBrightnessV;
    }

    public int getBrightnessMaxV() {
        if(brightnessMaxV>14){
            brightnessMaxV=14;
        }else if(brightnessMaxV<10){
            brightnessMaxV=10;
        }
        return brightnessMaxV;
    }

    public void setBrightnessMaxV(int brightnessMaxV) {

        this.brightnessMaxV = brightnessMaxV;
    }

    public int getBrightnessMinV() {
        if(brightnessMinV<0||brightnessMinV>1){
            brightnessMinV=0;
        }

        return brightnessMinV;
    }

    public void setBrightnessMinV(int brightnessMinV) {
        this.brightnessMinV = brightnessMinV;
    }

    public int getDeviceSoundStatu() {
        return deviceSoundStatu;
    }

    public void setDeviceSoundStatu(int deviceSoundStatu) {
        this.deviceSoundStatu = deviceSoundStatu;
    }
    public boolean deviceSoundIsOpen(){
        return deviceSoundStatu==1;
    }
    public int getNowSoundV() {
        return nowSoundV;
    }

    public void setNowSoundV(int nowSoundV) {
        this.nowSoundV = nowSoundV;
    }

    public int getTurnMCha() {
        return turnMCha;
    }

    public void setTurnMCha(int turnMCha) {
        this.turnMCha = turnMCha;
    }

    @Override
    public String toString() {
        return "HudUserConfigBean{" +
                "nowBrightnessV=" + nowBrightnessV +
                ", brightnessMaxV=" + brightnessMaxV +
                ", brightnessMinV=" + brightnessMinV +
                ", nowSoundV=" + nowSoundV +
                ", deviceSoundStatu=" + deviceSoundStatu +
                ", turnMCha=" + turnMCha +
                '}';
    }
}
