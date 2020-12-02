package com.jcuesoft.cuebridge.link.module;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ModuleCommon {
    
    public ModuleCommon(){
        System.out.println("ModuleCommon init");
    }
    
    public static Map<String, String> convertStrToMap(String param) throws Exception{
        System.out.println("ModuleCommon convertStrToMap init");
        Map<String, String> map = new HashMap<String, String>();
        
        String[] pairs = param.split("&");
        for(String pair : pairs){
            String[] kv = pair.split("=");         
            if(kv.length < 2){
                System.out.println("gabage param:"+pair.split("="));
                continue;
            }else{
                System.out.println("["+kv[0]+":"+kv[1]+"]");
            }
            map.put(kv[0], kv[1]);
        }
        
        return map;
    }
    
    public static String getCdCooperateByComCd(String param) throws Exception{
        System.out.println("ModuleCommon getCdCooperateByComCd init");
        String cdCooperate = null;
        
        if(param.equals("Y")){
            cdCooperate = "70301";
        }else if(param.equals("S")){
            cdCooperate = "70302";
        }else if(param.equals("M")){
            cdCooperate = "70303";
        }else{
            System.err.println("<notice> getCdCooperateByComCd unknown ComCd:"+param);
            cdCooperate = "unknown";
        }
        
        return cdCooperate;
    }
    
    public static boolean isNumeric(String s) throws Exception  { 
        if(s == null){
            return false;
        }
        Pattern pattern = Pattern.compile("[+-]?\\d+"); 
        return pattern.matcher(s).matches(); 
    }
    
    public static boolean isNumeric(String s, boolean nullable) throws Exception  { 
        if(nullable==true && s==null){
            return true;
        }else{
            return isNumeric(s);
        }
    }
    
    public static boolean isYN(String s) throws Exception {
        if(s.equals("Y") || s.equals("N")){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean isYN(String s, boolean nullable) throws Exception {
        if(nullable==true && s==null){
            return true;
        }else{
            return isYN(s);
        }
    }
    
    public static boolean isCdLike(String val, String cdGroup) throws Exception {
        if(val.length()!=5){
            return false;
        }
        else if(val.matches(cdGroup+"(.*)")){
            return true;
        }else{
            return false;
        }
    }
    
    public static boolean isCdLike(String val, String cdGroup, boolean nullable) throws Exception {
        if(val != null){
            String removeString = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
            val = val.trim();
            val = val.replaceAll(removeString, "");
        }
        
        if(nullable==true && val==null){
            return true;
        }else if(nullable==true && val.trim().length()==0){
            return true;
        }
        else{
            return isCdLike(val, cdGroup);
        }
    }
    
    public static String isNulltoMessage(String varVal, String varName) throws Exception {
        String result = "";
        
        if(varVal==null){
            result = varName+ " parameter empty!";   
        }else if(varVal.equals("")){
            result = varName+ " parameter empty!";   
        }
        
        return result;
    }
    
    public static String getFileExtension(String name) throws Exception {

        int pos = name.lastIndexOf(".");
        return name.substring(pos + 1);
    }
    
    public static String getFileNamOfURL(String url) throws Exception {

        int pos = url.lastIndexOf("/");
        return url.substring(pos + 1);
    }
    
    public static String getImgFileName(String nmCompShort, String noReq, BigDecimal noSeq) throws Exception {
        StringBuffer tmpFileName = new StringBuffer();
        Format formatter = null;
        String sDate = null;
        
        tmpFileName.append(nmCompShort + "_"); // 법인약명
        System.out.println("tmpFileName:" + tmpFileName.toString());
        tmpFileName.append(noReq + "_"); // 접수번호
        System.out.println("tmpFileName:" + tmpFileName.toString());
        formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        sDate = formatter.format(new Date());
        tmpFileName.append(sDate + "_"); // yyyymmddhh24miss
        System.out.println("tmpFileName:" + tmpFileName.toString());
        tmpFileName.append(noSeq); // 시퀀스
        System.out.println("tmpFileName:" + tmpFileName.toString());
        return tmpFileName.toString();
    }
    
    public static String getImgSavePath(String nasPath) throws Exception {
        System.out.println("CommonModule getImgSavePath...");
        StringBuffer tmpPath = new StringBuffer();
        Format formatter = null;
        String sDate = null;
        
        System.out.println(nasPath);
        tmpPath.append(nasPath);
        System.out.println("tmpPath:" + tmpPath.toString());

        formatter = new SimpleDateFormat("yyyy");
        sDate = formatter.format(new Date());
        tmpPath.append(sDate + "/"); // yyyy
        System.out.println("tmpPath:" + tmpPath.toString());
        formatter = new SimpleDateFormat("yyyyMMdd");
        sDate = formatter.format(new Date());
        tmpPath.append(sDate + "/"); // yyyymmdd
        System.out.println("tmpPath:" + tmpPath.toString());
        System.out.println("CommonModule-getSavePath:" + tmpPath.toString());
        return tmpPath.toString();
    }    
}
