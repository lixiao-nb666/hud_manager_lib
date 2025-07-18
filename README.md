# hud_manager_lib


1	settings.gradle
    dependencyResolutionManagement {
        repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
            mavenCentral()
            maven { url 'https://jitpack.io' }
        }
    }

2   build.gradle
    implementation 'com.github.lixiao-nb666:ble_lib:1.0.13'
    implementation 'com.github.lixiao-nb666:ble_event:1.0.7'
    implementation 'com.github.lixiao-nb666:hud_data_lib:1.0.3'
    implementation ('com.github.lixiao-nb666:hud_event_lib:1.0.6')
    implementation 'com.github.lixiao-nb666:hud_manager_lib:1.0.0'

3   AndroidManifest.xml
        <service android:name="com.newbee.ble_lib.service.BluetoothGattService"
            android:exported="true"/>
        <service android:name="com.nrmyw.hud_data_event_lib.service.HudEventService"
            android:exported="true"/>

4   Application
    onCreate() {
        HudManager.getInstance().init(getApplicationContext());
    }
    onTerminate(){
        HudManager.getInstance().close();
    }

5  #When you have all need permissions
    HudManager.getInstance().getBleEvent().havePermissionInitBle();
    HudManager.getInstance().getBleEvent().startSearchBle();

6 add and delect BleStatuListen
    BleStatuEventSubscriptionSubject.getInstance().attach(bleStatuEventObserver);
    BleStatuEventSubscriptionSubject.getInstance().detach(bleStatuEventObserver);

7 add and delect CmdRetrun
    HudRetrunEventSubscriptionSubject.getInstence().attach(hudRetrunEventObserver);
    HudRetrunEventSubscriptionSubject.getInstence().detach(hudRetrunEventObserver);

8 send HudCmd
    HudManager.getInstance().getHudEvent().sendNowSpeed(160);

9 NowDevice