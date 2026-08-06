package com.nrmyw.hud_manager_lib.manager.set_child;

import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.config.HudUserSetConfig;

public class HudTurnTypeManager {
    private static HudTurnTypeManager hudTurnTypeManager;
    private HudTurnTypeManager(){}

    public static HudTurnTypeManager getInstance(){
        if(null==hudTurnTypeManager){
            synchronized (HudTurnTypeManager.class){
                if(null==hudTurnTypeManager){
                    hudTurnTypeManager=new HudTurnTypeManager();
                }
            }
        }
        return hudTurnTypeManager;
    }


    public void sendTurnType(HudTurnType var1, int var2){
        var2=getNeedM(var2);
        HudManager.getInstance().getHudEvent().sendTurnType(var1,var2);
        if(HudUserSetConfig.getInstance().getUserConfigBean().isTurnAutoFlicker()){
            checkToFlicker(var1,var2);
        }

    }

    public void sendTurnType(HudTurnType var1, int var2,HudTurnType var3, int var4){
        var2=getNeedM(var2);
        var4=getNeedM(var4);
        HudManager.getInstance().getHudEvent().sendTurnType(var1,var2,var3,var4);
        if(HudUserSetConfig.getInstance().getUserConfigBean().isTurnAutoFlicker()){
            checkToFlicker(var1,var2);
        }
    }

    private HudTurnType lastTurnType;
    private int startDis;
    private int flickerDis=100;
    private boolean nowIsFlicker;
    private long lastSendFlickerTime;
    private void checkToFlicker(HudTurnType var1, int var2){
        if(null==var1){
            return;
        }
        //先计算闪烁开始的距离
        if(null==lastTurnType||lastTurnType!=var1){
            lastTurnType=var1;
            startDis=var2;
            countFlickerDis();
        }else {
            if(var2>startDis){
                startDis=var2;
                countFlickerDis();
            }
        }
        if(flickerDis<100){
            flickerDis=100;
        }
        //然后计算是否需要闪烁
        boolean needFlicker=false;
        if(var1!=HudTurnType.none&&var2<=flickerDis){
            needFlicker=true;
        }
        if(needFlicker==nowIsFlicker) {
   
            return;
        }
        nowIsFlicker=needFlicker;
        lastSendFlickerTime=System.currentTimeMillis();
        if(nowIsFlicker){
            HudManager.getInstance().getHudEvent().iconFlicherClose();
        }else {
            HudManager.getInstance().getHudEvent().iconFlicherOpen();
        }
    }

    private void countFlickerDis(){
        flickerDis=100;
        if(startDis>40000){
            flickerDis=2000;
        } else if(startDis>20000){
            flickerDis=1200;
        }else if(startDis>10000){
            flickerDis=666;
        }else if(startDis>5000){
            flickerDis=200;
        }else if(startDis>2000){
            flickerDis=150;
        }
    }

    private int getNeedM(int vM){
        try {
//            &&vM<HudUserSetConfig.getInstance().getUserConfigBean().getTurnMCha()
            if(HudUserSetConfig.getInstance().getUserConfigBean().getTurnMCha()==0||vM>HudUserSetConfig.getInstance().getUserConfigBean().getTurnMCha()){
                return vM;
            }
            if(vM>HudUserSetConfig.TURN_M_CHA_CAN_USE){
                return vM;
            }
            if(vM<1){
                return 0;
            }
            if(vM<=6){
                vM= 1;
            }else if(vM<=10){
                vM=  2;
            }else if(vM<=16){
                vM= 4;
            }else if(vM<=24){
                vM= 7;
            }else if(vM<=30){
                //显示范围值为： 15-10
                vM= (int) (vM/2);
            }else if(vM<=40){
                //显示范围值为： 26-20
                vM= (int) (vM/1.5);
            }else if(vM<=50){
                //显示范围值为： 38-31
                vM= (int) (vM/1.3);
            }else {
                //显示范围值为：55-42
                vM= (int) (vM/1.2);
            }
        }catch (Exception e){
        }
        return vM;
    }
}
