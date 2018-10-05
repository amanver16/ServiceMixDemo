package com.stpl.servicemix.example.camelosgi.invoice;

import org.springframework.stereotype.Component;

import com.stpl.servicemix.example.camelosgi.banking.Payment;
import com.stpl.servicemix.example.camelosgi.banking.PaymentCreator;
import com.stpl.servicemix.example.camelosgi.banking.PaymentException;


@Component
public class ForeignPaymentCreator implements PaymentCreator {

    // hard coded account value for demo purposes
    private static final String CURRENT_IBAN_ACC = "current-iban-acc";

    public Payment createPayment(Invoice invoice) throws PaymentException {
        if (null == invoice.getIban()) {
            throw new PaymentException("IBAN mustn't be null when creating foreign payment!");
        }

        return new Payment(CURRENT_IBAN_ACC, invoice.getIban(), invoice.getDollars());
    }

}
