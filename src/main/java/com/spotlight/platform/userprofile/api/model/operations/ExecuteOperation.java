package com.spotlight.platform.userprofile.api.model.operations;

import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.Map;

public interface ExecuteOperation {
    Map<UserProfilePropertyName, UserProfilePropertyValue> execute(OperationRequest operationRequest, Map<UserProfilePropertyName, UserProfilePropertyValue> oldProperties);
}
