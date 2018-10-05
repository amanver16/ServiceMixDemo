package com.stpl.servicemix.example.producer;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.spring.ActiveMQConnectionFactory;

public class Producer {
    
    private Connection connection;

    public Producer() throws JMSException {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://10.4.69.103:61616");
        connectionFactory.setUserName("smx");
        connectionFactory.setPassword("smx");
        connection = connectionFactory.createQueueConnection();
        connection.start();

        // Clean up
//      connection.close();

    }
    
    public void close() throws JMSException{
    	connection.close();
    }
    
    public void produceMessage(int x) {
        try {
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            
            // Create the destination
            
            Destination destination = session.createQueue("putMessage");
//            Destination destination = session.createTopic("Testtopic");
            
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            
            // Create a messages
            String text = "Hello world " + x + "! Standalone: " + Thread.currentThread().getName() + " : " ;
            text = text + ":hashcode:" +  text.hashCode();
            TextMessage message = session.createTextMessage(text);
    
            // Tell the producer to send the message
            System.out.println("Sent message: "+ message.getText());

            producer.send(message);
            session.close();
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
