package com.jcuesoft.cuebridge.link.service.a1;

import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.jcuesoft.cuebridge.link.module.ModuleBiz;
import com.jcuesoft.cuebridge.link.module.ModuleCommon;

public class LinkA1SyncScreenService {

    String callback;
    String sql;
    String dm_cooperate = "";
    String am_cooperate = "0";
    Query q;
    int insertCount;
    int updateCount;
    
    public LinkA1SyncScreenService(){
        System.out.println("LinkA1SyncScreenService init");
        callback = "";
    }
    
    public String excuteBiz(EntityManager em, Map<String, String> param) throws Exception {
        System.out.println("excuteBiz init");
        callback = "Succes";
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {            
            
            if(param.get("status").equals("대출")){
                sql = "SELECT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') AS DM FROM DUAL";
                dm_cooperate = (String) em.createNativeQuery(sql).getSingleResult();
            }
            if(ModuleCommon.isNumeric(param.get("money1"), false)){
                int tmpAmt = Integer.parseInt(param.get("money1").trim());
                am_cooperate = Integer.toString(tmpAmt*10000);
            }
            
            /*==========================================================================*/
            /*BEX_MASTER UPDATE */ 
            /*==========================================================================*/            
            
            sql = "UPDATE BEX_MASTER SET  "
                    + "DM_COOPERATE = ?6, "
                    + "AM_COOPERATE = ?2, "
                    + "DS_COOPERATE = SUBSTRB(NVL(?3, ' '), 1, 200), "
                    + "NM_SCREEN = NVL(?4, ' '), "
                    + "CD_PDT = ' ', "
                    + "DM_UPT = SYSDATE "
                    + "WHERE NO_COOPERATE = ?5 ";        
            q= em.createNativeQuery(sql);
            q.setParameter(2, am_cooperate);
            q.setParameter(3, param.get("memo"));
            q.setParameter(4, param.get("status"));
            q.setParameter(5, param.get("code"));
            q.setParameter(6, dm_cooperate);

            updateCount = q.executeUpdate();
            if (updateCount > 0) {
                System.out.println(updateCount + ": updateCount record!");      
            }else{
                System.out.println("<Notice> no updateCount record!");
            }
            
            /*==========================================================================*/
            /*BEX_CONTACT INSERT */ 
            /*==========================================================================*/            
            
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
            q.setParameter(1, param.get("code"));
            q.setParameter(2, param.get("memo"));
            q.setParameter(3, param.get("status"));
   
            insertCount = q.executeUpdate();
            if (insertCount > 0) {
                System.out.println(insertCount + ": insertCount record!");      
            }else{
                System.out.println("<Notice> no insertCount record!");
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
        biz.passSyncScreen(em, param.get("code"), 
                                            param.get("status"), 
                                            "", 
                                            "", 
                                            dm_cooperate, 
                                            am_cooperate, 
                                            "0", 
                                            param.get("memo"));
        
        return callback;
    }

}
