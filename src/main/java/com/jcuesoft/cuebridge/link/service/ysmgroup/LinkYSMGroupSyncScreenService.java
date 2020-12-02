package com.jcuesoft.cuebridge.link.service.ysmgroup;

import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.jcuesoft.cuebridge.link.module.ModuleBiz;
import com.jcuesoft.cuebridge.link.module.ModuleCommon;

public class LinkYSMGroupSyncScreenService {

    String callback;
    String sql;
    String dm_cooperate = "";
    String am_cooperate = "0";
    String am_cooperate_expect = "0";
    Query q;
    int insertCount;
    int updateCount;
    
    public LinkYSMGroupSyncScreenService(){
        System.out.println("LinkYSMGroupService init");
        callback = "";
    }
    
    public String excuteBizSyncScreen(EntityManager em, Map<String, String> param) throws Exception{
        System.out.println("excuteBizSyncScreen init");
        callback = "Succes";
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            if(param.get("nm_screen").equals("대출대상")){
                sql = "SELECT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') AS DM FROM DUAL";
                dm_cooperate = (String) em.createNativeQuery(sql).getSingleResult();
            }
            if(ModuleCommon.isNumeric(param.get("am_cooperate"), false)){
                int tmpAmt = Integer.parseInt(param.get("am_cooperate").trim());
                am_cooperate = Integer.toString(tmpAmt*10000);
            }
            if(ModuleCommon.isNumeric(param.get("am_cooperate_expect"), false)){
                int tmpAmtExpect = Integer.parseInt(param.get("am_cooperate_expect").trim());
                am_cooperate_expect = Integer.toString(tmpAmtExpect*10000);
            }
            
            sql = "UPDATE BEX_MASTER SET  "
                    + "NM_SCREEN_PATH = NVL(?1, ' '),"
                    + "DM_COOPERATE = NVL(?2, ' '), "
                    + "AM_COOPERATE = ?3, "
                    + "AM_COOPERATE_EXPECT = ?8, "                    
                    + "DS_COOPERATE = SUBSTRB(NVL(?4, ' '), 1, 200), "
                    + "NM_SCREEN = NVL(?5, ' '), "
                    + "CD_PDT = NVL(?6, ' '), "
                    + "DM_UPT = SYSDATE "
                    + "WHERE NO_COOPERATE = ?7 ";        
            q= em.createNativeQuery(sql);
            q.setParameter(1, param.get("nm_screen_path"));
            q.setParameter(2, dm_cooperate);
            q.setParameter(3, am_cooperate);
            q.setParameter(4, param.get("ds_cooperate"));
            q.setParameter(5, param.get("nm_screen"));
            q.setParameter(6, param.get("cd_pdt"));
            q.setParameter(7, param.get("no_cooperate"));
            q.setParameter(8, am_cooperate_expect);
            updateCount = q.executeUpdate();
            if (updateCount > 0) {
                System.out.println(updateCount + ": updateCount record!");      
            }else{
                System.err.println("<Notice> no updateCount record!");
            }
            
            sql = "  "
                    + "INSERT INTO BEX_CONTACT                                                                                 "
                    + "("
                    + "    NO            ,"
                    + "    NO_REQ        ,"
                    + "    NO_EX         ,"
                    + "    NO_CONTACT_SEQ,"
                    + "    DS_COOPERATE  ,"
                    + "    NM_SCREEN  ,"                    
                    + "    DM_REG        ,"
                    + "    ID_REG        "
                    + ")"
                    + "SELECT"
                    + "    BEX_CONTACT_SEQ.NEXTVAL            ,"
                    + "    SUBSTRB(?1, 1, 13)        ,"
                    + "    SUBSTRB(?1, -3)         ,"
                    + "    (SELECT LPAD(NVL(MAX(no_contact_seq), '0')+1, 3, '0') "
                    + "    FROM BEX_CONTACT"
                    + "    WHERE NO_REQ = SUBSTRB(?1, 1, 13) AND NO_EX = SUBSTRB(?1, -3)) ,"
                    + "    SUBSTRB(NVL(?2, ' '), 1, 200)  ,"
                    + "    NVL(?3, ' ')  ,"
                    + "    SYSDATE        ,"
                    + "    'SYSTEM'        "
                    + "FROM "
                    + "    BEX_MASTER "
                    + "WHERE    "
                    + "    NO_REQ = SUBSTRB(?1, 1, 13)    "
                    + "    AND NO_EX = SUBSTRB(?1, -3)    " ;
            q= em.createNativeQuery(sql);
            q.setParameter(1, param.get("no_cooperate"));
            q.setParameter(2, param.get("ds_cooperate"));
            q.setParameter(3, param.get("nm_screen"));
   
            insertCount = q.executeUpdate();
            if (insertCount > 0) {
                System.out.println(insertCount + ": insertCount record!");      
            }else{
                System.err.println("<Notice> no insertCount record!");
            }            
        }
        catch (RuntimeException re)
        {
            if (tx.isActive ())
                tx.rollback ();   // Alternative: Fix error and retry.
            callback = "Fail:internel db excute error";
            System.err.println(re);
            System.out.println(callback);
            return callback;
        }
        catch (Exception e)
        {
            if (tx.isActive ())
                tx.rollback ();   // Alternative: Fix error and retry.
            callback = "Fail:Unexpected error";
            System.err.println(e);
            System.out.println(callback);
            return callback;
        }
        
        tx.commit();
        
        /*중개협력업체간 기표연계*/
        ModuleBiz biz = new ModuleBiz();
        biz.passSyncScreen(em, param.get("no_cooperate"), 
                                            param.get("nm_screen"), 
                                            param.get("nm_screen_path"), 
                                            param.get("cd_pdt"), 
                                            dm_cooperate, 
                                            am_cooperate, 
                                            am_cooperate_expect, 
                                            param.get("ds_cooperate"));
        
        return callback;
    }
    
}
