package com.stpl.servicemix.example.camelosgi.banking;


public class PaymentException  extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5857373341111047350L;

	public PaymentException(String message) {
        super(message);
    }
}
