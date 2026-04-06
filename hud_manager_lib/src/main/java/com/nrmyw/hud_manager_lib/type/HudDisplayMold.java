package com.nrmyw.hud_manager_lib.type;

public enum HudDisplayMold {
    USER_DEF(1,73),
    NUMB_1(1,50),
    NUMB_2(5,55),
    NUMB_3(10,60),
    NUMB_4(15,65),
    NUMB_5(20,70),
    NUMB_6(25,75),
    NUMB_7(30,80),
    NUMB_8(35,85),
    NUMB_9(40,90),
    NUMB_10(45,95),

    USER_MAX_1_TO_100(1,100),
    DAY(60,80),
    NIGHT(10,48),
    DAT_NIGHT_CHANGE(28,62),
    REFLECTOR_USER_SET(20,93),
    REFLECTOR_DAY(70,100),
    REFLECTOR_NIGHT(30,65),
    REFLECTOR_DAT_NIGHT_CHANGE(40,73),

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
