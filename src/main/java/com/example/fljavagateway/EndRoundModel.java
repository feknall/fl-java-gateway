/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.example.fljavagateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

public final class EndRoundModel {
    private final String modelId;
    private final String weights;

    public EndRoundModel(@JsonProperty("modelId") final String modelId, @JsonProperty("weights") final String weights) {
        this.modelId = modelId;
        this.weights = weights;
    }

    public String getModelId() {
        return modelId;
    }

    public String getWeights() {
        return weights;
    }

    public String serialize() {
        return new Gson().toJson(this);
    }

    public static EndRoundModel deserialize(final String json) {
        return new Gson().fromJson(json, EndRoundModel.class);
    }

}
