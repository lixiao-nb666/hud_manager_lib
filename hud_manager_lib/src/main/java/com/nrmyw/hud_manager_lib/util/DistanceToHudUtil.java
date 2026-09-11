package com.nrmyw.hud_manager_lib.util;

public class DistanceToHudUtil {



    public static int getTmapDistance(int distance) {
        if (distance >= 10000) {
            int km = (int) (distance / 1000f);
            return km*1000;
        } else {
            return distance;
        }
    }



    public static int getTmapExitDistance(int distance) {
        if (distance >= 1000) {
            float km = distance / 1000f;
            if (km >= 10) {
                return ((int) km) *1000;
            } else {
                return (int) ((int) (km * 10 + 0.5f) / 10f*1000);
            }
        } else {
            return distance;
        }
    }
}
