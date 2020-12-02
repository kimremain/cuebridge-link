package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BRC_LOAN database table.
 * 
 */
@Entity
@Table(name="BRC_LOAN")
@NamedQuery(name="BrcLoan.findAll", query="SELECT b FROM BrcLoan b")
public class BrcLoan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private BigDecimal no;

	@Column(name="AM_APP")
	private BigDecimal amApp;

	@Column(name="AM_COOPERATE")
	private BigDecimal amCooperate;

	@Column(name="AM_CUSTOM")
	private BigDecimal amCustom;

	@Column(name="CD_CANCEL")
	private String cdCancel;

	@Column(name="CD_COOPERATE")
	private String cdCooperate;

	@Column(name="CD_ECHANEL")
	private String cdEchanel;

	@Column(name="CD_EPATH")
	private String cdEpath;

	@Column(name="CD_EPORTAL")
	private String cdEportal;

	@Column(name="CD_PATH")
	private String cdPath;

	@Column(name="CD_PDT")
	private String cdPdt;

	@Column(name="CD_REJECT")
	private String cdReject;

	@Column(name="CD_STATUS")
	private String cdStatus;

	@Column(name="CD_USE")
	private String cdUse;

	private String dd;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_AUTH")
	private Date dmAuth;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_CONTACT")
	private Date dmContact;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_RECEIVE")
	private Date dmReceive;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REJECT")
	private Date dmReject;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_SCREEN")
	private Date dmScreen;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_EXT_KEY")
	private String dsExtKey;

	@Column(name="DS_RECEIVE")
	private String dsReceive;

	@Column(name="DS_SCREEN")
	private String dsScreen;
	
        @Column(name="DS_COOPERATE")
        private String dsCooperate;	

	@Column(name="ID_AUTH")
	private String idAuth;

	@Column(name="ID_RECEIVE")
	private String idReceive;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_SCREEN")
	private String idScreen;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_EKEYWORD")
	private String nmEkeyword;

	@Column(name="NO_COOPERATE")
	private String noCooperate;

	@Column(name="NO_REQ")
	private String noReq;

	@Column(name="YN_REAPP")
	private String ynReapp;

	@Column(name="YN_SURETY")
	private String ynSurety;

	public BrcLoan() {
	}

	public BigDecimal getNo() {
		return this.no;
	}

	public void setNo(BigDecimal brcLoanNo) {
		this.no = brcLoanNo;
	}

	public BigDecimal getAmApp() {
		return this.amApp;
	}

	public void setAmApp(BigDecimal amApp) {
		this.amApp = amApp;
	}

	public BigDecimal getAmCooperate() {
		return this.amCooperate;
	}

	public void setAmCooperate(BigDecimal amCooperate) {
		this.amCooperate = amCooperate;
	}

	public BigDecimal getAmCustom() {
		return this.amCustom;
	}

	public void setAmCustom(BigDecimal amCustom) {
		this.amCustom = amCustom;
	}

	public String getCdCancel() {
		return this.cdCancel;
	}

	public void setCdCancel(String cdCancel) {
		this.cdCancel = cdCancel;
	}

	public String getCdCooperate() {
		return this.cdCooperate;
	}

	public void setCdCooperate(String cdCooperate) {
		this.cdCooperate = cdCooperate;
	}

	public String getCdEchanel() {
		return this.cdEchanel;
	}

	public void setCdEchanel(String cdEchanel) {
		this.cdEchanel = cdEchanel;
	}

	public String getCdEpath() {
		return this.cdEpath;
	}

	public void setCdEpath(String cdEpath) {
		this.cdEpath = cdEpath;
	}

	public String getCdEportal() {
		return this.cdEportal;
	}

	public void setCdEportal(String cdEportal) {
		this.cdEportal = cdEportal;
	}

	public String getCdPath() {
		return this.cdPath;
	}

	public void setCdPath(String cdPath) {
		this.cdPath = cdPath;
	}

	public String getCdPdt() {
		return this.cdPdt;
	}

	public void setCdPdt(String cdPdt) {
		this.cdPdt = cdPdt;
	}

	public String getCdReject() {
		return this.cdReject;
	}

	public void setCdReject(String cdReject) {
		this.cdReject = cdReject;
	}

	public String getCdStatus() {
		return this.cdStatus;
	}

	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}

	public String getCdUse() {
		return this.cdUse;
	}

	public void setCdUse(String cdUse) {
		this.cdUse = cdUse;
	}

	public String getDd() {
		return this.dd;
	}

	public void setDd(String dd) {
		this.dd = dd;
	}

	public Date getDmAuth() {
		return this.dmAuth;
	}

	public void setDmAuth(Date dmAuth) {
		this.dmAuth = dmAuth;
	}

	public Date getDmContact() {
		return this.dmContact;
	}

	public void setDmContact(Date dmContact) {
		this.dmContact = dmContact;
	}

	public Date getDmReceive() {
		return this.dmReceive;
	}

	public void setDmReceive(Date dmReceive) {
		this.dmReceive = dmReceive;
	}

	public Date getDmReg() {
		return this.dmReg;
	}

	public void setDmReg(Date dmReg) {
		this.dmReg = dmReg;
	}

	public Date getDmReject() {
		return this.dmReject;
	}

	public void setDmReject(Date dmReject) {
		this.dmReject = dmReject;
	}

	public Date getDmScreen() {
		return this.dmScreen;
	}

	public void setDmScreen(Date dmScreen) {
		this.dmScreen = dmScreen;
	}

	public Date getDmUpt() {
		return this.dmUpt;
	}

	public void setDmUpt(Date dmUpt) {
		this.dmUpt = dmUpt;
	}

	public String getDsExtKey() {
		return this.dsExtKey;
	}

	public void setDsExtKey(String dsExtKey) {
		this.dsExtKey = dsExtKey;
	}

	public String getDsReceive() {
		return this.dsReceive;
	}

	public void setDsReceive(String dsReceive) {
		this.dsReceive = dsReceive;
	}

	public String getDsScreen() {
		return this.dsScreen;
	}

	public void setDsScreen(String dsScreen) {
		this.dsScreen = dsScreen;
	}

	public String getIdAuth() {
		return this.idAuth;
	}

	public void setIdAuth(String idAuth) {
		this.idAuth = idAuth;
	}

	public String getIdReceive() {
		return this.idReceive;
	}

	public void setIdReceive(String idReceive) {
		this.idReceive = idReceive;
	}

	public String getIdReg() {
		return this.idReg;
	}

	public void setIdReg(String idReg) {
		this.idReg = idReg;
	}

	public String getIdScreen() {
		return this.idScreen;
	}

	public void setIdScreen(String idScreen) {
		this.idScreen = idScreen;
	}

	public String getIdUpt() {
		return this.idUpt;
	}

	public void setIdUpt(String idUpt) {
		this.idUpt = idUpt;
	}

	public String getNmEkeyword() {
		return this.nmEkeyword;
	}

	public void setNmEkeyword(String nmEkeyword) {
		this.nmEkeyword = nmEkeyword;
	}

	public String getNoCooperate() {
		return this.noCooperate;
	}

	public void setNoCooperate(String noCooperate) {
		this.noCooperate = noCooperate;
	}

	public String getNoReq() {
		return this.noReq;
	}

	public void setNoReq(String noReq) {
		this.noReq = noReq;
	}

	public String getYnReapp() {
		return this.ynReapp;
	}

	public void setYnReapp(String ynReapp) {
		this.ynReapp = ynReapp;
	}

	public String getYnSurety() {
		return this.ynSurety;
	}

	public void setYnSurety(String ynSurety) {
		this.ynSurety = ynSurety;
	}
	
        public String getDsCooperate() {
            return this.dsCooperate;
        }

        public void setDsCooperate(String dsCooperate) {
                this.dsCooperate = dsCooperate;
        }	


}