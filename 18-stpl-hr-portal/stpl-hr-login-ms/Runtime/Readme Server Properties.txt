activemq.xml - jms port(61617)
org.ops4j.pax.web.cfg.empty.stub 
	org.osgi.service.http.port = 8182
system.properties
	org.osgi.service.http.port=8182
	activemq.port = 61617
	activemq.jmx.url = service:jmx:rmi:///jndi/rmi://localhost:1100/karaf-${karaf.name}
org.ops4j.pax.web.cfg
	org.osgi.service.http.port = 8182
org.apache.karaf.shell.cfg
	sshPort = 8102
org.apache.karaf.management.cfg
	rmiRegistryPort = 1100
	rmiServerPort = 44445
org.apache.cxf.wsn.cfg
	cxf.wsn.rootUrl = http://0.0.0.0:8083
org.apache.activemq.webconsole.cfg
	webconsole.jmx.url=service:jmx:rmi:///jndi/rmi://localhost:1100/karaf-${karaf.name}
com.stpl.loginms.cfg
	CXFserver = http://localhost:8989/
	service = loginMVC
	jmsUrl = tcp://localhost:61617
	jmsUser = smx
	jmsPassword = smx
	commandBus = loginMsCommandBus
	eventBus = loginMsEventBus
	checkUserCredentials = checkUserCredentials
	updatePassword = updatePassword
	getAggregateIdByUserName = getAggregateIdByUserName
	getAggregateEvents = getAggregateEvents

