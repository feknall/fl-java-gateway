package com.example.fljavagateway;

import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.Network;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;

@Configuration
public class Org1Settings extends Settings {

    @Override
    public String getMspID() {
        return "Org1MSP";
    }

    @Override
    public String getOrganization() {
        return "org1.example.com";
    }

    @Override
    public String getPeerEndpoint() {
        return "localhost:7051";
    }

    @Bean(name = "org1Contract")
    @Override
    public Contract orgContract(Network org1Network) {
        return super.orgContract(org1Network);
    }

    @Bean(name = "org1Network")
    @Override
    public Network orgNetwork(Gateway org1Gateway) {
        return super.orgNetwork(org1Gateway);
    }

    @Bean(name = "org1Gateway")
    @Override
    public Gateway orgGateway() throws CertificateException, IOException, InvalidKeyException {
        return super.orgGateway();
    }
}
