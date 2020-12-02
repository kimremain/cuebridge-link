package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRC_SUPPLY database table.
 * 
 */
@Entity
@Table(name="BRC_SUPPLY")
@NamedQuery(name="BrcSupply.findAll", query="SELECT b FROM BrcSupply b")
public class BrcSupply implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Temporal(TemporalType.DATE)
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.DATE)
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DT_SUPPLY")
	private String dtSupply;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_SUPPLY")
	private String nmSupply;

	@Column(name="NO_REQ")
	private String noReq;

	@Column(name="NO_SUPPLY")
	private String noSupply;

	@Column(name="NO_SUPPLY_LOAN")
	private String noSupplyLoan;

	@Column(name="NO_SUPPLY_SOCIETY")
	private String noSupplySociety;

	@Column(name="NO_SUPPLY_TEL")
	private String noSupplyTel;

	public BrcSupply() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
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

	public String getDtSupply() {
		return this.dtSupply;
	}

	public void setDtSupply(String dtSupply) {
		this.dtSupply = dtSupply;
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

	public String getNmSupply() {
		return this.nmSupply;
	}

	public void setNmSupply(String nmSupply) {
		this.nmSupply = nmSupply;
	}

	public String getNoReq() {
		return this.noReq;
	}

	public void setNoReq(String noReq) {
		this.noReq = noReq;
	}

	public String getNoSupply() {
		return this.noSupply;
	}

	public void setNoSupply(String noSupply) {
		this.noSupply = noSupply;
	}

	public String getNoSupplyLoan() {
		return this.noSupplyLoan;
	}

	public void setNoSupplyLoan(String noSupplyLoan) {
		this.noSupplyLoan = noSupplyLoan;
	}

	public String getNoSupplySociety() {
		return this.noSupplySociety;
	}

	public void setNoSupplySociety(String noSupplySociety) {
		this.noSupplySociety = noSupplySociety;
	}

	public String getNoSupplyTel() {
		return this.noSupplyTel;
	}

	public void setNoSupplyTel(String noSupplyTel) {
		this.noSupplyTel = noSupplyTel;
	}

}