package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the BRC_SURETY_HOME database table.
 * 
 */
@Entity
@Table(name="BRC_SURETY_HOME")
@NamedQuery(name="BrcSuretyHome.findAll", query="SELECT b FROM BrcSuretyHome b")
public class BrcSuretyHome implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="AM_DEPOSIT_HOME")
	private BigDecimal amDepositHome;

	@Column(name="AM_RENT_HOME")
	private BigDecimal amRentHome;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_ADDR_CERT")
	private String dsAddrCert;

	@Column(name="DS_ADDR_HOME")
	private String dsAddrHome;

	@Column(name="DS_ADDR_REAL")
	private String dsAddrReal;

	@Column(name="DS_STRT_CERT")
	private String dsStrtCert;

	@Column(name="DS_STRT_HOME")
	private String dsStrtHome;

	@Column(name="DS_STRT_REAL")
	private String dsStrtReal;

	@Column(name="DT_REG_HOME")
	private String dtRegHome;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NO_POST_CERT")
	private String noPostCert;

	@Column(name="NO_POST_HOME")
	private String noPostHome;

	@Column(name="NO_POST_REAL")
	private String noPostReal;

	@Column(name="NO_REQ")
	private String noReq;

	@Column(name="NO_SURETY")
	private String noSurety;

	@Column(name="NO_TEL1_HOME")
	private String noTel1Home;

	@Column(name="NO_TEL2_HOME")
	private String noTel2Home;

	@Column(name="NO_TEL3_HOME")
	private String noTel3Home;

	public BrcSuretyHome() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public BigDecimal getAmDepositHome() {
		return this.amDepositHome;
	}

	public void setAmDepositHome(BigDecimal amDepositHome) {
		this.amDepositHome = amDepositHome;
	}

	public BigDecimal getAmRentHome() {
		return this.amRentHome;
	}

	public void setAmRentHome(BigDecimal amRentHome) {
		this.amRentHome = amRentHome;
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

	public String getDsAddrCert() {
		return this.dsAddrCert;
	}

	public void setDsAddrCert(String dsAddrCert) {
		this.dsAddrCert = dsAddrCert;
	}

	public String getDsAddrHome() {
		return this.dsAddrHome;
	}

	public void setDsAddrHome(String dsAddrHome) {
		this.dsAddrHome = dsAddrHome;
	}

	public String getDsAddrReal() {
		return this.dsAddrReal;
	}

	public void setDsAddrReal(String dsAddrReal) {
		this.dsAddrReal = dsAddrReal;
	}

	public String getDsStrtCert() {
		return this.dsStrtCert;
	}

	public void setDsStrtCert(String dsStrtCert) {
		this.dsStrtCert = dsStrtCert;
	}

	public String getDsStrtHome() {
		return this.dsStrtHome;
	}

	public void setDsStrtHome(String dsStrtHome) {
		this.dsStrtHome = dsStrtHome;
	}

	public String getDsStrtReal() {
		return this.dsStrtReal;
	}

	public void setDsStrtReal(String dsStrtReal) {
		this.dsStrtReal = dsStrtReal;
	}

	public String getDtRegHome() {
		return this.dtRegHome;
	}

	public void setDtRegHome(String dtRegHome) {
		this.dtRegHome = dtRegHome;
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

	public String getNoPostCert() {
		return this.noPostCert;
	}

	public void setNoPostCert(String noPostCert) {
		this.noPostCert = noPostCert;
	}

	public String getNoPostHome() {
		return this.noPostHome;
	}

	public void setNoPostHome(String noPostHome) {
		this.noPostHome = noPostHome;
	}

	public String getNoPostReal() {
		return this.noPostReal;
	}

	public void setNoPostReal(String noPostReal) {
		this.noPostReal = noPostReal;
	}

	public String getNoReq() {
		return this.noReq;
	}

	public void setNoReq(String noReq) {
		this.noReq = noReq;
	}

	public String getNoSurety() {
		return this.noSurety;
	}

	public void setNoSurety(String noSurety) {
		this.noSurety = noSurety;
	}

	public String getNoTel1Home() {
		return this.noTel1Home;
	}

	public void setNoTel1Home(String noTel1Home) {
		this.noTel1Home = noTel1Home;
	}

	public String getNoTel2Home() {
		return this.noTel2Home;
	}

	public void setNoTel2Home(String noTel2Home) {
		this.noTel2Home = noTel2Home;
	}

	public String getNoTel3Home() {
		return this.noTel3Home;
	}

	public void setNoTel3Home(String noTel3Home) {
		this.noTel3Home = noTel3Home;
	}

}