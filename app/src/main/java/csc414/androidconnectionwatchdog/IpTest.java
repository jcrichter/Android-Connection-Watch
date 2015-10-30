package csc414.androidconnectionwatchdog;

import android.os.AsyncTask;

import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

/**
 * Created by Bman on 10/29/2015.
 */
public class IpTest {
    private String passCode = "odmajxlityzk";
    private String domainList = "dnsbl.httpbl.org";
    private String ip;
    private IpInfo ipInfoObject;

    public IpTest(String ip) {
        this.ip = ipReverse(ip);
        this.ipInfoObject = genIpInfo();
    }

    private String ipReverse(String ip) {
        //ip address will be converted to reverse octet format
        String[] iPieces = ip.split(Pattern.quote("."));
        String reverseIp = "";
        try {
            for (int i = 3; i >= 0; i--) {
                reverseIp = reverseIp + iPieces[i] + ".";
            }
            return reverseIp;
        } catch (Exception ex) {
            return "unknown host";
        }
    }

    public class NetTask extends AsyncTask<String, Integer, String>
    {
        //class to handle performing the network connection in the background
        @Override
        protected String doInBackground(String... params)
        {
            InetAddress addr = null;
            try
            {
                addr = InetAddress.getByName(params[0]);
            }

            catch (UnknownHostException e)
            {
                //e.printStackTrace()
                return "unknown host";
            }
            return addr.getHostAddress();
        }
    }

    private String honeyConnect(){
        if (this.ip != "unknown host") {
            String query = this.passCode + "." + this.ip + this.domainList;
            String netAddress = null;

            try {
                netAddress = new NetTask().execute(query).get();
                return netAddress;
            } catch (Exception e1) {
                return "unknown host";
            }
        }
        else {
            return "unknown host";
        }
    }

    private IpInfo genIpInfo(){
        IpInfo ipObject = new IpInfo(honeyConnect());
        return ipObject;
    }

    public IpInfo getIpInfoObject(){
        return this.ipInfoObject;
    }

}
