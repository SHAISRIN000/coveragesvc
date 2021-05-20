package com.octank.coveragesvc.services;

import java.util.ArrayList;
import java.util.List;

public class CoverageDetails {

	List<Coverage> coverages=new ArrayList<>();

	public List<Coverage> getCoverages() {
		return coverages;
	}

	public void setCoverages(List<Coverage> coverages) {
		this.coverages = coverages;
	}
	
}
