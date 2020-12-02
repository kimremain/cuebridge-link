package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRC_CONTACT database table.
 * 
 */
@Entity
@Table(name="BRC_CONTACT")
@NamedQuery(name="BrcContact.findAll", query="SELECT b FROM BrcContact b")
public class BrcContact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_CALL_KIND")
	private String cdCallKind;

	@Column(name="CD_CALL_LOCAL")
	private String cdCallLocal;

	@Column(name="CD_CALLER")
	private String cdCaller;

	@Column(name="CD_CONTACT")
	private String cdContact;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_PROMISE")
	private Date dmPromise;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_MEMO")
	private String dsMemo;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NO_CONTACT_MODEL")
	private String noContactModel;

	@Column(name="NO_CONTACT_SEQ")
	private String noContactSeq;

	@Column(name="NO_RECORD")
	private String noRecord;

	@Column(name="NO_REQ")
	private String noReq;

	@Column(name="YN_IMPORTANT")
	private String ynImportant;

	@Column(name="YN_RECORD")
	private String ynRecord;

	public BrcContact() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdCallKind() {
		return this.cdCallKind;
	}

	public void setCdCallKind(String cdCallKind) {
		this.cdCallKind = cdCallKind;
	}

	public String getCdCallLocal() {
		return this.cdCallLocal;
	}

	public void setCdCallLocal(String cdCallLocal) {
		this.cdCallLocal = cdCallLocal;
	}

	public String getCdCaller() {
		return this.cdCaller;
	}

	public void setCdCaller(String cdCaller) {
		this.cdCaller = cdCaller;
	}

	public String getCdContact() {
		return this.cdContact;
	}

	public void setCdContact(String cdContact) {
		this.cdContact = cdContact;
	}

	public Date getDmPromise() {
		return this.dmPromise;
	}

	public void setDmPromise(Date dmPromise) {
		this.dmPromise = dmPromise;
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

	public String getDsMemo() {
		return this.dsMemo;
	}

	public void setDsMemo(String dsMemo) {
		this.dsMemo = dsMemo;
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

	public String getNoContactModel() {
		return this.noContactModel;
	}

	public void setNoContactModel(String noContactModel) {
		this.noContactModel = noContactModel;
	}

	public String getNoContactSeq() {
		return this.noContactSeq;
	}

	public void setNoContactSeq(String noContactSeq) {
		this.noContactSeq = noContactSeq;
	}

	public String getNoRecord() {
		return this.noRecord;
	}

	public void setNoRecord(String noRecord) {
		this.noRecord = noRecord;
	}

	public String getNoReq() {
		return this.noReq;
	}

	public void setNoReq(String noReq) {
		this.noReq = noReq;
	}

	public String getYnImportant() {
		return this.ynImportant;
	}

	public void setYnImportant(String ynImportant) {
		this.ynImportant = ynImportant;
	}

	public String getYnRecord() {
		return this.ynRecord;
	}

	public void setYnRecord(String ynRecord) {
		this.ynRecord = ynRecord;
	}

}