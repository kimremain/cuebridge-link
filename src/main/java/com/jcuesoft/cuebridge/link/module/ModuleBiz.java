package com.jcuesoft.cuebridge.link.module;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.jcuesoft.cuebridge.link.util.UtilHttpClient;

public class ModuleBiz {
    static ModuleCommon mc;
    String sql;
    Query q;
    
    public ModuleBiz(){
        System.out.println("ModuleBiz init");
        mc = new ModuleCommon();
    }
    
    /*    
    접수처실행식별번호       no_cooperate
    실행거래처심사상태명      nm_screen
    실행거래처접수위치       nm_screen_path
    실행거래처승인상품코드     cd_pdt
    실행거래처승인일시       dm_cooperate
    실행거래처승인금액       am_cooperate
    실행거래처승인예정금액     am_cooperate_expect
    실행거래처심사메모       ds_cooperate
    */
    public String passSyncScreen(EntityManager em,
                                                String no_cooperate, 
                                                String nm_screen, 
                                                String nm_screen_path, 
                                                String cd_pdt, 
                                                String dm_cooperate, 
                                                String am_cooperate, 
                                                String am_cooperate_expect,
                                                String ds_cooperate) throws Exception{
        String callback = "Succes";
        String noReq = no_cooperate.substring(0, 13);
        String orgNoReq = "";
        String orgNoCooperate = "";
        String cdCooperatePartner = "";
        String exCdCooperate = "";
        String destDsUrl = "";
        String dsResult = "";        
        
        if(em == null) return "Fail:em parameter is null!";        
        try
        {
            /*파라미터검증*/
            dsResult = dsResult+ModuleCommon.isNulltoMessage(no_cooperate, "no_cooperate");
            dsResult = dsResult+ModuleCommon.isNulltoMessage(nm_screen, "nm_screen");
            if(!dsResult.equals("")){
                callback = "Fail:"+dsResult;
                System.out.println(callback);
                return callback;
            }
            if(nm_screen_path == null) nm_screen_path = "";
            if(cd_pdt == null) cd_pdt = "";
            if(dm_cooperate == null) dm_cooperate = "";
            if(am_cooperate == null || am_cooperate.trim().equals("")) am_cooperate = "0";
            if(am_cooperate_expect == null || am_cooperate_expect.trim().equals("")) am_cooperate_expect = "0";
            if(ds_cooperate == null) ds_cooperate = "";            
            
            sql = "" + "\r\n" +
                    "SELECT  " + "\r\n" +
                    "    SUBSTRB(BL.NO_COOPERATE, 1, 13) AS ORG_NO_REQ, " + "\r\n" +
                    "    BL.NO_COOPERATE AS ORG_NO_COOPERATE,     " + "\r\n" +
                    "    (SELECT CD_COOPERATE_BRIDGE FROM ST_COMP) AS CD_COOPERATE_PARTNER, " + "\r\n" +                    
                    "    BM.CD_COOPERATE AS EX_CD_COOPERATE, " + "\r\n" +
                    "    (SELECT DS_URL FROM ST_PARTNER WHERE YN_USE = 'Y' AND CD_COOPERATE = BL.CD_COOPERATE) AS DS_URL " + "\r\n" +
                    "FROM  " + "\r\n" +
                    "    BRC_LOAN BL, " + "\r\n" +
                    "    BEX_MASTER BM " + "\r\n" +
                    "WHERE  " + "\r\n" +
                    "    BL.NO_REQ = BM.NO_REQ " + "\r\n" +
                    "    AND " + "\r\n" +
                    "    BM.NO_COOPERATE = ?1 " + "\r\n" ;

            q= em.createNativeQuery(sql);
            q.setParameter(1, no_cooperate);
            List<Object[]> cotisation = q.getResultList();
            Object[] cotisationData = null;
            
            for (int i = 0; i < cotisation.size(); i++) {
                cotisationData = cotisation.get(i);
                orgNoReq = (String) cotisationData[0];
                orgNoCooperate = (String) cotisationData[1];
                cdCooperatePartner = (String) cotisationData[2];
                exCdCooperate = (String) cotisationData[3];
                destDsUrl = (String) cotisationData[4];
            }                     
            
            if(destDsUrl == null || destDsUrl.equals("")){
                callback = "Fail:no_cooperate="+no_cooperate+" can not find any partner record";         
                return callback;
            }
            
            List <NameValuePair> sendParam = new ArrayList <NameValuePair>();
            sendParam.add(new BasicNameValuePair("no_req" , orgNoReq));
            sendParam.add(new BasicNameValuePair("cd_cooperate_partner" , cdCooperatePartner));
            sendParam.add(new BasicNameValuePair("cd_cooperate" , exCdCooperate));
            sendParam.add(new BasicNameValuePair("nm_screen" , nm_screen));
            sendParam.add(new BasicNameValuePair("nm_screen_path" , nm_screen_path));
            sendParam.add(new BasicNameValuePair("cd_pdt" , cd_pdt));
            sendParam.add(new BasicNameValuePair("dm_cooperate" , dm_cooperate));
            sendParam.add(new BasicNameValuePair("am_cooperate" , am_cooperate));
            sendParam.add(new BasicNameValuePair("am_cooperate_expect" , am_cooperate_expect));
            sendParam.add(new BasicNameValuePair("ds_cooperate" , ds_cooperate));
            
            System.out.println("requestHttpPost parameter");
            for(NameValuePair dummy: sendParam){
                System.out.println(dummy.getName()+":["+dummy.getValue()+"]");
            }
            
            UtilHttpClient utilHttpClient = new UtilHttpClient();
            String resultPost = utilHttpClient.requestHttpPost(destDsUrl,sendParam,"utf-8");
            System.out.println("POST Result : "+ resultPost);
            
        }
        catch (NoResultException e)
        {
            callback = "Fail:no_cooperate="+no_cooperate+" can not find any record";                
            System.err.println(e);
            System.err.println("Call CommonJPAUtil selectOneQuery NoResultException :" + q.toString());
            System.err.println(callback);
            return callback;
        }
        catch (RuntimeException e)
        {
            callback = "Fail:internel db excute error";
            System.err.println(e);
            System.err.println(callback);
            return callback;
        }
        catch (Exception e){
            callback = "Fail:unexpected error";
            System.err.println(e);
            System.err.println(callback);
            return callback;
        }

        return callback;
    }
}
