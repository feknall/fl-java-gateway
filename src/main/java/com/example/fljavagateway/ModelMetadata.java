/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.example.fljavagateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

public final class ModelMetadata {

    private final String modelId;
    private final String name;

    private final String clientsPerRound;
    private final String secretsPerClient;
    private final String status;
    private final String trainingRounds;


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

    public String getModelId() {
        return modelId;
    }

    public String getClientsPerRound() {
        return clientsPerRound;
    }

    public String getSecretsPerClient() {
        return secretsPerClient;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getTrainingRounds() {
        return trainingRounds;
    }

    public String serialize() {
        return new Gson().toJson(this);
    }

    public static ModelMetadata deserialize(final String ser) {
        return new Gson().fromJson(ser, ModelMetadata.class);
    }
}
