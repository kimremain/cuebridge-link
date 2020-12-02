package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ST_USER database table.
 * 
 */
@Entity
@Table(name="ST_USER")
@NamedQuery(name="StUser.findAll", query="SELECT s FROM StUser s")
public class StUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_PART")
	private String cdPart;

	@Column(name="CD_RANK")
	private String cdRank;

	@Column(name="CD_SCREEN_RATIO")
	private String cdScreenRatio;

	@Column(name="CD_TEAM")
	private String cdTeam;

	@Column(name="CN_SCREEN_ASSIGN")
	private BigDecimal cnScreenAssign;

	@Column(name="CN_SCREEN_MAX")
	private BigDecimal cnScreenMax;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_EMAIL")
	private String dsEmail;

	@Column(name="DS_IP")
	private String dsIp;

	@Column(name="DS_PWD")
	private String dsPwd;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_ROLE")
	private String idRole;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="ID_USER")
	private String idUser;

	@Column(name="NM_USER")
	private String nmUser;

	@Column(name="NO_HP")
	private String noHp;

	@Column(name="YN_ADMIN")
	private String ynAdmin;

	@Column(name="YN_BATCH")
	private String ynBatch;

	@Column(name="YN_CONFIRM")
	private String ynConfirm;

	@Column(name="YN_PRINT")
	private String ynPrint;

	@Column(name="YN_RECEIVER")
	private String ynReceiver;

	@Column(name="YN_RESIGN")
	private String ynResign;

	@Column(name="YN_ROLE")
	private String ynRole;

	@Column(name="YN_SCREEN")
	private String ynScreen;

	@Column(name="YN_SCREEN_ASSIGN")
	private String ynScreenAssign;

	public StUser() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdPart() {
		return this.cdPart;
	}

	public void setCdPart(String cdPart) {
		this.cdPart = cdPart;
	}

	public String getCdRank() {
		return this.cdRank;
	}

	public void setCdRank(String cdRank) {
		this.cdRank = cdRank;
	}

	public String getCdScreenRatio() {
		return this.cdScreenRatio;
	}

	public void setCdScreenRatio(String cdScreenRatio) {
		this.cdScreenRatio = cdScreenRatio;
	}

	public String getCdTeam() {
		return this.cdTeam;
	}

	public void setCdTeam(String cdTeam) {
		this.cdTeam = cdTeam;
	}

	public BigDecimal getCnScreenAssign() {
		return this.cnScreenAssign;
	}

	public void setCnScreenAssign(BigDecimal cnScreenAssign) {
		this.cnScreenAssign = cnScreenAssign;
	}

	public BigDecimal getCnScreenMax() {
		return this.cnScreenMax;
	}

	public void setCnScreenMax(BigDecimal cnScreenMax) {
		this.cnScreenMax = cnScreenMax;
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

	public String getDsEmail() {
		return this.dsEmail;
	}

	public void setDsEmail(String dsEmail) {
		this.dsEmail = dsEmail;
	}

	public String getDsIp() {
		return this.dsIp;
	}

	public void setDsIp(String dsIp) {
		this.dsIp = dsIp;
	}

	public String getDsPwd() {
		return this.dsPwd;
	}

	public void setDsPwd(String dsPwd) {
		this.dsPwd = dsPwd;
	}

	public String getIdReg() {
		return this.idReg;
	}

	public void setIdReg(String idReg) {
		this.idReg = idReg;
	}

	public String getIdRole() {
		return this.idRole;
	}

	public void setIdRole(String idRole) {
		this.idRole = idRole;
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

	public String getNmUser() {
		return this.nmUser;
	}

	public void setNmUser(String nmUser) {
		this.nmUser = nmUser;
	}

	public String getNoHp() {
		return this.noHp;
	}

	public void setNoHp(String noHp) {
		this.noHp = noHp;
	}

	public String getYnAdmin() {
		return this.ynAdmin;
	}

	public void setYnAdmin(String ynAdmin) {
		this.ynAdmin = ynAdmin;
	}

	public String getYnBatch() {
		return this.ynBatch;
	}

	public void setYnBatch(String ynBatch) {
		this.ynBatch = ynBatch;
	}

	public String getYnConfirm() {
		return this.ynConfirm;
	}

	public void setYnConfirm(String ynConfirm) {
		this.ynConfirm = ynConfirm;
	}

	public String getYnPrint() {
		return this.ynPrint;
	}

	public void setYnPrint(String ynPrint) {
		this.ynPrint = ynPrint;
	}

	public String getYnReceiver() {
		return this.ynReceiver;
	}

	public void setYnReceiver(String ynReceiver) {
		this.ynReceiver = ynReceiver;
	}

	public String getYnResign() {
		return this.ynResign;
	}

	public void setYnResign(String ynResign) {
		this.ynResign = ynResign;
	}

	public String getYnRole() {
		return this.ynRole;
	}

	public void setYnRole(String ynRole) {
		this.ynRole = ynRole;
	}

	public String getYnScreen() {
		return this.ynScreen;
	}

	public void setYnScreen(String ynScreen) {
		this.ynScreen = ynScreen;
	}

	public String getYnScreenAssign() {
		return this.ynScreenAssign;
	}

	public void setYnScreenAssign(String ynScreenAssign) {
		this.ynScreenAssign = ynScreenAssign;
	}

}