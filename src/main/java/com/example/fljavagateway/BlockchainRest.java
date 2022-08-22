package com.example.fljavagateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public byte[] createModelMetadata(@RequestBody ModelMetadata modelMetadata) {
        return blockchainBl.createModelMetadata(modelMetadata.getModelId(),
                modelMetadata.getName(), modelMetadata.getClientsPerRound(),
                modelMetadata.getSecretsPerClient(),
                modelMetadata.getTrainingRounds());
    }

    @PostMapping("/admin/getCheckInInfo")
    public byte[] getCheckInInfo() {
        return blockchainBl.getCheckInInfo();
    }

    @PostMapping("/leadAggregator/addEndRoundModel")
    public byte[] addEndRoundModel(EndRoundModel endRoundModel) {
        return blockchainBl.addEndRoundModel(endRoundModel.getModelId(),
                endRoundModel.getRound(),
                endRoundModel.getWeights());
    }

    @GetMapping("/leadAggregator/readAggregatedModelUpdate")
    public byte[] readAggregatedModelUpdate(@RequestParam String modelId, @RequestParam String round) {
        return blockchainBl.readAggregatedModelUpdate(modelId, round);
    }

    @PostMapping("/aggregator/addAggregatedSecret")
    public byte[] addAggregatedSecret(AggregatedSecret aggregatedSecret) {
        return blockchainBl.addAggregatedSecret(aggregatedSecret.getModelId(),
                aggregatedSecret.getRound(),
                aggregatedSecret.getWeights());
    }

    @GetMapping("/aggregator/readModelSecrets")
    public byte[] readModelSecrets(@RequestParam String modelId, @RequestParam String round) {
        return blockchainBl.readModelSecrets(modelId, round);
    }

    @PostMapping("/trainer/checkInTrainer")
    public byte[] addModelSecret() {
        return blockchainBl.checkInTrainer();
    }

    @PostMapping("/trainer/addModelSecret")
    public List<byte[]> addModelSecret(ModelSecret modelSecret) {
        return blockchainBl.addModelSecret(modelSecret.getModelId(),
                modelSecret.getRound(),
                modelSecret.getWeights1(),
                modelSecret.getWeights2());
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

}
