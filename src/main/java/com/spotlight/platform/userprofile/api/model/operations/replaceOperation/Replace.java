package com.spotlight.platform.userprofile.api.model.operations.replaceOperation;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.operations.ExecuteOperation;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyMap;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.HashMap;
import java.util.Map;

public class Replace implements ExecuteOperation {
    @Override
    public UserProfilePropertyMap execute(OperationRequest operationRequest, UserProfilePropertyMap oldProperties) {
        UserProfilePropertyMap newProperties = new UserProfilePropertyMap(oldProperties.userProfileProperties);

        for (Map.Entry<UserProfilePropertyName,UserProfilePropertyValue> entry : operationRequest.properties().userProfileProperties.entrySet()){
            UserProfilePropertyName key = entry.getKey();
            if(newProperties.userProfileProperties.containsKey(key) ){
                newProperties.userProfileProperties.put(key, entry.getValue());
            }
            else{
                throw new OperationValidationException(OperationTypesEnum.REPLACE, "User property object not found");
            }
        }
        return newProperties;
    }
}
