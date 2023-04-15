package com.spotlight.platform.userprofile.api.model.operations;

import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.core.request.OperationResponse;

public interface ExecuteOperation {
    OperationResponse execute(OperationRequest operationRequest);
}
