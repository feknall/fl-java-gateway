package com.example.fljavagateway;

import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Component;

@Component
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

    public byte[] addModelUpdate() {
        try {
            return contract.submitTransaction("AddModelUpdate");
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] createModelMetadata(String modelId, String modelName) {
        try {
            return contract.submitTransaction("CreateModelMetadata", modelId, modelName);
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

    public byte[] getAllAssets() {
        try {
            return contract.evaluateTransaction("GetAllAssets");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }
}
