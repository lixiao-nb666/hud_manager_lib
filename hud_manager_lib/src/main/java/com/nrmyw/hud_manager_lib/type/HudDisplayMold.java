package com.nrmyw.hud_manager_lib.type;

public enum HudDisplayMold {
    USER_SET(1,73),
    DAY(60,80),
    NIGHT(10,48),
    DAT_NIGHT_CHANGE(28,62),


    ;

    private int lowLuminance;
    private int hightLuminance;
    HudDisplayMold(int lowLuminance,int hightLuminance){
        this.lowLuminance=lowLuminance;
        this.hightLuminance=hightLuminance;
    }

    public int getLowLuminance() {
        return lowLuminance;
    }

    public void setLowLuminance(int lowLuminance) {
        this.lowLuminance = lowLuminance;
    }

    public int getHightLuminance() {
        return hightLuminance;
    }

    public void setHightLuminance(int hightLuminance) {
        this.hightLuminance = hightLuminance;
    }


}
