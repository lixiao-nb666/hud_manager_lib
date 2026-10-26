package com.nrmyw.hud_manager.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.newbee.bulid_lib.util.RandomUtil;
import com.newbee.erweima_lib.zxing.util.ScanUtil;
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
                    if(null!=snET.getText()){
                        String snStr=snET.getText().toString();
                        if(!TextUtils.isEmpty(snStr)){
                            HudManager.getInstance().getHudEvent().rewriteSN(snStr);
                            showViewHandler.sendEmptyMessageDelayed(MsgType.QUE_SN.ordinal(),1000);
                        }
                    }

                    break;
                case R.id.bt_que_sn:
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
                    HudManager.getInstance().getHudEvent().queDeviceVersion();
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
                Toast.makeText(this, "需要相机权限以使用扫码功能", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ScanUtil.isScanResult(requestCode, resultCode)) {
            String result = ScanUtil.getScanResultStr(data);
            Log.i("kankanshuju", "看看数据是什么:" + result);
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
            Log.i("gfsjf","fdskfjslkjf--："+bleStatu);
            switch (bleStatu) {
                case CONNECTING:
                    HudDevice hudDevice = HudManager.getInstance().getHudDevice();
                    if (null != hudDevice) {
                        bleNameTV.setText(hudDevice.name() + "-");
                    }
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
            MsgType msgType = MsgType.values()[msg.what];
            switch (msgType) {
                case SHOW_DEVICE:
                    if (HudManager.getInstance().deviceIsConnected()) {
                        HudDevice hudDevice = HudManager.getInstance().getHudDevice();
                        if (null != hudDevice) {
                            bleNameTV.setText(hudDevice.name());
                            bleConnectTV.setText("已连接");
                        }
                        bg1.setVisibility(View.GONE);
                        bg2.setVisibility(View.VISIBLE);
                        showViewHandler.sendEmptyMessage(MsgType.QUE_SN.ordinal());
                        showViewHandler.sendEmptyMessage(MsgType.QUE_VERSION.ordinal());
                    } else {
                        bleNameTV.setText("");
                        bleConnectTV.setText("未连接");
                        bg1.setVisibility(View.VISIBLE);
                        bg2.setVisibility(View.GONE);
                    }
                    bleStatuTV.setText("");
                    queVersionBT.setText("查询版本");
                    break;
                case SHOW_SCAN_SN:
                    snET.setText(msg.obj.toString());
                    break;
                case QUE_SN:
                    long nowTime = System.currentTimeMillis();
                    if (nowTime - lastQueSNtime >= 1000) {
                        HudManager.getInstance().getHudEvent().queSN();
                        lastQueSNtime = nowTime;
                    }
                    break;
                case SHOW_SN:
                    bleStatuTV.setText(msg.obj.toString());
                    break;

                case QUE_VERSION:
                    long nowTime1 = System.currentTimeMillis();
                    if (nowTime1 - lastQueVersiontime >= 1000) {
                        HudManager.getInstance().getHudEvent().queDeviceVersion();
                        lastQueVersiontime = nowTime1;
                    }
                    break;
                case SHOW_VERSION:
                    queVersionBT.setText("("+msg.obj+")"+"查询版本");
                    break;
            }

        }
    };
    private long lastQueSNtime,lastQueVersiontime;
    private TestThread testThread;

    private Button toScanBT, initBT, searchBT, disconnectBT, startTestBT, stopTestBT,queSNBT,reWriteSNBT,oldMoldBT,rgStartBT,rgStopBT,blAutoBT,blABT,blDBt,queVersionBT;
    private TextView bleNameTV, bleConnectTV, bleStatuTV;

    private LinearLayout bg1, bg2;
    private EditText snET;

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_hud_factory;
    }

    @Override
    public void initView() {

        bg1 = findViewById(R.id.ll_bg_1);
        bg2 = findViewById(R.id.ll_bg_2);
        bleNameTV = findViewById(R.id.tv_ble_name);
        bleConnectTV = findViewById(R.id.tv_ble_connect_statu);
        bleStatuTV = findViewById(R.id.tv_ble_statu);
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
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
        HudRetrunEventSubscriptionSubject.getInstence().attach(hudRetrunEventObserver);
    }

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

    private enum MsgType {
        SHOW_DEVICE,
        SHOW_SCAN_SN,
        QUE_SN,
        SHOW_SN,
        QUE_VERSION,
        SHOW_VERSION,

    }
}
