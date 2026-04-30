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
    }

    public void sendTurnType(HudTurnType var1, int var2,HudTurnType var3, int var4){
        var2=getNeedM(var2);
        var4=getNeedM(var4);
        HudManager.getInstance().getHudEvent().sendTurnType(var1,var2,var3,var4);
    }

    private int getNeedM(int vM){
        vM=vM- HudUserSetConfig.getInstance().getUserConfigBean().getTurnMCha();
        if(vM<0){
            vM=0;
        }
        return vM;
    }
}
