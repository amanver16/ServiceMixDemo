package com.stpl.gtn.company.domain.query.mongo.service;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsAddressBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyIdentifierBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyInformationBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsCompanyTradeClassBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsDomainConstant;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsParentCompanyBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchBean;
import com.stpl.gtn.company.domain.query.data.bean.CompanyMsSearchCriteria;

public class CompanyMsQueryMongoService {

	private final MongoClient mongoClient;
	private final MongoDatabase mongoDb;

	public CompanyMsQueryMongoService(CompanyMsMongoConnectionBean connectionBean) {

		CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

		ServerAddress address = new ServerAddress(connectionBean.getHostName(), connectionBean.getPortNo());

		this.mongoClient = new MongoClient(address,
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		this.mongoDb = this.mongoClient.getDatabase(connectionBean.getDbName());
	}

	public void destroy() {
		this.mongoClient.close();
	}

	public void insertCompany(CompanyMsCompanyBean companyBean) {

		MongoCollection<CompanyMsCompanyBean> collection = mongoDb.getCollection("TGTN_COMPANY_QUERY",
				CompanyMsCompanyBean.class);
		collection.insertOne(companyBean);
		insertSearchCompany(companyBean);
	}

	public void deleteCompany(CompanyMsCompanyBean companyBean) {

		MongoCollection<CompanyMsCompanyBean> collection = mongoDb.getCollection("TGTN_COMPANY_QUERY",
				CompanyMsCompanyBean.class);
		collection.deleteOne(new Document("aggregateId", companyBean.getAggregateId()));
		deleteSearchCompany(companyBean);
	}

	public void updateCompany(CompanyMsCompanyBean companyBean) {

		MongoCollection<CompanyMsCompanyBean> collection = mongoDb.getCollection("TGTN_COMPANY_QUERY",
				CompanyMsCompanyBean.class);
		collection.deleteOne(new Document("aggregateId", companyBean.getAggregateId()));
		collection.insertOne(companyBean);
		updateSearchCompany(companyBean);
	}

	private void insertSearchCompany(CompanyMsCompanyBean companyBean) {

		MongoCollection<CompanyMsSearchBean> collection = mongoDb.getCollection("TGTN_COMPANY_SEARCH_QUERY",
				CompanyMsSearchBean.class);
		collection.insertMany(getSearchCompanyBeanList(companyBean));
	}

	private void deleteSearchCompany(CompanyMsCompanyBean companyBean) {

		MongoCollection<CompanyMsSearchBean> collection = mongoDb.getCollection("TGTN_COMPANY_SEARCH_QUERY",
				CompanyMsSearchBean.class);
		collection.deleteMany(new Document("aggregateId", companyBean.getAggregateId()));
	}

	private void updateSearchCompany(CompanyMsCompanyBean companyBean) {

		MongoCollection<CompanyMsSearchBean> collection = mongoDb.getCollection("TGTN_COMPANY_SEARCH_QUERY",
				CompanyMsSearchBean.class);
		collection.deleteMany(new Document("aggregateId", companyBean.getAggregateId()));
		collection.insertMany(getSearchCompanyBeanList(companyBean));
	}

	public List<CompanyMsSearchBean> queryCompany(List<CompanyMsSearchCriteria> searchCriteriaList, int start,
			int offset) {
		MongoCollection<CompanyMsSearchBean> collection = mongoDb.getCollection("TGTN_COMPANY_SEARCH_QUERY",
				CompanyMsSearchBean.class);
		Document searchInput = new Document();
		if (!searchCriteriaList.isEmpty()) {
			for (CompanyMsSearchCriteria companyMsSearchCriteria : searchCriteriaList) {
				searchInput.append(companyMsSearchCriteria.getFieldId(), companyMsSearchCriteria.getFilterValue1());
			}

			MongoIterable<CompanyMsSearchBean> results = collection.find(searchInput, CompanyMsSearchBean.class)
					.skip(start).limit(offset).batchSize(offset);
			List<CompanyMsSearchBean> companyBean = new ArrayList<>();
			for (CompanyMsSearchBean companyMsSearchBean : results) {
				companyBean.add(companyMsSearchBean);
			}
			return companyBean;
		}
		return null;

	}

	public long queryCompanyCount(List<CompanyMsSearchCriteria> searchCriteriaList, int start, int offset) {
		MongoCollection<CompanyMsSearchBean> collection = mongoDb.getCollection("TGTN_COMPANY_SEARCH_QUERY",
				CompanyMsSearchBean.class);
		Document searchInput = new Document();
		if (!searchCriteriaList.isEmpty()) {
			for (CompanyMsSearchCriteria companyMsSearchCriteria : searchCriteriaList) {
				searchInput.append(companyMsSearchCriteria.getFieldId(),
						companyMsSearchCriteria.getFilterValue1());
			}

			return collection.count(searchInput);
		}
		return 0l;
	}

	public List<String> getDomainConstantList(String domainConstantName) {
		MongoCollection<CompanyMsDomainConstant> collection = mongoDb.getCollection("TGTN_DOMAIN_CONSTANT_QUERY",
				CompanyMsDomainConstant.class);
		Document searchInput = new Document("domainConstantName", domainConstantName);
		MongoIterable<CompanyMsDomainConstant> results = collection.find(searchInput, CompanyMsDomainConstant.class);

		for (CompanyMsDomainConstant companyMsDomainConstant : results) {
			return companyMsDomainConstant.getDomainConstantList();
		}

		return null;
	}

	public CompanyMsCompanyBean getCompanyDetails(String aggregateId) {
		MongoCollection<CompanyMsCompanyBean> collection = mongoDb.getCollection("TGTN_COMPANY_QUERY",
				CompanyMsCompanyBean.class);
		Document searchInput = new Document("aggregateId", aggregateId);
		MongoIterable<CompanyMsCompanyBean> results = collection.find(searchInput, CompanyMsCompanyBean.class);

		for (CompanyMsCompanyBean companyMsCompanyBean : results) {
			return companyMsCompanyBean;
		}

		return null;
	}

	public List<CompanyMsCompanyIdentifierBean> getCompanyIdentifier() {
		MongoCollection<CompanyMsCompanyIdentifierBean> collection = mongoDb
				.getCollection("TGTN_COMPANY_IDENTIFIER_QUERY", CompanyMsCompanyIdentifierBean.class);
		MongoIterable<CompanyMsCompanyIdentifierBean> results = collection.find(CompanyMsCompanyIdentifierBean.class);

		List<CompanyMsCompanyIdentifierBean> retResult = new ArrayList<>();
		for (CompanyMsCompanyIdentifierBean companyIdentifier : results) {
			retResult.add(companyIdentifier);
		}

		return retResult;
	}

	public List<CompanyMsCompanyTradeClassBean> getCompanyTradeClass() {
		MongoCollection<CompanyMsCompanyTradeClassBean> collection = mongoDb
				.getCollection("TGTN_COMPANY_TRADE_CLASS_QUERY", CompanyMsCompanyTradeClassBean.class);
		MongoIterable<CompanyMsCompanyTradeClassBean> results = collection.find(CompanyMsCompanyTradeClassBean.class);

		List<CompanyMsCompanyTradeClassBean> retResult = new ArrayList<>();
		for (CompanyMsCompanyTradeClassBean companyIdentifier : results) {
			retResult.add(companyIdentifier);
		}

		return retResult;
	}

	public boolean getDuplicateCompany(String aggregateId) {
		MongoCollection<CompanyMsCompanyBean> collection = mongoDb.getCollection("TGTN_COMPANY_QUERY",
				CompanyMsCompanyBean.class);
		Document searchInput = new Document("aggregateId", aggregateId);
		MongoIterable<CompanyMsCompanyBean> results = collection.find(searchInput, CompanyMsCompanyBean.class);
		if (results.iterator().hasNext()) {
			return true;
		}

		return false;
	}

	private List<CompanyMsSearchBean> getSearchCompanyBeanList(CompanyMsCompanyBean companyBean) {

		List<CompanyMsSearchBean> searchBeanList = new ArrayList<>();
		for (CompanyMsCompanyIdentifierBean identifierBean : companyBean.getCompanyIdentifierBean()) {
			for (CompanyMsCompanyTradeClassBean tradeClassBean : companyBean.getCompanyTradeClassBean()) {
				for (CompanyMsParentCompanyBean parentCompanyBean : companyBean.getParentCompanyBean()) {
					CompanyMsSearchBean searchBean = new CompanyMsSearchBean();
					searchBean.setAggregateId(companyBean.getAggregateId());
					fillCompanyInformation(searchBean, companyBean.getCompanyInformationBean());
					fillCompanyAddress(searchBean, companyBean.getAddressBean());
					fillCompanyIdentifier(searchBean, identifierBean);
					fillCompanyTradeClass(searchBean, tradeClassBean);
					fillParentCompany(searchBean, parentCompanyBean);
					searchBeanList.add(searchBean);
				}
			}
		}

		return searchBeanList;
	}

	private void fillCompanyInformation(CompanyMsSearchBean searchBean,
			CompanyMsCompanyInformationBean inormationBean) {
		searchBean.setCompanyCategory(inormationBean.getCompanyCategory());
		searchBean.setCompanyEndDate(inormationBean.getCompanyEndDate());
		searchBean.setCompanyGroup(inormationBean.getCompanyGroup());
		searchBean.setCompanyId(inormationBean.getCompanyId());
		searchBean.setCompanyName(inormationBean.getCompanyName());
		searchBean.setCompanyNo(inormationBean.getCompanyNo());
		searchBean.setCompanyStartDate(inormationBean.getCompanyStartDate());
		searchBean.setCompanyStatus(inormationBean.getCompanyStatus());
		searchBean.setCompanyType(inormationBean.getCompanyType());
		searchBean.setCreatedBy(inormationBean.getCreatedBy());
		searchBean.setCreatedDate(inormationBean.getCreatedDate());
		searchBean.setModifiedBy(inormationBean.getModifiedBy());
		searchBean.setModifiedDate(inormationBean.getModifiedDate());
		searchBean.setOrganizationKey(inormationBean.getOrganizationKey());
		searchBean.setSource(inormationBean.getSource());
		searchBean.setSystemId(inormationBean.getSystemId());
		searchBean.setUdc1(inormationBean.getUdc1());
		searchBean.setUdc2(inormationBean.getUdc2());
		searchBean.setUdc3(inormationBean.getUdc3());
		searchBean.setUdc4(inormationBean.getUdc4());
		searchBean.setUdc5(inormationBean.getUdc5());
		searchBean.setUdc6(inormationBean.getUdc6());
	}

	private void fillCompanyAddress(CompanyMsSearchBean searchBean, CompanyMsAddressBean addressBean) {
		searchBean.setAddress1(addressBean.getAddress1());
		searchBean.setAddress1(addressBean.getAddress2());
		searchBean.setAddress1(addressBean.getCity());
		searchBean.setAddress1(addressBean.getCountry());
		searchBean.setAddress1(addressBean.getRegionCode());
		searchBean.setAddress1(addressBean.getState());
		searchBean.setAddress1(addressBean.getZipCode());
	}

	private void fillCompanyIdentifier(CompanyMsSearchBean searchBean, CompanyMsCompanyIdentifierBean identifierBean) {
		searchBean.setCompanyIdentifier(identifierBean.getCompanyIdentifier());
		searchBean.setCompanyQualifierName(identifierBean.getCompanyQualifierName());
	}

	private void fillCompanyTradeClass(CompanyMsSearchBean searchBean, CompanyMsCompanyTradeClassBean tradeClassBean) {
		searchBean.setTradeClass(tradeClassBean.getTradeClass());
		searchBean.setTradeClassEndDate(tradeClassBean.getTradeClassEndDate());
		searchBean.setTradeClassStartDate(tradeClassBean.getTradeClassStartDate());
	}

	private void fillParentCompany(CompanyMsSearchBean searchBean, CompanyMsParentCompanyBean parentCompanyBean) {
		searchBean.setParentCompanyEndDate(parentCompanyBean.getParentCompanyEndDate());
		searchBean.setParentCompanyNo(parentCompanyBean.getParentCompanyNo());
		searchBean.setParentCompanyStartDate(parentCompanyBean.getParentCompanyStartDate());
	}

}
