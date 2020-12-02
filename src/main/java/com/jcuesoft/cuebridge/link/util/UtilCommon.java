package com.jcuesoft.cuebridge.link.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class UtilCommon {

    
    public void copyFile(String fileFrom, String fileTo) throws Exception {

        if(fileFrom.equals(null) || fileTo.equals(null)){
            System.err.println("copyFile param null!");
            return;
        }
        
        File sourceFile = new File( fileFrom );
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel fcin = null;
        FileChannel fcout = null;
        
        inputStream = new FileInputStream(sourceFile);
        outputStream = new FileOutputStream(fileTo);
        
        fcin = inputStream.getChannel();
        fcout = outputStream.getChannel();
        long size = fcin.size();
        fcin.transferTo(0, size, fcout);
        
        fcout.close();
        fcin.close();
        outputStream.close();
        inputStream.close();
    }
}
