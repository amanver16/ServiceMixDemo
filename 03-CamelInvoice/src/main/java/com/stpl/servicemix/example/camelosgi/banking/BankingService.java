package com.stpl.servicemix.example.camelosgi.banking;

public interface BankingService {
	void pay(Payment payment) throws PaymentException;
}
