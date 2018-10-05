package com.stpl.gtn.item.domain.query.handler;

import java.io.IOException;
import java.util.List;

import com.stpl.gtn.item.domain.query.data.bean.ItemMsItemBean;
import com.stpl.gtn.item.domain.query.data.bean.ItemMsSearchInputBean;
import com.stpl.gtn.item.domain.query.mongo.service.ItemMsQueryMongoService;
import com.stpl.gtn.item.domain.service.ItemMsJsonService;

public class ItemMsDomainQueryGatewayHandler {

	private ItemMsQueryMongoService domainQueryDbSevice;

	public void setDomainQueryDbSevice(ItemMsQueryMongoService domainQueryDbSevice) {
		this.domainQueryDbSevice = domainQueryDbSevice;
	}

	public long getItemCount(String searchInput) throws IOException {
		ItemMsSearchInputBean convertedSearchInput = (ItemMsSearchInputBean) ItemMsJsonService
				.convertJsonToObject(ItemMsSearchInputBean.class, searchInput);
		return domainQueryDbSevice.queryItemCount(convertedSearchInput.getSearchCriteriaList(),
				convertedSearchInput.getStart(), convertedSearchInput.getOffset());
	}

	public List<ItemMsItemBean> getItem(String searchInput) throws IOException {
		ItemMsSearchInputBean convertedSearchInput = (ItemMsSearchInputBean) ItemMsJsonService
				.convertJsonToObject(ItemMsSearchInputBean.class, searchInput);
		return domainQueryDbSevice.queryItem(convertedSearchInput.getSearchCriteriaList(),
				convertedSearchInput.getStart(), convertedSearchInput.getOffset());
	}

	public List<String> getDomainConstant(String domainConstantName) {
		return domainQueryDbSevice.getDomainConstantList(domainConstantName);
	}

	public boolean getDuplicateItem(String aggregateId) {
		return domainQueryDbSevice.getDuplicateItem(aggregateId);
	}

	public ItemMsItemBean getItemDetails(String aggregateId) {
		return domainQueryDbSevice.getItemDetails(aggregateId);
	}

}
