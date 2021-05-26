package com.octank.coveragesvc.services;

import java.util.ArrayList;
import java.util.List;

public class CoverageDetails {

	List<Coverage> coverages=new ArrayList<>();
	
    private Integer policyNumber;
    
    private String message;
    


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(Integer policyNumber) {
		this.policyNumber = policyNumber;
	}

	public List<Coverage> getCoverages() {
		return coverages;
	}

	public void setCoverages(List<Coverage> coverages) {
		this.coverages = coverages;
	}
	
}
