package com.stpl.servicemix.example.camelosgi.invoice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stpl.servicemix.example.camelosgi.banking.BankingService;
import com.stpl.servicemix.example.camelosgi.banking.Payment;
import com.stpl.servicemix.example.camelosgi.banking.PaymentException;


/**
 * Endpoint that picks Payments from the system and dispatches them to the
 * service provided by bank.
 */
@Component
public class PaymentProcessor {

    @Autowired
    BankingService bankingService;

    public void processPayment(Payment payment) throws PaymentException {
        bankingService.pay(payment);
    }

}
