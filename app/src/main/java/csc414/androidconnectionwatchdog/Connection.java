package csc414.androidconnectionwatchdog;

/**
 * Created by Bman on 11/29/2015.
 */
public class Connection {
    private String src;
    private String spt;
    private String dst;
    private String dpt;
    private String uid;
    private int pid; //process id of the process that owns the connection
    private String pName; //name of process

    public String getSrc() {
        return src;
    }

    public String getSpt() {
        return spt;
    }

    public String getDst() {
        return dst;
    }

    public String getDpt() {
        return dpt;
    }

    public String getUid() {
        return uid;
    }

    public void setSrc(String s) {
        this.src = s;
    }

    public void setSpt(String s) {
        this.spt = s;
    }

    public void setDst(String s) {
        this.dst = s;
    }

    public void setDpt(String s) {
        this.dpt = s;
    }

    public void setUid(String s) {
        this.uid = s;
    }

    public int getPid() {return pid;}

    public void setPid(int p) {this.pid = p;}

    public void setPName(String n) {this.pName = n; }

    public String getPName() {return this.pName; }


}