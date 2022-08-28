/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.example.fljavagateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

public record ModelMetadata(String modelId, String name,
                            String clientsPerRound, String secretsPerClient, String status,
                            String trainingRounds) {

    public ModelMetadata(@JsonProperty("modelId") final String modelId,
                         @JsonProperty("name") final String name,
                         @JsonProperty("clientsPerRound") final String clientsPerRound,
                         @JsonProperty("secretsPerClient") final String secretsPerClient,
                         @JsonProperty("status") final String status,
                         @JsonProperty("trainingRounds") final String trainingRounds) {
        this.modelId = modelId;
        this.name = name;
        this.clientsPerRound = clientsPerRound;
        this.secretsPerClient = secretsPerClient;
        this.status = status;
        this.trainingRounds = trainingRounds;
    }

    public String serialize() {
        return new Gson().toJson(this);
    }

    public static ModelMetadata deserialize(final String ser) {
        return new Gson().fromJson(ser, ModelMetadata.class);
    }
}
