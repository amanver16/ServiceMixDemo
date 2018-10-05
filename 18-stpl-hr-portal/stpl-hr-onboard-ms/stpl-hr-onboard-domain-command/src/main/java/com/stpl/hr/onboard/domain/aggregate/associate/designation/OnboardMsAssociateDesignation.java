package com.stpl.hr.onboard.domain.aggregate.associate.designation;

public enum OnboardMsAssociateDesignation {

	Trainee("Trainee", 0), Analyst("Analyst", 1), Associate("Associate", 3), AssociateLead("AssociateLead",
			5), Manager("Manager", 10);

	private OnboardMsAssociateDesignation(String designation, float minYearsOfExperience) {
		this.designation = designation;
		this.minYearsOfExperience = minYearsOfExperience;
	}

	private String designation;
	private float minYearsOfExperience;

	public String getDesignation() {
		return designation;
	}

	public float getMinYearsOfExperience() {
		return minYearsOfExperience;
	}

	public boolean validateDesignation(String yearsOfExperienceStr) {
		float yearsOfExperience = Float.parseFloat(yearsOfExperienceStr);
		return yearsOfExperience >= minYearsOfExperience;
	}

}
