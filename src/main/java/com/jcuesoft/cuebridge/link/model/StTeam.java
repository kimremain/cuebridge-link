package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ST_TEAM database table.
 * 
 */
@Entity
@Table(name="ST_TEAM")
@NamedQuery(name="StTeam.findAll", query="SELECT s FROM StTeam s")
public class StTeam implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_TEAM")
	private String cdTeam;

	@Column(name="CD_TEAM_PARENT")
	private String cdTeamParent;

	@Column(name="CD_TEAM_PART")
	private String cdTeamPart;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_ADDR")
	private String dsAddr;

	@Column(name="DS_STRT")
	private String dsStrt;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_TEAM")
	private String nmTeam;

	@Column(name="NO_ORDER")
	private String noOrder;

	@Column(name="NO_POST")
	private String noPost;

	@Column(name="NO_TEL1")
	private String noTel1;

	@Column(name="NO_TEL1_FAX")
	private String noTel1Fax;

	@Column(name="NO_TEL2")
	private String noTel2;

	@Column(name="NO_TEL2_FAX")
	private String noTel2Fax;

	@Column(name="NO_TEL3")
	private String noTel3;

	@Column(name="NO_TEL3_FAX")
	private String noTel3Fax;

	@Column(name="YN_CLOSE")
	private String ynClose;

	public StTeam() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdTeam() {
		return this.cdTeam;
	}

	public void setCdTeam(String cdTeam) {
		this.cdTeam = cdTeam;
	}

	public String getCdTeamParent() {
		return this.cdTeamParent;
	}

	public void setCdTeamParent(String cdTeamParent) {
		this.cdTeamParent = cdTeamParent;
	}

	public String getCdTeamPart() {
		return this.cdTeamPart;
	}

	public void setCdTeamPart(String cdTeamPart) {
		this.cdTeamPart = cdTeamPart;
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

	public String getDsAddr() {
		return this.dsAddr;
	}

	public void setDsAddr(String dsAddr) {
		this.dsAddr = dsAddr;
	}

	public String getDsStrt() {
		return this.dsStrt;
	}

	public void setDsStrt(String dsStrt) {
		this.dsStrt = dsStrt;
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

	public String getNmTeam() {
		return this.nmTeam;
	}

	public void setNmTeam(String nmTeam) {
		this.nmTeam = nmTeam;
	}

	public String getNoOrder() {
		return this.noOrder;
	}

	public void setNoOrder(String noOrder) {
		this.noOrder = noOrder;
	}

	public String getNoPost() {
		return this.noPost;
	}

	public void setNoPost(String noPost) {
		this.noPost = noPost;
	}

	public String getNoTel1() {
		return this.noTel1;
	}

	public void setNoTel1(String noTel1) {
		this.noTel1 = noTel1;
	}

	public String getNoTel1Fax() {
		return this.noTel1Fax;
	}

	public void setNoTel1Fax(String noTel1Fax) {
		this.noTel1Fax = noTel1Fax;
	}

	public String getNoTel2() {
		return this.noTel2;
	}

	public void setNoTel2(String noTel2) {
		this.noTel2 = noTel2;
	}

	public String getNoTel2Fax() {
		return this.noTel2Fax;
	}

	public void setNoTel2Fax(String noTel2Fax) {
		this.noTel2Fax = noTel2Fax;
	}

	public String getNoTel3() {
		return this.noTel3;
	}

	public void setNoTel3(String noTel3) {
		this.noTel3 = noTel3;
	}

	public String getNoTel3Fax() {
		return this.noTel3Fax;
	}

	public void setNoTel3Fax(String noTel3Fax) {
		this.noTel3Fax = noTel3Fax;
	}

	public String getYnClose() {
		return this.ynClose;
	}

	public void setYnClose(String ynClose) {
		this.ynClose = ynClose;
	}

}