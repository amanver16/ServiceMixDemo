package com.stpl.servicemix.example.camelosgi.banking;

import com.stpl.servicemix.example.camelosgi.invoice.Invoice;

public interface PaymentCreator {
	Payment createPayment(Invoice invoice) throws PaymentException;
}
