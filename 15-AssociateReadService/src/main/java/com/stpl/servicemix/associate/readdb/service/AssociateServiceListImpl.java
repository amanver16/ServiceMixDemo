package com.stpl.servicemix.associate.readdb.service;

import java.util.ArrayList;
import java.util.List;

import com.stpl.associate.ui.bean.AssociateBean;

public class AssociateServiceListImpl {

	private List<AssociateBean> associateList = new ArrayList<>();
	private long associateId = 0l;

	public List<AssociateBean> getAssociateList() {
		return associateList;
	}

	public boolean addAssociate(AssociateBean associateInputBean) {
		associateInputBean.setAssociateId(++associateId);
		return associateList.add(associateInputBean);
	}

	public boolean updateAssociate(AssociateBean associateInputBean) {
		for (AssociateBean associateBean : associateList) {
			if (associateBean.getAssociateId() == associateInputBean.getAssociateId()) {
				associateBean.setAssociateName(associateInputBean.getAssociateName());
				return true;
			}
		}
		return false;
	}

	public boolean deleteAssociate(long associateId) {
		AssociateBean deleteBean = null;
		for (AssociateBean associateBean : associateList) {
			if (associateBean.getAssociateId() == associateId) {
				deleteBean = associateBean;
				break;
			}
		}
		return associateList.remove(deleteBean);
	}

	public void deleteAllAssociate() {
		associateList.clear();
	}

}
