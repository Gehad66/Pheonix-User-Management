package com.spotlight.platform.userprofile.api.model.operations;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyMap;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExecuteOperationFactoryTest {
    public static final UserId USER_ID = UserId.valueOf("existing-user-id");
    private static final UserProfilePropertyMap USER_MAP = new UserProfilePropertyMap( new HashMap<>(
            Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(600))
    ));
    @Test
    void replace_Operation_Request() {
        UserProfilePropertyMap requestMap = new UserProfilePropertyMap( new HashMap<>(
                Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(400))
        ));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.REPLACE, requestMap);
        UserProfilePropertyMap requestResult = new ExecuteOperationFactory().execute(operationRequest, USER_MAP);

        assertThat(requestResult.userProfileProperties
                .get(UserProfilePropertyName.valueOf("currentGold"))).isEqualTo(UserProfilePropertyValue.valueOf(400));
    }
    @Test
    void increment_Operation_Request() {
        UserProfilePropertyMap requestMap = new UserProfilePropertyMap( new HashMap<>(
                Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(400))
        ));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.INCREMENT, requestMap);
        UserProfilePropertyMap requestResult = new ExecuteOperationFactory().execute(operationRequest, USER_MAP);

        assertThat(requestResult.userProfileProperties
                .get(UserProfilePropertyName.valueOf("currentGold"))).isEqualTo(UserProfilePropertyValue.valueOf(1000));
    }
    @Test
    void collect_Operation_Request() {
        UserProfilePropertyMap userMap = new UserProfilePropertyMap( new HashMap<>(
                Map.of(UserProfilePropertyName.valueOf("Inventory"),
                        UserProfilePropertyValue.valueOf(List.of("sword1", "sword2")))));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.COLLECT,
                new UserProfilePropertyMap(
                Map.of(UserProfilePropertyName.valueOf("Inventory"),
                        UserProfilePropertyValue.valueOf(List.of("sword3", "sword4")))));
        UserProfilePropertyMap requestResult = new ExecuteOperationFactory().execute(operationRequest, userMap);

        assertThat(requestResult.userProfileProperties
                .get(UserProfilePropertyName.valueOf("Inventory"))).isEqualTo(UserProfilePropertyValue.valueOf(
                List.of("sword1", "sword2","sword3", "sword4")));
    }
    @Test
    void invalid_Operation_Request_Type() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.valueOf("multiply"),
                    new UserProfilePropertyMap(
                    Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(500))));
        });
    }
    @Test
    void unsupported_Operation_Request_Type() {
        ExecuteOperationFactory operationFactory = new ExecuteOperationFactory();
        UserProfilePropertyMap oldProperties = new UserProfilePropertyMap(
                Map.of(UserProfilePropertyName.valueOf("battleFought"), UserProfilePropertyValue.valueOf(100)));
        UserProfilePropertyMap newProperties = new UserProfilePropertyMap(
                Map.of(UserProfilePropertyName.valueOf("battleFought"), UserProfilePropertyValue.valueOf(500)));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.OTHERS, newProperties);
        assertThrows(OperationValidationException.class, () -> {
            operationFactory.execute(operationRequest, oldProperties);}  );
    }
}