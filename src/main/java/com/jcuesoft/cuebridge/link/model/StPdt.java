package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ST_PDT database table.
 * 
 */
@Entity
@Table(name="ST_PDT")
@NamedQuery(name="StPdt.findAll", query="SELECT s FROM StPdt s")
public class StPdt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_COOPERATE")
	private String cdCooperate;

	@Column(name="CD_KND_PAY")
	private String cdKndPay;

	@Column(name="CD_PDT")
	private String cdPdt;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="ID_PDT_UID")
	private String idPdtUid;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_PDT")
	private String nmPdt;

	@Column(name="NO_MONTH_PAY")
	private BigDecimal noMonthPay;

	@Column(name="RT_DLY")
	private BigDecimal rtDly;

	@Column(name="RT_MONTH_PAY")
	private BigDecimal rtMonthPay;

	@Column(name="RT_NOR")
	private BigDecimal rtNor;

	@Column(name="YN_USE")
	private String ynUse;

	public StPdt() {
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

	public String getCdKndPay() {
		return this.cdKndPay;
	}

	public void setCdKndPay(String cdKndPay) {
		this.cdKndPay = cdKndPay;
	}

	public String getCdPdt() {
		return this.cdPdt;
	}

	public void setCdPdt(String cdPdt) {
		this.cdPdt = cdPdt;
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

	public String getIdPdtUid() {
		return this.idPdtUid;
	}

	public void setIdPdtUid(String idPdtUid) {
		this.idPdtUid = idPdtUid;
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

	public String getNmPdt() {
		return this.nmPdt;
	}

	public void setNmPdt(String nmPdt) {
		this.nmPdt = nmPdt;
	}

	public BigDecimal getNoMonthPay() {
		return this.noMonthPay;
	}

	public void setNoMonthPay(BigDecimal noMonthPay) {
		this.noMonthPay = noMonthPay;
	}

	public BigDecimal getRtDly() {
		return this.rtDly;
	}

	public void setRtDly(BigDecimal rtDly) {
		this.rtDly = rtDly;
	}

	public BigDecimal getRtMonthPay() {
		return this.rtMonthPay;
	}

	public void setRtMonthPay(BigDecimal rtMonthPay) {
		this.rtMonthPay = rtMonthPay;
	}

	public BigDecimal getRtNor() {
		return this.rtNor;
	}

	public void setRtNor(BigDecimal rtNor) {
		this.rtNor = rtNor;
	}

	public String getYnUse() {
		return this.ynUse;
	}

	public void setYnUse(String ynUse) {
		this.ynUse = ynUse;
	}

}