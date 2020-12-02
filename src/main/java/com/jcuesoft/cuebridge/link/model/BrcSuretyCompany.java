package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BRC_SURETY_COMPANY database table.
 * 
 */
@Entity
@Table(name="BRC_SURETY_COMPANY")
@NamedQuery(name="BrcSuretyCompany.findAll", query="SELECT b FROM BrcSuretyCompany b")
public class BrcSuretyCompany implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="AM_PAY_MONTH")
	private BigDecimal amPayMonth;

	@Column(name="CD_JOB_ACT")
	private String cdJobAct;

	@Column(name="CD_JOB_KIND")
	private String cdJobKind;

	@Column(name="CD_JOB_STATUS")
	private String cdJobStatus;

	@Column(name="CD_RANK")
	private String cdRank;

	@Column(name="DD_PAY")
	private String ddPay;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_ADDR_COMP")
	private String dsAddrComp;

	@Column(name="DS_STRT_COMP")
	private String dsStrtComp;

	@Column(name="DT_REG_JOIN")
	private String dtRegJoin;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_COMP")
	private String nmComp;

	@Column(name="NM_DEPT")
	private String nmDept;

	@Column(name="NO_EXT_COMP")
	private String noExtComp;

	@Column(name="NO_MAINTEL1_COMP")
	private String noMaintel1Comp;

	@Column(name="NO_MAINTEL2_COMP")
	private String noMaintel2Comp;

	@Column(name="NO_MAINTEL3_COMP")
	private String noMaintel3Comp;

	@Column(name="NO_POST_COMP")
	private String noPostComp;

	@Column(name="NO_REQ")
	private String noReq;

	@Column(name="NO_SURETY")
	private String noSurety;

	@Column(name="NO_TEL1_COMP")
	private String noTel1Comp;

	@Column(name="NO_TEL2_COMP")
	private String noTel2Comp;

	@Column(name="NO_TEL3_COMP")
	private String noTel3Comp;

	public BrcSuretyCompany() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public BigDecimal getAmPayMonth() {
		return this.amPayMonth;
	}

	public void setAmPayMonth(BigDecimal amPayMonth) {
		this.amPayMonth = amPayMonth;
	}

	public String getCdJobAct() {
		return this.cdJobAct;
	}

	public void setCdJobAct(String cdJobAct) {
		this.cdJobAct = cdJobAct;
	}

	public String getCdJobKind() {
		return this.cdJobKind;
	}

	public void setCdJobKind(String cdJobKind) {
		this.cdJobKind = cdJobKind;
	}

	public String getCdJobStatus() {
		return this.cdJobStatus;
	}

	public void setCdJobStatus(String cdJobStatus) {
		this.cdJobStatus = cdJobStatus;
	}

	public String getCdRank() {
		return this.cdRank;
	}

	public void setCdRank(String cdRank) {
		this.cdRank = cdRank;
	}

	public String getDdPay() {
		return this.ddPay;
	}

	public void setDdPay(String ddPay) {
		this.ddPay = ddPay;
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

	public String getDsAddrComp() {
		return this.dsAddrComp;
	}

	public void setDsAddrComp(String dsAddrComp) {
		this.dsAddrComp = dsAddrComp;
	}

	public String getDsStrtComp() {
		return this.dsStrtComp;
	}

	public void setDsStrtComp(String dsStrtComp) {
		this.dsStrtComp = dsStrtComp;
	}

	public String getDtRegJoin() {
		return this.dtRegJoin;
	}

	public void setDtRegJoin(String dtRegJoin) {
		this.dtRegJoin = dtRegJoin;
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

	public String getNmComp() {
		return this.nmComp;
	}

	public void setNmComp(String nmComp) {
		this.nmComp = nmComp;
	}

	public String getNmDept() {
		return this.nmDept;
	}

	public void setNmDept(String nmDept) {
		this.nmDept = nmDept;
	}

	public String getNoExtComp() {
		return this.noExtComp;
	}

	public void setNoExtComp(String noExtComp) {
		this.noExtComp = noExtComp;
	}

	public String getNoMaintel1Comp() {
		return this.noMaintel1Comp;
	}

	public void setNoMaintel1Comp(String noMaintel1Comp) {
		this.noMaintel1Comp = noMaintel1Comp;
	}

	public String getNoMaintel2Comp() {
		return this.noMaintel2Comp;
	}

	public void setNoMaintel2Comp(String noMaintel2Comp) {
		this.noMaintel2Comp = noMaintel2Comp;
	}

	public String getNoMaintel3Comp() {
		return this.noMaintel3Comp;
	}

	public void setNoMaintel3Comp(String noMaintel3Comp) {
		this.noMaintel3Comp = noMaintel3Comp;
	}

	public String getNoPostComp() {
		return this.noPostComp;
	}

	public void setNoPostComp(String noPostComp) {
		this.noPostComp = noPostComp;
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

	public String getNoTel1Comp() {
		return this.noTel1Comp;
	}

	public void setNoTel1Comp(String noTel1Comp) {
		this.noTel1Comp = noTel1Comp;
	}

	public String getNoTel2Comp() {
		return this.noTel2Comp;
	}

	public void setNoTel2Comp(String noTel2Comp) {
		this.noTel2Comp = noTel2Comp;
	}

	public String getNoTel3Comp() {
		return this.noTel3Comp;
	}

	public void setNoTel3Comp(String noTel3Comp) {
		this.noTel3Comp = noTel3Comp;
	}

}