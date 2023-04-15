package com.spotlight.platform.userprofile.api.model.operations.replaceOperation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.core.request.OperationResponse;
import com.spotlight.platform.userprofile.api.model.operations.ExecuteOperation;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.HashMap;
import java.util.Map;

public class Replace implements ExecuteOperation {
    @Override
    public Map<UserProfilePropertyName, UserProfilePropertyValue> execute(OperationRequest operationRequest, Map<UserProfilePropertyName, UserProfilePropertyValue> oldProperties) {
        Map<UserProfilePropertyName, UserProfilePropertyValue> newProperties = new HashMap<>(oldProperties);

        for (Map.Entry<UserProfilePropertyName,UserProfilePropertyValue> entry : operationRequest.properties().entrySet()){
            UserProfilePropertyName key = entry.getKey();
            if(newProperties.containsKey(key) ){
                newProperties.put(key, entry.getValue());
            }
            else{
                throw new OperationValidationException(OperationTypesEnum.REPLACE, "User property object not found");
            }
        }
        return newProperties;
    }
}
