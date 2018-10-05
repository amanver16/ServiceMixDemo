package com.stpl.servicemix.example.camelosgi.banking;

import java.util.Random;

public class MockBankingService implements BankingService {

    private final Random rand = new Random();

    public void pay(Payment payment) throws PaymentException {
		if (rand.nextDouble() > 0.9) {
            throw new PaymentException("Banking services are offline, try again later!");
        }

        System.out.println("Processing payment " + payment);
		
	}

    

}
