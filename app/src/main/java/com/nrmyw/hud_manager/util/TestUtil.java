package com.nrmyw.hud_manager.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.newbee.bulid_lib.util.RandomUtil;
import com.nrmyw.hud_data_lib.bean.HudLaneCountBean;
import com.nrmyw.hud_data_lib.bean.HudLaneHiPassCountBean;
import com.nrmyw.hud_data_lib.type.lane.HudLaneType;
import com.nrmyw.hud_data_lib.type.reach.HudReachType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_manager.R;
import com.nrmyw.hud_manager.app.MyApp;
import com.nrmyw.hud_manager_lib.HudManager;

public class TestUtil {

    public static void testSpeed(){
        HudManager.getInstance().getHudEvent().sendNowSpeed(RandomUtil.getInstance().getRandomInt(255));
    }

    public static void testTrun(){
        int trc = HudTurnType.values().length - 2;
        int tr1 = RandomUtil.getInstance().getRandomInt(trc)+1;
        int tmr1 = RandomUtil.getInstance().getRandomInt(99999);
        int tr2 = RandomUtil.getInstance().getRandomInt(trc)+1;
        int tmr2 = RandomUtil.getInstance().getRandomInt(99999);
        HudManager.getInstance().getHudEvent().sendTurnType(HudTurnType.values()[tr1], tmr1, HudTurnType.values()[tr2], tmr2);
    }

    public static void testWp(){
        int twc = HudWarningPointType.values().length - 1;
        int tw1 = RandomUtil.getInstance().getRandomInt(twc);
        int tmw1 = RandomUtil.getInstance().getRandomInt(99999);
        int tw2 = RandomUtil.getInstance().getRandomInt(twc);
        int tmw2 = RandomUtil.getInstance().getRandomInt(99999);
        HudManager.getInstance().getHudEvent().sendWarningPoint(HudWarningPointType.values()[tw1], tmw1, HudWarningPointType.values()[tw2], tmw2);
    }

    public static void testNextRoadName(){
        int l=RandomUtil.getInstance().getRandomInt(16);
        String nextRoadName=RandomUtil.getInstance().getRandomString(l+1);
        HudManager.getInstance().getHudEvent().sendNextLaneName(nextRoadName);
    }



    public static boolean isShowImage;
    public static void testImage(){
        isShowImage=!isShowImage;
        if(isShowImage){
            Bitmap bt1 = BitmapFactory.decodeResource(MyApp.getContext().getResources(), R.drawable.img_test_1);
            HudManager.getInstance().getHudEvent().sendImage(bt1);
        }else {
            HudManager.getInstance().getHudEvent().hideImage();

        }
    }

    public static boolean isShowISpeed;
    public static void testISpeed(){
        isShowISpeed=!isShowISpeed;
        if(isShowISpeed){
            HudManager.getInstance().getHudEvent().sendIntervalSpeed(66,666,6666,6,16);
        }else {
            HudManager.getInstance().getHudEvent().hideIntervalSpeed();
        }
    }

    public static void testReach(){

        int dis= RandomUtil.getInstance().getRandomInt(99999)+1;
        int h=RandomUtil.getInstance().getRandomInt(50)+1;
        int m=RandomUtil.getInstance().getRandomInt(24)+1;

        HudManager.getInstance().getHudEvent().sendReachInfo(dis,h,m, HudReachType.ARRIVAL);
    }


    public static void testLane(){
        int lnc=HudLaneType.values().length/10;
        int ln=RandomUtil.getInstance().getRandomInt(lnc-1);
        HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln));
    }

    public static HudLaneCountBean getNeedT800LaneCountBean(int numb){
        int l= HudLaneType.values().length;
        HudLaneCountBean t800LaneCountBean=new HudLaneCountBean();
        for(int i=0;i<10;i++){
            int index=numb+i;
            if(index<l){
                t800LaneCountBean.add(HudLaneType.values()[index]);
            }

        }
        return t800LaneCountBean;
    }

    public static void testHiLane(){
        HudLaneHiPassCountBean t800LaneHiPassCountBean=new HudLaneHiPassCountBean();
        int lihpr=RandomUtil.getInstance().getRandomInt(8)+1;
        for(int i=0;i<lihpr;i++){
            int nowRi=RandomUtil.getInstance().getRandomInt(20)+1;
            t800LaneHiPassCountBean.add(nowRi);
        }

        HudManager.getInstance().getHudEvent().sendLaneHiPass(t800LaneHiPassCountBean);
    }
}
