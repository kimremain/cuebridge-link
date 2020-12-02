package com.jcuesoft.cuebridge.link.service.unicn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.jcuesoft.cuebridge.link.model.BrcMisscall;
import com.jcuesoft.cuebridge.link.model.StTeam;

public class LinkUnicnService {

    String callback;
    StringBuffer sbSql;
    String sql;
    Query q;
    ArrayList<StTeam> rs;
    
    public LinkUnicnService(){
        System.out.println("LinkUnicnService init");
        callback = "";
        sbSql = new StringBuffer();
    }
    
    public String excuteBiz(EntityManager em, Map<String, String> param){
        System.out.println("LinkUnicnService excuteBIZ");
        callback = "Succes";
        
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try
        {            
            BrcMisscall bind = new BrcMisscall();
            q = em.createNativeQuery("SELECT BRC_MISSCALL_SEQ.NEXTVAL FROM DUAL");
            BigDecimal no=(BigDecimal)q.getSingleResult();
            bind.setNo(no);
            bind.setNoTel(param.get("no_tel"));
            bind.setCdCallProc("71501");
            bind.setDsRemk(" ");
            q = em.createNativeQuery("SELECT SYSDATE from DUAL");
            Date dmReg =(Date) q.getSingleResult();
            bind.setDmReg(dmReg);
            bind.setIdReg("SYSTEM");
            em.persist(bind);

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
