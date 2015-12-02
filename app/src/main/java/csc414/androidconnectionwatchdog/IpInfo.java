package csc414.androidconnectionwatchdog;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.regex.Pattern;

/**
 * Created by Bman on 10/29/2015.
 */
public class IpInfo {
    private int honeyDaysActive; //the number of days since last activity detected
    private int honeyThreatScore; //a threat score from 0-255
    private int honeyThreatType; //describes the type of threat identified by honeypot
    private String honeyResult;
    private int[] honeyResArr;
    private String honeyThreatDescription;
    private String honeySearchEngineName; //if the ip was a search engine, list which one
    private static int notificationCounter;

    public IpInfo(String honeyIp) { //input MUST be result from IpTest.honeyConnect()
        honeyResArr = new int[4];
        if (honeyIp.equals("unknown host")){
            this.honeyDaysActive = 0;
            this.honeyThreatScore = 0;
            this.honeyThreatDescription = "unknown host";
            this.honeyResult = "unknown host";
        }
        else {
            this.honeyResult = honeyIp;
            transHoney();
        }
    }

    public boolean isSearchEngine() {//true if ip is a search engine
        if (this.honeyResArr[3] == 0){
            return true;
        }
        else{
            return false;
        }
    }

    private void transHoney(){
        String[] tmpRes = this.honeyResult.split(Pattern.quote("."));
        for (int i = 0; i < tmpRes.length; i++){
            this.honeyResArr[i] = Integer.parseInt(tmpRes[i]);
        }

        if (this.honeyResArr[3] != 0) { //if it is not a search engine
            this.honeyDaysActive = this.honeyResArr[1];
            this.honeyThreatScore = this.honeyResArr[2];
            this.honeyThreatType = this.honeyResArr[3];
        }

        if (this.honeyResArr[3] == 0) { //app is connecting to a search engine
            this.honeyDaysActive = 0; //this doesn't apply to search engines
            this.honeyThreatScore = 0; //also doesnt apply to search engines
            this.honeyThreatType = 0; //0 is search engine

            switch(this.honeyResArr[2]) {
                case 0: this.honeySearchEngineName = "Undocumented Search Engine";
                    break;
                case 1: this.honeySearchEngineName = "AltaVista Search Engine";
                    break;
                case 2: this.honeySearchEngineName = "Ask Search Engine";
                    break;
                case 3: this.honeySearchEngineName = "Baidu Search Engine";
                    break;
                case 4: this.honeySearchEngineName = "Excite Search Engine";
                    break;
                case 5: this.honeySearchEngineName = "Google Search Engine";
                    break;
                case 6: this.honeySearchEngineName = "Looksmart Search Engine";
                    break;
                case 7: this.honeySearchEngineName = "Lycos Search Engine";
                    break;
                case 8: this.honeySearchEngineName = "MSN Search Engine";
                    break;
                case 9: this.honeySearchEngineName = "Yahoo Search Engine";
                    break;
                case 10: this.honeySearchEngineName = "Cuil Search Engine";
                    break;
                case 11: this.honeySearchEngineName = "InfoSeek Search Engine";
                    break;
                case 12: this.honeySearchEngineName = "Miscellaneous Search Engine";
                    break;
                default: this.honeySearchEngineName = "unknown search engine";
                    break;
            }
        }

        switch(this.honeyThreatType){
            case 0: this.honeyThreatDescription = "Search Engine";
                break;
            case 1: this.honeyThreatDescription = "Suspicious Robot Activity";
                break;
            case 2: this.honeyThreatDescription = "Email Address Harvesting";
                break;
            case 3: this.honeyThreatDescription = "Suspicious Robot Activity and Email Address Harvesting";
                break;
            case 4: this.honeyThreatDescription = "Comment Spamming";
                break;
            case 5: this.honeyThreatDescription = "Suspicious Robot Activity and Comment Spamming";
                break;
            case 6: this.honeyThreatDescription = "Email Address Harvesting and Comment Spamming";
                break;
            case 7: this.honeyThreatDescription = "Suspicious Robot Activity, Email Address Harvesting, and Comment Spamming";
                break;
            default: this.honeyThreatDescription = "Unknown Threat Type";
                break;
        }

    }

    public String getHoneySearchEngineName () {
        if (this.isSearchEngine()) {
            return this.honeySearchEngineName;
        }
        else {
            return "not a search engine";
        }
    }

    public int getHoneyThreatScore () {

        if (honeyThreatScore>50){
            //Notify(this.honeyResult);
            WatchDogMain.Notify(this.honeyResult);
        }
        return this.honeyThreatScore;
    }

    public int getHoneyDaysActive () {
        return this.honeyDaysActive;
    }

    public String getRawHoneyResult () { //return the honeypot dns query result
        return this.honeyResult;
    }

    /*public void Notify(String BadIp){

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(WatchDogMain.getContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Warning from Connection Watchdog")
                        .setContentText("Harmful Ip: "+BadIp);

// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(WatchDogMain.getContext(), WatchDogMain.class);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(WatchDogMain.getContext(),0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) WatchDogMain.getContext().getSystemService(WatchDogMain.getContext().NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(notificationCounter++, mBuilder.build());
    }*/
    }

