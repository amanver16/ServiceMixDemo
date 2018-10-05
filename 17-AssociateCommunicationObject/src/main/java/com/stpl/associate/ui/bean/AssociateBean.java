package com.stpl.associate.ui.bean;

import java.io.Serializable;

public class AssociateBean implements Serializable {

	private static final long serialVersionUID = -6894407282927864766L;

	private long associateId;
	private String associateName;
	private String address;
	private long mobile;
	private String email;

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(long associateId) {
		this.associateId = associateId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "AssociateBean [associateId=" + associateId + ", associateName=" + associateName + ", address=" + address
				+ ", mobile=" + mobile + ", email=" + email + "]";
	}

}
