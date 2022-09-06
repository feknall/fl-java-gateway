package com.example.fljavagateway;

import org.hyperledger.fabric.client.CommitException;
import org.hyperledger.fabric.client.Contract;
import org.hyperledger.fabric.client.EndorseException;
import org.hyperledger.fabric.client.GatewayException;
import org.hyperledger.fabric.protos.gateway.ErrorDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlockchainBl {
    private final Logger logger = LoggerFactory.getLogger(BlockchainBl.class);
    private final Contract org1Contract;
    private final Contract org2Contract;
    private final Contract defaultContract;

    private final Contract aggregatorContract;

    private static final String ORG1 = "org1";
    private static final String ORG2 = "org2";

    public BlockchainBl(@Value("{fl.default.organization}") String defaultOrganization,
                        @Value("${fl.aggregator.organization}") String aggregatorOrganization,
                        Contract org1Contract, Contract org2Contract) {
        this.org1Contract = org1Contract;
        this.org2Contract = org2Contract;

        if (ORG1.equals(defaultOrganization)) {
            logger.info("Using org1Contract for defaultContract");
            this.defaultContract = org1Contract;
        } else {
            logger.info("Using org2Contract for defaultContract");
            this.defaultContract = org2Contract;
        }

        // Either org1Contract or org2Contract should be fine
        logger.info("--fl.aggregator.organization=" + aggregatorOrganization);
        if (ORG1.equals(aggregatorOrganization)) {
            logger.info("Using org1Contract for aggregatorContract");
            this.aggregatorContract = org1Contract;
        } else {
            logger.info("Using org2Contract for aggregatorContract");
            this.aggregatorContract = org2Contract;
        }
    }

    public byte[] initLedger() {
        try {
            return defaultContract.submitTransaction("initLedger");
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] startTraining(String modelId) {
        try {
            return defaultContract.submitTransaction("startTraining", modelId);
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
            byte[] bytes = defaultContract.submitTransaction("createModelMetadata",
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

    public List<byte[]> addModelSecret(String modelId, String weights1, String weights2) {
        try {
            List<byte[]> list = new ArrayList<>();

            byte[] resp1 = org1Contract.submitTransaction("addModelSecret", modelId, weights1);
            list.add(resp1);

            byte[] resp2 = org2Contract.submitTransaction("addModelSecret", modelId, weights2);
            list.add(resp2);

            return list;
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] addEndRoundModel(String modelId, String weights) {
        try {
            return defaultContract.submitTransaction("addEndRoundModel", modelId, weights);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] addAggregatedSecret(String modelId, String weights) {
        try {
            return aggregatorContract.submitTransaction("addAggregatedSecret", modelId, weights);
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] readAggregatedModelUpdate(String modelId, String round) {
        try {
            return defaultContract.evaluateTransaction("readAggregatedModelUpdate", modelId, round);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getModelSecretList(String modelId, String round) {
        try {
            return aggregatorContract.evaluateTransaction("getModelSecretList", modelId, round);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getModelSecretListForCurrentRound(String modelId) {
        try {
            return aggregatorContract.evaluateTransaction("getModelSecretListForCurrentRound", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getEndRoundModel(String modelId) {
        try {
            return defaultContract.evaluateTransaction("getEndRoundModel", modelId);
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
            return defaultContract.evaluateTransaction("getTrainedModel", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkInTrainer() {
        try {
            return defaultContract.submitTransaction("checkInTrainer");
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getCheckInInfo() {
        try {
            return defaultContract.evaluateTransaction("getCheckInInfo");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkHasFlAdminAttribute() {
        try {
            return defaultContract.evaluateTransaction("checkHasFlAdminAttribute");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkHasLeadAggregatorAttribute() {
        try {
            return defaultContract.evaluateTransaction("checkHasLeadAggregatorAttribute");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkHasAggregatorAttribute() {
        try {
            return defaultContract.evaluateTransaction("checkHasAggregatorAttribute");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkHasTrainerAttribute() {
        try {
            return defaultContract.evaluateTransaction("checkHasTrainerAttribute");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getSelectedTrainersForCurrentRound() {
        try {
            return defaultContract.evaluateTransaction("getSelectedTrainersForCurrentRound");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkIAmSelectedForRound() {
        try {
            return defaultContract.evaluateTransaction("checkIAmSelectedForRound");
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkAllSecretsReceived(String modelId) {
        try {
            return defaultContract.evaluateTransaction("checkAllSecretsReceived", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkAllAggregatedSecretsReceived(String modelId) {
        try {
            return defaultContract.evaluateTransaction("checkAllAggregatedSecretsReceived", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getNumberOfReceivedAggregatedSecrets(String modelId) {
        try {
            return defaultContract.evaluateTransaction("getNumberOfReceivedAggregatedSecrets", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getNumberOfReceivedSecrets(String modelId) {
        try {
            return defaultContract.evaluateTransaction("getNumberOfReceivedSecrets", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getAggregatedSecretListForCurrentRound(String modelId) {
        try {
            return defaultContract.evaluateTransaction("getAggregatedSecretListForCurrentRound", modelId);
        } catch (GatewayException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkInAggregator() {
        try {
            return defaultContract.submitTransaction("checkInAggregator");
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] checkInLeadAggregator() {
        try {
            return defaultContract.submitTransaction("checkInLeadAggregator");
        } catch (GatewayException | CommitException e) {
            throw new RuntimeException(e);
        }
    }
}
