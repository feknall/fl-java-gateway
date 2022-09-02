package com.example.fljavagateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BlockchainRest {

    private final Logger logger = LoggerFactory.getLogger(EventListener.class);
    private final BlockchainBl blockchainBl;

    public BlockchainRest(BlockchainBl blockchainBl) {
        this.blockchainBl = blockchainBl;
    }

    @PostMapping("/admin/initLedger")
    public byte[] initLedger() {
        return blockchainBl.initLedger();
    }

    @PostMapping("/admin/startTraining")
    public byte[] startTraining(@RequestParam String modelId) {
        return blockchainBl.startTraining(modelId);
    }

    @PostMapping("/admin/createModelMetadata")
    public ResponseEntity<Object> createModelMetadata(@RequestBody ModelMetadata modelMetadata) {
        return blockchainBl.createModelMetadata(modelMetadata.modelId(),
                modelMetadata.name(), modelMetadata.clientsPerRound(),
                modelMetadata.secretsPerClient(),
                modelMetadata.trainingRounds());
    }

    @GetMapping("/admin/getCheckInInfo")
    public byte[] getCheckInInfo() {
        return blockchainBl.getCheckInInfo();
    }

    @PostMapping("/leadAggregator/addEndRoundModel")
    public byte[] addEndRoundModel(@RequestBody EndRoundModel endRoundModel) {
        return blockchainBl.addEndRoundModel(endRoundModel.getModelId(),
                endRoundModel.getWeights());
    }

    @GetMapping("/leadAggregator/readAggregatedModelUpdate")
    public byte[] readAggregatedModelUpdate(@RequestParam String modelId, @RequestParam String round) {
        return blockchainBl.readAggregatedModelUpdate(modelId, round);
    }

    @GetMapping("/leadAggregator/checkAllAggregatedSecretsReceived")
    public byte[] checkAllAggregatedSecretsReceived(@RequestParam String modelId) {
        return blockchainBl.checkAllAggregatedSecretsReceived(modelId);
    }

    @GetMapping("/leadAggregator/getNumberOfReceivedAggregatedSecrets")
    public byte[] getNumberOfReceivedAggregatedSecrets(@RequestParam String modelId) {
        return blockchainBl.getNumberOfReceivedAggregatedSecrets(modelId);
    }

    @GetMapping("/leadAggregator/getAggregatedSecretsForCurrentRound")
    public byte[] getAggregatedSecretsForCurrentRound(@RequestParam String modelId) {
        return blockchainBl.getAggregatedSecretsForCurrentRound(modelId);
    }

    @PostMapping("/aggregator/addAggregatedSecret")
    public byte[] addAggregatedSecret(@RequestBody AggregatedSecret aggregatedSecret) {
        return blockchainBl.addAggregatedSecret(aggregatedSecret.modelId(), aggregatedSecret.weights());
    }

    @GetMapping("/aggregator/checkAllSecretsReceived")
    public byte[] checkAllSecretsReceived(@RequestParam String modelId) {
        return blockchainBl.checkAllSecretsReceived(modelId);
    }

    @GetMapping("/aggregator/getNumberOfReceivedSecrets")
    public byte[] getNumberOfReceivedSecrets(@RequestParam String modelId) {
        return blockchainBl.getNumberOfReceivedSecrets(modelId);
    }

    @GetMapping("/aggregator/readModelSecrets")
    public byte[] readModelSecrets(@RequestParam String modelId, @RequestParam String round) {
        return blockchainBl.readModelSecrets(modelId, round);
    }

    @GetMapping("/aggregator/readModelSecretsForCurrentRound")
    public byte[] readModelSecretsForCurrentRound(@RequestParam String modelId) {
        return blockchainBl.readModelSecretsForCurrentRound(modelId);
    }

    @PostMapping("/trainer/checkInTrainer")
    public byte[] checkInTrainer() {
        return blockchainBl.checkInTrainer();
    }

    @PostMapping("/trainer/addModelSecret")
    public List<byte[]> addModelSecret(@RequestBody ModelSecret modelSecret) {
        return blockchainBl.addModelSecret(modelSecret.modelId(),
                modelSecret.weights1(),
                modelSecret.weights2());
    }

    @GetMapping("/trainer/readEndRoundModel")
    public byte[] readEndRoundModel(@RequestParam String modelId, @RequestParam String round) {
        return blockchainBl.readEndRoundModel(modelId, round);
    }

    @GetMapping("/general/getPersonalInfo")
    public List<byte[]> getPersonalInfo() {
        return blockchainBl.getPersonalInfo();
    }

    @GetMapping("/general/getTrainedModel")
    public byte[] getTrainedModel(@RequestParam String modelId) {
        return blockchainBl.getTrainedModel(modelId);
    }

    @GetMapping("/general/checkHasFlAdminAttribute")
    public byte[] hasFlAdminAttribute() {
        return blockchainBl.checkHasFlAdminAttribute();
    }

    @GetMapping("/general/checkHasAggregatorAttribute")
    public byte[] hasAggregatorAttribute() {
        return blockchainBl.checkHasAggregatorAttribute();
    }

    @GetMapping("/general/checkHasLeadAggregatorAttribute")
    public byte[] hasLeadAggregatorAttribute() {
        return blockchainBl.checkHasLeadAggregatorAttribute();
    }

    @GetMapping("/general/checkHasTrainerAttribute")
    public byte[] hasTrainerAttribute() {
        return blockchainBl.checkHasTrainerAttribute();
    }

    @GetMapping("/general/getSelectedTrainersForCurrentRound")
    public byte[] getSelectedTrainersForCurrentRound() {
        return blockchainBl.getSelectedTrainersForCurrentRound();
    }

    @GetMapping("/general/checkIAmSelectedForRound")
    public byte[] checkIAmSelectedForRound() {
        return blockchainBl.checkIAmSelectedForRound();
    }
}
