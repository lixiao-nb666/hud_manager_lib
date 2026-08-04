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
    private boolean nowIsFlicker;
    private void checkToFlicker(HudTurnType var1, int var2){
        if(null==var1){
            return;
        }
        if(nowIsFlicker){
            if(var1==HudTurnType.none||var2>100){
                nowIsFlicker=false;
                HudManager.getInstance().getHudEvent().iconFlicherClose();
            }
        }else {
            if(var1!=HudTurnType.none&&var2<=100){
                nowIsFlicker=true;
                HudManager.getInstance().getHudEvent().iconFlicherOpen();
            }
        }





    }

    private int getNeedM(int vM){
        try {
            if(vM<=HudUserSetConfig.TURN_M_CHA_CAN_USE&&HudUserSetConfig.getInstance().getUserConfigBean().getTurnMCha()!=0&&vM>1&&vM<HudUserSetConfig.getInstance().getUserConfigBean().getTurnMCha()){
                if(vM<6){
                    vM= 1;
                }else if(vM<10){
                    vM=  2;
                }else if(vM<12){
                    vM= 3;
                }else if(vM<14){
                    vM=  4;
                }else if(vM<16){
                    vM=  5;
                }else if(vM<20){
                    vM=  (int)(vM/2.0);
                }else if(vM<25){
                    vM= (int) (vM/1.5);
                }else if(vM<31){
                    vM= (int) (vM/1.3);
                }else {
                    vM= (int) (vM/1.2);
                }
            }
        }catch (Exception e){
        }
        return vM;
    }
}
