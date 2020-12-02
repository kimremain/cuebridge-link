package com.jcuesoft.cuebridge.link.service.sanwa;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.jcuesoft.cuebridge.link.module.ModuleBiz;
import com.jcuesoft.cuebridge.link.module.ModuleCommon;

public class LinkSanwaSyncScreenService {

    String callback;
    String sql;
    String dm_cooperate = "";
    String am_cooperate = "0";    
    Query q;
    int insertCount;
    int updateCount;
    
    public LinkSanwaSyncScreenService(){
        System.out.println("LinkSanwaSyncScreenService init");
        callback = "";
    }
    
    public String excuteBiz(EntityManager em, Map<String, String> param) throws Exception {
        System.out.println("excuteBiz init");
        callback = "Succes";
        String memo = "";
        String nmScreenPath = "";
        if(param.get("auth_code") != null && !param.get("auth_code").isEmpty()){
            memo = memo + "[인증코드"+param.get("auth_code")+"]";
        } 
        if(param.get("rate_cheat") != null && param.get("rate_cheat").equals("1")){
            memo = memo + "<이율허위>";
        }        
        if(param.get("memo") != null){
            memo = memo + param.get("memo");
        }
        if(param.get("debug") != null){
            memo = memo + param.get("debug");
        }
        if(param.get("branch_name") != null){
            nmScreenPath = param.get("branch_name");
        }        
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {            
            if(param.get("status").equals("승인")){
                sql = "SELECT TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') AS DM FROM DUAL";
                dm_cooperate = (String) em.createNativeQuery(sql).getSingleResult();
            }
            if(ModuleCommon.isNumeric(param.get("amt"), false)){
                int tmpAmt = Integer.parseInt(param.get("amt").trim());
                am_cooperate = Integer.toString(tmpAmt*10000);
            }      
            
            /*==========================================================================*/
            /*BEX_MASTER UPDATE */ 
            /*==========================================================================*/            
            
            sql = "UPDATE BEX_MASTER SET  "
                    + "DM_COOPERATE = ?7, "
                    + "AM_COOPERATE = ?2, "
                    + "DS_COOPERATE = SUBSTRB(NVL(?3, ' '), 1, 200), "                    
                    + "NM_SCREEN = NVL(?4, ' '), "
                    + "CD_PDT = ' ', "
                    + "NM_SCREEN_PATH = SUBSTRB(NVL(?6, NM_SCREEN_PATH), 1, 20), "                    
                    + "DM_UPT = SYSDATE "
                    + "WHERE NO_COOPERATE = ?5 ";
            q= em.createNativeQuery(sql);
            q.setParameter(2, am_cooperate);
            q.setParameter(3, memo);
            q.setParameter(4, param.get("status"));
            q.setParameter(5, param.get("app_id"));
            q.setParameter(6, nmScreenPath);
            q.setParameter(7, dm_cooperate);

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
            q.setParameter(1, param.get("app_id"));
            q.setParameter(2, memo);
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
        String result = biz.passSyncScreen(em, param.get("app_id"), 
                                            param.get("status"), 
                                            nmScreenPath, 
                                            "", 
                                            dm_cooperate, 
                                            am_cooperate, 
                                            "0", 
                                            memo);
        System.out.println("passSyncScreen result:"+result);
        
        return callback;
    }

}
