package com.example.fljavagateway;

import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

@Service
public class BlockchainBl {

    private final Contract contract;

    public BlockchainBl(Contract contract) {
        this.contract = contract;
    }

    public byte[] initLedger() {
        try {
            return contract.submitTransaction("InitLedger");
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] startTraining(String modelId) {
        try {
            return contract.submitTransaction("StartTraining", modelId);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] createModelMetadata(String modelId,
                                      String modelName,
                                      String clientsPerRound,
                                      String secretsPerClient,
                                      String trainingRounds) {
        try {
            return contract.submitTransaction("CreateModelMetadata",
                    modelId,
                    modelName,
                    clientsPerRound,
                    secretsPerClient,
                    trainingRounds);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] addModelSecret(String modelId, String round, String weights) {
        try {
            return contract.submitTransaction("AddModelSecret", modelId, round, weights);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] addEndRoundModel(String modelId, String round, String weights) {
        try {
            return contract.submitTransaction("AddEndRoundModel", modelId, round, weights);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] addAggregatedSecret(String modelId, String round, String weights) {
        try {
            return contract.submitTransaction("AddAggregatedSecret", modelId, round, weights);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readAggregatedModelUpdate(String modelId, String round) {
        try {
            return contract.evaluateTransaction("ReadAggregatedModelUpdate", modelId, round);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readModelSecrets(String modelId, String round) {
        try {
            return contract.evaluateTransaction("ReadModelSecrets", modelId, round);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readEndRoundModel(String modelId, String round) {
        try {
            return contract.evaluateTransaction("ReadEndRoundModel", modelId, round);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getRoleInCertificate() {
        try {
            return contract.evaluateTransaction("GetRoleInCertificate");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public void listen() {

    }

    public byte[] getTrainedModel(String modelId) {
        try {
            return contract.evaluateTransaction("GetTrainedModel", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }
}
