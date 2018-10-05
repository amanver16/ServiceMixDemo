activemq.xml - jms port(61618)
org.ops4j.pax.web.cfg.empty.stub 
	org.osgi.service.http.port = 8183
system.properties
	org.osgi.service.http.port=8183
	activemq.port = 61618
	activemq.jmx.url = service:jmx:rmi:///jndi/rmi://localhost:1101/karaf-${karaf.name}
org.ops4j.pax.web.cfg
	org.osgi.service.http.port = 8183
org.apache.karaf.shell.cfg
	sshPort = 8103
org.apache.karaf.management.cfg
	rmiRegistryPort = 1101
	rmiServerPort = 44446
org.apache.cxf.wsn.cfg
	cxf.wsn.rootUrl = http://0.0.0.0:8084
org.apache.activemq.webconsole.cfg
	webconsole.jmx.url=service:jmx:rmi:///jndi/rmi://localhost:1101/karaf-${karaf.name}
com.stpl.loginms.cfg
	CXFserver = http://localhost:8991/
	service = assetMVC
	jmsUrl = tcp://localhost:61617
	jmsUser = smx
	jmsPassword = smx
	commandBus = loginMsCommandBus
	eventBus = loginMsEventBus
	
Urls
----
1) Web-console - http://localhost:8183/system/console/
2) MQ console - http://localhost:8183/activemqweb/

