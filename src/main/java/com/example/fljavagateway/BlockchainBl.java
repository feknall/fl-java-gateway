package com.example.fljavagateway;

import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.GatewayException;
import org.hyperledger.fabric.protos.gateway.ErrorDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            return contract.submitTransaction("initLedger");
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] startTraining(String modelId) {
        try {
            return contract.submitTransaction("startTraining", modelId);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public ResponseEntity<Object> createModelMetadata(String modelId,
                                                      String modelName,
                                                      String clientsPerRound,
                                                      String secretsPerClient,
                                                      String trainingRounds) {
        try {
            byte[] bytes = contract.submitTransaction("createModelMetadata",
                    modelId,
                    modelName,
                    clientsPerRound,
                    secretsPerClient,
                    trainingRounds);
            return new ResponseEntity<>(bytes, HttpStatus.ACCEPTED);
        } catch (EndorseException e) {
            String message = e.getDetails().stream().map(ErrorDetail::getMessage).collect(Collectors.joining());
            return new ApiError(HttpStatus.FORBIDDEN, message, e).toResponseEntity();
        } catch (GatewayException | CommitException e) {
            return new ApiError(HttpStatus.BAD_REQUEST, e).toResponseEntity();
        }
    }

    public List<byte[]> addModelSecret(String modelId, String round, String weights1, String weights2) {
        try {
            List<byte[]> list = new ArrayList<>();

            byte[] resp1 = org1Contract.submitTransaction("addModelSecret", modelId, round, weights1);
            list.add(resp1);

            byte[] resp2 = org2Contract.submitTransaction("addModelSecret", modelId, round, weights2);
            list.add(resp2);

            return list;
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] addEndRoundModel(String modelId, String round, String weights) {
        try {
            return contract.submitTransaction("addEndRoundModel", modelId, round, weights);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] addAggregatedSecret(String modelId, String round, String weights) {
        try {
            return contract.submitTransaction("addAggregatedSecret", modelId, round, weights);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readAggregatedModelUpdate(String modelId, String round) {
        try {
            return contract.evaluateTransaction("readAggregatedModelUpdate", modelId, round);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readModelSecrets(String modelId, String round) {
        try {
            return contract.evaluateTransaction("readModelSecrets", modelId, round);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readEndRoundModel(String modelId, String round) {
        try {
            return contract.evaluateTransaction("readEndRoundModel", modelId, round);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public List<byte[]> getPersonalInfo() {
        try {
            List<byte[]> list = new ArrayList<>();

            byte[] resp1 = org1Contract.evaluateTransaction("getPersonalInfo");
            list.add(resp1);

            byte[] resp2 = org2Contract.evaluateTransaction("getPersonalInfo");
            list.add(resp2);

            return list;
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getTrainedModel(String modelId) {
        try {
            return contract.evaluateTransaction("getTrainedModel", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkInTrainer() {
        try {
            return contract.submitTransaction("checkInTrainer");
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getCheckInInfo() {
        try {
            return contract.evaluateTransaction("getCheckInInfo");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkHasFlAdminAttribute() {
        try {
            return contract.evaluateTransaction("checkHasFlAdminAttribute");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkHasLeadAggregatorAttribute() {
        try {
            return contract.evaluateTransaction("checkHasLeadAggregatorAttribute");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkHasAggregatorAttribute() {
        try {
            return contract.evaluateTransaction("checkHasAggregatorAttribute");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkHasTrainerAttribute() {
        try {
            return contract.evaluateTransaction("checkHasTrainerAttribute");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }
}
