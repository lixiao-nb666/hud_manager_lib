package com.nrmyw.hud_manager.thread;

import com.newbee.bulid_lib.util.RandomUtil;
import com.nrmyw.hud_manager.util.TestUtil;
import com.nrmyw.hud_manager_lib.HudManager;

public class TestThread extends Thread {
    private boolean nowStartTest;
    public TestThread(){
        nowStartTest=true;
    }
    public void setStop(){
        nowStartTest=false;
    }

    @Override
    public void run() {
        super.run();
        try {
            HudManager.getInstance().getHudEvent().sendWarningPointLimitedSpeed(0,0);
            while (nowStartTest){
                nowTest();
                Thread.sleep(222);
            }
        }catch (Exception e){

        }
    }

    private void nowTest(){
        int nowR = RandomUtil.getInstance().getRandomInt(13);
        switch (nowR) {
            case 0:
                TestUtil.testSpeed();
                break;
            case 1:
            case 2:
            case 3:
                TestUtil.testTrun();
                break;


            case 4:
                TestUtil.testImage();
                break;
            case 5:
                TestUtil.testISpeed();
                break;
            case 6:
                TestUtil.testReach();
                break;
            case 7:
                TestUtil.testLane();
                break;
            case 8:
                TestUtil.testHiLane();
                break;
            case 9:
                TestUtil.testNextRoadName();
                break;
            case 10:
                case 11:

            default:
                TestUtil.testWp();
                break;
        }
    }
}
