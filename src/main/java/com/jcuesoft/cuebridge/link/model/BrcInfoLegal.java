package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the BRC_INFO_LEGAL database table.
 * 
 */
@Entity
@Table(name="BRC_INFO_LEGAL")
@NamedQuery(name="BrcInfoLegal.findAll", query="SELECT b FROM BrcInfoLegal b")
public class BrcInfoLegal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_CALL_AGENT")
	private String cdCallAgent;

	@Column(name="CD_CONTACT_AGENT")
	private String cdContactAgent;

	@Column(name="CD_CONTACT_CUST")
	private String cdContactCust;

	@Column(name="CD_INFO_COLLECT")
	private String cdInfoCollect;

	@Temporal(TemporalType.DATE)
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.DATE)
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="DS_CONTACT_AGENT")
	private String dsContactAgent;

	@Column(name="DS_CONTACT_CUST")
	private String dsContactCust;

	@Column(name="DT_AGENT")
	private String dtAgent;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_AGENT_CEO")
	private String nmAgentCeo;

	@Column(name="NM_AGENT_MANAGER")
	private String nmAgentManager;

	@Column(name="NM_CONTACT_AGENT")
	private String nmContactAgent;

	@Column(name="NO_REQ")
	private String noReq;

	public BrcInfoLegal() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdCallAgent() {
		return this.cdCallAgent;
	}

	public void setCdCallAgent(String cdCallAgent) {
		this.cdCallAgent = cdCallAgent;
	}

	public String getCdContactAgent() {
		return this.cdContactAgent;
	}

	public void setCdContactAgent(String cdContactAgent) {
		this.cdContactAgent = cdContactAgent;
	}

	public String getCdContactCust() {
		return this.cdContactCust;
	}

	public void setCdContactCust(String cdContactCust) {
		this.cdContactCust = cdContactCust;
	}

	public String getCdInfoCollect() {
		return this.cdInfoCollect;
	}

	public void setCdInfoCollect(String cdInfoCollect) {
		this.cdInfoCollect = cdInfoCollect;
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

	public String getDsContactAgent() {
		return this.dsContactAgent;
	}

	public void setDsContactAgent(String dsContactAgent) {
		this.dsContactAgent = dsContactAgent;
	}

	public String getDsContactCust() {
		return this.dsContactCust;
	}

	public void setDsContactCust(String dsContactCust) {
		this.dsContactCust = dsContactCust;
	}

	public String getDtAgent() {
		return this.dtAgent;
	}

	public void setDtAgent(String dtAgent) {
		this.dtAgent = dtAgent;
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

	public String getNmAgentCeo() {
		return this.nmAgentCeo;
	}

	public void setNmAgentCeo(String nmAgentCeo) {
		this.nmAgentCeo = nmAgentCeo;
	}

	public String getNmAgentManager() {
		return this.nmAgentManager;
	}

	public void setNmAgentManager(String nmAgentManager) {
		this.nmAgentManager = nmAgentManager;
	}

	public String getNmContactAgent() {
		return this.nmContactAgent;
	}

	public void setNmContactAgent(String nmContactAgent) {
		this.nmContactAgent = nmContactAgent;
	}

	public String getNoReq() {
		return this.noReq;
	}

	public void setNoReq(String noReq) {
		this.noReq = noReq;
	}

}