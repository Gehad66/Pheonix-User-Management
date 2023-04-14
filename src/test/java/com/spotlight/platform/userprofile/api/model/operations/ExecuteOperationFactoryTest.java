package com.spotlight.platform.userprofile.api.model.operations;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.core.request.OperationResponse;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

class ExecuteOperationFactoryTest {
    public static final UserId USER_ID = UserId.valueOf("existing-user-id");
    public static final OperationTypesEnum TYPE = OperationTypesEnum.valueOf("REPLACE");
    public static final OperationRequest OPERATION_REQUEST = new OperationRequest(USER_ID, TYPE,
            Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(500)));

    //TODO
    @Test
    void test() {
        Assertions.assertNull(new ExecuteOperationFactory().execute(OPERATION_REQUEST));
    }
}