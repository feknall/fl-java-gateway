package com.example.fljavagateway;

import org.hyperledger.fabric.client.ChaincodeEvent;

public interface EventProcessor {

    void allSecretsReceivedEvent(ChaincodeEvent event);
    void aggregationFinishedEvent(ChaincodeEvent event);
    void roundFinishedEvent(ChaincodeEvent event);
    void modelSecretAddedEvent(ChaincodeEvent event);
}
