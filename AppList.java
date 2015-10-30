package csc414.myapplication;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;


/**
 * Created by coltonladner on 10/24/15.
 */
public class AppList {

    static private ActivityManager actMan;
    static private List<ActivityManager.RunningAppProcessInfo> procList;
    static private List<ActivityManager.RunningServiceInfo> servList;
    static private int maxServices = 255;

    AppList() {

        actMan = getSystemService(Context.ACTIVITY_SERVICE);
        procList = actMan.getRunningAppProcesses();
        servList = actMan.getRunningServices(maxServices);

    }

    public void updateLists() {

        actMan = null;
        procList = null;
        servList = null;

        actMan = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        procList = actMan.getRunningAppProcesses();
        servList = actMan.getRunningServices(maxServices);

    }

    public int getSizeProc(){

        return proclist.size();

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

    /* public TYPE getServiceList(){

        this.updateLists();


    }

    public TYPE getProcessList(){

        this.updateLists();


    }*/

}
