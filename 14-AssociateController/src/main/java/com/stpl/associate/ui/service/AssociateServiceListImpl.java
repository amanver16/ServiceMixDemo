package com.stpl.associate.ui.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stpl.associate.ui.bean.AssociateBean;
import com.stpl.associate.ui.camel.AssociateCamelProducer;

@Service("associateService")
public class AssociateServiceListImpl implements AssociateService {

	@Autowired
	private AssociateCamelProducer associateCamelProducer;

	public AssociateCamelProducer getAssociateCamelProducer() {
		return associateCamelProducer;
	}

	public void setAssociateCamelProducer(AssociateCamelProducer associateCamelProducer) {
		this.associateCamelProducer = associateCamelProducer;
	}

	@Override
	public List<AssociateBean> getAssociateList() {
		return associateCamelProducer.getAllAssociate();
	}

	@Override
	public boolean addAssociate(AssociateBean associateInputBean) {
		associateCamelProducer.addAssociate(associateInputBean);
		return true;
	}

	@Override
	public boolean updateAssociate(AssociateBean associateInputBean) {
		associateCamelProducer.updateAssociate(associateInputBean);
		return true;
	}

	@Override
	public boolean deleteAssociate(long associateId) {
		associateCamelProducer.deleteAssociate(associateId);
		return true;
	}

	@Override
	public void deleteAllAssociate() {
		associateCamelProducer.deleteAllAssociate();
	}

}
