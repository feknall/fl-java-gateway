/*
 * SPDX-License-Identifier: Apache-2.0
 */

package com.example.fljavagateway;

import com.fasterxml.jackson.annotation.JsonProperty;


public record AggregatedSecret(String modelId, String weights) {

    public AggregatedSecret(@JsonProperty("modelId") final String modelId,
                            @JsonProperty("weights") final String weights) {
        this.modelId = modelId;
        this.weights = weights;
    }
}
