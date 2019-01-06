package com.donation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donation.entity.Designation;
import com.donation.entity.DesignationCode;
import com.donation.entity.Title;
import com.donation.entity.Suffix;
import com.donation.entity.ContactMethod;
import com.donation.repository.DesignationCodeRepository;
import com.donation.repository.TitleRepository;
import com.donation.repository.SuffixRepository;
import com.donation.repository.ContactMethodRepository;

@Service
public class DesignationCodeService {
	
	
	@Autowired
	DesignationCodeRepository  designationCodeRepository;
	@Autowired
	TitleRepository  titleRepository;
	@Autowired
	SuffixRepository  suffixRepository;
	@Autowired
	ContactMethodRepository  contactMethodRepository;
	
	public List <DesignationCode> getDesignationCode() {
		
		List <DesignationCode> shortListDesignationCode = new ArrayList <DesignationCode>();
		
//		List <DesignationCode> dbListDesignationCode = designationCodeRepository.findAll();
		List <DesignationCode> dbListDesignationCode = designationCodeRepository.findAllByOrderByDesignationnameAsc();

		dbListDesignationCode.stream().forEach(designation -> shortListDesignationCode
		          .add(new DesignationCode(designation.getDesignationid(), designation.getDesignationname(),
		        		  				   designation.getGlaccountnumber(), designation.getGlbusinessunit(), designation.getGldepartmentnumber()
		        		  				   )));
		return shortListDesignationCode;
	}

	public List<Title> getTitle() {
		// TODO Auto-generated method stub
		List<Title> shortListTitle = new ArrayList <Title>();
		
		List<Title> dbListTitle = titleRepository.findAll();


		dbListTitle.stream().forEach(title -> shortListTitle
		          .add(new Title(title.getTitleid(), title.getDisplayvalue()
	        		  				   )));
		return shortListTitle;
	}
	
	

	public List<Suffix> getSuffix() {
		// TODO Auto-generated method stub
		List<Suffix> shortListSuffix = new ArrayList <Suffix>();
		
		List<Suffix> dbListSuffix = suffixRepository.findAll();


		dbListSuffix.stream().forEach(suffix -> shortListSuffix
		          .add(new Suffix(suffix.getSuffixid(), suffix.getDisplayvalue()
	        		  				   )));
		return shortListSuffix;
	}
	
	public List<ContactMethod> getContactMethod() {
		// TODO Auto-generated method stub
		List<ContactMethod> shortListContactMethod = new ArrayList <ContactMethod>();
		
		List<ContactMethod> dbListContactMethod = contactMethodRepository.findAll();

//		for(ContactMethod dbList : dbListContactMethod) {
//			shortListContactMethod.add(dbList.getDisplayvalue());
//		}

		dbListContactMethod.stream().forEach(contactMethod -> shortListContactMethod
		          .add(new ContactMethod(contactMethod.getContactmethodid(), contactMethod.getDisplayvalue()
	        		  				   )));
		return shortListContactMethod;
	}
	
	
}
