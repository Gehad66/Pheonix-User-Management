package com.spotlight.platform.userprofile.api.core.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BatchOperationRequest(@JsonProperty List<OperationRequest> batchOperation){
}

