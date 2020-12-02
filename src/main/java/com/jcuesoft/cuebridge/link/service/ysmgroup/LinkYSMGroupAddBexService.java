package com.jcuesoft.cuebridge.link.service.ysmgroup;

import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.jcuesoft.cuebridge.link.module.ModuleCommon;

public class LinkYSMGroupAddBexService {

    String callback;
    String sql;
    Query q;
    int insertCount;
    int updateCount;
    
    public LinkYSMGroupAddBexService(){
        System.out.println("LinkYSMGroupService init");
        callback = "";
    }
    
    public String excuteBizAddBex(EntityManager em, Map<String, String> param) throws Exception{
        System.out.println("excuteBizAddBex init");
        callback = "Succes";
        String noReq = param.get("no_cooperate").substring(0, 13);
        String noEx = null;
        
        ModuleCommon moduleCommon = new ModuleCommon();
        param.put("cd_cooperate", ModuleCommon.getCdCooperateByComCd(param.get("cd_company_ex")));
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {            
            /*==========================================================================*/
            /*BEX_MASTER 실행건 유무 확인 */ 
            /*==========================================================================*/            
            sql = "SELECT COUNT(*) CNT "
                    + "FROM BEX_MASTER "
                    + "WHERE NO_COOPERATE = ?1 ";
            BigDecimal cnt = (BigDecimal)em.createNativeQuery(sql)
                    .setParameter(1, param.get("no_cooperate"))
                    .getSingleResult();
            if(cnt.equals("0")){
                callback = "Fail:can't find record by no_cooperate";
                System.err.println(callback);
                tx.rollback ();
                return callback;
            }            
            /*==========================================================================*/
            /*BEX_MASTER 채번 */ 
            /*==========================================================================*/            
            sql = ""
                    + "SELECT "
                    + " LPAD(NO_EX+1, 3, '0')  AS NO_EX "
                    + "FROM "
                    + " BEX_MASTER "
                    + "WHERE "
                    + " NO_REQ = ?1 "
                    + " AND "
                    + " NO_EX = (SELECT MAX(NO_EX) FROM BEX_MASTER WHERE NO_REQ = ?1  )      "
                    + "FOR UPDATE ";
            noEx = (String) em.createNativeQuery(sql)
                    .setParameter(1, noReq)
                    .getSingleResult();
            
            /*==========================================================================*/
            /*BEX_MASTER 생성 */ 
            /*==========================================================================*/
            sql = ""
                    + "INSERT INTO BEX_MASTER "
                    + "(                                                                                      "
                    + "     NO,              "
                    + "     NO_REQ,          "
                    + "     NO_EX,           "
                    + "     CD_COOPERATE,    "
                    + "     NO_COOPERATE,    "
                    + "     NO_COOPERATE_ACT,"
                    + "     NM_SCREEN_PATH,  "
                    + "     DM_COOPERATE,    "
                    + "     AM_COOPERATE,    "
                    + "     DS_COOPERATE,    "
                    + "     NM_SCREEN,       "
                    + "     AM_APP,          "
                    + "     CD_PDT,          "
                    + "     DM_REG,          "
                    + "     ID_REG           "
                    + ") "
                    + "SELECT  "
                    + "     BEX_MASTER_SEQ.NEXTVAL, "
                    + "     ?1 , "
                    + "     '"+ noEx  + "' , "
                    + "     ?3 AS CD_COOPERATE, "
                    + "     ?1 || '-' || ?2 AS NO_COOPERATE, "
                    + "     ' ' AS NO_COOPERATE_ACT, "
                    + "     ?4 AS NM_SCREEN_PATH, "
                    + "     NULL AS DM_COOPERATE, "
                    + "     0 AS AM_COOPERATE, "
                    + "     '최초실행 by system' AS DS_COOPERATE, "
                    + "     ?5 AS NM_SCREEN, "
                    + "     A.AM_CUSTOM AS AM_APP, "
                    + "     ' ' AS CD_PDT, "
                    + "     SYSDATE AS DM_REG, "
                    + "     A.ID_SCREEN AS ID_REG "
                    + "FROM "
                    + "     BRC_LOAN A "
                    + "WHERE "
                    + "     A.NO_REQ = ?1 "
                    + "     AND ROWNUM <= 1 "
                    + "";
            
            q= em.createNativeQuery(sql);
            q.setParameter(1, noReq);
            q.setParameter(2, noEx);
            q.setParameter(3, param.get("cd_cooperate"));
            q.setParameter(4, param.get("nm_screen_path"));
            q.setParameter(5, param.get("nm_screen"));
   
            insertCount = q.executeUpdate();
            if (insertCount > 0) {
                System.out.println(insertCount + ": insertCount record!");
            }else{
                System.err.println("<Notice> no insertCount record!");
            }            
            /*==========================================================================*/
            /*BEX_CONTACT 생성 */ 
            /*==========================================================================*/                
            sql = ""
                    + "INSERT INTO BEX_CONTACT "
                    + "(                                                                                      "
                    + "     NO,              "
                    + "     NO_REQ,          "
                    + "     NO_EX,           "
                    + "     NO_CONTACT_SEQ,    "
                    + "     DS_COOPERATE,    "
                    + "     NM_SCREEN,"
                    + "     DM_REG,  "
                    + "     ID_REG    "
                    + ") "
                    + "VALUES  "
                    + "(                                                                                      "
                    + "     BEX_CONTACT_SEQ.NEXTVAL, "
                    + "     ?1 , "
                    + "     ?2 , "
                    + "     (SELECT LPAD(NVL(MAX(NO_CONTACT_SEQ), '0')+1, 3, '0') "
                    + "     FROM BEX_CONTACT "
                    + "     WHERE NO_REQ = ?1 AND NO_EX = ?2 ),  "
                    + "     '최초실행 by system', "
                    + "     NVL(?3, ' '), "
                    + "     SYSDATE, "
                    + "     'SYSTEM'  "
                    + ") "
                    + "";
            
            q= em.createNativeQuery(sql);
            q.setParameter(1, noReq);
            q.setParameter(2, noEx);
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
            throw re;
        }
        tx.commit();
        callback = "EXCUTEKEY[" +noReq + "-" + noEx+ "]EXCUTEKEY";
        return callback;
    }    
}
