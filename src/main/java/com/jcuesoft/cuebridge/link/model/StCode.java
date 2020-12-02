package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ST_CODE database table.
 * 
 */
@Entity
@Table(name="ST_CODE")
@NamedQuery(name="StCode.findAll", query="SELECT s FROM StCode s")
public class StCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	private String cd;

	@Column(name="CD_GROUP")
	private String cdGroup;

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

	@Column(name="NM_CD")
	private String nmCd;

	@Column(name="NO_ORDER")
	private String noOrder;

	@Column(name="YN_USE")
	private String ynUse;

	public StCode() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCd() {
		return this.cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getCdGroup() {
		return this.cdGroup;
	}

	public void setCdGroup(String cdGroup) {
		this.cdGroup = cdGroup;
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

	public String getNmCd() {
		return this.nmCd;
	}

	public void setNmCd(String nmCd) {
		this.nmCd = nmCd;
	}

	public String getNoOrder() {
		return this.noOrder;
	}

	public void setNoOrder(String noOrder) {
		this.noOrder = noOrder;
	}

	public String getYnUse() {
		return this.ynUse;
	}

	public void setYnUse(String ynUse) {
		this.ynUse = ynUse;
	}

}