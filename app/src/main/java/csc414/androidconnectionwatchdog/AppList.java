package csc414.androidconnectionwatchdog;


import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by coltonladner on 10/24/15.
 */
public class AppList{

    static private ActivityManager actMan;
    static private List<ActivityManager.RunningAppProcessInfo> procList;
    static private List<ActivityManager.RunningServiceInfo> servList;
    static private int maxServices = 255;
    private Context myCon;

    public AppList() {

        //actMan = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        myCon = WatchDogMain.getContext();
        actMan = (ActivityManager)myCon.getSystemService(Context.ACTIVITY_SERVICE); //aww yis
        procList = actMan.getRunningAppProcesses();
        servList = actMan.getRunningServices(maxServices);

    }

    public void updateLists() {
        /*
        actMan = null;
        procList = null;
        servList = null;

        actMan = (ActivityManager) WatchDogMain.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        procList = actMan.getRunningAppProcesses();
        servList = actMan.getRunningServices(maxServices);
        */
        actMan = null;
        procList = null;
        servList = null;

        actMan = (ActivityManager)myCon.getSystemService(Context.ACTIVITY_SERVICE); //aww yis
        procList = actMan.getRunningAppProcesses();
        servList = actMan.getRunningServices(maxServices);
    }

    public int getSizeProc(){

        return procList.size();

    }

    public int getSizeServ(){

        return servList.size();

    }

    public ActivityManager.RunningAppProcessInfo getProcElement(int element){

        return procList.get(element);

    }

    public ActivityManager.RunningServiceInfo getServElement(int element){

        return servList.get(element);

    }

    public String getServElementName(int element){

        return servList.get(element).toString();

    }

    public String getProcElementName(int element){

        return procList.get(element).processName;

    }

}
