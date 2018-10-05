package com.stpl.associate.ui.cxf;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/associateService/")
public class AssociateCxfService {

	@GET
	@Path("/associate/readAllAssociate")
	@Produces("application/json")
	public String readAllAssociate() {
		return null;
	}

}
