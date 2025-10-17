package com.nrmyw.hud_manager_lib.hud.bean.reach;

import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.type.reach.HudReachType;
import com.nrmyw.hud_manager_lib.hud.util.HudCheckUtil;

import java.io.Serializable;

public class HudReachInfoBean implements Serializable {
    private int reachDis;
    private int reachTime;




    public void setReachInfo(int reachDis, int reachTime){
        if(HudCheckUtil.checkIntNeedChange(this.reachDis,reachDis)
                ||HudCheckUtil.checkIntNeedChange(this.reachTime,reachTime)){
            this.reachDis=reachDis;
            this.reachTime=reachTime;
            int timeM=this.reachTime/60;
            int useTimeH=timeM/60;
            int useTimeM=timeM%60;
            HudEventManager.getInstance().getHudEvent().sendReachInfo(this.reachDis,useTimeH,useTimeM, HudReachType.NONE);

        }
    }



    @Override
    public String toString() {
        return "HudReachInfoBean{" +
                "reachDis=" + reachDis +
                ", reachTime=" + reachTime +
                '}';
    }
}
