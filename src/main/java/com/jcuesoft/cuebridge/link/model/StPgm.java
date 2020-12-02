package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ST_PGM database table.
 * 
 */
@Entity
@Table(name="ST_PGM")
@NamedQuery(name="StPgm.findAll", query="SELECT s FROM StPgm s")
public class StPgm implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_ONOFF")
	private String cdOnoff;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_HELP")
	private String dsHelp;

	@Column(name="DS_PGM")
	private String dsPgm;

	@Column(name="DS_PGM_ID")
	private String dsPgmId;

	@Column(name="DS_URL")
	private String dsUrl;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="YN_ADMIN")
	private String ynAdmin;

	@Column(name="YN_APPLY")
	private String ynApply;

	public StPgm() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdOnoff() {
		return this.cdOnoff;
	}

	public void setCdOnoff(String cdOnoff) {
		this.cdOnoff = cdOnoff;
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

	public String getDsHelp() {
		return this.dsHelp;
	}

	public void setDsHelp(String dsHelp) {
		this.dsHelp = dsHelp;
	}

	public String getDsPgm() {
		return this.dsPgm;
	}

	public void setDsPgm(String dsPgm) {
		this.dsPgm = dsPgm;
	}

	public String getDsPgmId() {
		return this.dsPgmId;
	}

	public void setDsPgmId(String dsPgmId) {
		this.dsPgmId = dsPgmId;
	}

	public String getDsUrl() {
		return this.dsUrl;
	}

	public void setDsUrl(String dsUrl) {
		this.dsUrl = dsUrl;
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

	public String getYnAdmin() {
		return this.ynAdmin;
	}

	public void setYnAdmin(String ynAdmin) {
		this.ynAdmin = ynAdmin;
	}

	public String getYnApply() {
		return this.ynApply;
	}

	public void setYnApply(String ynApply) {
		this.ynApply = ynApply;
	}

}