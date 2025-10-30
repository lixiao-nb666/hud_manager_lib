package com.nrmyw.hud_manager.app;

import com.newbee.bulid_lib.mybase.appliction.BaseApplication;
import com.nrmyw.hud_manager_lib.HudManager;

public class MyApp extends BaseApplication {
    @Override
    protected void init() {
        // 1 step
        HudManager.getInstance().init(getApplicationContext());
    }

    @Override
    protected void needClear(String str) {

    }

    @Override
    protected void close() {
        // 2 step
        HudManager.getInstance().close();
    }
}
