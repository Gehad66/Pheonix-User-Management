package com.spotlight.platform.userprofile.api.model.operations;

import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyMap;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.Map;

public interface ExecuteOperation {
    UserProfilePropertyMap execute(OperationRequest operationRequest, UserProfilePropertyMap oldProperties);
}
