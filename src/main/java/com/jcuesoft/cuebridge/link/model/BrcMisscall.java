package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the BRC_MISSCALL database table.
 * 
 */
@Entity
@Table(name="BRC_MISSCALL")
@NamedQuery(name="BrcMisscall.findAll", query="SELECT b FROM BrcMisscall b")
public class BrcMisscall implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private BigDecimal no;

	@Column(name="CD_CALL_PROC")
	private String cdCallProc;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_REMK")
	private String dsRemk;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NO_TEL")
	private String noTel;

	public BrcMisscall() {
	}

	public BigDecimal getNo() {
		return this.no;
	}

	public void setNo(BigDecimal no) {
		this.no = no;
	}

	public String getCdCallProc() {
		return this.cdCallProc;
	}

	public void setCdCallProc(String cdCallProc) {
		this.cdCallProc = cdCallProc;
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

	public String getDsRemk() {
		return this.dsRemk;
	}

	public void setDsRemk(String dsRemk) {
		this.dsRemk = dsRemk;
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

	public String getNoTel() {
		return this.noTel;
	}

	public void setNoTel(String noTel) {
		this.noTel = noTel;
	}

}