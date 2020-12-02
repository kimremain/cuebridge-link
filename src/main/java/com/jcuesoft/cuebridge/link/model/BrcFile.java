package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRC_FILE database table.
 * 
 */
@Entity
@Table(name="BRC_FILE")
@NamedQuery(name="BrcFile.findAll", query="SELECT b FROM BrcFile b")
public class BrcFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_FILE")
	private String cdFile;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_PATH")
	private String dsPath;

	@Column(name="DS_REMK")
	private String dsRemk;

	@Column(name="DS_URL")
	private String dsUrl;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_FILE")
	private String nmFile;

	@Column(name="NM_FILE_USER")
	private String nmFileUser;

	@Column(name="NO_IMG")
	private String noImg;

	@Column(name="NO_REQ")
	private String noReq;

	public BrcFile() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdFile() {
		return this.cdFile;
	}

	public void setCdFile(String cdFile) {
		this.cdFile = cdFile;
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

	public String getDsPath() {
		return this.dsPath;
	}

	public void setDsPath(String dsPath) {
		this.dsPath = dsPath;
	}

	public String getDsRemk() {
		return this.dsRemk;
	}

	public void setDsRemk(String dsRemk) {
		this.dsRemk = dsRemk;
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

	public String getNmFile() {
		return this.nmFile;
	}

	public void setNmFile(String nmFile) {
		this.nmFile = nmFile;
	}

	public String getNmFileUser() {
		return this.nmFileUser;
	}

	public void setNmFileUser(String nmFileUser) {
		this.nmFileUser = nmFileUser;
	}

	public String getNoImg() {
		return this.noImg;
	}

	public void setNoImg(String noImg) {
		this.noImg = noImg;
	}

	public String getNoReq() {
		return this.noReq;
	}

	public void setNoReq(String noReq) {
		this.noReq = noReq;
	}

}