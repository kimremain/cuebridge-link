package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ST_CUSTOMER database table.
 * 
 */
@Entity
@Table(name="ST_CUSTOMER")
@NamedQuery(name="StCustomer.findAll", query="SELECT s FROM StCustomer s")
public class StCustomer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_COOPERATE")
	private String cdCooperate;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_ID")
	private String dsId;

	@Column(name="DS_URL")
	private String dsUrl;

	@Column(name="DS_URL_IMG")
	private String dsUrlImg;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_CUSTOMER")
	private String nmCustomer;

	@Column(name="YN_USE")
	private String ynUse;

	public StCustomer() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdCooperate() {
		return this.cdCooperate;
	}

	public void setCdCooperate(String cdCooperate) {
		this.cdCooperate = cdCooperate;
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

	public String getDsId() {
		return this.dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getDsUrl() {
		return this.dsUrl;
	}

	public void setDsUrl(String dsUrl) {
		this.dsUrl = dsUrl;
	}

	public String getDsUrlImg() {
		return this.dsUrlImg;
	}

	public void setDsUrlImg(String dsUrlImg) {
		this.dsUrlImg = dsUrlImg;
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

	public String getNmCustomer() {
		return this.nmCustomer;
	}

	public void setNmCustomer(String nmCustomer) {
		this.nmCustomer = nmCustomer;
	}

	public String getYnUse() {
		return this.ynUse;
	}

	public void setYnUse(String ynUse) {
		this.ynUse = ynUse;
	}

}