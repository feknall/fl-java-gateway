package com.example.fljavagateway;

import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.Gateway;
import org.hyperledger.fabric.client.Network;
import org.hyperledger.fabric.client.identity.Identities;
import org.hyperledger.fabric.client.identity.Identity;
import org.hyperledger.fabric.client.identity.Signer;
import org.hyperledger.fabric.client.identity.Signers;
import org.hyperledger.fabric.client.identity.X509Identity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

public abstract class Settings {

    @Value("${fl.user}")
    private String user;

    @Value("${fl.channel.name}")
    private String channelName;

    public static final String chaincodeName = "basic";

    // Path to crypto materials.

    public abstract String getMspID();
    public abstract String getOrganization();
    public abstract String getPeerEndpoint();

    private Path getCryptoPath() {
        return Paths.get("/home/hamid/Documents/fabric-samples", "test-network", "organizations", "peerOrganizations", getOrganization());
    }

    private Path getCertPath() {
        return getCryptoPath().resolve(Paths.get("users", user + "@" + getOrganization(), "msp", "signcerts", "cert.pem"));
    }

    private Path getKeyDirPath() {
        return getCryptoPath().resolve(Paths.get("users", user + "@" + getOrganization(), "msp", "keystore"));
    }

    private Path getTlsCertPath() {
        return getCryptoPath().resolve(Paths.get("peers", "peer0." + getOrganization(), "tls", "ca.crt"));
    }

    private final String overrideAuth = "peer0." + getOrganization();

    public Gateway orgGateway() throws CertificateException, IOException, InvalidKeyException {
        var channel = newGrpcConnection();

        var builder = Gateway.newInstance()
                .identity(newIdentity())
                .signer(newSigner())
                .connection(channel)
                .evaluateOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .endorseOptions(options -> options.withDeadlineAfter(15, TimeUnit.SECONDS))
                .submitOptions(options -> options.withDeadlineAfter(5, TimeUnit.SECONDS))
                .commitStatusOptions(options -> options.withDeadlineAfter(1, TimeUnit.MINUTES));

        return builder.connect();
    }


    public Contract orgContract(Network orgNetwork) {
        return orgNetwork.getContract(chaincodeName);
    }

    public Network orgNetwork(Gateway org1Gateway) {
        return org1Gateway.getNetwork(channelName);
    }

    private Identity newIdentity() throws IOException, CertificateException {
        var certReader = Files.newBufferedReader(getCertPath());
        var certificate = Identities.readX509Certificate(certReader);

        return new X509Identity(getMspID(), certificate);
    }

    private ManagedChannel newGrpcConnection() throws IOException, CertificateException {
        var tlsCertReader = Files.newBufferedReader(getTlsCertPath());
        var tlsCert = Identities.readX509Certificate(tlsCertReader);

        return NettyChannelBuilder.forTarget(getPeerEndpoint())
                .sslContext(GrpcSslContexts.forClient().trustManager(tlsCert).build())
                .overrideAuthority(overrideAuth)
                .maxInboundMessageSize(4 * 4194304)
                .build();
    }

    private Signer newSigner() throws IOException, InvalidKeyException {
        var keyReader = Files.newBufferedReader(getPrivateKeyPath());
        var privateKey = Identities.readPrivateKey(keyReader);

        return Signers.newPrivateKeySigner(privateKey);
    }

    private Path getPrivateKeyPath() throws IOException {
        try (var keyFiles = Files.list(getKeyDirPath())) {
            return keyFiles.findFirst().orElseThrow();
        }
    }

}
