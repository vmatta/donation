package com.donation.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.ToString;

/**
 * Created by Vijay on 9/19/2017.
 */
@Entity
@Table(name = "suffix")
@Getter
@ToString
public class Suffix {
	
	@Id
	@Column(name = "suffixid")
	private final Integer suffixid;
	@Column(name = "displayvalue")
	private final String displayvalue;
	@Column(name = "active")
	private final Integer active;
	@Column(name = "createby")
	private final String createby;
	@Column(name = "createdate")
	private final Date createdate;
	@Column(name = "updateby")
	private final String updateby;
	@Column(name = "updatedate")
	private final  Date  updatedate;
	@Column(name = "sortorder")
	private final  Integer sortorder;
	
	public Suffix(	Integer suffixid, String displayvalue, Integer active,
			String createby , Date createdate, String updateby, Date  updatedate, Integer sortorder){
		this.suffixid = suffixid;
		this.displayvalue = displayvalue;
		this.active = active;
		this.createby = createby;
		this.createdate = createdate;
		this.updateby = updateby;
		this.updatedate= updatedate;
		this.sortorder = sortorder;
	}
	
	public Suffix() {
	    this(null,null,null,null,null,null,null,null);
	  }
	
	public Suffix(Integer suffixid, String displayvalue) {
	    this(suffixid,displayvalue,null, null, null, null,null,null);
	  }
	
}