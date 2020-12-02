package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ST_USER_AUTH_LOG database table.
 * 
 */
@Entity
@Table(name="ST_USER_AUTH_LOG")
@NamedQuery(name="StUserAuthLog.findAll", query="SELECT s FROM StUserAuthLog s")
public class StUserAuthLog implements Serializable {
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

	@Column(name="DS_PGM_ID")
	private String dsPgmId;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="ID_USER")
	private String idUser;

	@Column(name="YN_A")
	private String ynA;

	@Column(name="YN_D")
	private String ynD;

	@Column(name="YN_E")
	private String ynE;

	@Column(name="YN_P")
	private String ynP;

	@Column(name="YN_S")
	private String ynS;

	@Column(name="YN_U")
	private String ynU;

	public StUserAuthLog() {
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

	public String getDsPgmId() {
		return this.dsPgmId;
	}

	public void setDsPgmId(String dsPgmId) {
		this.dsPgmId = dsPgmId;
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

	public String getIdUser() {
		return this.idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getYnA() {
		return this.ynA;
	}

	public void setYnA(String ynA) {
		this.ynA = ynA;
	}

	public String getYnD() {
		return this.ynD;
	}

	public void setYnD(String ynD) {
		this.ynD = ynD;
	}

	public String getYnE() {
		return this.ynE;
	}

	public void setYnE(String ynE) {
		this.ynE = ynE;
	}

	public String getYnP() {
		return this.ynP;
	}

	public void setYnP(String ynP) {
		this.ynP = ynP;
	}

	public String getYnS() {
		return this.ynS;
	}

	public void setYnS(String ynS) {
		this.ynS = ynS;
	}

	public String getYnU() {
		return this.ynU;
	}

	public void setYnU(String ynU) {
		this.ynU = ynU;
	}

}