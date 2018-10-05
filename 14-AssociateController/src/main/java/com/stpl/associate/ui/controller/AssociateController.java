package com.stpl.associate.ui.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stpl.associate.ui.bean.AssociateBean;
import com.stpl.associate.ui.service.AssociateService;

@Controller
@RequestMapping("/associate")
public class AssociateController {

	@Autowired
	private AssociateService associateService;

	public AssociateService getAssociateService() {
		return associateService;
	}

	public void setAssociateService(AssociateService associateService) {
		this.associateService = associateService;
	}

	@RequestMapping("/associatelist.json")
	public @ResponseBody List<AssociateBean> getassociateList() {
		return associateService.getAssociateList();
	}

	@RequestMapping(value = "/addAssociate", method = RequestMethod.POST)
	public @ResponseBody void addassociate(@RequestBody AssociateBean associate) {
		associateService.addAssociate(associate);
	}

	@RequestMapping(value = "/updateAssociate", method = RequestMethod.PUT)
	public @ResponseBody void updateassociate(@RequestBody AssociateBean associate) {
		associateService.updateAssociate(associate);
	}

	@RequestMapping(value = "/removeAssociate/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void removeassociate(@PathVariable("id") Long id) {
		associateService.deleteAssociate(id);
	}

	@RequestMapping(value = "/removeAllAssociate", method = RequestMethod.DELETE)
	public @ResponseBody void removeAllAssociate() {
		associateService.deleteAllAssociate();
	}

	@RequestMapping("/layout")
	public String getassociatePartialPage(ModelMap modelMap) {
		return "associate/layout";
	}
}
