package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ST_USER_LOG database table.
 * 
 */
@Entity
@Table(name="ST_USER_LOG")
@NamedQuery(name="StUserLog.findAll", query="SELECT s FROM StUserLog s")
public class StUserLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_USER_ACTION")
	private String cdUserAction;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_PGM_ID")
	private String dsPgmId;

	@Column(name="DS_REMK")
	private String dsRemk;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="ID_USER")
	private String idUser;

	@Column(name="NO_REQ")
	private String noReq;

	@Column(name="NO_SEQ")
	private String noSeq;

	public StUserLog() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdUserAction() {
		return this.cdUserAction;
	}

	public void setCdUserAction(String cdUserAction) {
		this.cdUserAction = cdUserAction;
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

	public String getIdUser() {
		return this.idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getNoReq() {
		return this.noReq;
	}

	public void setNoReq(String noReq) {
		this.noReq = noReq;
	}

	public String getNoSeq() {
		return this.noSeq;
	}

	public void setNoSeq(String noSeq) {
		this.noSeq = noSeq;
	}

}