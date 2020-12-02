package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRC_SURETY database table.
 * 
 */
@Entity
@Table(name="BRC_SURETY")
@NamedQuery(name="BrcSurety.findAll", query="SELECT b FROM BrcSurety b")
public class BrcSurety implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_KND_ETC")
	private String cdKndEtc;

	@Column(name="CD_KND_MOBILE")
	private String cdKndMobile;

	@Column(name="CD_MARRY")
	private String cdMarry;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_PROMISE")
	private Date dmPromise;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_EMAIL")
	private String dsEmail;

	@Column(name="DS_EXT_KEY")
	private String dsExtKey;

	@Column(name="DS_NICE_SAFEKEY")
	private String dsNiceSafekey;

	@Column(name="DT_CB_AGREE")
	private String dtCbAgree;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_CUSTOM")
	private String nmCustom;

	@Column(name="NO_CUSTOM")
	private String noCustom;

	@Column(name="NO_FAX1")
	private String noFax1;

	@Column(name="NO_FAX2")
	private String noFax2;

	@Column(name="NO_FAX3")
	private String noFax3;

	@Column(name="NO_REQ")
	private String noReq;

	@Column(name="NO_SURETY")
	private String noSurety;

	@Column(name="NO_TEL1_ETC1")
	private String noTel1Etc1;

	@Column(name="NO_TEL1_MOBILE")
	private String noTel1Mobile;

	@Column(name="NO_TEL2_ETC1")
	private String noTel2Etc1;

	@Column(name="NO_TEL2_MOBILE")
	private String noTel2Mobile;

	@Column(name="NO_TEL3_ETC1")
	private String noTel3Etc1;

	@Column(name="NO_TEL3_MOBILE")
	private String noTel3Mobile;

	@Column(name="YN_AUTH_ETC")
	private String ynAuthEtc;

	@Column(name="YN_AUTH_MOBILE")
	private String ynAuthMobile;

	@Column(name="YN_NICE_SAFEKEY")
	private String ynNiceSafekey;

	public BrcSurety() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdKndEtc() {
		return this.cdKndEtc;
	}

	public void setCdKndEtc(String cdKndEtc) {
		this.cdKndEtc = cdKndEtc;
	}

	public String getCdKndMobile() {
		return this.cdKndMobile;
	}

	public void setCdKndMobile(String cdKndMobile) {
		this.cdKndMobile = cdKndMobile;
	}

	public String getCdMarry() {
		return this.cdMarry;
	}

	public void setCdMarry(String cdMarry) {
		this.cdMarry = cdMarry;
	}

	public Date getDmPromise() {
		return this.dmPromise;
	}

	public void setDmPromise(Date dmPromise) {
		this.dmPromise = dmPromise;
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

	public String getDsEmail() {
		return this.dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getDsExtKey() {
		return this.dsExtKey;
	}

	public void setDsExtKey(String dsExtKey) {
		this.dsExtKey = dsExtKey;
	}

	public String getDsNiceSafekey() {
		return this.dsNiceSafekey;
	}

	public void setDsNiceSafekey(String dsNiceSafekey) {
		this.dsNiceSafekey = dsNiceSafekey;
	}

	public String getDtCbAgree() {
		return this.dtCbAgree;
	}

	public void setDtCbAgree(String dtCbAgree) {
		this.dtCbAgree = dtCbAgree;
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

	public String getNmCustom() {
		return this.nmCustom;
	}

	public void setNmCustom(String nmCustom) {
		this.nmCustom = nmCustom;
	}

	public String getNoCustom() {
		return this.noCustom;
	}

	public void setNoCustom(String noCustom) {
		this.noCustom = noCustom;
	}

	public String getNoFax1() {
		return this.noFax1;
	}

	public void setNoFax1(String noFax1) {
		this.noFax1 = noFax1;
	}

	public String getNoFax2() {
		return this.noFax2;
	}

	public void setNoFax2(String noFax2) {
		this.noFax2 = noFax2;
	}

	public String getNoFax3() {
		return this.noFax3;
	}

	public void setNoFax3(String noFax3) {
		this.noFax3 = noFax3;
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

	public String getNoTel1Etc1() {
		return this.noTel1Etc1;
	}

	public void setNoTel1Etc1(String noTel1Etc1) {
		this.noTel1Etc1 = noTel1Etc1;
	}

	public String getNoTel1Mobile() {
		return this.noTel1Mobile;
	}

	public void setNoTel1Mobile(String noTel1Mobile) {
		this.noTel1Mobile = noTel1Mobile;
	}

	public String getNoTel2Etc1() {
		return this.noTel2Etc1;
	}

	public void setNoTel2Etc1(String noTel2Etc1) {
		this.noTel2Etc1 = noTel2Etc1;
	}

	public String getNoTel2Mobile() {
		return this.noTel2Mobile;
	}

	public void setNoTel2Mobile(String noTel2Mobile) {
		this.noTel2Mobile = noTel2Mobile;
	}

	public String getNoTel3Etc1() {
		return this.noTel3Etc1;
	}

	public void setNoTel3Etc1(String noTel3Etc1) {
		this.noTel3Etc1 = noTel3Etc1;
	}

	public String getNoTel3Mobile() {
		return this.noTel3Mobile;
	}

	public void setNoTel3Mobile(String noTel3Mobile) {
		this.noTel3Mobile = noTel3Mobile;
	}

	public String getYnAuthEtc() {
		return this.ynAuthEtc;
	}

	public void setYnAuthEtc(String ynAuthEtc) {
		this.ynAuthEtc = ynAuthEtc;
	}

	public String getYnAuthMobile() {
		return this.ynAuthMobile;
	}

	public void setYnAuthMobile(String ynAuthMobile) {
		this.ynAuthMobile = ynAuthMobile;
	}

	public String getYnNiceSafekey() {
		return this.ynNiceSafekey;
	}

	public void setYnNiceSafekey(String ynNiceSafekey) {
		this.ynNiceSafekey = ynNiceSafekey;
	}

}