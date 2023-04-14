package com.spotlight.platform.userprofile.api.model.operations;

import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.core.request.OperationResponse;
import com.spotlight.platform.userprofile.api.model.operations.collectOperation.Collect;
import com.spotlight.platform.userprofile.api.model.operations.incrementOperation.Increment;
import com.spotlight.platform.userprofile.api.model.operations.replaceOperation.Replace;

import static com.spotlight.platform.userprofile.api.core.enums.StatusEnum.FAIL;

public class ExecuteOperationFactory {
    public OperationResponse execute(OperationRequest operationRequest){
        switch(operationRequest.type()) {
            case REPLACE:
                return new Replace().execute(operationRequest);
            case INCREMENT:
                return new Increment().execute(operationRequest);
            case COLLECT:
                return new Collect().execute(operationRequest);
            default:
                String message = "No Operation defined for this request";
                return new OperationResponse(message, FAIL);
        }
    }
}
