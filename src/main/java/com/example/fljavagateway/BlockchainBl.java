package com.example.fljavagateway;

import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.GatewayException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlockchainBl {

    private final Contract org1Contract;
    private final Contract org2Contract;

    private final Contract contract;
    public BlockchainBl(Contract org1Contract, Contract org2Contract) {
        this.org1Contract = org1Contract;
        this.org2Contract = org2Contract;

        // Either org1Contract or org2Contract should be fine
        this.contract = org2Contract;
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

    public List<byte[]> addModelSecret(String modelId, String round, String weights1, String weights2) {
        try {
            List<byte[]> list = new ArrayList<>();

            byte[] resp1 = org1Contract.submitTransaction("AddModelSecret", modelId, round, weights1);
            list.add(resp1);

            byte[] resp2 = org2Contract.submitTransaction("AddModelSecret", modelId, round, weights2);
            list.add(resp2);

            return list;
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

    public List<byte[]> getPersonalInfo() {
        try {
            List<byte[]> list = new ArrayList<>();

            byte[] resp1 = org1Contract.evaluateTransaction("GetPersonalInfo");
            list.add(resp1);

            byte[] resp2 = org2Contract.evaluateTransaction("GetPersonalInfo");
            list.add(resp2);

            return list;
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getTrainedModel(String modelId) {
        try {
            return contract.evaluateTransaction("GetTrainedModel", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkInTrainer() {
        try {
            return contract.submitTransaction("CheckInTrainer");
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getCheckInInfo() {
        try {
            return contract.evaluateTransaction("GetCheckInInfo");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }
}
