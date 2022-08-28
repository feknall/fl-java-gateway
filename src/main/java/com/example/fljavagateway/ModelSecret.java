/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.example.fljavagateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

public record ModelSecret(String modelId, String round, String weights1, String weights2) {

    public ModelSecret(@JsonProperty("modelId") final String modelId,
                       @JsonProperty("round") final String round,
                       @JsonProperty("weights1") final String weights1,
                       @JsonProperty("weights2") final String weights2) {
        this.modelId = modelId;
        this.round = round;
        this.weights1 = weights1;
        this.weights2 = weights2;
    }


    public String serialize() {
        return new Gson().toJson(this);
    }

    public static ModelSecret deserialize(final String json) {
        return new Gson().fromJson(json, ModelSecret.class);
    }

}
