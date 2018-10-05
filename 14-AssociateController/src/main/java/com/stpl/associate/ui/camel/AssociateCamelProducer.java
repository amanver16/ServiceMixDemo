package com.stpl.associate.ui.camel;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.cxf.helpers.IOUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stpl.associate.ui.bean.AssociateBean;

@Service
public class AssociateCamelProducer extends RouteBuilder {

	private ProducerTemplate addAssociateTemplate;
	private ConsumerTemplate readAllConsumerTemplate;

	private static final String ASSOCIATE_SERVICE_URL = "http://localhost:8989/rest/associateService/";

	@Override
	public void configure() throws Exception {
		addAssociateTemplate = getContext().createProducerTemplate();
		readAllConsumerTemplate = getContext().createConsumerTemplate();
	}

	public void addAssociate(AssociateBean associate) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			addAssociateTemplate.sendBody("direct-vm:addAssociate", mapper.writeValueAsString(associate));
		} catch (CamelExecutionException | JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public void deleteAssociate(long id) {
		addAssociateTemplate.sendBody("direct-vm:deleteAssociate", String.valueOf(id));
	}

	public void deleteAllAssociate() {
		addAssociateTemplate.sendBody("direct-vm:deleteAllAssociate", "");
	}

	public void updateAssociate(AssociateBean associate) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			addAssociateTemplate.sendBody("direct-vm:updateAssociate", mapper.writeValueAsString(associate));
		} catch (CamelExecutionException | JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public List<AssociateBean> getAllAssociate() {
		ObjectMapper mapper = new ObjectMapper();
		System.out.println("Seda start : ");
		String url = ASSOCIATE_SERVICE_URL + "associate/readAllAssociate/";
		HttpURLConnection connection;
		try {
			connection = connect(url);
			connection.setDoInput(true);
			InputStream stream = connection.getResponseCode() / 100 == 2 ? connection.getInputStream()
					: connection.getErrorStream();

			return mapper.readValue(IOUtils.toString(stream), List.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private HttpURLConnection connect(String url) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		return connection;
	}
}
