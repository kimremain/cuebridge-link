package com.jcuesoft.cuebridge.link.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ST_GROUP_CODE database table.
 * 
 */
@Entity
@Table(name="ST_GROUP_CODE")
@NamedQuery(name="StGroupCode.findAll", query="SELECT s FROM StGroupCode s")
public class StGroupCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="\"NO\"")
	private long no;

	@Column(name="CD_GROUP")
	private String cdGroup;

	@Column(name="CD_PARENT_GROUP")
	private String cdParentGroup;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_REG")
	private Date dmReg;

	@Temporal(TemporalType.TIMESTAMP )
	@Column(name="DM_UPT")
	private Date dmUpt;

	@Column(name="ID_REG")
	private String idReg;

	@Column(name="ID_UPT")
	private String idUpt;

	@Column(name="NM_GROUP")
	private String nmGroup;

	public StGroupCode() {
	}

	public long getNo() {
		return this.no;
	}

	public void setNo(long no) {
		this.no = no;
	}

	public String getCdGroup() {
		return this.cdGroup;
	}

	public void setCdGroup(String cdGroup) {
		this.cdGroup = cdGroup;
	}

	public String getCdParentGroup() {
		return this.cdParentGroup;
	}

	public void setCdParentGroup(String cdParentGroup) {
		this.cdParentGroup = cdParentGroup;
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

	public String getNmGroup() {
		return this.nmGroup;
	}

	public void setNmGroup(String nmGroup) {
		this.nmGroup = nmGroup;
	}

}