package com.spotlight.platform.userprofile.api.model.operations;

import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.operations.collectOperation.Collect;
import com.spotlight.platform.userprofile.api.model.operations.incrementOperation.Increment;
import com.spotlight.platform.userprofile.api.model.operations.replaceOperation.Replace;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.Map;

public class ExecuteOperationFactory {
    public Map<UserProfilePropertyName, UserProfilePropertyValue> execute(OperationRequest operationRequest, Map<UserProfilePropertyName, UserProfilePropertyValue> oldProperties){
        switch(operationRequest.type()) {
            case REPLACE:
                return new Replace().execute(operationRequest, oldProperties);
            case INCREMENT:
                return new Increment().execute(operationRequest, oldProperties);
            case COLLECT:
                return new Collect().execute(operationRequest, oldProperties);
            default:
                String message = "Unsupported Operation";
                throw new OperationValidationException(null, message);
        }
    }
}
