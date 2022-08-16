package com.example.fljavagateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockchainRest {

    private final BlockchainBl blockchainBl;

    public BlockchainRest(BlockchainBl blockchainBl) {
        this.blockchainBl = blockchainBl;
    }

    @PostMapping("/admin/initLedger")
    public byte[] initLedger() {
        return blockchainBl.initLedger();
    }

    @PostMapping("/admin/createModelMetadata")
    public byte[] createModelMetadata(@RequestParam String modelId, @RequestParam String modelName) {
        return blockchainBl.createModelMetadata(modelId, modelName);
    }

    @PostMapping("/leadAggregator/addEndRoundModel")
    public byte[] addEndRoundModel(String modelId, String round, String weights) {
        return blockchainBl.addEndRoundModel(modelId, round, weights);
    }

    @GetMapping("/leadAggregator/readAggregatedModelUpdate")
    public byte[] readAggregatedModelUpdate(@RequestParam String modelId, @RequestParam String round) {
        return blockchainBl.readAggregatedModelUpdate(modelId, round);
    }

    @PostMapping("/aggregator/addAggregatedSecret")
    public byte[] addAggregatedSecret(String modelId, String round, String weights) {
        return blockchainBl.addAggregatedSecret(modelId, round, weights);
    }

    @GetMapping("/aggregator/readModelSecrets")
    public byte[] readModelSecrets(@RequestParam String modelId, @RequestParam String round) {
        return blockchainBl.readModelSecrets(modelId, round);
    }

    @PostMapping("/user/addModelSecret")
    public byte[] addModelSecret(String modelId, String round, String weights) {
        return blockchainBl.addModelSecret(modelId, round, weights);
    }

    @GetMapping("/user/readEndRoundModel")
    public byte[] readEndRoundModel(@RequestParam String modelId, @RequestParam String round) {
        return blockchainBl.readEndRoundModel(modelId, round);
    }

    @GetMapping("/user/getAllAssets")
    public byte[] getAllAssets() {
        return blockchainBl.getAllAssets();
    }

}
