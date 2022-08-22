/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.example.fljavagateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

public final class ModelSecret {

    private final String modelId;
    private final String round;
    private final String weights1;
    private final String weights2;

    public ModelSecret(@JsonProperty("modelId") final String modelId,
                       @JsonProperty("round") final String round,
                       @JsonProperty("weights1") final String weights1,
                       @JsonProperty("weights2") final String weights2) {
        this.modelId = modelId;
        this.round = round;
        this.weights1 = weights1;
        this.weights2 = weights2;
    }

    public String getModelId() {
        return modelId;
    }


    public String getRound() {
        return round;
    }

    public String getWeights1() {
        return weights1;
    }

    public String getWeights2() {
        return weights2;
    }


    public String serialize() {
        return new Gson().toJson(this);
    }

    public static ModelSecret deserialize(final String json) {
        return new Gson().fromJson(json, ModelSecret.class);
    }

}
