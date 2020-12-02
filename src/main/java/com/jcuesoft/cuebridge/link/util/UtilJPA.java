package com.jcuesoft.cuebridge.link.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class UtilJPA {

        private static final UtilProperties utilProps;

        static{
            try{
                utilProps = new UtilProperties();
            }
            catch (Throwable e){
                    System.err.println("UtilProperties fail" + e);
                    e.printStackTrace();
                    throw new ExceptionInInitializerError(e);
            }
        } 

        public static EntityManagerFactory getentityManagerFactory(String url) throws Exception{
            System.out.println("getentityManagerFactory init:"+url);
            if(url.matches("(.*)"+utilProps.getNetProperty("youi.hostname")+"(.*)")){
                return Persistence.createEntityManagerFactory("bridge_youi");
            }else if(url.matches("(.*)"+utilProps.getNetProperty("star.hostname")+"(.*)")){
                return Persistence.createEntityManagerFactory("bridge_star");
            }else if(url.matches("(.*)"+utilProps.getNetProperty("mill.hostname")+"(.*)")){
                return Persistence.createEntityManagerFactory("bridge_mill");
            }else{
                System.err.println("getCdCompanyByHost unknown cd_company:" + url);
                return null;
            }
        }

}