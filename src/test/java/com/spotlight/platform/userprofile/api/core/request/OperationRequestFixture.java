package com.spotlight.platform.userprofile.api.core.request;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyMap;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import java.util.Map;

public class OperationRequestFixture {
    public static final UserId USER_ID = UserId.valueOf("existing-user-id");
    public static final UserId NON_EXISTING_USER_ID = UserId.valueOf("non-existing-user-id");
    public static final UserId INVALID_USER_ID = UserId.valueOf("invalid-user-id-%");

    public static final OperationTypesEnum TYPE = OperationTypesEnum.valueOf("REPLACE");

    public static final OperationRequest OPERATION_REQUEST = new OperationRequest(USER_ID, TYPE,
            new UserProfilePropertyMap(
            Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(500))));

    public static final String SERIALIZED_OPERATION_REQUEST = FixtureHelpers.fixture("/fixtures/core/request/operationRequest.json");

}
