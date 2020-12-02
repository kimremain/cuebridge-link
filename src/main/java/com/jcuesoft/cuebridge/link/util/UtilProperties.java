package com.jcuesoft.cuebridge.link.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.Inet4Address;
import java.net.URL;
import java.util.Properties;

public class UtilProperties {

    ClassLoader cl;

    URL netURL;
    File netPropFile;
    FileInputStream netIS;
    Properties netProps;
    
    URL sslURL;
    File sslPropFile;
    FileInputStream sslIS;
    Properties sslProps;    
    String localFlag;

    public UtilProperties() throws Exception {
        System.out.println("call UtilProperties");
        System.out.println("getHostAddress : " + Inet4Address.getLocalHost().getHostAddress());
        if("192.168.234.101".equals(Inet4Address.getLocalHost().getHostAddress())){
            localFlag = "LOC";
        }else{
            localFlag = "";
        }
        
        cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        
        // network
        netURL = cl.getResource("net.properties");
        System.out.println("net.properties path:" + netURL.getPath());
        netPropFile = new File(netURL.getPath());

        try {
            netIS = new FileInputStream(netPropFile);
            netProps = new Properties();
            netProps.load(netIS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // ssl cert
        sslURL = cl.getResource("ssl.properties");
        System.out.println("ssl.properties path:" + sslURL.getPath());
        sslPropFile = new File(sslURL.getPath());

        try {
            sslIS = new FileInputStream(sslPropFile);
            sslProps = new Properties();
            sslProps.load(sslIS);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }

    public int getHttpServicePort(String param) throws Exception { 

        int port = 0;
        port = Integer.parseInt(netProps.getProperty(param).trim());

        return port;
    }
    
    public File getServiceSSLCertAllChainFile() throws Exception {
        File certChainFile = new File(sslProps.getProperty(localFlag+"allChainFile").trim());
        
        return certChainFile;
    }    

    public File getServiceSSLCertChainFile() throws Exception {
        File certChainFile = new File(sslProps.getProperty(localFlag+"certChainFile").trim());
        
        return certChainFile;
    }
    
    public File getServiceSSLKeyFile() throws Exception {
        File certChainFile = new File(sslProps.getProperty(localFlag+"keyFile").trim());
        
        return certChainFile;
    }
    
    public File getSslProperty(String param) throws Exception{
        File result = new File(sslProps.getProperty(param).trim());
        return result;
    }    
    
    public String getNetProperty(String param) throws Exception{
        String result = null;
        result = netProps.getProperty(param).trim();
        return result;
    }
    
    public String getBridgeSshIP() throws Exception{
        String result = netProps.getProperty("bridge.ssh.ip").trim();
        
        return result;
    }
    
    public int getBridgeSshPort() throws Exception{
        int result = Integer.parseInt(netProps.getProperty("bridge.ssh.port").trim());
        
        return result;
    }
    
    public String getBridgeSshUser() throws Exception{
        String result = netProps.getProperty("bridge.ssh.user").trim();
        
        return result;
    }
    
    public String getBridgeSshPassword() throws Exception{
        String result = netProps.getProperty("bridge.ssh.password").trim();
        
        return result;
    }    
}
