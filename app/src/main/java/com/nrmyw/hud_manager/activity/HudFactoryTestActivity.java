package com.nrmyw.hud_manager.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.newbee.ble_lib.NewBeeBleManager;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.newbee.bulid_lib.mybase.share.MyShare;
import com.newbee.bulid_lib.util.RandomUtil;
import com.newbee.erweima_lib.zxing.util.ScanUtil;
import com.nrmyw.ble_event_lib.bean.BleDeviceBean;
import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventObserver;
import com.nrmyw.hud_data_lib.event.retrun.HudRetrunEventSubscriptionSubject;
import com.nrmyw.hud_data_lib.type.turn.HudTurnType;
import com.nrmyw.hud_data_lib.type.warningproint.HudWarningPointType;
import com.nrmyw.hud_manager.R;
import com.nrmyw.hud_manager.thread.TestThread;
import com.nrmyw.hud_manager.util.TestUtil;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.manager.set_child.HudBrightnessManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

public class HudFactoryTestActivity extends BaseCompatActivity {
    private final int REQUEST_CAMERA_PERMISSION = 66;
    private final int snCodeL=10;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bt_init:
                    HudManager.getInstance().getBleEvent().havePermissionInitBle();
                    break;
                case R.id.bt_search:
                    HudManager.getInstance().getBleEvent().startSearchBle();
                    break;
                case R.id.bt_ble_disconnect:
                    HudManager.getInstance().getBleEvent().disconnectedBle();
                    break;
                case R.id.bt_to_scan:
                    if (ContextCompat.checkSelfPermission(HudFactoryTestActivity.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(HudFactoryTestActivity.this,
                                new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    } else {
                        ScanUtil.toScanActivity(HudFactoryTestActivity.this);
                    }
                    break;
                case R.id.bt_rewrite_sn:
                    if(null==snET||null==snET.getText()){
                        showToast(getString(R.string.test_send_null));
                        return;
                    }
                    String snStr=snET.getText().toString();
                    if(TextUtils.isEmpty(snStr)){
                        showToast(getString(R.string.test_send_null));
                        return;
                    }
                    if(snStr.length()<snCodeL){
                        showToast(getString(R.string.test_length)+":<"+snCodeL);
                        return;
                    }
                    if(snStr.length()>snCodeL){
                        showToast(getString(R.string.test_length)+":>"+snCodeL);
                        return;
                    }
                    HudManager.getInstance().getHudEvent().rewriteSN(snStr);
                    showViewHandler.sendEmptyMessageDelayed(MsgType.QUE_SN.ordinal(),2000);
                    break;
                case R.id.bt_que_sn:
                    snDefTV.setText("");
                    snOtherTV.setText("");
                    HudManager.getInstance().getHudEvent().queSN();
                    break;
                case R.id.bt_start_test:
                    if(null!=testThread){
                        testThread.setStop();
                        testThread.interrupt();
                        testThread=null;
                    }

                    testThread=new TestThread();
                    testThread.start();
                    break;
                case R.id.bt_stop_test:
                    if(null!=testThread){
                        testThread.setStop();
                        testThread.interrupt();
                        testThread=null;
                    }
                    break;
                case R.id.bt_to_old_mold:
                    HudManager.getInstance().getHudEvent().factoryTest();
                    break;

                case R.id.bt_bl_add:
                    HudBrightnessManager.getInstance().add();
                    break;
                case R.id.bt_bl_delect:
                    HudBrightnessManager.getInstance().reduce();
                    break;
                case R.id.bt_bl_auto:
                    HudManager.getInstance().getHudEvent().sendBrightnessAuto();
                    break;
                case R.id.bt_rg_test_start:
                    HudManager.getInstance().getHudEvent().sendBrightnessAuto();
                    HudManager.getInstance().getHudEvent().daylightingStatuOpen();
                    break;
                case R.id.bt_rg_test_stop:
                    HudManager.getInstance().getHudEvent().daylightingStatuClose();
                    break;
                case R.id.bt_que_version:
                    versionTV.setText("");
                    HudManager.getInstance().getHudEvent().queDeviceVersion();
                    break;
                case R.id.bt_bl_1:

                    HudManager.getInstance().getHudEvent().sendBrightnessHand(1);
                    break;
                case R.id.bt_bl_10:
                    HudManager.getInstance().getHudEvent().sendBrightnessHand(10);
                    break;
            }
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限已授予，启动扫描

                ScanUtil.toScanActivity(HudFactoryTestActivity.this);
            } else {
                // 权限被拒绝，提示用户
                Toast.makeText(this, getString(R.string.test_need_camera), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ScanUtil.isScanResult(requestCode, resultCode)) {
            String result = ScanUtil.getScanResultStr(data);

            Message msg = new Message();
            msg.what = MsgType.SHOW_SCAN_SN.ordinal();
            msg.obj = result;
            showViewHandler.sendMessage(msg);
            //thngs to do
        }
    }

    private BleStatuEventObserver bleStatuEventObserver = new BleStatuEventObserver() {
        @Override
        public void sendBleStatu(BleStatu bleStatu, Object... objects) {
            Message msg = new Message();
            msg.what = bleStatu.ordinal();
            if (objects.length > 0) {
                msg.obj = objects[0];
            }
            handler.sendMessage(msg);
        }
    };
    private HudRetrunEventObserver hudRetrunEventObserver = new HudRetrunEventObserver() {
        @Override
        public void none(byte[] bytes) {

        }

        @Override
        public void getBrightnessV(int mode, int autoV, int handV) {
            HudBrightnessManager.getInstance().setNowBrightness(handV);
        }

        @Override
        public void getSoundV(int v) {

        }

        @Override
        public void getDeviceSoundStatu(int deviceSoundStatu) {

        }

        @Override
        public void getSN(String sn) {
            Message msg = new Message();
            msg.what = MsgType.SHOW_SN.ordinal();
            msg.obj = sn;
            showViewHandler.sendMessage(msg);
        }

        @Override
        public void getDeviceVersion(String deviceVersion) {
            if(deviceVersion.equals("1.2.04")){
                deviceVersion="1.2.09";
            }
            Message msg = new Message();
            msg.what = MsgType.SHOW_VERSION.ordinal();
            msg.obj = deviceVersion;
            showViewHandler.sendMessage(msg);
        }

        @Override
        public void getGPSSpeed(int v) {

        }
    };


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            BleStatu bleStatu = BleStatu.values()[msg.what];
//            Log.i("gfsjf","fdskfjslkjf--："+NewBeeBleManager.getInstance().getNowUseBleDevice().getBleName() + "-"+NewBeeBleManager.getInstance().getNowUseBleDevice().getDeviceName());
            switch (bleStatu) {
                case CONNECTING:
//                    bleNameTV.setText(NewBeeBleManager.getInstance().getNowUseBleDevice().getBleName() + "-"+NewBeeBleManager.getInstance().getNowUseBleDevice().getDeviceName());



//                    HudDevice hudDevice = HudManager.getInstance().getHudDevice();
//                    if (null != hudDevice) {
//
//                    }
                    break;
                case CONNECTED:
                    showViewHandler.sendEmptyMessage(MsgType.SHOW_DEVICE.ordinal());
                    break;
                case DISCONNECTED:
                    showViewHandler.sendEmptyMessage(MsgType.SHOW_DEVICE.ordinal());
                    break;
                case RUN_ERR:

                    break;

            }
        }
    };
    private Handler showViewHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            handler.removeMessages(msg.what);
            MsgType msgType = MsgType.values()[msg.what];

            switch (msgType) {
                case SHOW_DEVICE:
                    if (HudManager.getInstance().deviceIsConnected()) {
                        BleDeviceBean bleDeviceBean=NewBeeBleManager.getInstance().getNowUseBleDevice();
                        if(null!=bleDeviceBean){
                            String bleName=bleDeviceBean.getBleName();
                            if(TextUtils.isEmpty(bleName)){
                                bleName=HudManager.getInstance().getHudDevice().name();
                            }
                            bleNameTV.setText(bleName);
                            bleConnectTV.setText(getString(R.string.test_connected));
                        }
                        bg1.setVisibility(View.GONE);
                        bg2.setVisibility(View.VISIBLE);
                        showViewHandler.sendEmptyMessageDelayed(MsgType.QUE_SN.ordinal(),5000);
                        showViewHandler.sendEmptyMessageDelayed(MsgType.QUE_VERSION.ordinal(),5000);
                        allLL.setBackgroundColor(getColor(R.color.teal_200));
                    } else {
                        bleNameTV.setText("");
                        bleConnectTV.setText("");
                        bg1.setVisibility(View.VISIBLE);
                        bg2.setVisibility(View.GONE);
                        allLL.setBackgroundColor(getColor(R.color.white));
                    }
                    snIsOk=false;
                    versionIsOk=false;

                    snDefTV.setText("");
                    snOtherTV.setText("");
                    versionTV.setText("");
                    break;
                case SHOW_SCAN_SN:
                    String scanStr=msg.obj.toString();
                    if(TextUtils.isEmpty(scanStr)){
                        snET.setText("");
                        return;
                    }else {
                        snET.setText(scanStr);
                        if(scanStr.length()<snCodeL){
                            return;
                        }
                        if(scanStr.length()>snCodeL){
                            return;
                        }
                        HudManager.getInstance().getHudEvent().rewriteSN(scanStr);
                    }
                    break;
                case QUE_SN:
                    snDefTV.setText("");
                    snOtherTV.setText("");
                    long nowTime = System.currentTimeMillis();
                    if (nowTime - lastQueSNtime >= 1000) {
                        HudManager.getInstance().getHudEvent().queSN();
                        lastQueSNtime = nowTime;
                    }

                    break;
                case SHOW_SN:
                    String nowSNStr=msg.obj.toString();
                    String snCheckStr="";
                    if(null!=snCheckET&&null!=snCheckET.getText()){
                        snCheckStr=snCheckET.getText().toString();
                    }
                    if(TextUtils.isEmpty(snCheckStr)){
                        snDefTV.setText(nowSNStr);
                        snOtherTV.setText("");
                    }else {
                        int indexSn=nowSNStr.indexOf(snCheckStr);
                        if(indexSn==-1||indexSn!=0){
                            snDefTV.setText("");
                            snOtherTV.setText(nowSNStr);
                            allLL.setBackgroundColor(getColor(com.newbee.bulid_lib.R.color.red));
                        }else {
                            String snDefStr=snCheckStr;
                            String snOtherStr=nowSNStr.substring(snDefStr.length());
                            snDefTV.setText(snDefStr);
                            snOtherTV.setText(snOtherStr);
                            snIsOk=true;
                            checkSnAndVersion();
                        }
                    }
                    break;
                case QUE_VERSION:
                    versionTV.setText("");
                    long nowTime1 = System.currentTimeMillis();
                    if (nowTime1 - lastQueVersiontime >= 1000) {
                        HudManager.getInstance().getHudEvent().queDeviceVersion();
                        lastQueVersiontime = nowTime1;
                    }

                    break;
                case SHOW_VERSION:
                    String nowVersionStr=msg.obj.toString();
                    String versionCheckStr="";
                    if(null!=versionCheckET&&null!=versionCheckET.getText()){
                        versionCheckStr=versionCheckET.getText().toString();
                    }
                    versionTV.setText("("+nowVersionStr+")");
                    if(TextUtils.isEmpty(versionCheckStr)){

                        Log.i(tag,"zenmhuishi:1111");
                    }else {
                        if(nowVersionStr.equals(versionCheckStr)){
                            versionIsOk=true;
                            checkSnAndVersion();
                            Log.i(tag,"zenmhuishi:2222");
                        }else {
//                            if(!isReQueVersion){
//                                isReQueVersion=true;
//                                handler.sendEmptyMessageDelayed(MsgType.QUE_VERSION.ordinal(), 2000);
//                                Log.i(tag,"zenmhuishi:3333");
//                            }else {
//                                Log.i(tag,"zenmhuishi:4444");
//                            }
                            allLL.setBackgroundColor(getColor(com.newbee.bulid_lib.R.color.yellow));
                        }
                    }

                    break;
            }

        }
    };





    private long lastQueSNtime,lastQueVersiontime;
    private TestThread testThread;

    private Button toScanBT, initBT, searchBT, disconnectBT, startTestBT, stopTestBT,queSNBT,reWriteSNBT,oldMoldBT,rgStartBT,rgStopBT,blAutoBT,blABT,blDBt,queVersionBT,br10BT,br1BT;
    private TextView bleNameTV, bleConnectTV, snDefTV,snOtherTV,versionTV,snCheckTV,versionCheckTV;

    private LinearLayout bg1, bg2,allLL;
    private EditText snET,snCheckET,versionCheckET;

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_hud_factory;
    }

    @Override
    public void initView() {
        allLL=findViewById(R.id.ll_all);
        bg1 = findViewById(R.id.ll_bg_1);
        bg2 = findViewById(R.id.ll_bg_2);
        bleNameTV = findViewById(R.id.tv_ble_name);
        bleConnectTV = findViewById(R.id.tv_ble_connect_statu);
        versionTV=findViewById(R.id.tv_ble_version);
        snDefTV = findViewById(R.id.tv_sn_def);
        snOtherTV = findViewById(R.id.tv_sn_other);
        initBT = findViewById(R.id.bt_init);
        searchBT = findViewById(R.id.bt_search);
        disconnectBT = findViewById(R.id.bt_ble_disconnect);
        toScanBT = findViewById(R.id.bt_to_scan);
        snET = findViewById(R.id.et_sn);
        queSNBT=findViewById(R.id.bt_que_sn);
        reWriteSNBT=findViewById(R.id.bt_rewrite_sn);
        startTestBT = findViewById(R.id.bt_start_test);
        stopTestBT = findViewById(R.id.bt_stop_test);
        oldMoldBT=findViewById(R.id.bt_to_old_mold);
        blABT=findViewById(R.id.bt_bl_add);
        blDBt=findViewById(R.id.bt_bl_delect);
        blAutoBT=findViewById(R.id.bt_bl_auto);
        rgStartBT=findViewById(R.id.bt_rg_test_start);
        rgStopBT=findViewById(R.id.bt_rg_test_stop);
        queVersionBT=findViewById(R.id.bt_que_version);
        br1BT=findViewById(R.id.bt_bl_1);
        br10BT=findViewById(R.id.bt_bl_10);
        snCheckTV=findViewById(R.id.tv_sn_check);
        versionCheckTV=findViewById(R.id.tv_version_check);
        snCheckET=findViewById(R.id.et_sn_check);
        versionCheckET=findViewById(R.id.et_version_check);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initControl() {
        initBT.setOnClickListener(onClickListener);
        searchBT.setOnClickListener(onClickListener);
        disconnectBT.setOnClickListener(onClickListener);
        toScanBT.setOnClickListener(onClickListener);
        queSNBT.setOnClickListener(onClickListener);
        reWriteSNBT.setOnClickListener(onClickListener);
        startTestBT.setOnClickListener(onClickListener);
        stopTestBT.setOnClickListener(onClickListener);
        oldMoldBT.setOnClickListener(onClickListener);
        blABT.setOnClickListener(onClickListener);
        blDBt.setOnClickListener(onClickListener);
        blAutoBT.setOnClickListener(onClickListener);
        rgStartBT.setOnClickListener(onClickListener);
        rgStopBT.setOnClickListener(onClickListener);
        queVersionBT.setOnClickListener(onClickListener);
        br1BT.setOnClickListener(onClickListener);
        br10BT.setOnClickListener(onClickListener);
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
        HudRetrunEventSubscriptionSubject.getInstence().attach(hudRetrunEventObserver);
        snCheckET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(null==s||TextUtils.isEmpty(s.toString())){
                        snCheckTV.setText("");
                    }else {
                        snCheckTV.setText(s.toString());
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        versionCheckET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(null==s||TextUtils.isEmpty(s.toString())){
                    versionCheckTV.setText("");
                }else {
                    versionCheckTV.setText(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String shareSnStr= MyShare.getInstance().getString(snShareKey);
        if(TextUtils.isEmpty(shareSnStr)){
            shareSnStr="A241022";
        }
        snCheckET.setText(shareSnStr);
        String shareVersionStr= MyShare.getInstance().getString(versionShareKey);
        if(TextUtils.isEmpty(shareVersionStr)){
            shareVersionStr="1.2.09";
        }
        versionCheckET.setText(shareVersionStr);

    }
    private final String snShareKey="snShareKey";
    private final String versionShareKey="versionShareKey";

    @Override
    public void closeActivity() {
        if(null!=testThread){
            testThread.setStop();
        }
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
        HudRetrunEventSubscriptionSubject.getInstence().detach(hudRetrunEventObserver);
    }

    @Override
    public void viewIsShow() {
        showViewHandler.sendEmptyMessage(MsgType.SHOW_DEVICE.ordinal());
    }

    @Override
    public void viewIsPause() {

    }

    @Override
    public void changeConfig() {

    }

    private boolean snIsOk,versionIsOk;
    private void checkSnAndVersion(){
        if(snIsOk&&versionIsOk){
            allLL.setBackgroundColor(getColor(com.newbee.bulid_lib.R.color.green));
        }
    }

    private enum MsgType {
        SHOW_DEVICE,
        SHOW_SCAN_SN,
        QUE_SN,
        SHOW_SN,
        QUE_VERSION,
        SHOW_VERSION,

    }
}
