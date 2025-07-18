package com.nrmyw.hud_manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.newbee.ble_lib.NewBeeBleManager;
import com.newbee.bulid_lib.mybase.activity.BaseCompatActivity;
import com.nrmyw.ble_event_lib.statu.BleStatu;
import com.nrmyw.ble_event_lib.statu.BleStatuEventObserver;
import com.nrmyw.ble_event_lib.statu.BleStatuEventSubscriptionSubject;
import com.nrmyw.hud_manager.R;
import com.nrmyw.hud_manager.activity.t800.T800BleDataTestActivity;
import com.nrmyw.hud_manager_lib.HudManager;
import com.nrmyw.hud_manager_lib.type.HudDevice;

public class MainActivity extends BaseCompatActivity {
    private BleStatuEventObserver bleStatuEventObserver=new BleStatuEventObserver() {
        @Override
        public void sendBleStatu(BleStatu bleStatu, Object... objects) {
            Message msg=new Message();
            msg.what=bleStatu.ordinal();
            if(objects.length>0){
                msg.obj=objects[0];
            }
            handler.sendMessage(msg);
        }
    };
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            BleStatu bleStatu=BleStatu.values()[msg.what];
            bleStatuTV.setText(getResources().getText(bleStatu.getStrId()));
            switch (bleStatu){
                case CONNECTING:
                    HudDevice hudDevice = HudManager.getInstance().getHudDevice();
                    if(null!=hudDevice){
                        bleNameTV.setText(hudDevice.name()+"-");
                    }
                    break;
                case CONNECTED:
                    bleConnectTV.setText(getResources().getText(R.string.ble_connected));
                    setViewByBleConnectStatu(NewBeeBleManager.getInstance().isConnect());
                    break;
                case DISCONNECTED:
                    bleConnectTV.setText(getResources().getText(R.string.ble_disconnect));
                    setViewByBleConnectStatu(NewBeeBleManager.getInstance().isConnect());
                    break;
                case RUN_ERR:
                    if(null!=msg.obj){
                        int errRsId= (int) msg.obj;
                        bleStatuTV.append(getResources().getText(errRsId));
                    }
                    break;

            }
        }
    };
    private LinearLayout bg1,bg2;
    private TextView bleNameTV,bleConnectTV,bleStatuTV;
//    ,disconnectedBT
    private Button initBT,searchBT,bleSetBT,sendTestBT,startGaoDeBT;
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_init:
                    HudManager.getInstance().getBleEvent().havePermissionInitBle();
                    break;
                case R.id.bt_search:
                    HudManager.getInstance().getBleEvent().startSearchBle();
                    break;
                case R.id.bt_ble_set:
                    HudManager.getInstance().getHudEvent().sendNowSpeed(160);
                    break;
                case R.id.bt_send_test:
                    toActivity(T800BleDataTestActivity.class);
                    break;

            }
        }
    };

    @Override
    public int getViewLayoutRsId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        bg1=findViewById(R.id.ll_bg_1);
        bg2=findViewById(R.id.ll_bg_2);
        bleNameTV=findViewById(R.id.tv_ble_name);
        bleConnectTV=findViewById(R.id.tv_ble_connect_statu);
        bleStatuTV=findViewById(R.id.tv_ble_statu);
        initBT=findViewById(R.id.bt_init);
        searchBT=findViewById(R.id.bt_search);
        bleSetBT=findViewById(R.id.bt_ble_set);
        sendTestBT=findViewById(R.id.bt_send_test);
        startGaoDeBT=findViewById(R.id.bt_to_gaode_t);
        initBT.setOnClickListener(onClickListener);
        searchBT.setOnClickListener(onClickListener);
        bleSetBT.setOnClickListener(onClickListener);
        sendTestBT.setOnClickListener(onClickListener);
        startGaoDeBT.setOnClickListener(onClickListener);
    }

    @Override
    public void initData() {
        //法律法规这个必须调用
//        PrivacyComplianceDialogUtil.getInstance().privacyCompliance(this);
        BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
//        BleEventSubscriptionSubject.getInstance().havePermissionInitBle();
        NewBeeBleManager.getInstance().getEventImp().havePermissionInitBle();
    }

    @Override
    public void initControl() {

    }

    @Override
    public void closeActivity() {

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("TAG","onCreate------------");
        //这是为了应用程序安装完后直接打开，按home键退出后，再次打开程序出现的BUG
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            Log.e("TAG","onCreate------------getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0");
            //结束你的activity
            finish();
            return;
        }
        if (!isTaskRoot()) {
            Log.e("TAG","onCreate------------isTaskRoot");
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent
                    .ACTION_MAIN)) {
                finish();
                return;
            }
        }
        if (!isTaskRoot()) {
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
    }


    /**
     * 返回键处理事件
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);// 退出程序
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);
    }

    public void setViewByBleConnectStatu(boolean isConnected){
        if(isConnected){
            bg1.setVisibility(View.GONE);
            bg2.setVisibility(View.VISIBLE);
        }else {
            bg1.setVisibility(View.VISIBLE);
            bg2.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        setViewByBleConnectStatu(NewBeeBleManager.getInstance().isConnect());
    }
}
