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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

@Configuration
public class Org2Settings {

    private static final String mspID = "Org2MSP";
    private static final String channelName = "mychannel";
    public static final String chaincodeName = "basic";

    // Path to crypto materials.
    private static final Path cryptoPath = Paths.get("/home/hamid/Documents/fabric-samples", "test-network", "organizations", "peerOrganizations", "org2.example.com");
    // Path to user certificate.
    private static final Path certPath = cryptoPath.resolve(Paths.get("users", "User1@org2.example.com", "msp", "signcerts", "cert.pem"));
    // Path to user private key directory.
    private static final Path keyDirPath = cryptoPath.resolve(Paths.get("users", "User1@org2.example.com", "msp", "keystore"));
    // Path to peer tls certificate.
    private static final Path tlsCertPath = cryptoPath.resolve(Paths.get("peers", "peer0.org2.example.com", "tls", "ca.crt"));

    private static final String peerEndpoint = "localhost:9051";
    private static final String overrideAuth = "peer0.org2.example.com";

    @Bean
    public Gateway org2Gateway() throws CertificateException, IOException, InvalidKeyException {
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

    @Bean
    public Contract org2Contract(Network org2Network) {
        return org2Network.getContract(chaincodeName);
    }

    @Bean
    public Network org2Network(Gateway org2Gateway) {
        return org2Gateway.getNetwork(channelName);
    }

    private static Identity newIdentity() throws IOException, CertificateException {
        var certReader = Files.newBufferedReader(certPath);
        var certificate = Identities.readX509Certificate(certReader);

        return new X509Identity(mspID, certificate);
    }

    private static ManagedChannel newGrpcConnection() throws IOException, CertificateException {
        var tlsCertReader = Files.newBufferedReader(tlsCertPath);
        var tlsCert = Identities.readX509Certificate(tlsCertReader);

        return NettyChannelBuilder.forTarget(peerEndpoint)
                .sslContext(GrpcSslContexts.forClient().trustManager(tlsCert).build())
                .overrideAuthority(overrideAuth)
                .maxInboundMessageSize(4 * 4194304)
                .build();
    }

    private static Signer newSigner() throws IOException, InvalidKeyException {
        var keyReader = Files.newBufferedReader(getPrivateKeyPath());
        var privateKey = Identities.readPrivateKey(keyReader);

        return Signers.newPrivateKeySigner(privateKey);
    }

    private static Path getPrivateKeyPath() throws IOException {
        try (var keyFiles = Files.list(keyDirPath)) {
            return keyFiles.findFirst().orElseThrow();
        }
    }
}
