/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.example.fljavagateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
public final class AggregatedSecret {

    private final String modelId;
    private final String round;
    private final String weights;

    public AggregatedSecret(@JsonProperty("modelId") final String modelId,
                            @JsonProperty("round") final String round,
                            @JsonProperty("weights") final String weights) {
        this.modelId = modelId;
        this.round = round;
        this.weights = weights;
    }

    public String getModelId() {
        return modelId;
    }

    public String getRound() {
        return round;
    }

    public String getWeights() {
        return weights;
    }

    public String serialize() {
        return new Gson().toJson(this);
    }

    public static AggregatedSecret deserialize(final String json) {
        return new Gson().fromJson(json, AggregatedSecret.class);
    }

}
