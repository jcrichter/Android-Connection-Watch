package csc414.androidconnectionwatchdog;

import android.app.ActivityManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bman on 11/28/2015.
 */


public class ConnectionFinder {

    //private ProcessManager pMan;
    //private List<ProcessManager.Process> pList = pMan.getRunningProcesses();
    private int a;
    private int b;
    private int pid;
    private Connection chosenOne = null;

    public ConnectionFinder(int p){
        this.pid = p;
    }
    public void checkLog() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("/proc/" + pid + "/net/tcp"));
            BufferedReader in6 = new BufferedReader(new FileReader("/proc/" + pid + "/net/tcp6")); //if the system uses v6
            String line;
            int z;
            while((line = in.readLine()) != null) {
                System.out.println(" tcp Netstat line: " + line);
                line = line.trim();
                String[] fields = line.split("\\s+", 10);
                int fieldn = 0;

                if(fields[0].equals("sl")) {
                    continue;
                }
                Connection connection = new Connection();
                Connection connection6 = new Connection();

                String src[] = fields[1].split(":", 2);
                String dst[] = fields[2].split(":", 2);

                connection.setSrc(getAddress(src[0]));
                connection.setSpt(String.valueOf(getInt16(src[1])));
                connection.setDst(getAddress(dst[0]));
                if (connection.getDst().equals("-1.-1.-1.-1")) {
                    break;
                }
                connection.setDpt(String.valueOf(getInt16(dst[1])));
                connection.setUid(fields[7]);
                chosenOne = connection;


            }

            while((line = in6.readLine()) != null) {
                if (chosenOne != null){
                    break;
                }
                line = line.trim();
                String[] fields = line.split("\\s+", 10);
                int fieldn = 0;

                if(fields[0].equals("sl")) {
                    continue;
                }
                Connection connection6 = new Connection();

                String src[] = fields[1].split(":", 2);
                String dst[] = fields[2].split(":", 2);

                connection6.setSrc(getAddress6(src[0]));
                connection6.setSpt(String.valueOf(getInt16(src[1])));
                connection6.setDst(getAddress6(dst[0]));
                connection6.setDpt(String.valueOf(getInt16(dst[1])));
                connection6.setUid(fields[7]);

                chosenOne = connection6;
                

            }

        }
        catch(Exception e) {
            System.out.println(" checknetlog() Exception: " + e.toString());
        }
    }

    final String states[] = { "ESTBLSH",   "SYNSENT",   "SYNRECV",   "FWAIT1",   "FWAIT2",   "TMEWAIT",
            "CLOSED",    "CLSWAIT",   "LASTACK",   "LISTEN",   "CLOSING",  "UNKNOWN"
    };

    private final String getAddress(final String hexa) {
        try {
            final long v = Long.parseLong(hexa, 16);
            final long adr = (v >>> 24) | (v << 24) |
                    ((v << 8) & 0x00FF0000) | ((v >> 8) & 0x0000FF00);
            return ((adr >> 24) & 0xff) + "." + ((adr >> 16) & 0xff) + "." + ((adr >> 8) & 0xff) + "." + (adr & 0xff);
        } catch(Exception e) {
            Log.w("NetworkLog", e.toString(), e);
            return "-1.-1.-1.-1";
        }
    }

    private final String getAddress6(final String hexa) {
        try {
            final String ip4[] = hexa.split("0000000000000000FFFF0000");

            if(ip4.length == 2) {
                final long v = Long.parseLong(ip4[1], 16);
                final long adr = (v >>> 24) | (v << 24) |
                        ((v << 8) & 0x00FF0000) | ((v >> 8) & 0x0000FF00);
                return ((adr >> 24) & 0xff) + "." + ((adr >> 16) & 0xff) + "." + ((adr >> 8) & 0xff) + "." + (adr & 0xff);
            } else {
                return "-1.-1.-1.-1"; //used to be -2 for each. changed for universal error code.
            }
        } catch(Exception e) {
            Log.w("NetworkLog", e.toString(), e);
            return "-1.-1.-1.-1";
        }
    }

    private final int getInt16(final String hexa) {
        try {
            return Integer.parseInt(hexa, 16);
        } catch(Exception e) {
            Log.w("NetworkLog", e.toString(), e);
            return -1;
        }
    }

    public String getConnection(){
        checkLog();
        return chosenOne.getDst();
    }

}

