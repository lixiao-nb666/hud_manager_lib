package com.nrmyw.hud_manager_lib.bean;

import java.io.Serializable;

public class HudUserConfigBean implements Serializable {

        private int nowBrightnessV;
        private int brightnessMaxV=10;
        private int brightnessMinV=10;

        private int nowSoundV;

        private int deviceSoundStatu;

    public int getNowBrightnessV() {
        return nowBrightnessV;
    }

    public void setNowBrightnessV(int nowBrightnessV) {
        this.nowBrightnessV = nowBrightnessV;
    }

    public int getBrightnessMaxV() {
        return brightnessMaxV;
    }

    public void setBrightnessMaxV(int brightnessMaxV) {
        this.brightnessMaxV = brightnessMaxV;
    }

    public int getBrightnessMinV() {
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



    @Override
    public String toString() {
        return "HudUserConfigBean{" +
                "nowBrightnessV=" + nowBrightnessV +
                ", brightnessMaxV=" + brightnessMaxV +
                ", brightnessMinV=" + brightnessMinV +
                ", nowSoundV=" + nowSoundV +
                '}';
    }
}
