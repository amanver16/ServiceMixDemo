package com.stpl.servicemix.example.producer;

import java.util.Scanner;

import org.apache.activemq.broker.BrokerService;

public class Main {
    public static void main(String... args) throws Exception {
        
        Producer producer = new Producer();
        int x = 0;
        Scanner scanner = new Scanner(System.in);
        
        while(true) {
            Thread.sleep(1000);
            producer.produceMessage(x);
            x++;
            System.out.println("stop?");
            String s = scanner.next();
            System.out.println(s);
            if ( s.equals("y")){
            	System.out.println("inside if");
            	break;
            }
        }
        
        producer.close();
//        broker.stop();
    }
}
