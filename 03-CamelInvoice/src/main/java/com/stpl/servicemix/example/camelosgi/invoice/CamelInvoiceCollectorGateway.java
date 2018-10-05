package com.stpl.servicemix.example.camelosgi.invoice;

import java.util.Collection;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Component
public class CamelInvoiceCollectorGateway implements InvoiceCollectorGateway {

    @Produce(uri = "seda:newInvoicesChannel")
    ProducerTemplate producerTemplate;

    public void collectInvoices(Collection<Invoice> invoices) {
        producerTemplate.sendBody(invoices);
    }

}
