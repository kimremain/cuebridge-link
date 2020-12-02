package com.jcuesoft.cuebridge.link.service.recruit;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.jcuesoft.cuebridge.link.module.ModuleCommon;
import com.jcuesoft.cuebridge.link.util.UtilCommon;
import com.jcuesoft.cuebridge.link.util.UtilProperties;
import com.jcuesoft.cuebridge.link.util.UtilSSH;

public class LinkRecruitService {

    ModuleCommon mc;
    UtilCommon utilCommon;
    UtilSSH utilSSH;
    UtilProperties utilProperties;
    String callback;
    String sql;
    Query q;
    int insertCount;
    int updateCount;
    
    public LinkRecruitService() throws Exception{
        System.out.println("LinkYSMGroupService init");
        mc = new ModuleCommon();
        utilCommon = new UtilCommon();
        utilSSH = new UtilSSH();
        utilProperties = new UtilProperties();
        callback = "";
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String excuteBizRecruit(EntityManager em, Map<String, String> param) throws Exception{
        System.out.println("excuteBizRecruit init");
        callback = "S";
        String noReq = null;
        String strSplit = "\\|\\|";
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {
            /*==========================================================================*/
            /*BRC_LOAN 채번 */ 
            /*==========================================================================*/  
            try{
          
                sql = ""
                        + "SELECT "
                        + " SUBSTRB(NO_REQ, 1, 9) || LPAD((SUBSTRB(NO_REQ, 10, 14)  + 1), 4, '0') AS NO_REQ "
                        + "FROM "
                        + " BRC_LOAN "
                        + "WHERE "
                        + " NO_REQ = (SELECT MAX(NO_REQ) FROM BRC_LOAN WHERE NO_REQ >= TO_CHAR(SYSDATE, 'YYYYMMDD'))      "
                        + "FOR UPDATE ";
                noReq = (String) em.createNativeQuery(sql)
                        .getSingleResult();
            }catch(NoResultException e){
                if (noReq==null || noReq.isEmpty()) {
                    StringBuffer sb = new StringBuffer();
                    Date now = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");      
                    sb.append(format.format(now));
                    sb.append("-0001");
                    noReq = sb.toString();
                }
            }

            /*==========================================================================*/
            /*BRC_LOAN INSERT */ 
            /*==========================================================================*/
            sql = ""
                    + "INSERT INTO BRC_LOAN "
                    + "(                                  "
                    + "     NO          ,"
                    + "     NO_REQ      ,"
                    + "     CD_COOPERATE,"
                    + "     NO_COOPERATE,"
                    + "     CD_PATH     ,"
                    + "     CD_STATUS   ,"
                    + "     YN_SURETY   ,"
                    + "     CD_PDT      ,"
                    + "     DD          ,"
                    + "     AM_CUSTOM   ,"
                    + "     AM_COOPERATE,"
                    + "     AM_APP      ,"
                    + "     CD_USE      ,"
                    + "     CD_REJECT   ,"
                    + "     CD_ECHANEL  ,"
                    + "     CD_EPORTAL  ,"
                    + "     NM_EKEYWORD ,"
                    + "     CD_EPATH    ,"
                    + "     ID_RECEIVE  ,"
                    + "     ID_SCREEN   ,"
                    + "     ID_AUTH     ,"
                    + "     DS_RECEIVE  ,"
                    + "     DS_SCREEN   ,"
                    + "     DM_RECEIVE  ,"
                    + "     DM_SCREEN   ,"
                    + "     DM_CONTACT  ,"
                    + "     DM_AUTH     ,"
                    + "     CD_CANCEL   ,"             
                    + "     DM_REJECT   ,"
                    + "     DS_EXT_KEY  ,"
                    + "     DM_REG      ,"
                    + "     ID_REG      ,"
                    + "     YN_REAPP    ,"
                    + "     DS_ACCESS_IP    ,"
                    + "     CD_RECRUIT_GROUP    ,"                    
                    + "     DS_COOPERATE"
                    + ") "
                    + "VALUES  "
                    + "(                                  "
                    + "     BRC_LOAN_SEQ.NEXTVAL          ,"
                    + "     ?1       ,"                 //NO_REQ
                    + "     ?2 ,"                       //CD_COOPERATE
                    + "     ?3 ,"                       //NO_COOPERATE
                    + "     NVL(?4, ' ')      ,"                  //CD_PATH
                    + "     CASE WHEN ?2 IN ('70306', '70307', '70308') THEN '71013' ELSE '71001' END    ,"               //CD_STATUS
                    + "     NVL(?5, 'N')    ,"                    //YN_SURETY
                    + "     NVL(?6, ' ')       ,"                 //CD_PDT
                    + "     NVL(?7, ' ')           ,"             //DD
                    + "     NVL(?8, 0)    ,"                    //AM_CUSTOM
                    + "     0 ,"                        //AM_COOPERATE
                    + "     0       ,"                  //AM_APP
                    + "     NVL(?9, ' ')       ,"                 //CD_USE
                    + "     ' '    ,"                   //CD_REJECT
                    + "     NVL(?10, ' ')   ,"                    //CD_ECHANEL
                    + "     NVL(?11, ' ')   ,"                    //CD_EPORTAL
                    + "     NVL(?12, ' ')  ,"                     //NM_EKEYWORD
                    + "     NVL(?13, ' ')     ,"                  //CD_EPATH
                    + "     ' '   ,"                    //ID_RECEIVE
                    + "     ' '    ,"                   //ID_SCREEN
                    + "     ' '      ,"                 //ID_AUTH
                    + "     NVL(?14, ' ')   ,"                    //DS_RECEIVE
                    + "     ' '    ,"                   //DS_SCREEN
                    + "     SYSDATE   ,"        //DM_RECEIVE
                    + "     NULL    ,"                  //DM_SCREEN
                    + "     SYSDATE   ,"        //DM_CONTACT
                    + "     NULL      ,"                //DM_AUTH
                    + "     ' '    ,"                   //CD_CANCEL             
                    + "     NULL    ,"                   //DM_REJECT
                    + "     ' '   ,"                    //DS_EXT_KEY
                    + "     SYSDATE       ,"            //DM_REG
                    + "     'SYSTEM'       ,"           //ID_REG
                    + "     'N'     ,"                  //YN_REAPP
                    + "     NVL(?15, ' ')   ,"                  //DS_ACCESS_IP
                    + "     NVL(?16, ' ')   ,"                  //CD_RECRUIT_GROUP                     
                    + "     ' ' "                       //DS_COOPERATE
                    + ") "
                    + "";
            
            q= em.createNativeQuery(sql);
            q.setParameter(1, noReq);
            q.setParameter(2, param.get("cd_cooperate"));
            q.setParameter(3, param.get("no_cooperate"));
            q.setParameter(4, param.get("cd_path"));
            q.setParameter(5, param.get("yn_surety"));
            q.setParameter(6, param.get("cd_pdt"));
            q.setParameter(7, param.get("dd"));
            q.setParameter(8, param.get("am_custom"));
            q.setParameter(9, param.get("cd_use"));
            q.setParameter(10, param.get("cd_echanel"));
            q.setParameter(11, param.get("cd_eportal"));
            q.setParameter(12, param.get("nm_ekeyword"));
            q.setParameter(13, param.get("cd_epath"));
            q.setParameter(14, param.get("ds_receive"));
            q.setParameter(15, param.get("ds_access_ip"));
            q.setParameter(16, param.get("cd_recruit_group"));
   
            insertCount = q.executeUpdate();
            if (insertCount > 0) {
                System.out.println(insertCount + ": insertCount record!");
            }else{
                System.err.println("<Notice> no insertCount record!");
            }            
            /*==========================================================================*/
            /*BRC_CUSTOM INSERT */ 
            /*==========================================================================*/
            sql = ""
                    + "INSERT INTO BRC_CUSTOM "
                    + "(                                                                                      "
                    + "     NO             ,"
                    + "     NO_REQ         ,"
                    + "     NM_CUSTOM      ,"
                    + "     NO_CUSTOM      ,"
                    + "     CD_BANK        ,"
                    + "     NO_BANK_ACT    ,"
                    + "     DS_EMAIL       ,"
                    + "     CD_MARRY       ,"
                    + "     CD_KND_MOBILE  ,"
                    + "     NO_TEL1_MOBILE ,"
                    + "     NO_TEL2_MOBILE ,"
                    + "     NO_TEL3_MOBILE ,"
                    + "     YN_AUTH_MOBILE ,"
                    + "     CD_KND_ETC     ,"
                    + "     NO_TEL1_ETC1   ,"
                    + "     NO_TEL2_ETC1   ,"
                    + "     NO_TEL3_ETC1   ,"
                    + "     YN_AUTH_ETC    ,"
                    + "     NO_FAX1        ,"
                    + "     NO_FAX2        ,"
                    + "     NO_FAX3        ,"
                    + "     DT_CB_AGREE    ,"
                    + "     DM_PROMISE     ,"
                    + "     DS_NICE_SAFEKEY,"
                    + "     YN_NICE_SAFEKEY,"
                    + "     YN_CDT_INQUIRY,"
                    + "     CD_NICE_SAFEKEY_EXT," 
                    + "     DS_NICE_SAFEKEY_EXT," 
                    + "     DM_REG         ,"
                    + "     ID_REG         "
                    + ") "
                    + "VALUES  "
                    + "(                                                                                      "
                    + "     BRC_CUSTOM_SEQ.NEXTVAL             ,"
                    + "     ?1          ,"                                      //NO_REQ
                    + "     NVL(?2, ' ')       ,"                               //NM_CUSTOM
                    + "     NVL(?3, ' ')       ,"                               //NO_CUSTOM
                    + "     NVL(?4, ' ')         ,"                             //CD_BANK
                    + "     NVL(?5, ' ')     ,"                                 //NO_BANK_ACT
                    + "     NVL(?6, ' ')        ,"                              //DS_EMAIL
                    + "     NVL(?7, ' ')        ,"                              //CD_MARRY
                    + "     NVL(?8, ' ')   ,"                                   //CD_KND_MOBILE
                    + "     NVL(?9, ' ')  ,"                                    //NO_TEL1_MOBILE
                    + "     NVL(?10, ' ')  ,"                                   //NO_TEL2_MOBILE
                    + "     NVL(?11, ' ')  ,"                                   //NO_TEL3_MOBILE
                    + "     'N'  ,"                                             //YN_AUTH_MOBILE
                    + "     ' '      ,"                                         //CD_KND_ETC
                    + "     ' '    ,"                                           //NO_TEL1_ETC1
                    + "     ' '    ,"                                           //NO_TEL2_ETC1
                    + "     ' '    ,"                                           //NO_TEL3_ETC1
                    + "     'N'     ,"                                          //YN_AUTH_ETC
                    + "     NVL(?12, ' ')         ,"                            //NO_FAX1
                    + "     NVL(?13, ' ')         ,"                            //NO_FAX2
                    + "     NVL(?14, ' ')         ,"                            //NO_FAX3
                    + "     ' '     ,"                                          //DT_CB_AGREE
                    + "     NULL      ,"                                        //DM_PROMISE
                    + "     NVL(?15, ' ') ,"                                    //DS_NICE_SAFEKEY
                    + "     NVL(?16, 'N') ,"                                    //YN_NICE_SAFEKEY
                    + "     CASE WHEN ?15 > ' ' THEN 'Y' ELSE 'N' END,"                                    //YN_CDT_INQUIRY         
                    + "     NVL(?17, ' ')         ,"                            //CD_NICE_SAFEKEY_EXT
                    + "     NVL(?18, ' ')         ,"                            //DS_NICE_SAFEKEY_EXT
                    + "     SYSDATE          ,"                                 //DM_REG
                    + "     'SYSTEM'         "
                    + ") "
                    + "";
            
            q= em.createNativeQuery(sql);
            q.setParameter(1, noReq);
            q.setParameter(2, param.get("nm_custom"));
            q.setParameter(3, param.get("no_custom"));
            q.setParameter(4, param.get("cd_bank"));
            q.setParameter(5, param.get("no_bank_act"));
            q.setParameter(6, param.get("ds_email"));
            q.setParameter(7, param.get("cd_marry"));
            q.setParameter(8, param.get("cd_knd_mobile"));
            q.setParameter(9, param.get("no_tel1_mobile"));
            q.setParameter(10, param.get("no_tel2_mobile"));
            q.setParameter(11, param.get("no_tel3_mobile"));
            q.setParameter(12, param.get("no_fax1"));
            q.setParameter(13, param.get("no_fax2"));
            q.setParameter(14, param.get("no_fax3"));
            q.setParameter(15, param.get("ds_nice_safekey"));
            q.setParameter(16, param.get("yn_nice_safekey"));
            q.setParameter(17, param.get("cd_nice_safekey_ext"));
            q.setParameter(18, param.get("ds_nice_safekey_ext"));
   

            insertCount = q.executeUpdate();
            if (insertCount > 0) {
                System.out.println(insertCount + ": insertCount record!");
            }else{
                System.err.println("<Notice> no insertCount record!");
            }
            
            /*==========================================================================*/
            /*BRC_CUSTOM_HASH INSERT */ 
            /*==========================================================================*/
            sql = ""
                    + "INSERT INTO BRC_CUSTOM_HASH "
                    + "(                                                                                      "
                    + "     NO             ,"
                    + "     NO_REQ         ,"
                    + "     NO_CUSTOM_HASH      ,"
                    + "     NO_TEL_MOBILE_HASH      ,"
                    + "     DM_REG         ,"
                    + "     ID_REG         "
                    + ") "
                    + "VALUES  "
                    + "(                                                                                      "
                    + "     BRC_CUSTOM_HASH_SEQ.NEXTVAL             ,"
                    + "     ?1          ,"                                      //NO_REQ
                    + "     NVL(SHA512_HASH(?3), ' ')       ,"                               //NO_CUSTOM
                    + "     NVL(SHA512_HASH(?9), ' ')  ,"                                    //NO_TEL1_MOBILE
                    + "     SYSDATE          ,"                                 //DM_REG
                    + "     'SYSTEM'         "
                    + ") "
                    + "";
            
            q= em.createNativeQuery(sql);
            q.setParameter(1, noReq);
            q.setParameter(3, param.get("no_custom"));
            q.setParameter(9, param.get("no_tel1_mobile")+param.get("no_tel2_mobile")+param.get("no_tel3_mobile"));

            insertCount = q.executeUpdate();
            if (insertCount > 0) {
                System.out.println(insertCount + ": insertCount record!");
            }else{
                System.err.println("<Notice> no insertCount record!");
            }
            
            /*==========================================================================*/
            /*BRC_CUSTOM_COMPANY INSERT */ 
            /*==========================================================================*/
            sql = ""
                    + "INSERT INTO BRC_CUSTOM_COMPANY "
                    + "(                                                                                      "
                    + "     NO              ,"
                    + "     NO_REQ          ,"
                    + "     CD_JOB_KIND     ,"
                    + "     CD_RANK         ,"
                    + "     CD_JOB_ACT      ,"
                    + "     CD_JOB_STATUS   ,"
                    + "     NM_COMP         ,"
                    + "     NM_DEPT         ,"
                    + "     DT_REG_JOIN     ,"
                    + "     DD_PAY          ,"
                    + "     AM_PAY_MONTH    ,"
                    + "     NO_POST_COMP    ,"
                    + "     DS_STRT_COMP    ,"
                    + "     DS_ADDR_COMP    ,"
                    + "     NO_TEL1_COMP    ,"
                    + "     NO_TEL2_COMP    ,"
                    + "     NO_TEL3_COMP    ,"
                    + "     NO_EXT_COMP     ,"
                    + "     NO_MAINTEL1_COMP,"
                    + "     NO_MAINTEL2_COMP,"
                    + "     NO_MAINTEL3_COMP,"
                    + "     DM_REG          ,"
                    + "     ID_REG          "
                    + ") "
                    + "VALUES  "
                    + "(                                                                                      "
                    + "     BRC_CUSTOM_COMPANY_SEQ.NEXTVAL              ,"
                    + "     ?1           ,"                                             //NO_REQ
                    + "     NVL(?2, ' ')      ,"                                        //CD_JOB_KIND
                    + "     NVL(?3, ' ')          ,"                                    //CD_RANK
                    + "     NVL(?4, ' ')       ,"                                       //CD_JOB_ACT
                    + "     NVL(?5, ' ')    ,"                                          //CD_JOB_STATUS
                    + "     NVL(?6, ' ')          ,"                                    //NM_COMP
                    + "     NVL(?7, ' ')          ,"                                    //NM_DEPT
                    + "     NVL(?8, ' ')      ,"                                        //DT_REG_JOIN
                    + "     NVL(?9, ' ')           ,"                                   //DD_PAY
                    + "     NVL(?10, 0)     ,"                                          //AM_PAY_MONTH
                    + "     NVL(?11, ' ')     ,"                                        //NO_POST_COMP
                    + "     NVL(?12, ' ')     ,"                                        //DS_STRT_COMP
                    + "     NVL(?13, ' ')     ,"                                        //DS_ADDR_COMP
                    + "     ' '     ,"                                                  //NO_TEL1_COMP
                    + "     ' '     ,"                                                  //NO_TEL2_COMP
                    + "     ' '     ,"                                                  //NO_TEL3_COMP
                    + "     ' '      ,"                                                 //NO_EXT_COMP
                    + "     NVL(?14, ' ') ,"                                            //NO_MAINTEL1_COMP
                    + "     NVL(?15, ' ') ,"                                            //NO_MAINTEL2_COMP
                    + "     NVL(?16, ' ') ,"                                            //NO_MAINTEL3_COMP
                    + "     SYSDATE           ,"                                        //DM_REG
                    + "     'SYSTEM'           "                                        //ID_REG
                    + ") "
                    + "";
            
            q= em.createNativeQuery(sql);
            q.setParameter(1, noReq);
            q.setParameter(2, param.get("cd_job_kind"));
            q.setParameter(3, param.get("cd_rank"));
            q.setParameter(4, param.get("cd_job_act"));
            q.setParameter(5, param.get("cd_job_status"));
            q.setParameter(6, param.get("nm_comp"));
            q.setParameter(7, param.get("nm_dept"));
            q.setParameter(8, param.get("dt_reg_join"));
            q.setParameter(9, param.get("dd_pay"));
            q.setParameter(10, param.get("am_pay_month"));
            q.setParameter(11, param.get("no_post_comp"));
            q.setParameter(12, param.get("ds_strt_comp"));
            q.setParameter(13, param.get("ds_addr_comp"));
            q.setParameter(14, param.get("no_maintel1_comp"));
            q.setParameter(15, param.get("no_maintel2_comp"));
            q.setParameter(16, param.get("no_maintel3_comp")); 
   
            insertCount = q.executeUpdate();
            if (insertCount > 0) {
                System.out.println(insertCount + ": insertCount record!");
            }else{
                System.err.println("<Notice> no insertCount record!");
            }
            /*==========================================================================*/
            /*BRC_CUSTOM_HOME INSERT */ 
            /*==========================================================================*/
            sql = ""
                    + "INSERT INTO BRC_CUSTOM_HOME "
                    + "(                                                                                      "
                    + "     NO             ,"
                    + "     NO_REQ         ,"
                    + "     DT_REG_HOME    ,"
                    + "     AM_DEPOSIT_HOME,"
                    + "     AM_RENT_HOME   ,"
                    + "     NO_POST_HOME   ,"
                    + "     DS_STRT_HOME   ,"
                    + "     DS_ADDR_HOME   ,"
                    + "     NO_TEL1_HOME   ,"
                    + "     NO_TEL2_HOME   ,"
                    + "     NO_TEL3_HOME   ,"
                    + "     NO_POST_CERT   ,"
                    + "     DS_STRT_CERT   ,"
                    + "     DS_ADDR_CERT   ,"
                    + "     NO_POST_REAL   ,"
                    + "     DS_STRT_REAL   ,"
                    + "     DS_ADDR_REAL   ,"
                    + "     DM_REG         ,"
                    + "     ID_REG         "
                    + ") "
                    + "VALUES  "
                    + "(                                                                                      "
                    + "     BRC_CUSTOM_HOME_SEQ.NEXTVAL             ,"
                    + "     ?1          ,"                                      //NO_REQ
                    + "     NVL(?2, ' ')     ,"                                           //DT_REG_HOME
                    + "     NVL(?3, 0) ,"                                               //AM_DEPOSIT_HOME
                    + "     NVL(?4, 0)    ,"                                            //AM_RENT_HOME
                    + "     NVL(?5, ' ')    ,"                                            //NO_POST_HOME
                    + "     NVL(?6, ' ')    ,"                                            //DS_STRT_HOME
                    + "     NVL(?7, ' ')    ,"                                            //DS_ADDR_HOME
                    + "     NVL(?8, ' ')    ,"                                            //NO_TEL1_HOME
                    + "     NVL(?9, ' ')    ,"                                            //NO_TEL2_HOME
                    + "     NVL(?10, ' ')    ,"                                           //NO_TEL3_HOME
                    + "     NVL(?11, ' ')    ,"                                           //NO_POST_CERT
                    + "     NVL(?12, ' ')    ,"                                           //DS_STRT_CERT
                    + "     NVL(?13, ' ')    ,"                                           //DS_ADDR_CERT
                    + "     NVL(?14, ' ')    ,"                                           //NO_POST_REAL
                    + "     NVL(?15, ' ')    ,"                                           //DS_STRT_REAL
                    + "     NVL(?16, ' ')    ,"                                           //DS_ADDR_REAL
                    + "     SYSDATE          ,"                                 //DM_REG
                    + "     'SYSTEM'          "                                 //ID_REG
                    + ") "
                    + "";
            
            q= em.createNativeQuery(sql);
            q.setParameter(1, noReq);
            q.setParameter(2, param.get("dt_reg_home"));
            q.setParameter(3, param.get("am_deposit_home"));
            q.setParameter(4, param.get("am_rent_home"));
            q.setParameter(5, param.get("no_post_home"));
            q.setParameter(6, param.get("ds_strt_home"));
            q.setParameter(7, param.get("ds_addr_home"));
            q.setParameter(8, param.get("no_tel1_home"));
            q.setParameter(9, param.get("no_tel2_home"));
            q.setParameter(10, param.get("no_tel3_home"));
            q.setParameter(11, param.get("no_post_cert"));
            q.setParameter(12, param.get("ds_strt_cert"));
            q.setParameter(13, param.get("ds_addr_cert"));
            q.setParameter(14, param.get("no_post_real"));
            q.setParameter(15, param.get("ds_strt_real"));
            q.setParameter(16, param.get("ds_addr_real")); 
   
            insertCount = q.executeUpdate();
            if (insertCount > 0) {
                System.out.println(insertCount + ": insertCount record!");
            }else{
                System.err.println("<Notice> no insertCount record!");
            }
            

            if(param.get("cd_cooperate").equals("70301")||param.get("cd_cooperate").equals("70302")||param.get("cd_cooperate").equals("70303")||param.get("cd_cooperate").equals("70316")){
                /*==========================================================================*/
                /*YSM계열사 자체모집의 경우 중개경로/적법수집 skip
                 * 20170424 오케이포스 70316 추가
                 */
                /*==========================================================================*/  
            }else{
                /*==========================================================================*/
                /*BRC_SUPPLY INSERT */ 
                /*==========================================================================*/      
                ArrayList arrNoSupply;
                ArrayList arrDtSupply;
                ArrayList arrNmSupply;
                ArrayList arrNoSupplyLoan;
                ArrayList arrNoSupplySociety;
                ArrayList arrNoSupplyTel;

                
                arrNoSupply = new ArrayList(Arrays.asList(param.get("no_supply").split(strSplit)));
                arrDtSupply = new ArrayList(Arrays.asList(param.get("dt_supply").split(strSplit)));
                arrNmSupply = new ArrayList(Arrays.asList(param.get("nm_supply").split(strSplit)));
                arrNoSupplyLoan = new ArrayList(Arrays.asList(param.get("no_supply_loan").split(strSplit)));
                arrNoSupplySociety = new ArrayList(Arrays.asList(param.get("no_supply_society").split(strSplit)));
                arrNoSupplyTel = new ArrayList(Arrays.asList(param.get("no_supply_tel").split(strSplit)));     

                long arrCnt = arrNoSupply.size();
                if(arrCnt == arrDtSupply.size() 
                        && arrCnt == arrNmSupply.size() 
                        && arrCnt == arrNoSupplyLoan.size() 
                        && arrCnt == arrNoSupplySociety.size() 
                        && arrCnt == arrNoSupplyTel.size()){
                    System.out.println("BrcSupply same array length:"+arrCnt);
                }else{
                    System.err.println("<Notice> BrcSupply different array length:"+arrCnt);
                    System.err.println("arrNoSupply.size():"+arrNoSupply.size()+":"+param.get("no_supply"));
                    System.err.println("arrDtSupply.size():"+arrDtSupply.size()+":"+param.get("dt_supply"));
                    System.err.println("arrNmSupply.size():"+arrNmSupply.size()+":"+param.get("nm_supply"));
                    System.err.println("arrNoSupplyLoan.size():"+arrNoSupplyLoan.size()+":"+param.get("no_supply_loan"));
                    System.err.println("arrNoSupplySociety.size():"+arrNoSupplySociety.size()+":"+param.get("no_supply_society"));
                    System.err.println("arrNoSupplyTel.size():"+arrNoSupplyTel.size()+":"+param.get("no_supply_tel"));
                }

                if(arrDtSupply.size() < arrCnt){
                    arrCnt = arrDtSupply.size();
                }
                if(arrNmSupply.size() < arrCnt){
                    arrCnt = arrNmSupply.size();
                }
                if(arrNoSupplyLoan.size() < arrCnt){
                    arrCnt = arrNoSupplyLoan.size();
                }
                if(arrNoSupplySociety.size() < arrCnt){
                    arrCnt = arrNoSupplySociety.size();
                }
                if(arrNoSupplyTel.size() < arrCnt){
                    arrCnt = arrNoSupplyTel.size();
                }
                
                for (int i = 0; i < arrCnt; i++){
                    sql = ""
                            + "INSERT INTO BRC_SUPPLY "
                            + "(                                                                                      "
                            + "     NO               ,"
                            + "     NO_REQ           ,"
                            + "     NO_SUPPLY        ,"
                            + "     DT_SUPPLY        ,"
                            + "     NM_SUPPLY        ,"
                            + "     NO_SUPPLY_LOAN   ,"
                            + "     NO_SUPPLY_SOCIETY,"
                            + "     NO_SUPPLY_TEL    ,"
                            + "     DM_REG           ,"
                            + "     ID_REG           "
                            + ") "
                            + "VALUES  "
                            + "(                                                                                      "
                            + "     BRC_SUPPLY_SEQ.NEXTVAL               ,"
                            + "     ?1            ,"                                        //NO_REQ
                            + "     NVL(?2, ' ')         ,"                                           //NO_SUPPLY
                            + "     NVL(?3, ' ')         ,"                                           //DT_SUPPLY
                            + "     NVL(?4, ' ')         ,"                                           //NM_SUPPLY
                            + "     NVL(?5, ' ')    ,"                                                //NO_SUPPLY_LOAN
                            + "     NVL(?6, ' ') ,"                                                   //NO_SUPPLY_SOCIETY
                            + "     NVL(?7, ' ')     ,"                                               //NO_SUPPLY_TEL
                            + "     SYSDATE          ,"                                 //DM_REG
                            + "     'SYSTEM'          "                                 //ID_REG
                            + ") "
                            + "";
                    
                    q= em.createNativeQuery(sql);
                    q.setParameter(1, noReq);
                    q.setParameter(2, arrNoSupply.get(i));
                    q.setParameter(3, arrDtSupply.get(i));
                    q.setParameter(4, arrNmSupply.get(i));
                    q.setParameter(5, arrNoSupplyLoan.get(i));
                    q.setParameter(6, arrNoSupplySociety.get(i));
                    q.setParameter(7, arrNoSupplyTel.get(i));
           
                    insertCount = q.executeUpdate();
                    if (insertCount > 0) {
                        System.out.println(insertCount + ": insertCount record!");
                    }else{
                        System.err.println("<Notice> no insertCount record!");
                    }
                }

                /*==========================================================================*/
                /*BRC_INFO_LEGAL INSERT */ 
                /*==========================================================================*/
                sql = ""
                        + "INSERT INTO BRC_INFO_LEGAL "
                        + "(                                                                                      "
                        + "     NO              ,"
                        + "     NO_REQ          ,"
                        + "     CD_INFO_COLLECT ,"
                        + "     CD_CONTACT_CUST ,"
                        + "     DS_CONTACT_CUST ,"
                        + "     CD_CONTACT_AGENT,"
                        + "     NM_CONTACT_AGENT,"
                        + "     DS_CONTACT_AGENT,"
                        + "     CD_CALL_AGENT   ,"
                        + "     DT_AGENT        ,"
                        + "     NM_AGENT        ,"                    
                        + "     NM_AGENT_CEO    ,"
                        + "     NM_AGENT_MANAGER,"
                        + "     DS_AGENT_URL,"                        
                        + "     DM_REG          ,"
                        + "     ID_REG          "
                        + ") "
                        + "VALUES  "
                        + "(                                                                                      "
                        + "     BRC_INFO_LEGAL_SEQ.NEXTVAL              ,"
                        + "     ?1           ,"                                     //NO_REQ
                        + "     NVL(?2, ' ')  ,"                                              //CD_INFO_COLLECT
                        + "     NVL(?3, ' ')  ,"                                              //CD_CONTACT_CUST
                        + "     NVL(?4, ' ')  ,"                                              //DS_CONTACT_CUST
                        + "     NVL(?5, ' ') ,"                                               //CD_CONTACT_AGENT
                        + "     NVL(?6, ' ') ,"                                               //NM_CONTACT_AGENT
                        + "     NVL(?7, ' ') ,"                                               //DS_CONTACT_AGENT
                        + "     NVL(?8, ' ')    ,"                                            //CD_CALL_AGENT
                        + "     NVL(?9, ' ')         ,"                                       //DT_AGENT
                        + "     NVL(?12, ' ')         ,"                                       //NM_AGENT                    
                        + "     NVL(?10, ' ')     ,"                                          //NM_AGENT_CEO
                        + "     NVL(?11, ' ') ,"                                              //NM_AGENT_MANAGER
                        + "     NVL(?13, ' ') ,"                                              //DS_AGENT_URL                        
                        + "     SYSDATE          ,"                                 //DM_REG
                        + "     'SYSTEM'          "                                 //ID_REG
                        + ") "
                        + "";
                
                q= em.createNativeQuery(sql);
                q.setParameter(1, noReq);
                q.setParameter(2, param.get("cd_info_collect"));
                q.setParameter(3, param.get("cd_contact_cust"));
                q.setParameter(4, param.get("ds_contact_cust"));
                q.setParameter(5, param.get("cd_contact_agent"));
                q.setParameter(6, param.get("nm_contact_agent"));
                q.setParameter(7, param.get("ds_contact_agent"));
                q.setParameter(8, param.get("cd_call_agent"));
                q.setParameter(9, param.get("dt_agent"));
                q.setParameter(10, param.get("nm_agent_ceo"));
                q.setParameter(11, param.get("nm_agent_manager"));
                q.setParameter(12, param.get("nm_agent"));
                q.setParameter(13, param.get("ds_agent_url"));
       
                insertCount = q.executeUpdate();
                if (insertCount > 0) {
                    System.out.println(insertCount + ": insertCount record!");
                }else{
                    System.err.println("<Notice> no insertCount record!");
                }
            }
            
            

            /*==========================================================================*/
            /*BRC_CONTACT INSERT */ 
            /*==========================================================================*/
            if(param.get("ds_memo")!=null){
                ArrayList arrContact = new ArrayList(Arrays.asList(param.get("ds_memo").split(strSplit)));
                
                for (int i = 0; i < arrContact.size(); i++){
                    sql = ""
                            + "INSERT INTO BRC_CONTACT "
                            + "(                                                                                      "
                            + "     NO              ,"
                            + "     NO_REQ          ,"
                            + "     NO_CONTACT_SEQ  ,"
                            + "     NO_CONTACT_MODEL,"
                            + "     CD_CONTACT      ,"
                            + "     CD_CALL_KIND    ,"
                            + "     CD_CALLER       ,"
                            + "     CD_CALL_LOCAL   ,"
                            + "     YN_IMPORTANT    ,"
                            + "     DM_PROMISE      ,"
                            + "     YN_RECORD       ,"
                            + "     NO_RECORD       ,"
                            + "     DS_MEMO         ,"
                            + "     DM_REG          ,"
                            + "     ID_REG          "
                            + ") "
                            + "VALUES  "
                            + "(                                                                                      "
                            + "     BRC_CONTACT_SEQ.NEXTVAL              ,"
                            + "     ?1           ,"                                                                                 //NO_REQ
                            + "     (SELECT  LPAD(NVL(MAX(NO_CONTACT_SEQ)+1,1),5,'0') "
                            + "     FROM BRC_CONTACT WHERE NO_REQ = ?1 )   ,"                               //NO_CONTACT_SEQ
                            + "     ' ' ,"                                                                                          //NO_CONTACT_MODEL
                            + "     '71201'       ,"                                                                                //CD_CONTACT
                            + "     ' '     ,"                                                                                      //CD_CALL_KIND
                            + "     ' '        ,"                                                                                   //CD_CALLER
                            + "     ' '    ,"                                                                                       //CD_CALL_LOCAL
                            + "     'N'     ,"                                                                                      //YN_IMPORTANT
                            + "     NULL       ,"                                                                                   //DM_PROMISE
                            + "     'N'        ,"                                                                                   //YN_RECORD
                            + "     ' '        ,"                                                                                   //NO_RECORD
                            + "     NVL(?2, ' ')          ,"                                                                    //DS_MEMO
                            + "     SYSDATE          ,"                                 //DM_REG
                            + "     'SYSTEM'          "                                 //ID_REG
                            + ") "
                            + "";
                    
                    q= em.createNativeQuery(sql);
                    q.setParameter(1, noReq);
                    q.setParameter(2, arrContact.get(i));
           
                    insertCount = q.executeUpdate();
                    if (insertCount > 0) {
                        System.out.println(insertCount + ": insertCount record!");
                    }else{
                        System.err.println("<Notice> no insertCount record!");
                    }
                }
            }
            
            /*==========================================================================*/
            /*BRC_FILE INSERT */ 
            /*==========================================================================*/
            if(param.get("ds_url") != null){
                ArrayList arrFile = new ArrayList(Arrays.asList(param.get("ds_url").split(strSplit)));
                String nmFile = null;
                String nmFileUser = null;
                String extension = null;
                String dsPath = null;
                String nasPath = null;
                String nmCompShort = null;
                BigDecimal noSeq = null;
                
                sql = ""
                        + "SELECT NM_COMP_SHORT_ENG FROM ST_COMP ";                
                q = em.createNativeQuery(sql);
                String nmCompShortEng = (String) q.getSingleResult();
                System.out.println("nmCompShortEng:"+nmCompShortEng);
                String tmpFilePath = utilProperties.getNetProperty("bridge."+nmCompShortEng+".tmp.filepath");                
                // SSH CONN
/*                Session session = utilSSH.sshConnect(utilProperties.getBridgeSshIP(), utilProperties.getBridgeSshPort(), utilProperties.getBridgeSshUser(), utilProperties.getBridgeSshPassword());
                System.out.println("utilProperties.getBridgeSshIP():"+utilProperties.getBridgeSshIP());
                System.out.println("utilProperties.getBridgeSshPort():"+utilProperties.getBridgeSshPort());
                System.out.println("utilProperties.getBridgeSshUser():"+utilProperties.getBridgeSshUser());
                System.out.println("utilProperties.getBridgeSshPassword():"+utilProperties.getBridgeSshPassword());*/
                
                for (int i = 0; i < arrFile.size(); i++){
                    
                    URL url = new URL((String) arrFile.get(i));
                    System.out.println("arrFile.get(i):"+arrFile.get(i));
                    HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                    int responseCode = httpConn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        nmFileUser = ModuleCommon.getFileNamOfURL((String) arrFile.get(i));
                        extension = ModuleCommon.getFileExtension(nmFileUser);
                        
                        sql = ""
                                + "SELECT DS_NAS_IMG_PATH FROM ST_SETUP WHERE ROWNUM <= 1 ";
                        q = em.createNativeQuery(sql);
                        nasPath=(String) q.getSingleResult();
                        
                        sql = ""
                                + "SELECT NM_COMP_SHORT_ENG FROM ST_COMP WHERE ROWNUM <= 1 ";
                        q = em.createNativeQuery(sql);
                        nmCompShort=(String) q.getSingleResult();
                        
                        sql = ""
                                + "SELECT BRC_FILE_SEQ.NEXTVAL FROM DUAL ";
                        q = em.createNativeQuery(sql);
                        noSeq=(BigDecimal) q.getSingleResult();
                        
                        nmFile = ModuleCommon.getImgFileName(nmCompShort,noReq, noSeq) + "." + extension;
                        dsPath = ModuleCommon.getImgSavePath(nasPath);
                        
                        sql = ""
                                + "INSERT INTO BRC_FILE "
                                + "(                                                                                      "
                                + "     NO          ,"
                                + "     NO_REQ      ,"
                                + "     NO_IMG      ,"
                                + "     CD_FILE     ,"
                                + "     NM_FILE     ,"
                                + "     NM_FILE_USER,"
                                + "     DS_PATH     ,"
                                + "     DS_URL      ,"
                                + "     DS_REMK     ,"
                                + "     DM_REG      ,"
                                + "     ID_REG      "
                                + ") "
                                + "VALUES  "
                                + "(                                                                                      "
                                + "     ?1           ,"                                                                 //BRC_FILE_SEQ.NEXTVAL
                                + "     ?2       ,"                                                                     //NO_REQ
                                + "     (SELECT LPAD(NVL(MAX(NO_IMG), '0')+1, 3, '0') FROM BRC_FILE"
                                + "     WHERE NO_REQ = ?2), "                                               //NO_IMG
                                + "     '20301'      ,"                                                                 //CD_FILE
                                + "     ?3      ,"                                                                          //NM_FILE
                                + "     ?4 ,"                                                                               //NM_FILE_USER
                                + "     ?5      ,"                                                                          //DS_PATH
                                + "     ?6       ,"                                                                         //DS_URL
                                + "     ' '     ,"                                                                          //DS_REMK
                                + "     SYSDATE          ,"                                                         //DM_REG
                                + "     'SYSTEM'          "                                                         //ID_REG
                                + ") "
                                + "";
                        
                        q= em.createNativeQuery(sql);
                        q.setParameter(1, noSeq);
                        q.setParameter(2, noReq);
                        q.setParameter(3, nmFile);
                        q.setParameter(4, nmFileUser);
                        q.setParameter(5, dsPath);
                        q.setParameter(6, arrFile.get(i));
               
                        insertCount = q.executeUpdate();
                        if (insertCount > 0) {
                            System.out.println(insertCount + ": insertCount record!");
                        }else{
                            System.err.println("<Notice> no insertCount record!");
                        }
                        // HTTP FILE => TMP DOWNLOAD
                        InputStream inputStream = httpConn.getInputStream();
                        FileOutputStream outputStream = new FileOutputStream(tmpFilePath+"/"+nmFile);
                        int bytesRead = -1;
                        byte[] buffer = new byte[4096];
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                        outputStream.close();
                        inputStream.close();

/*                        // SSH UPLOAD 
                        utilSSH.sshFileUpload(session, dsPath, new File(tmpFilePath+"\\"+nmFile));*/
                        utilCommon.copyFile(tmpFilePath+"/"+nmFile, dsPath+nmFile);
                    }
                    httpConn.disconnect();                
                }
                //session.disconnect();
            }

            if(param.get("yn_surety").equals("Y")){
                /*==========================================================================*/
                /*BRC_SURETY INSERT */ 
                /*==========================================================================*/
                sql = ""
                        + "INSERT INTO BRC_SURETY "
                        + "(                                                                                      "
                        + "     NO             ,"
                        + "     NO_REQ         ,"
                        + "     NO_SURETY         ,"
                        + "     NM_CUSTOM      ,"
                        + "     NO_CUSTOM      ,"
                        + "     DS_EMAIL       ,"
                        + "     CD_MARRY       ,"
                        + "     CD_KND_MOBILE  ,"
                        + "     NO_TEL1_MOBILE ,"
                        + "     NO_TEL2_MOBILE ,"
                        + "     NO_TEL3_MOBILE ,"
                        + "     YN_AUTH_MOBILE ,"
                        + "     CD_KND_ETC     ,"
                        + "     NO_TEL1_ETC1   ,"
                        + "     NO_TEL2_ETC1   ,"
                        + "     NO_TEL3_ETC1   ,"
                        + "     YN_AUTH_ETC    ,"
                        + "     NO_FAX1        ,"
                        + "     NO_FAX2        ,"
                        + "     NO_FAX3        ,"
                        + "     DT_CB_AGREE    ,"
                        + "     DM_PROMISE     ,"
                        + "     DS_NICE_SAFEKEY,"
                        + "     YN_NICE_SAFEKEY,"
                        + "     YN_CDT_INQUIRY,"
                        + "     CD_NICE_SAFEKEY_EXT,"       
                        + "     DS_NICE_SAFEKEY_EXT,"       
                        + "     DM_REG         ,"
                        + "     ID_REG         "
                        + ") "
                        + "VALUES  "
                        + "(                                                                                      "
                        + "     BRC_SURETY_SEQ.NEXTVAL             ,"
                        + "     ?1          ,"                                      //NO_REQ
                        + "     '001'          ,"                                      //NO_SURETY
                        + "     NVL(?2, ' ')       ,"                               //NM_CUSTOM
                        + "     NVL(?3, ' ')       ,"                               //NO_CUSTOM
                        + "     NVL(?6, ' ')        ,"                              //DS_EMAIL
                        + "     NVL(?7, ' ')        ,"                              //CD_MARRY
                        + "     NVL(?8, ' ')   ,"                                   //CD_KND_MOBILE
                        + "     NVL(?9, ' ')  ,"                                    //NO_TEL1_MOBILE
                        + "     NVL(?10, ' ')  ,"                                   //NO_TEL2_MOBILE
                        + "     NVL(?11, ' ')  ,"                                   //NO_TEL3_MOBILE
                        + "     'N'  ,"                                             //YN_AUTH_MOBILE
                        + "     ' '      ,"                                         //CD_KND_ETC
                        + "     ' '    ,"                                           //NO_TEL1_ETC1
                        + "     ' '    ,"                                           //NO_TEL2_ETC1
                        + "     ' '    ,"                                           //NO_TEL3_ETC1
                        + "     'N'     ,"                                          //YN_AUTH_ETC
                        + "     NVL(?12, ' ')         ,"                            //NO_FAX1
                        + "     NVL(?13, ' ')         ,"                            //NO_FAX2
                        + "     NVL(?14, ' ')         ,"                            //NO_FAX3
                        + "     ' '     ,"                                          //DT_CB_AGREE
                        + "     NULL      ,"                                        //DM_PROMISE
                        + "     NVL(?15, ' ') ,"                                    //DS_NICE_SAFEKEY
                        + "     NVL(?16, 'N') ,"                                    //YN_NICE_SAFEKEY
                        + "     CASE WHEN ?15 > ' ' THEN 'Y' ELSE 'N' END,"                                    //YN_CDT_INQUIRY
                        + "     NVL(?17, ' ')         ,"                            //CD_NICE_SAFEKEY_EXT
                        + "     NVL(?18, ' ')         ,"                            //DS_NICE_SAFEKEY_EXT                                
                        + "     SYSDATE          ,"                                 //DM_REG                
                        + "     'SYSTEM'         "                    
                        + ") "
                        + "";
                
                q= em.createNativeQuery(sql);
                q.setParameter(1, noReq);
                q.setParameter(2, param.get("s_nm_custom"));
                q.setParameter(3, param.get("s_no_custom"));
/*                q.setParameter(4, param.get("cd_bank"));
                q.setParameter(5, param.get("no_bank_act"));*/
                q.setParameter(6, param.get("s_ds_email"));
                q.setParameter(7, param.get("s_cd_marry"));
                q.setParameter(8, param.get("s_cd_knd_mobile"));
                q.setParameter(9, param.get("s_no_tel1_mobile"));
                q.setParameter(10, param.get("s_no_tel2_mobile"));
                q.setParameter(11, param.get("s_no_tel3_mobile"));
                q.setParameter(12, param.get("s_no_fax1"));
                q.setParameter(13, param.get("s_no_fax2"));
                q.setParameter(14, param.get("s_no_fax3"));
                q.setParameter(15, param.get("s_ds_nice_safekey"));
                q.setParameter(16, param.get("s_yn_nice_safekey"));
                q.setParameter(17, param.get("s_cd_nice_safekey_ext"));
                q.setParameter(18, param.get("s_ds_nice_safekey_ext")); 
       
                insertCount = q.executeUpdate();
                if (insertCount > 0) {
                    System.out.println(insertCount + ": insertCount record!");
                }else{
                    System.err.println("<Notice> no insertCount record!");
                }                
                
                /*==========================================================================*/
                /*BRC_SURETY_HASH INSERT */ 
                /*==========================================================================*/
                sql = ""
                        + "INSERT INTO BRC_SURETY_HASH "
                        + "(                                                                                      "
                        + "     NO             ,"
                        + "     NO_REQ         ,"
                        + "     NO_SURETY         ,"                        
                        + "     NO_CUSTOM_HASH      ,"
                        + "     NO_TEL_MOBILE_HASH      ,"
                        + "     DM_REG         ,"
                        + "     ID_REG         "
                        + ") "
                        + "VALUES  "
                        + "(                                                                                      "
                        + "     BRC_SURETY_HASH_SEQ.NEXTVAL             ,"
                        + "     ?1          ,"                                      //NO_REQ
                        + "     '001'          ,"                                      //NO_SURETY                        
                        + "     NVL(SHA512_HASH(?3), ' ')       ,"                               //NO_CUSTOM
                        + "     NVL(SHA512_HASH(?9), ' ')  ,"                                    //NO_TEL1_MOBILE
                        + "     SYSDATE          ,"                                 //DM_REG
                        + "     'SYSTEM'         "
                        + ") "
                        + "";
                
                q= em.createNativeQuery(sql);
                q.setParameter(1, noReq);
                q.setParameter(3, param.get("s_no_custom"));
                q.setParameter(9, param.get("s_no_tel1_mobile")+param.get("s_no_tel2_mobile")+param.get("s_no_tel3_mobile"));

                insertCount = q.executeUpdate();
                if (insertCount > 0) {
                    System.out.println(insertCount + ": insertCount record!");
                }else{
                    System.err.println("<Notice> no insertCount record!");
                }                   
                                
                /*==========================================================================*/
                /*BRC_SURETY_COMPANY INSERT */ 
                /*==========================================================================*/
                sql = ""
                        + "INSERT INTO BRC_SURETY_COMPANY "
                        + "(                                                                                      "
                        + "     NO              ,"
                        + "     NO_REQ          ,"
                        + "     NO_SURETY          ,"
                        + "     CD_JOB_KIND     ,"
                        + "     CD_RANK         ,"
                        + "     CD_JOB_ACT      ,"
                        + "     CD_JOB_STATUS   ,"
                        + "     NM_COMP         ,"
                        + "     NM_DEPT         ,"
                        + "     DT_REG_JOIN     ,"
                        + "     DD_PAY          ,"
                        + "     AM_PAY_MONTH    ,"
                        + "     NO_POST_COMP    ,"
                        + "     DS_STRT_COMP    ,"
                        + "     DS_ADDR_COMP    ,"
                        + "     NO_TEL1_COMP    ,"
                        + "     NO_TEL2_COMP    ,"
                        + "     NO_TEL3_COMP    ,"
                        + "     NO_EXT_COMP     ,"
                        + "     NO_MAINTEL1_COMP,"
                        + "     NO_MAINTEL2_COMP,"
                        + "     NO_MAINTEL3_COMP,"
                        + "     DM_REG          ,"
                        + "     ID_REG          "
                        + ") "
                        + "VALUES  "
                        + "(                                                                                      "
                        + "     BRC_SURETY_COMPANY_SEQ.NEXTVAL              ,"
                        + "     ?1           ,"                                                 //NO_REQ
                        + "     '001'          ,"                                               //NO_SURETY
                        + "     NVL(?2, ' ')      ,"                                        //CD_JOB_KIND
                        + "     NVL(?3, ' ')          ,"                                    //CD_RANK
                        + "     NVL(?4, ' ')       ,"                                       //CD_JOB_ACT
                        + "     NVL(?5, ' ')    ,"                                          //CD_JOB_STATUS
                        + "     NVL(?6, ' ')          ,"                                    //NM_COMP
                        + "     NVL(?7, ' ')          ,"                                    //NM_DEPT
                        + "     NVL(?8, ' ')      ,"                                        //DT_REG_JOIN
                        + "     NVL(?9, ' ')           ,"                                   //DD_PAY
                        + "     NVL(?10, 0)     ,"                                          //AM_PAY_MONTH
                        + "     NVL(?11, ' ')     ,"                                        //NO_POST_COMP
                        + "     NVL(?12, ' ')     ,"                                        //DS_STRT_COMP
                        + "     NVL(?13, ' ')     ,"                                        //DS_ADDR_COMP
                        + "     ' '     ,"                                                  //NO_TEL1_COMP
                        + "     ' '     ,"                                                  //NO_TEL2_COMP
                        + "     ' '     ,"                                                  //NO_TEL3_COMP
                        + "     ' '      ,"                                                 //NO_EXT_COMP
                        + "     NVL(?14, ' ') ,"                                            //NO_MAINTEL1_COMP
                        + "     NVL(?15, ' ') ,"                                            //NO_MAINTEL2_COMP
                        + "     NVL(?16, ' ') ,"                                            //NO_MAINTEL3_COMP
                        + "     SYSDATE           ,"                                        //DM_REG
                        + "     'SYSTEM'           "                                        //ID_REG
                        + ") "
                        + "";
                
                q= em.createNativeQuery(sql);
                q.setParameter(1, noReq);
                q.setParameter(2, param.get("s_cd_job_kind"));
                q.setParameter(3, param.get("s_cd_rank"));
                q.setParameter(4, param.get("s_cd_job_act"));
                q.setParameter(5, param.get("s_cd_job_status"));
                q.setParameter(6, param.get("s_nm_comp"));
                q.setParameter(7, param.get("s_nm_dept"));
                q.setParameter(8, param.get("s_dt_reg_join"));
                q.setParameter(9, param.get("s_dd_pay"));
                q.setParameter(10, param.get("s_am_pay_month"));
                q.setParameter(11, param.get("s_no_post_comp"));
                q.setParameter(12, param.get("s_ds_strt_comp"));
                q.setParameter(13, param.get("s_ds_addr_comp"));
                q.setParameter(14, param.get("s_no_maintel1_comp"));
                q.setParameter(15, param.get("s_no_maintel2_comp"));
                q.setParameter(16, param.get("s_no_maintel3_comp")); 
       
                insertCount = q.executeUpdate();
                if (insertCount > 0) {
                    System.out.println(insertCount + ": insertCount record!");
                }else{
                    System.err.println("<Notice> no insertCount record!");
                }                
                             
                /*==========================================================================*/
                /*BRC_SURETY_HOME INSERT */ 
                /*==========================================================================*/
                sql = ""
                        + "INSERT INTO BRC_SURETY_HOME "
                        + "(                                                                                      "
                        + "     NO             ,"
                        + "     NO_REQ         ,"
                        + "     NO_SURETY         ,"
                        + "     DT_REG_HOME    ,"
                        + "     AM_DEPOSIT_HOME,"
                        + "     AM_RENT_HOME   ,"
                        + "     NO_POST_HOME   ,"
                        + "     DS_STRT_HOME   ,"
                        + "     DS_ADDR_HOME   ,"
                        + "     NO_TEL1_HOME   ,"
                        + "     NO_TEL2_HOME   ,"
                        + "     NO_TEL3_HOME   ,"
                        + "     NO_POST_CERT   ,"
                        + "     DS_STRT_CERT   ,"
                        + "     DS_ADDR_CERT   ,"
                        + "     NO_POST_REAL   ,"
                        + "     DS_STRT_REAL   ,"
                        + "     DS_ADDR_REAL   ,"
                        + "     DM_REG         ,"
                        + "     ID_REG         "
                        + ") "
                        + "VALUES  "
                        + "(                                                                                      "
                        + "     BRC_SURETY_HOME_SEQ.NEXTVAL             ,"
                        + "     ?1          ,"                                      //NO_REQ
                        + "     '001'          ,"                                      //NO_SURETY
                        + "     NVL(?2, ' ')     ,"                                           //DT_REG_HOME
                        + "     NVL(?3, 0) ,"                                               //AM_DEPOSIT_HOME
                        + "     NVL(?4, 0)    ,"                                            //AM_RENT_HOME
                        + "     NVL(?5, ' ')    ,"                                            //NO_POST_HOME
                        + "     NVL(?6, ' ')    ,"                                            //DS_STRT_HOME
                        + "     NVL(?7, ' ')    ,"                                            //DS_ADDR_HOME
                        + "     NVL(?8, ' ')    ,"                                            //NO_TEL1_HOME
                        + "     NVL(?9, ' ')    ,"                                            //NO_TEL2_HOME
                        + "     NVL(?10, ' ')    ,"                                           //NO_TEL3_HOME
                        + "     NVL(?11, ' ')    ,"                                           //NO_POST_CERT
                        + "     NVL(?12, ' ')    ,"                                           //DS_STRT_CERT
                        + "     NVL(?13, ' ')    ,"                                           //DS_ADDR_CERT
                        + "     NVL(?14, ' ')    ,"                                           //NO_POST_REAL
                        + "     NVL(?15, ' ')    ,"                                           //DS_STRT_REAL
                        + "     NVL(?16, ' ')    ,"                                           //DS_ADDR_REAL
                        + "     SYSDATE          ,"                                 //DM_REG
                        + "     'SYSTEM'          "                                 //ID_REG
                        + ") "
                        + "";
                
                q= em.createNativeQuery(sql);
                q.setParameter(1, noReq);
                q.setParameter(2, param.get("s_dt_reg_home"));
                q.setParameter(3, param.get("s_am_deposit_home"));
                q.setParameter(4, param.get("s_am_rent_home"));
                q.setParameter(5, param.get("s_no_post_home"));
                q.setParameter(6, param.get("s_ds_strt_home"));
                q.setParameter(7, param.get("s_ds_addr_home"));
                q.setParameter(8, param.get("s_no_tel1_home"));
                q.setParameter(9, param.get("s_no_tel2_home"));
                q.setParameter(10, param.get("s_no_tel3_home"));
                q.setParameter(11, param.get("s_no_post_cert"));
                q.setParameter(12, param.get("s_ds_strt_cert"));
                q.setParameter(13, param.get("s_ds_addr_cert"));
                q.setParameter(14, param.get("s_no_post_real"));
                q.setParameter(15, param.get("s_ds_strt_real"));
                q.setParameter(16, param.get("s_ds_addr_real")); 
       
                insertCount = q.executeUpdate();
                if (insertCount > 0) {
                    System.out.println(insertCount + ": insertCount record!");
                }else{
                    System.err.println("<Notice> no insertCount record!");
                }                
            }

        }
        catch (RuntimeException re)
        {
            if (tx.isActive ())
                tx.rollback ();   // Alternative: Fix error and retry.
            callback = "Fail:internel db excute error";
            System.err.println("<Error> excuteBizRecruit RuntimeException");
            System.err.println(re);
            re.printStackTrace();
            return callback;
            //throw re;
        }
        catch (Exception e) {
            if (tx.isActive ())
                tx.rollback ();   // Alternative: Fix error and retry.
            callback = "Fail:internel db excute error";
            System.err.println("<Error> excuteBizRecruit Exception");
            e.printStackTrace();
            return callback;
        }

        
        tx.commit();
        callback = noReq;
        return callback;
    }    
}
