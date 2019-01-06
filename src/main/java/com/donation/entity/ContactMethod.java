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
@Table(name = "contactmethod")
@Getter
@ToString
public class ContactMethod {
	
	@Id
	@Column(name = "contactmethodid")
	private final Integer contactmethodid;
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
	
	public ContactMethod(	Integer contactmethodid, String displayvalue, Integer active,
			String createby , Date createdate, String updateby, Date  updatedate, Integer sortorder){
		this.contactmethodid = contactmethodid;
		this.displayvalue = displayvalue;
		this.active = active;
		this.createby = createby;
		this.createdate = createdate;
		this.updateby = updateby;
		this.updatedate= updatedate;
		this.sortorder = sortorder;
	}
	
	public ContactMethod() {
	    this(null,null,null,null,null,null,null,null);
	  }
	
	public ContactMethod(Integer contactmethodid, String displayvalue) {
	    this(contactmethodid,displayvalue,null, null, null, null,null,null);
	  }
	
}