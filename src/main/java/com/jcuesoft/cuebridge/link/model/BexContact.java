package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BEX_CONTACT database table.
 * 
 */
@Entity
@Table(name="BEX_CONTACT")
@NamedQuery(name="BexContact.findAll", query="SELECT b FROM BexContact b")
public class BexContact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

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

	@Column(name="NO_CONTACT_SEQ")
	private String noContactSeq;

	@Column(name="NO_EX")
	private String noEx;

	@Column(name="NO_REQ")
	private String noReq;

	public BexContact() {
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

	public String getNoContactSeq() {
		return this.noContactSeq;
	}

	public void setNoContactSeq(String noContactSeq) {
		this.noContactSeq = noContactSeq;
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