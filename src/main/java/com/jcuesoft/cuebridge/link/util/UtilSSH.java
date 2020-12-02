package com.jcuesoft.cuebridge.link.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class UtilSSH {

    public Session sshConnect(String ip, int port, String user, String pwd) throws JSchException{

        Session session = null;
        
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(user, ip, port);
            session.setPassword(pwd);
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no"); 
            session.setConfig(config);
            session.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
                
        return session;
    }    
    
    public void sshDisconnect(Session session){
        session.disconnect();
    }
    
    public void sshFileUpload(Session session, String path, File file){

        Channel channel = null;
        ChannelSftp channelSftp = null;
        FileInputStream in = null;

        try {
            channel = session.openChannel("sftp");
            channel.connect();
            in = new FileInputStream(file);
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(path);
            channelSftp.put(in, file.getName());            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                in.close();
                channelSftp.quit();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
    }
}
