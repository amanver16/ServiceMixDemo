package com.stpl.associate.ui.service;

import java.util.List;

import com.stpl.associate.ui.bean.AssociateBean;

public interface AssociateService {
	public List<AssociateBean> getAssociateList();

	public boolean addAssociate(AssociateBean associateBean);

	public boolean updateAssociate(AssociateBean associateBean);

	public boolean deleteAssociate(long id);

	public void deleteAllAssociate();
}
