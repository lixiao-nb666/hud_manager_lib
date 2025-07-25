package com.nrmyw.hud_manager.activity.t800;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.newbee.bulid_lib.mybase.LG;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.bulid_lib.util.RandomUtil;
import com.nrmyw.ble_event_lib.bean.BleSendImageEndInfoBean;
import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;

import com.nrmyw.ble_event_lib.type.BleSendBitmapQualityType;
import com.nrmyw.hud_data_event_lib.HudEventManager;
import com.nrmyw.hud_data_lib.bean.HudLaneCountBean;
import com.nrmyw.hud_data_lib.bean.HudLaneHiPassCountBean;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventObserver;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventSubscriptionSubject;

import com.nrmyw.hud_data_lib.type.lane.HudLaneType;
import com.nrmyw.hud_data_lib.type.lane.HudNowLaneStrType;
import com.nrmyw.hud_data_lib.type.set.HudGpsStatuType;
import com.nrmyw.hud_data_lib.type.speed.HudSpeedingShowBJType;

import com.nrmyw.hud_data_lib.type.speed.HudSpeedingTextType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnBjType;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;

import com.nrmyw.hud_data_lib.type.type.HudStatuType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_data_lib.type.yellow_statu.HudYellowStatuBjType;
import com.nrmyw.hud_manager.R;
import com.nrmyw.hud_manager.util.BleByteUtil;
import com.nrmyw.hud_manager_lib.HudManager;

import java.nio.charset.StandardCharsets;

public class T800BleDataTestActivity extends BaseCompatActivity {
    private RecyclerView rv;
    private TextView showCmdTV,showCmdRetrunTV;
    private T800TestTitleAdapter adapter;
    long firstTime;
    long lastTime;
    int numb=0;
    private BleStatuEventObserver bleStatuEventObserver=new BleStatuEventObserver() {
        @Override
        public void sendBleStatu(BleStatu bleStatu, Object... objects) {
            switch (bleStatu){
                case CAN_SEND_DATA:
                    numb++;
//                    LG.i("收到指令没");
//                    BLEDataTools.testSpeed();
                    long nowTime=System.currentTimeMillis();
                    if(firstTime==0){
                        firstTime=nowTime;
//                        LG.i("指令时间差为:次数为--,当前发送总次数为:"+numb);
                    }else {
                        long timezongcha=(nowTime-firstTime)/1000;
//                        LG.i("指令时间差为:次数为---"+timezongcha+"s,当前发送总次数为:"+numb);
                    }
                    if(lastTime!=0){
                        long shijiancha=nowTime-lastTime;
//                        LG.i("指令时间差为:"+shijiancha);
                    }
                    lastTime=nowTime;
                    break;
                case SENDING_DATA:
                    byte[] cmd= (byte[]) objects[0];
                    String cmdStr= BleByteUtil.parseByte2HexStr(cmd);
                    setShowCmdTV(cmdStr);
//                    LG.i( "发送 === "+cmd.length+"--"+BleByteUtil.parseByte2HexStr(cmd));
                    break;
                case RETRUN_BYTES:
//                    byte[] retrunBytes= (byte[]) objects[0];

                    break;
                case SEND_IMAGE_END:
                    BleSendImageEndInfoBean bleSendImageEndInfoBean= (BleSendImageEndInfoBean) objects[0];
                    setShowTV("Send Image time :"+bleSendImageEndInfoBean.getUseTime());
                    HudManager.getInstance().getHudEvent().setShowImageBitmapQualityType(BleSendBitmapQualityType.ULTRA_LOW);
                    break;
                case RUN_ERR:

                    break;
            }
        }
    };



    private HudRetrunEventObserver hudRetrunEventObserver=new HudRetrunEventObserver() {
        @Override
        public void none(byte[] bytes) {
            setShowTV("none :"+new String(bytes, StandardCharsets.UTF_8));
        }

        @Override
        public void getBrightnessV(int mode, int autoV, int handV) {
            setShowTV("Brightness V:"+mode+"-"+autoV+"-"+handV);
        }



        @Override
        public void getSoundV(int v) {
            setShowTV("Sound V:"+v);
        }

        @Override
        public void getDeviceSoundStatu(int deviceSoundStatu) {
            setShowTV("DeviceSoundStatu:"+ HudStatuType.values()[deviceSoundStatu]);
        }

        @Override
        public void getSN(String sn) {
            setShowTV("SN Code:"+sn);
        }

        @Override
        public void getDeviceVersion(String deviceVersion) {
            setShowTV("Device version:"+deviceVersion);
        }

        @Override
        public void getGPSSpeed(int v) {
            setShowTV("gps speed v:"+v);
        }


    };

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String showStr= (String) msg.obj;
                    showCmdTV.setText(showStr);
                    break;
                case 1:
                    String cmdStr=(String) msg.obj;
                    showCmdRetrunTV.setText("CMD : "+cmdStr);
                    break;

            }




        }
    };

    private void setShowCmdTV(String cmd){
        Message message=new Message();
        message.what=1;
        message.obj=cmd;
        handler.sendMessage(message);
    }

    private void setShowTV(String str){
        LG.i("shenmegui","怎么没了"+str);
        Message message=new Message();
        message.obj=str;
        handler.sendMessage(message);
    }
    private int wpIndex=0;
    private int twLoopIndex=0;
    private int speedingStyle=-1;
    private int yellowStatuStyle=0;
    private T800TestDataAdapter.ItemClick itemClick=new T800TestDataAdapter.ItemClick() {
        @Override
        public void nowSelect(T800TestDataType t800TestDataType) {
            switch (t800TestDataType){
                case mq_test:
                    for(int i=0;i<6;i++){
                        HudManager.getInstance().getHudEvent().sendTime();
                        HudManager.getInstance().getHudEvent().sendNowSpeed(getRandomIntBySpeed());
                        HudManager.getInstance().getHudEvent().sendReachInfo(getRandomIntBySpeedByMeter(10000),15,33);
                        HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.roadName,"T800Lane:"+RandomUtil.getInstance().getRandomInt(100));
                        HudManager.getInstance().getHudEvent().queDeviceVersion();
                    }
                    break;
                case time:
                    HudManager.getInstance().getHudEvent().sendTime();
                    break;
                case speed_1:
                    int speed1=getRandomIntBySpeed();
                    HudManager.getInstance().getHudEvent().sendNowSpeed(speed1);
                    setShowTV("send speed:"+speed1);
                    break;
                case speed_2:
                    int speed2=getRandomIntBySpeed();

                    int limitedSpeed1 =getRandomIntBySpeed();
                    int limitedSpeed2 =getRandomIntBySpeed();
                    HudManager.getInstance().getHudEvent().sendNowSpeed(speed2,limitedSpeed1,limitedSpeed2);
                    setShowTV("send speed:"+speed2+"-"+limitedSpeed1+"-"+limitedSpeed2);
                    break;
                case speeding:
                    speedingStyle++;
                    if(speedingStyle>=HudSpeedingShowBJType.values().length){
                        speedingStyle=0;
                    }
                    HudSpeedingTextType speedingTextType=null;
                    if(speedingStyle==0){
                        speedingTextType=HudSpeedingTextType.WRITE;
                    }else {
                        speedingTextType=HudSpeedingTextType.RED;
                    }
                    HudSpeedingShowBJType speedingShowBJType=HudSpeedingShowBJType.values()[speedingStyle];

                    HudManager.getInstance().getHudEvent().sendSpeeding(speedingTextType,speedingShowBJType);

                    break;
                case IntervalSpeed:
                    HudManager.getInstance().getHudEvent().sendIntervalSpeed(100,9500000,RandomUtil.getInstance().getRandomInt(200),3,20);
                    break;
                case WarningPoint_1:
                    int w1=RandomUtil.getInstance().getRandomInt(HudWarningPointType.values().length);
                    int d1=getRandomIntBySpeedByMeter(2000);
                    HudManager.getInstance().getHudEvent().sendWarningPoint(HudWarningPointType.values()[w1],d1);
                    break;
                case WarningPoint_2:
                    int w21=RandomUtil.getInstance().getRandomInt(HudWarningPointType.values().length);
                    int d21=getRandomIntBySpeedByMeter(2000);
                    int w22=RandomUtil.getInstance().getRandomInt(HudWarningPointType.values().length);
                    int d22=getRandomIntBySpeedByMeter(2000);
                    HudManager.getInstance().getHudEvent().sendWarningPoint(HudWarningPointType.values()[w21],d21,HudWarningPointType.values()[w22],d22);
                    break;
                case BIG_WarningPoint_1:
//                    int bw1=RandomUtil.getInstance().getRandomInt(T800WarningPointType.values().length);
                    int bd1=getRandomIntBySpeedByMeter(2000);
                    HudManager.getInstance().getHudEvent().sendBigWarningPoint(HudWarningPointType.values()[wpIndex],bd1);
                    wpIndex++;
                    if(wpIndex>=HudWarningPointType.values().length){
                        wpIndex=0;
                    }
                    break;
                case WarningPoint_1_T:
                    HudManager.getInstance().getHudEvent().sendWarningPoint1TitleStr("T1");
                    break;
                case WarningPoint_1_B:
                    HudManager.getInstance().getHudEvent().sendWarningPoint1BodyStr("B1");
                    break;
                case WarningPoint_2_T:
                    HudManager.getInstance().getHudEvent().sendWarningPoint2TitleStr("T2");
                    break;
                case WarningPoint_2_B:
                    HudManager.getInstance().getHudEvent().sendWarningPoint2BodyStr("B2");
                    break;
                case ReachInfo_1:

                    HudManager.getInstance().getHudEvent().sendReachInfo(getRandomIntBySpeedByMeter(10000),14,30);
                    break;
                case ReachInfo_2:
                    HudManager.getInstance().getHudEvent().sendReachInfo("fdsfsfjskljf");
                    break;
                case LaneInformation_hide:
                    HudManager.getInstance().getHudEvent().sendLaneHide();
                    break;
                case LaneInformation_0:
                    int r0=RandomUtil.getInstance().getRandomInt(10);
                    r0++;

                    int indexLI0=RandomUtil.getInstance().getRandomInt(HudLaneType.values().length-r0);
                    HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(indexLI0,r0));
                    break;
                case LaneInformation_1:
                    int ln1=0;
                    HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln1));
                    break;
                case LaneInformation_2:
                    int ln2=10;
                    HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln2));
                    break;
                case LaneInformation_3:
                    int ln3=20;
                    HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln3));
                    break;
                case LaneInformation_4:
                    int ln4=30;
                    HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln4));
                    break;
                case LaneInformation_5:
                    int ln5=40;
                    HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln5));
                    break;
                case LaneInformation_6:
                    int ln6=50;
                    HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln6));
                    break;
                case LaneInformation_7:
                    int ln7=60;
                    HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln7));
                    break;
                case LaneInformation_8:
                    int ln8=70;
                    HudManager.getInstance().getHudEvent().sendLaneInformation(getNeedT800LaneCountBean(ln8));
                    break;
                case LaneInformation_hi_pass_auto_l:
//                    HudCmdDataUtil.autoGetHipassL=!T800CmdDataUtil.autoGetHipassL;
                    break;
                case LaneInformation_hi_pass_1:
                    HudLaneHiPassCountBean t800LaneHiPassCountBean=new HudLaneHiPassCountBean();
                    int lihpr=RandomUtil.getInstance().getRandomInt(8)+1;
                    for(int i=0;i<lihpr;i++){
                        int nowRi=RandomUtil.getInstance().getRandomInt(20)+1;
                        t800LaneHiPassCountBean.add(nowRi);
                    }
                    HudManager.getInstance().getHudEvent().sendLaneHiPass(t800LaneHiPassCountBean,lihpr);
                    break;
                case LaneInformation_hi_pass_2:
                    HudLaneHiPassCountBean t800LaneHiPassCountBean2=new HudLaneHiPassCountBean();
                    t800LaneHiPassCountBean2.add(1);
                    t800LaneHiPassCountBean2.add(-1);
                    t800LaneHiPassCountBean2.add(10);
                    t800LaneHiPassCountBean2.add(-1);
                    t800LaneHiPassCountBean2.add(20);
                    t800LaneHiPassCountBean2.add(-1);
                    t800LaneHiPassCountBean2.add(30);
                    HudManager.getInstance().getHudEvent().sendLaneHiPass(t800LaneHiPassCountBean2,5);
                    break;
                case TrunType_1:
                    int tw1=RandomUtil.getInstance().getRandomInt(HudTurnType.values().length);
                    int td1=getRandomIntBySpeedByMeter(2000);
                    HudManager.getInstance().getHudEvent().sendTurnType(HudTurnType.values()[tw1],td1);
                    break;
                case TrunType_2:
                    int tw21=RandomUtil.getInstance().getRandomInt(HudTurnType.values().length);
                    int td21=getRandomIntBySpeedByMeter(2000);
                    int tw22=RandomUtil.getInstance().getRandomInt(HudTurnType.values().length);
                    int td22=getRandomIntBySpeedByMeter(2000);
                    HudManager.getInstance().getHudEvent().sendTurnType(HudTurnType.values()[tw21],td21,HudTurnType.values()[tw22],td22);
                    break;
                case TrunType_3:
                    HudManager.getInstance().getHudEvent().sendTurnType(HudTurnType.values()[twLoopIndex],60);
                    setShowTV("trun type index："+ BleByteUtil.parseByte2HexStr(HudTurnType.values()[twLoopIndex].getType())+"--"+HudTurnType.values()[twLoopIndex]);
                    twLoopIndex++;
                    if(twLoopIndex>=HudTurnType.values().length){
                        twLoopIndex=0;
                    }
                    break;
                case NEXT_ROAD_NAME:
                    HudManager.getInstance().getHudEvent().sendNextLaneName("下一条道路名:"+RandomUtil.getInstance().getRandomInt(100));
                    break;
                case NOW_ROAD_NAME_HIDE:
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.none,"T800Lane道路名:"+RandomUtil.getInstance().getRandomInt(100));
                    break;
                case NOW_ROAD_NAME_1:
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.destination,"T800Destination道路名:"+RandomUtil.getInstance().getRandomInt(100));
                    break;
                case NOW_ROAD_NAME_2:
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.location,"T800Location道路名:"+RandomUtil.getInstance().getRandomInt(100));
                    break;
                case NOW_ROAD_NAME_3:
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.roadName,"T800RoadName道路名"+RandomUtil.getInstance().getRandomInt(100));
                    break;
                case NOW_ROAD_NAME_4:
                    HudManager.getInstance().getHudEvent().sendNowLaneStr(HudNowLaneStrType.def,"EDF----道路名:"+RandomUtil.getInstance().getRandomInt(100));
                    break;
                case gps_0:
                    HudManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.CLOSE);
                    break;
                case gps_1:
                    HudManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.OPEN);
                    break;
                case gps_2:
                    HudManager.getInstance().getHudEvent().sendGpsStatu(HudGpsStatuType.LOST);
                    break;
                case SET_GPS:
                    int gpsSpeedV=RandomUtil.getInstance().getRandomInt(11);
                    setShowTV("set gps speed v:"+gpsSpeedV);
                    HudManager.getInstance().getHudEvent().setGpsSpeed(gpsSpeedV);
                    break;
                case que_GPS:
                    HudManager.getInstance().getHudEvent().queGpsSpeed();
                    break;
                case Brightness_1:
                    HudManager.getInstance().getHudEvent().sendBrightnessAuto();
                    break;
                case Brightness_2:
                    int brightness=RandomUtil.getInstance().getRandomInt(10)+1;
                    setShowTV("set brightness:"+brightness);
                    HudManager.getInstance().getHudEvent().sendBrightnessHand(brightness);
                    break;
                case que_Brightness:
                    HudManager.getInstance().getHudEvent().queBrightness();
                    break;
                case que_SN:
                    HudManager.getInstance().getHudEvent().queSN();
                    break;
                case rewrite_SN:
                    String sn="T700";
                    sn+=RandomUtil.getInstance().getRandomString(8);
                    HudManager.getInstance().getHudEvent().rewriteSN(sn.toUpperCase());
                    break;
                case que_DeviceVersion:
                    HudManager.getInstance().getHudEvent().queDeviceVersion();
                    break;
                case SET_SOUND:
                    int soundV=RandomUtil.getInstance().getRandomInt(11);
                    setShowTV("set sound:"+soundV);
                    HudManager.getInstance().getHudEvent().setSound(soundV);
                    break;
                case que_SOUND:
                    HudManager.getInstance().getHudEvent().queSound();
                    break;
                case send_Image1:

                    Bitmap bt1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_test_1);
                    HudManager.getInstance().getHudEvent().sendImage(bt1);

                    break;
                case send_Image2:
                    Bitmap bt2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_test_2);
                    HudManager.getInstance().getHudEvent().sendImage(bt2);
                    break;
                case send_Image3:
                    Bitmap bt3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_test_3);
                    HudManager.getInstance().getHudEvent().sendImage(bt3);
                    break;
                case send_Image4:
                    Bitmap bt4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.img_test_4);
                    HudManager.getInstance().getHudEvent().sendImage(bt4);
                    break;
                case show_Image:
                    HudManager.getInstance().getHudEvent().showImage();
                    break;
                case hide_Image:
                    HudManager.getInstance().getHudEvent().hideImage();
                    break;
                case show_Yellow_Statu:
                    yellowStatuStyle++;
                    if(yellowStatuStyle>4){
                        yellowStatuStyle=1;
                    }
                    HudManager.getInstance().getHudEvent().setYellowStatu(HudYellowStatuBjType.values()[yellowStatuStyle]);
                    break;
                case hide_Yellow_Statu:
                    HudManager.getInstance().getHudEvent().setYellowStatu(HudYellowStatuBjType.HIDE);
                    break;

                case icon_flicker_open:
                    HudManager.getInstance().getHudEvent().iconFlicherOpen();
                    break;
                case Yellow_Statu_STR:
                    HudManager.getInstance().getHudEvent().sendYellowStatuStr("黄色状态栏");
                    break;
                case icon_flicker_close:
                    HudManager.getInstance().getHudEvent().iconFlicherClose();
                    break;
                case factory_set:
                    HudManager.getInstance().getHudEvent().factorySet();
                    break;
                case device_Sound_open:
                    HudManager.getInstance().getHudEvent().deviceSoundOpen();
                    break;
                case device_Sound_close:
                    HudManager.getInstance().getHudEvent().deviceSoundClose();
                    break;
                case que_device_Sound_Statu:
                    HudManager.getInstance().getHudEvent().queDeviceSound();
                    break;
                case daylight_Statu_open:
                    HudManager.getInstance().getHudEvent().daylightingStatuOpen();
                    break;
                case daylight_Statu_close:
                    HudManager.getInstance().getHudEvent().daylightingStatuClose();
                    break;
                case TRUN_BJ:
                    int numb1=turnBjNumb%4;
                    HudTurnBjType hudTurnBjType=HudTurnBjType.values()[numb1];
                    HudManager.getInstance().getHudEvent().setTurnBj(hudTurnBjType);
                    turnBjNumb++;
                    break;
//                case SPEEDING_BJ:
//                    int numb2=bjNumb%4;
//                    HudStyleType styleType2=HudStyleType.values()[numb2];
//                    HudManager.getInstance().getHudEvent().setSpeedingBj(styleType2);
//                    bjNumb++;
//                    break;
//                case YELLOW_STATU_BJ:
//                    int numb3=bjNumb%4;
//                    HudStyleType styleType3=HudStyleType.values()[numb3];
//                    HudManager.getInstance().getHudEvent().setYellowStatuBj(styleType3);
//                    bjNumb++;
//                    break;
            }
        }
    };
    private int turnBjNumb=0;

    private int getRandomIntByHour(){
        return RandomUtil.getInstance().getRandomInt(24);
    }

    private int getRandomIntByMin(){
        return RandomUtil.getInstance().getRandomInt(60);
    }

    private int getRandomIntBySpeed(){
        return RandomUtil.getInstance().getRandomInt(200);
    }

    private int getRandomIntBySpeedByMeter(int needMi){
        return RandomUtil.getInstance().getRandomInt(needMi);
    }

    public HudLaneCountBean getNeedT800LaneCountBean(int numb){
        int l=HudLaneType.values().length;
        HudLaneCountBean t800LaneCountBean=new HudLaneCountBean();
        for(int i=0;i<10;i++){
            int index=numb+i;
            if(index<l){
                t800LaneCountBean.add(HudLaneType.values()[index]);
            }

        }
        return t800LaneCountBean;
    }

    public HudLaneCountBean getNeedT800LaneCountBean(int numb, int needl){
        int l=HudLaneType.values().length;
        HudLaneCountBean t800LaneCountBean=new HudLaneCountBean();
        for(int i=0;i<needl;i++){
            int index=numb+i;
            if(index<l){
                t800LaneCountBean.add(HudLaneType.values()[index]);
            }
        }
        return t800LaneCountBean;
    }

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_t800_test;
    }

    @Override
    public void initView() {
        rv=findViewById(R.id.rv);
    }

    @Override
    public void initData() {
        showCmdTV=findViewById(R.id.tv_show_cmd);
        showCmdRetrunTV=findViewById(R.id.tv_show_cmd_retrun);
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        adapter=new T800TestTitleAdapter(context,itemClick);
        rv.setAdapter(adapter);
    }

    @Override
    public void initControl() {
//        BleCmdRetrunEventSubscriptionSubject.getInstance().attach(bleCmdRetrunEventObserver);
        HudRetrunEventSubscriptionSubject.getInstence().attach(hudRetrunEventObserver);
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
    }

    @Override
    public void closeActivity() {
        handler.removeCallbacksAndMessages(null);
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
        HudRetrunEventSubscriptionSubject.getInstence().detach(hudRetrunEventObserver);
//        BleCmdRetrunEventSubscriptionSubject.getInstance().detach(bleCmdRetrunEventObserver);
    }

    @Override
    public void viewIsShow() {

    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {
    }
}
