package com.donation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by Vijay on 9/19/2017.
 */
@Entity
@Table(name = "designation")
@Getter
@ToString
public class DesignationCode {
	
	@Id
	@Column(name = "designationid")
	private final Integer designationid;
	@Column(name = "designationname")
	private final String designationname;
	@Column(name = "active")
	private final Integer active;
	@Column(name = "shortdescription")
	private final String shortdescription;
	@Column(name = "longdescription")
	private final String longdescription;
	@Column(name = "glbusinessunit")
	private final String glbusinessunit;
	@Column(name = "glaccountnumber")
	private final String glaccountnumber;
	@Column(name = "gldepartmentnumber")
	private final  String gldepartmentnumber;
	@Column(name = "createby")
	private final  String createby;
	@Column(name = "createdate")
	private final Date createdate;
	@Column(name = "updateby")
	private final String updateby;
	@Column(name = "updatedate")
	private final  Date  updatedate;
	@Column(name = "parentdesignationid")
	private final  Integer parentdesignationid;
	@Column(name = "sortorder")
	private final  Integer sortorder;

	
	public DesignationCode(	Integer designationid, String designationname, Integer active, String shortdescription, String longdescription,
			String glbusinessunit,  String glaccountnumber, String gldepartmentnumber ,String createby , Date createdate, String updateby,
			Date  updatedate, Integer parentdesignationid, Integer sortorder) {
		this.designationid = designationid;
		this.designationname = designationname;
		this.active = active;
		this.shortdescription = shortdescription;
		this.longdescription = longdescription;
		this.glbusinessunit = glbusinessunit;
		this.glaccountnumber = glaccountnumber;
		this.gldepartmentnumber = gldepartmentnumber;
		this.createby = createby;
		this.createdate = createdate;
		this.updateby = updateby;
		this.updatedate= updatedate;
		this.parentdesignationid = parentdesignationid;
		this.sortorder = sortorder;
		
	}
	
	public DesignationCode() {
	    this(null,null,null,null,null,null,null,null,null,null,null,null,null,null);
	  }
	
	public DesignationCode(Integer designationid, String designationname,String glbusinessunit,  String glaccountnumber, String gldepartmentnumber) {
	    this(designationid,designationname,null, null, null, glbusinessunit,glaccountnumber,gldepartmentnumber,null,null,null,null,null,null);
	  }
}
