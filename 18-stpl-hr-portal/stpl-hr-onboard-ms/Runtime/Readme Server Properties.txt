activemq.xml - jms port(61616)
org.ops4j.pax.web.cfg.empty.stub 
	org.osgi.service.http.port = 8181
system.properties
	org.osgi.service.http.port=8181
	activemq.port = 61616
	activemq.jmx.url = service:jmx:rmi:///jndi/rmi://localhost:1099/karaf-${karaf.name}
org.ops4j.pax.web.cfg
	org.osgi.service.http.port = 8181
org.apache.karaf.shell.cfg
	sshPort = 8101
org.apache.karaf.management.cfg
	rmiRegistryPort = 1099
	rmiServerPort = 44444
org.apache.cxf.wsn.cfg
	cxf.wsn.rootUrl = http://0.0.0.0:8082
org.apache.activemq.webconsole.cfg
	webconsole.jmx.url=service:jmx:rmi:///jndi/rmi://localhost:1099/karaf-${karaf.name}
com.stpl.loginms.cfg
	CXFserver = http://localhost:8990/
	service = loginMVC
	jmsUrl = tcp://localhost:61617
	jmsUser = smx
	jmsPassword = smx
	commandBus = loginMsCommandBus
	eventBus = loginMsEventBus
	queryAllAggregateDatails = getAllAggregate
	queryAssociateDetails =	getAssociateDetails
	queryLatestOnboardAssociateId =	getOnboardLatestAssociateId
	queryOnboardAggregateEvents = getOnboardAggregateEvents
	onboardAssociate = onboardCandidate
	queryAllAssociate = getAllAssociate


