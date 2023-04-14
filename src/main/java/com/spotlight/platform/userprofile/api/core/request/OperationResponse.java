package com.spotlight.platform.userprofile.api.core.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spotlight.platform.userprofile.api.core.enums.StatusEnum;

public record OperationResponse (@JsonProperty String msg, @JsonProperty StatusEnum status){
}
