package com.spotlight.platform.userprofile.api.model.operations;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.enums.StatusEnum;
import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.core.request.OperationResponse;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExecuteOperationFactoryTest {
    public static final UserId USER_ID = UserId.valueOf("existing-user-id");
    public static final OperationTypesEnum TYPE = OperationTypesEnum.REPLACE;
    public static final OperationRequest OPERATION_REQUEST = new OperationRequest(USER_ID, TYPE,
            Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(500)));

    //TODO
    @Test
    void replace_Operation_Request() {
//        Assertions.assertNull(new ExecuteOperationFactory().execute(OPERATION_REQUEST));
    }
    @Test
    void increment_Operation_Request() {
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.INCREMENT,
                Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(500)));
//        Assertions.assertNull(new ExecuteOperationFactory().execute(operationRequest));
    }
    @Test
    void collect_Operation_Request() {
        List<String> arrayInventory = new LinkedList<>(List.of("sword1",
                "sword2",
                "sword3"));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.COLLECT,
                Map.of(UserProfilePropertyName.valueOf("inventory"), UserProfilePropertyValue.valueOf(arrayInventory)));
//        Assertions.assertNull(new ExecuteOperationFactory().execute(operationRequest));
    }
    @Test
    void invalid_Operation_Request_Type() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.valueOf("multiply"),
                    Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(500)));
        });
    }
    @Test
    void unsupported_Operation_Request_Type() {
        ExecuteOperationFactory operationFactory = new ExecuteOperationFactory();
        Map<UserProfilePropertyName, UserProfilePropertyValue> oldProperties =
                Map.of(UserProfilePropertyName.valueOf("battleFought"), UserProfilePropertyValue.valueOf(100));
        Map<UserProfilePropertyName, UserProfilePropertyValue> newProperties =
                Map.of(UserProfilePropertyName.valueOf("battleFought"), UserProfilePropertyValue.valueOf(500));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.OTHERS, newProperties);
        assertThrows(OperationValidationException.class, () -> {
            operationFactory.execute(operationRequest, oldProperties);}  );
    }
}