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
public class Org2Settings extends Settings {

    @Override
    public String getMspID() {
        return "Org2MSP";
    }

    @Override
    public String getOrganization() {
        return "org2.example.com";
    }

    @Override
    public String getPeerEndpoint() {
        return "localhost:9051";
    }

    @Bean(name = "org2Contract")
    @Override
    public Contract orgContract(Network org2Network) {
        return super.orgContract(org2Network);
    }

    @Bean(name = "org2Network")
    @Override
    public Network orgNetwork(Gateway org2Gateway) {
        return super.orgNetwork(org2Gateway);
    }

    @Bean(name = "org2Gateway")
    @Override
    public Gateway orgGateway() throws CertificateException, IOException, InvalidKeyException {
        return super.orgGateway();
    }
}
