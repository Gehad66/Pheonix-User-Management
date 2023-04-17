package com.spotlight.platform.userprofile.api.model.operations.collectOperation;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.operations.ExecuteOperation;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyMap;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.ArrayList;
import java.util.Map;

public class Collect implements ExecuteOperation {

    ArrayList mergeArrayList(ArrayNode currentList, ArrayNode newList){
        ArrayNode outputList = currentList;
        for (int i=0;i<newList.size();i++){
            outputList.add(newList.get(i).asText());
        }
        return new ObjectMapper().convertValue(outputList, ArrayList.class);
    }
    @Override
    public UserProfilePropertyMap execute(OperationRequest operationRequest, UserProfilePropertyMap oldProperties) {
        UserProfilePropertyMap newProperties = new UserProfilePropertyMap(oldProperties.userProfileProperties);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeCurrent = mapper.valueToTree(oldProperties);
        JsonNode jsonNodeRequested = mapper.valueToTree(operationRequest.properties());

        for (Map.Entry<UserProfilePropertyName,UserProfilePropertyValue> entry : operationRequest.properties().userProfileProperties.entrySet()){
            UserProfilePropertyName key = entry.getKey();
            if(newProperties.userProfileProperties.containsKey(key) ){
                ArrayList mergedArray = mergeArrayList(
                        (ArrayNode) jsonNodeCurrent.get(key.toString()),
                        (ArrayNode) jsonNodeRequested.get(key.toString())
                                );

                newProperties.userProfileProperties.put(key, UserProfilePropertyValue.valueOf(mergedArray));
            }
            else{
                throw new OperationValidationException(OperationTypesEnum.REPLACE, "User property object not found");
            }
        }
        return newProperties;
    }
}
