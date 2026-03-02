package com.nrmyw.hud_manager_lib.type;

public enum HudDisplayReflectorMold {
    USER_SET(20,93),
    DAY(70,100),
    NIGHT(30,65),
    DAT_NIGHT_CHANGE(40,73),


    ;

    private int lowLuminance;
    private int hightLuminance;
    HudDisplayReflectorMold(int lowLuminance, int hightLuminance){
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
