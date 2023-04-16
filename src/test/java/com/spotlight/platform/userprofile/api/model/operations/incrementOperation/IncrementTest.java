package com.spotlight.platform.userprofile.api.model.operations.incrementOperation;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.operations.ExecuteOperationFactory;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures.LAST_UPDATE_TIMESTAMP;
import static com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IncrementTest {
    UserProfile userProfile = new UserProfile(USER_ID, LAST_UPDATE_TIMESTAMP,
            Map.of(UserProfilePropertyName.valueOf("battleFought"), UserProfilePropertyValue.valueOf(100),
            UserProfilePropertyName.valueOf("questsNotCompleted"), UserProfilePropertyValue.valueOf(10)));
    @Test
    void increment_valid() {
        ExecuteOperationFactory operationFactory = new ExecuteOperationFactory();
        Map<UserProfilePropertyName, UserProfilePropertyValue> oldProperties = userProfile.userProfileProperties();
        Map<UserProfilePropertyName, UserProfilePropertyValue> newProperties = new HashMap<>();
        newProperties.put(UserProfilePropertyName.valueOf("battleFought"), UserProfilePropertyValue.valueOf(500));
        newProperties.put(UserProfilePropertyName.valueOf("questsNotCompleted"), UserProfilePropertyValue.valueOf(2));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.INCREMENT, newProperties);
        UserProfilePropertyValue result = operationFactory.execute(operationRequest, oldProperties).get(UserProfilePropertyName.valueOf("battleFought"));
        assertThat(result).isEqualTo(UserProfilePropertyValue.valueOf(600));
        }

    @Test
    void decrement_valid() {
        ExecuteOperationFactory operationFactory = new ExecuteOperationFactory();
        Map<UserProfilePropertyName, UserProfilePropertyValue> oldProperties = userProfile.userProfileProperties();
        Map<UserProfilePropertyName, UserProfilePropertyValue> newProperties = new HashMap<>();
        newProperties.put(UserProfilePropertyName.valueOf("battleFought"), UserProfilePropertyValue.valueOf(-10));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.INCREMENT, newProperties);
        UserProfilePropertyValue result = operationFactory.execute(operationRequest, oldProperties).get(UserProfilePropertyName.valueOf("battleFought"));
        assertThat(result).isEqualTo(UserProfilePropertyValue.valueOf(90));
    }
    @Test
    void non_existing_user_property() {
        ExecuteOperationFactory operationFactory = new ExecuteOperationFactory();
        Map<UserProfilePropertyName, UserProfilePropertyValue> oldProperties = userProfile.userProfileProperties();
        Map<UserProfilePropertyName, UserProfilePropertyValue> newProperties = new HashMap<>();
        newProperties.put(UserProfilePropertyName.valueOf("non-existing"), UserProfilePropertyValue.valueOf(-10));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.INCREMENT, newProperties);
        assertThrows(OperationValidationException.class, () -> operationFactory.execute(operationRequest, oldProperties));
    }
}