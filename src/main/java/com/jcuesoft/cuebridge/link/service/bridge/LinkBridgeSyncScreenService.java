package com.jcuesoft.cuebridge.link.service.bridge;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.jcuesoft.cuebridge.link.module.ModuleCommon;
import com.jcuesoft.cuebridge.link.util.UtilProperties;
import com.jcuesoft.cuebridge.link.util.UtilSSH;

public class LinkBridgeSyncScreenService {

    ModuleCommon mc;
    UtilSSH utilSSH;
    UtilProperties utilProperties;
    String callback;
    String sql;
    Query q;
    int insertCount;
    int updateCount;
    
    public LinkBridgeSyncScreenService() throws Exception{
        System.out.println("LinkBridgeSyncScreenService init");
        mc = new ModuleCommon();
        utilSSH = new UtilSSH();
        utilProperties = new UtilProperties();
        callback = "";
    }
    
    public String excuteBiz(EntityManager em, Map<String, String> param) throws Exception{
        System.out.println("excuteBiz init");
        callback = "S";
        String noEx = null;
        BigDecimal cnt;
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            /*==========================================================================*/
            /*BEX_MASTER 실행건 유무 확인 */ 
            /*==========================================================================*/            
            sql = "" + "\r\n" +
                    "SELECT  " + "\r\n" +
                    "    COUNT(*) CNT  " + "\r\n" +
                    "FROM  " + "\r\n" +
                    "    BEX_MASTER  " + "\r\n" +
                    "WHERE  " + "\r\n" +
                    "    NO_REQ = ?1  " + "\r\n" ;

            cnt = (BigDecimal)em.createNativeQuery(sql)
                    .setParameter(1, param.get("no_req"))
                    .getSingleResult();
            if(cnt.equals("0")){
                callback = "Fail:can't find any bex record by no_req";
                System.err.println(callback);
                tx.rollback ();
                return callback;
            }
            /*==========================================================================*/
            /*BEX_MASTER 해당 거래처 실행건 유무 확인 */ 
            /*==========================================================================*/            
            sql = "" + "\r\n" +
                    "SELECT  " + "\r\n" +
                    "    NO_EX  " + "\r\n" +
                    "FROM  " + "\r\n" +
                    "    BEX_MASTER  " + "\r\n" +
                    "WHERE  " + "\r\n" +
                    "    NO_REQ = ?1  " + "\r\n" +
                    "    AND " + "\r\n" +
                    "    CD_COOPERATE = ?9 " + "\r\n" +
                    "    AND " + "\r\n" +
                    "    CD_COOPERATE_PARTNER = ?11 " + "\r\n" ;

            try {            
                noEx = (String)em.createNativeQuery(sql)
                        .setParameter(1, param.get("no_req"))
                        .setParameter(9, param.get("cd_cooperate"))
                        .setParameter(11, param.get("cd_cooperate_partner"))
                        .getSingleResult();
            } catch (NoResultException e) {
                noEx = null;
            }            
            
            /*실행건 미존재 BEX_MASTER INSERT*/
            if(noEx == null){
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
                        .setParameter(1, param.get("no_req"))
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
                        + "     AM_COOPERATE_EXPECT,    "                        
                        + "     DS_COOPERATE,    "
                        + "     NM_SCREEN,       "
                        + "     AM_APP,          "
                        + "     CD_PDT,          "
                        + "     YN_PARTNER_ACT,          "
                        + "     CD_COOPERATE_PARTNER,          "                        
                        + "     DM_REG,          "
                        + "     ID_REG           "
                        + ") "
                        + "SELECT  "
                        + "     BEX_MASTER_SEQ.NEXTVAL, "
                        + "     ?1 , "
                        + "     ?10 , "
                        + "     ?9 AS CD_COOPERATE, "
                        + "     ?1 || '-' || ?10 AS NO_COOPERATE, "
                        + "     ' ' AS NO_COOPERATE_ACT, "
                        + "     NVL(?3, ' ') AS NM_SCREEN_PATH, "
                        + "     NVL(?5, ' ') AS DM_COOPERATE, "
                        + "     NVL(?6, 0) AS AM_COOPERATE, "
                        + "     NVL(?7, 0) AS AM_COOPERATE_EXPECT, "
                        + "     NVL(?8, '협력사 최초실행 by system') AS DS_COOPERATE, "
                        + "     NVL(?2, ' ') AS NM_SCREEN, "
                        + "     A.AM_CUSTOM AS AM_APP, "
                        + "     NVL(?4, ' ') AS CD_PDT, "
                        + "     'Y' AS YN_PARTNER_ACT, "
                        + "     NVL(?11, ' '), "                        
                        + "     SYSDATE AS DM_REG, "
                        + "     A.ID_SCREEN AS ID_REG "
                        + "FROM "
                        + "     BRC_LOAN A "
                        + "WHERE "
                        + "     A.NO_REQ = ?1 "
                        + "     AND ROWNUM <= 1 "
                        + "";
                
                q= em.createNativeQuery(sql);
                q.setParameter(1, param.get("no_req"));
                q.setParameter(2, param.get("nm_screen"));
                q.setParameter(3, param.get("nm_screen_path"));
                q.setParameter(4, param.get("cd_pdt"));
                q.setParameter(5, param.get("dm_cooperate"));
                q.setParameter(6, param.get("am_cooperate"));
                q.setParameter(7, param.get("am_cooperate_expect"));
                q.setParameter(8, param.get("ds_cooperate"));
                q.setParameter(9, param.get("cd_cooperate"));
                q.setParameter(10, noEx);
                q.setParameter(11, param.get("cd_cooperate_partner"));
       
                insertCount = q.executeUpdate();
                if (insertCount > 0) {
                    System.out.println(insertCount + ": insertCount record!");
                }else{
                    System.err.println("<Notice> no insertCount record!");
                }                
            }
            else{
                /*실행건 미존재 BEX_MASTER UPDATE*/  
                /*==========================================================================*/
                /*BEX_MASTER UPDATE*/ 
                /*==========================================================================*/
                sql = "" + "\r\n" +
                        "UPDATE BEX_MASTER T SET " + "\r\n" +
                        "    T.NM_SCREEN_PATH = NVL(TRIM(?3), T.NM_SCREEN_PATH), " + "\r\n" +
                        "    T.DM_COOPERATE = NVL(TRIM(?5), T.DM_COOPERATE), " + "\r\n" +
                        "    T.AM_COOPERATE = NVL(?6, 0) , " + "\r\n" +
                        "    T.AM_COOPERATE_EXPECT = NVL(?7, 0) , " + "\r\n" +
                        "    T.DS_COOPERATE = SUBSTRB(NVL(?8, ' '), 1, 200), " + "\r\n" +
                        "    T.NM_SCREEN = NVL(?2, ' '), " + "\r\n" +
                        "    T.CD_PDT = NVL(?4, ' '), " + "\r\n" +
                        "    T.DM_UPT = SYSDATE " + "\r\n" +
                        "WHERE  " + "\r\n" +
                        "    T.NO_REQ = ?1 " + "\r\n" +
                        "    AND " + "\r\n" +
                        "    CD_COOPERATE = ?9 " + "\r\n" +
                        "    AND " + "\r\n" +
                        "    CD_COOPERATE_PARTNER = ?11 " + "\r\n" ;
                

                q= em.createNativeQuery(sql);
                q.setParameter(1, param.get("no_req"));
                q.setParameter(2, param.get("nm_screen"));
                q.setParameter(3, param.get("nm_screen_path"));
                q.setParameter(4, param.get("cd_pdt"));
                q.setParameter(5, param.get("dm_cooperate"));
                q.setParameter(6, param.get("am_cooperate"));
                q.setParameter(7, param.get("am_cooperate_expect"));
                q.setParameter(8, param.get("ds_cooperate"));
                q.setParameter(9, param.get("cd_cooperate"));
                q.setParameter(11, param.get("cd_cooperate_partner"));
                updateCount = q.executeUpdate();
                if (updateCount > 0) {
                    System.out.println(updateCount + ": updateCount record!");      
                }else{
                    System.err.println("<Notice> no updateCount record!");
                }
            }
            
            /*==========================================================================*/
            /*BEX_CONTACT INSERT */ 
            /*==========================================================================*/
            sql = "" + "\r\n" +
                    "INSERT INTO BEX_CONTACT                                                                                  " + "\r\n" +
                    "( " + "\r\n" +
                    "    NO            , " + "\r\n" +
                    "    NO_REQ        , " + "\r\n" +
                    "    NO_EX         , " + "\r\n" +
                    "    NO_CONTACT_SEQ, " + "\r\n" +
                    "    DS_COOPERATE  , " + "\r\n" +
                    "    NM_SCREEN  , " + "\r\n" +
                    "    DM_REG        , " + "\r\n" +
                    "    ID_REG         " + "\r\n" +
                    ") " + "\r\n" +
                    "SELECT " + "\r\n" +
                    "    BEX_CONTACT_SEQ.NEXTVAL            , " + "\r\n" +
                    "    ?1        , " + "\r\n" +
                    "    ?10         , " + "\r\n" +
                    "    (SELECT LPAD(NVL(MAX(no_contact_seq), '0')+1, 3, '0')  " + "\r\n" +
                    "    FROM BEX_CONTACT " + "\r\n" +
                    "    WHERE NO_REQ = ?1 AND NO_EX = ?10) , " + "\r\n" +
                    "    SUBSTRB(NVL(?8, '협력사 최초실행 by system'), 1, 200)  , " + "\r\n" +
                    "    NVL(?2, ' ')  , " + "\r\n" +
                    "    SYSDATE        , " + "\r\n" +
                    "    'SYSTEM'         " + "\r\n" +
                    "FROM  " + "\r\n" +
                    "    BEX_MASTER  " + "\r\n" +
                    "WHERE     " + "\r\n" +
                    "    NO_REQ = ?1     " + "\r\n" +
                    "    AND NO_EX = ?10     " + "\r\n" ;

            q= em.createNativeQuery(sql);
            q.setParameter(1, param.get("no_req"));
            q.setParameter(8, param.get("ds_cooperate"));
            q.setParameter(2, param.get("nm_screen"));
            q.setParameter(10, noEx);
   
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
            System.err.println("<Error> excuteBiz RuntimeException");
            System.err.println(re);
            re.printStackTrace();
            System.out.println(callback);
            return callback;
        }
        catch (Exception e) {
            if (tx.isActive ())
                tx.rollback ();   // Alternative: Fix error and retry.
            callback = "Fail:internel db excute error";
            System.err.println("<Error> excuteBiz Exception");
            e.printStackTrace();
            System.out.println(callback);
            return callback;
        }

        
        tx.commit();
        callback = noEx;
        return callback;
    }    
}
