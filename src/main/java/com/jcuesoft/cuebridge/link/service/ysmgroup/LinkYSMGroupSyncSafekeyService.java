package com.jcuesoft.cuebridge.link.service.ysmgroup;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class LinkYSMGroupSyncSafekeyService {

    String callback;
    String sql;
    Query q;
    int insertCount;
    int updateCount;
    
    public LinkYSMGroupSyncSafekeyService(){
        System.out.println("LinkYSMGroupService init");
        callback = "";
    }
  
    public String excuteBizSyncSafekey(EntityManager em, Map<String, String> param) throws Exception{
        System.out.println("excuteBizSyncSafekey init");
        callback = "Succes";
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {            
            if(param.get("yn_surety").equals("Y")){
                sql = "UPDATE BRC_SURETY SET "
                        + "DS_NICE_SAFEKEY = ?1,"
                        + "NO_CUSTOM = NVL(?2, NO_CUSTOM), "
                        + "YN_CDT_INQUIRY = CASE WHEN ?1 > ' ' THEN 'Y' ELSE YN_CDT_INQUIRY END, "              
                        + "CN_SAFEKEY_EXT = NVL(?4, 0), "
                        + "DM_UPT = SYSDATE "
                        + "WHERE NO = ?3 ";        
                q= em.createNativeQuery(sql);
                q.setParameter(1, param.get("ds_nice_safekey"));
                q.setParameter(2, param.get("no_custom"));
                q.setParameter(3, param.get("no_key"));
                q.setParameter(4, param.get("cn_safekey_ext"));
                updateCount = q.executeUpdate();
                if (updateCount > 0) {
                    System.out.println(updateCount + ": update record!");      
                }else{
                    System.err.println("<Notice> no update record!");
                }
                
                sql = "UPDATE BRC_SURETY_HASH SET "
                        + "NO_CUSTOM_HASH = NVL(SHA512_HASH(?2), NO_CUSTOM_HASH), "
                        + "DM_UPT = SYSDATE "
                        + "WHERE (NO_REQ, NO_SURETY) = (SELECT NO_REQ, NO_SURETY FROM BRC_SURETY WHERE NO = ?3) ";        
                q= em.createNativeQuery(sql);
                q.setParameter(2, param.get("no_custom"));
                q.setParameter(3, param.get("no_key"));
                updateCount = q.executeUpdate();
                if (updateCount > 0) {
                    System.out.println(updateCount + ": update record!");      
                }else{
                    System.err.println("<Notice> no update record!");
                }                
            }else{
                sql = "UPDATE BRC_CUSTOM SET "
                        + "DS_NICE_SAFEKEY = ?1,"
                        + "NO_CUSTOM = NVL(?2, NO_CUSTOM), "
                        + "YN_CDT_INQUIRY = CASE WHEN ?1 > ' ' THEN 'Y' ELSE YN_CDT_INQUIRY END, "
                        + "CN_SAFEKEY_EXT = NVL(?4, 0), "                        
                        + "DM_UPT = SYSDATE "
                        + "WHERE NO_REQ = ?3 ";        
                q= em.createNativeQuery(sql);
                q.setParameter(1, param.get("ds_nice_safekey"));
                q.setParameter(2, param.get("no_custom"));
                q.setParameter(3, param.get("no_key"));
                q.setParameter(4, param.get("cn_safekey_ext"));
                updateCount = q.executeUpdate();
                if (updateCount > 0) {
                    System.out.println(updateCount + ": update record!");
                }else{
                    System.err.println("<Notice> no update record!");
                }
                
                sql = "UPDATE BRC_CUSTOM_HASH SET "
                        + "NO_CUSTOM_HASH = NVL(SHA512_HASH(?2), NO_CUSTOM_HASH), "
                        + "DM_UPT = SYSDATE "
                        + "WHERE NO_REQ = ?3 ";        
                q= em.createNativeQuery(sql);
                q.setParameter(2, param.get("no_custom"));
                q.setParameter(3, param.get("no_key"));
                updateCount = q.executeUpdate();
                if (updateCount > 0) {
                    System.out.println(updateCount + ": update record!");
                }else{
                    System.err.println("<Notice> no update record!");
                }                
            }
        }
        catch (RuntimeException re)
        {
            if (tx.isActive ())
                tx.rollback ();   // Alternative: Fix error and retry.
            callback = "Fail:internel db excute error";
            System.err.println(re);
            throw re;
        }
        
        tx.commit();
        return callback;
    }
}
