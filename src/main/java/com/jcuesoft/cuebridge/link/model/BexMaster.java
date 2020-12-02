package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BEX_MASTER database table.
 * 
 */
@Entity
@Table(name="BEX_MASTER")
@NamedQuery(name="BexMaster.findAll", query="SELECT b FROM BexMaster b")
public class BexMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="AM_APP")
	private BigDecimal amApp;

	@Column(name="AM_COOPERATE")
	private BigDecimal amCooperate;

	@Column(name="CD_COOPERATE")
	private String cdCooperate;

	@Column(name="CD_PDT")
	private String cdPdt;

	@Column(name="DM_COOPERATE")
	private String dmCooperate;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_COOPERATE")
	private String dsCooperate;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_SCREEN")
	private String nmScreen;

	@Column(name="NM_SCREEN_PATH")
	private String nmScreenPath;

	@Column(name="NO_COOPERATE")
	private String noCooperate;

	@Column(name="NO_COOPERATE_ACT")
	private String noCooperateAct;

	@Column(name="NO_EX")
	private String noEx;

	@Column(name="NO_REQ")
	private String noReq;

	public BexMaster() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
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

	public String getCdCooperate() {
		return this.cdCooperate;
	}

	public void setCdCooperate(String cdCooperate) {
		this.cdCooperate = cdCooperate;
	}

	public String getCdPdt() {
		return this.cdPdt;
	}

	public void setCdPdt(String cdPdt) {
		this.cdPdt = cdPdt;
	}

	public String getDmCooperate() {
		return this.dmCooperate;
	}

	public void setDmCooperate(String dmCooperate) {
		this.dmCooperate = dmCooperate;
	}

	public Date getDmReg() {
		return this.dmReg;
	}

	public void setDmReg(Date dmReg) {
		this.dmReg = dmReg;
	}

	public Date getDmUpt() {
		return this.dmUpt;
	}

	public void setDmUpt(Date dmUpt) {
		this.dmUpt = dmUpt;
	}

	public String getDsCooperate() {
		return this.dsCooperate;
	}

	public void setDsCooperate(String dsCooperate) {
		this.dsCooperate = dsCooperate;
	}

	public String getIdReg() {
		return this.idReg;
	}

	public void setIdReg(String idReg) {
		this.idReg = idReg;
	}

	public String getIdUpt() {
		return this.idUpt;
	}

	public void setIdUpt(String idUpt) {
		this.idUpt = idUpt;
	}

	public String getNmScreen() {
		return this.nmScreen;
	}

	public void setNmScreen(String nmScreen) {
		this.nmScreen = nmScreen;
	}

	public String getNmScreenPath() {
		return this.nmScreenPath;
	}

	public void setNmScreenPath(String nmScreenPath) {
		this.nmScreenPath = nmScreenPath;
	}

	public String getNoCooperate() {
		return this.noCooperate;
	}

	public void setNoCooperate(String noCooperate) {
		this.noCooperate = noCooperate;
	}

	public String getNoCooperateAct() {
		return this.noCooperateAct;
	}

	public void setNoCooperateAct(String noCooperateAct) {
		this.noCooperateAct = noCooperateAct;
	}

	public String getNoEx() {
		return this.noEx;
	}

	public void setNoEx(String noEx) {
		this.noEx = noEx;
	}

	public String getNoReq() {
		return this.noReq;
	}

	public void setNoReq(String noReq) {
		this.noReq = noReq;
	}

}