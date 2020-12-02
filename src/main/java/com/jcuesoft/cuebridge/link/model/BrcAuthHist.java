package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRC_AUTH_HIST database table.
 * 
 */
@Entity
@Table(name="BRC_AUTH_HIST")
@NamedQuery(name="BrcAuthHist.findAll", query="SELECT b FROM BrcAuthHist b")
public class BrcAuthHist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_AUTH")
	private String cdAuth;

	@Column(name="CD_CUSTOM")
	private String cdCustom;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_AUTH")
	private Date dmAuth;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NO_AUTH")
	private String noAuth;

	@Column(name="NO_HP")
	private String noHp;

	@Column(name="NO_REQ")
	private String noReq;

	@Column(name="NO_SURETY")
	private String noSurety;

	@Column(name="YN_AUTH")
	private String ynAuth;

	@Column(name="YN_BIZ_EMAIL")
	private String ynBizEmail;

	@Column(name="YN_BIZ_SMS")
	private String ynBizSms;

	@Column(name="YN_BIZ_TEL")
	private String ynBizTel;

	@Column(name="YN_INFO_COLLECT")
	private String ynInfoCollect;

	@Column(name="YN_INFO_OPT_SUPPLY")
	private String ynInfoOptSupply;

	@Column(name="YN_INFO_OPT_USE")
	private String ynInfoOptUse;

	@Column(name="YN_INFO_SEARCH")
	private String ynInfoSearch;

	@Column(name="YN_INFO_SUPPLY")
	private String ynInfoSupply;

	@Column(name="YN_INFO_UID_USE")
	private String ynInfoUidUse;

	@Column(name="YN_UID_SUPPLY")
	private String ynUidSupply;

	@Column(name="YN_UID_USE")
	private String ynUidUse;

	public BrcAuthHist() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdAuth() {
		return this.cdAuth;
	}

	public void setCdAuth(String cdAuth) {
		this.cdAuth = cdAuth;
	}

	public String getCdCustom() {
		return this.cdCustom;
	}

	public void setCdCustom(String cdCustom) {
		this.cdCustom = cdCustom;
	}

	public Date getDmAuth() {
		return this.dmAuth;
	}

	public void setDmAuth(Date dmAuth) {
		this.dmAuth = dmAuth;
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

	public String getNoAuth() {
		return this.noAuth;
	}

	public void setNoAuth(String noAuth) {
		this.noAuth = noAuth;
	}

	public String getNoHp() {
		return this.noHp;
	}

	public void setNoHp(String noHp) {
		this.noHp = noHp;
	}

	public String getNoReq() {
		return this.noReq;
	}

	public void setNoReq(String noReq) {
		this.noReq = noReq;
	}

	public String getNoSurety() {
		return this.noSurety;
	}

	public void setNoSurety(String noSurety) {
		this.noSurety = noSurety;
	}

	public String getYnAuth() {
		return this.ynAuth;
	}

	public void setYnAuth(String ynAuth) {
		this.ynAuth = ynAuth;
	}

	public String getYnBizEmail() {
		return this.ynBizEmail;
	}

	public void setYnBizEmail(String ynBizEmail) {
		this.ynBizEmail = ynBizEmail;
	}

	public String getYnBizSms() {
		return this.ynBizSms;
	}

	public void setYnBizSms(String ynBizSms) {
		this.ynBizSms = ynBizSms;
	}

	public String getYnBizTel() {
		return this.ynBizTel;
	}

	public void setYnBizTel(String ynBizTel) {
		this.ynBizTel = ynBizTel;
	}

	public String getYnInfoCollect() {
		return this.ynInfoCollect;
	}

	public void setYnInfoCollect(String ynInfoCollect) {
		this.ynInfoCollect = ynInfoCollect;
	}

	public String getYnInfoOptSupply() {
		return this.ynInfoOptSupply;
	}

	public void setYnInfoOptSupply(String ynInfoOptSupply) {
		this.ynInfoOptSupply = ynInfoOptSupply;
	}

	public String getYnInfoOptUse() {
		return this.ynInfoOptUse;
	}

	public void setYnInfoOptUse(String ynInfoOptUse) {
		this.ynInfoOptUse = ynInfoOptUse;
	}

	public String getYnInfoSearch() {
		return this.ynInfoSearch;
	}

	public void setYnInfoSearch(String ynInfoSearch) {
		this.ynInfoSearch = ynInfoSearch;
	}

	public String getYnInfoSupply() {
		return this.ynInfoSupply;
	}

	public void setYnInfoSupply(String ynInfoSupply) {
		this.ynInfoSupply = ynInfoSupply;
	}

	public String getYnInfoUidUse() {
		return this.ynInfoUidUse;
	}

	public void setYnInfoUidUse(String ynInfoUidUse) {
		this.ynInfoUidUse = ynInfoUidUse;
	}

	public String getYnUidSupply() {
		return this.ynUidSupply;
	}

	public void setYnUidSupply(String ynUidSupply) {
		this.ynUidSupply = ynUidSupply;
	}

	public String getYnUidUse() {
		return this.ynUidUse;
	}

	public void setYnUidUse(String ynUidUse) {
		this.ynUidUse = ynUidUse;
	}

}