package com.spotlight.platform.userprofile.api.model.operations.incrementOperation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.operations.ExecuteOperation;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyMap;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.HashMap;
import java.util.Map;

public class Increment implements ExecuteOperation {

    @Override
    public UserProfilePropertyMap execute(OperationRequest operationRequest, UserProfilePropertyMap oldProperties) {
        UserProfilePropertyMap newProperties = new UserProfilePropertyMap(oldProperties.userProfileProperties);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeNew = mapper.valueToTree(oldProperties);
        JsonNode jsonNodeRequested = mapper.valueToTree(operationRequest.properties());

        for (Map.Entry<UserProfilePropertyName,UserProfilePropertyValue> entry : operationRequest.properties().userProfileProperties.entrySet()){
            UserProfilePropertyName key = entry.getKey();
            if(newProperties.userProfileProperties.containsKey(key) ){
                Integer newValue = jsonNodeNew.get(key.toString()).asInt() +  jsonNodeRequested.get(key.toString()).asInt() ;
                newProperties.userProfileProperties.put(key, UserProfilePropertyValue.valueOf(newValue));
            }
            else{
                throw new OperationValidationException(OperationTypesEnum.INCREMENT, "User property object not found");
            }
        }
            return newProperties;
    }
}
