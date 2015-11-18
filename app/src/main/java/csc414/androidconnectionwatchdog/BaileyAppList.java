package csc414.androidconnectionwatchdog;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Bman on 11/18/2015.
 */

public class BaileyAppList {
    List<ActivityManager.RunningAppProcessInfo> procInfos;

    public BaileyAppList(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        procInfos = activityManager.getRunningAppProcesses();
    }


    public String firstName(){
        return procInfos.get(0).processName;
    }
}
