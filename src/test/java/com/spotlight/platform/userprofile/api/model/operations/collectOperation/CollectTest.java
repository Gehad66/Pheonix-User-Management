package com.spotlight.platform.userprofile.api.model.operations.collectOperation;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.operations.ExecuteOperationFactory;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyMap;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures.LAST_UPDATE_TIMESTAMP;
import static com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures.USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CollectTest {

    UserProfile userProfile = new UserProfile(USER_ID, LAST_UPDATE_TIMESTAMP,
            new UserProfilePropertyMap(Map.of(UserProfilePropertyName.valueOf("inventory"), UserProfilePropertyValue.valueOf(new String[] {"inventory", "inventory2"}),
                UserProfilePropertyName.valueOf("tools"), UserProfilePropertyValue.valueOf(new String[] {"tools", "tools2"}))));
    UserProfilePropertyName castString(String property){
        return UserProfilePropertyName.valueOf(property);
    }
    @Test
    void collect_valid_operation() {
        ExecuteOperationFactory operationFactory = new ExecuteOperationFactory();
        UserProfilePropertyMap oldProperties = userProfile.userProfileProperties();
        UserProfilePropertyMap newProperties = new UserProfilePropertyMap();
        newProperties.userProfileProperties.put(UserProfilePropertyName.valueOf("inventory"), UserProfilePropertyValue.valueOf(new String[] {"sword1", "sword2"}));
        newProperties.userProfileProperties.put(UserProfilePropertyName.valueOf("tools"), UserProfilePropertyValue.valueOf(new String[] {"tools3", "tools4"}));

        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.COLLECT, newProperties);
        UserProfilePropertyMap result = operationFactory.execute(operationRequest, oldProperties);

        UserProfilePropertyValue ExpectedInventoryOutput = UserProfilePropertyValue.valueOf( Arrays.asList(
                new String[] {"inventory", "inventory2", "sword1", "sword2"}));
        UserProfilePropertyValue ExpectedToolsOutput = UserProfilePropertyValue.valueOf( Arrays.asList(
                new String[] {"tools", "tools2", "tools3", "tools4"}));

        assertThat(result.userProfileProperties.get(castString("inventory")).equals(ExpectedInventoryOutput)).isTrue();
        assertThat(result.userProfileProperties.get(castString("tools")).equals(ExpectedToolsOutput)).isTrue();
    }
    @Test
    void non_existing_user_property() {
        ExecuteOperationFactory operationFactory = new ExecuteOperationFactory();
        UserProfilePropertyMap oldProperties = userProfile.userProfileProperties();
        UserProfilePropertyMap newProperties = new UserProfilePropertyMap();
        newProperties.userProfileProperties.put(UserProfilePropertyName.valueOf("non-existing"), UserProfilePropertyValue.valueOf(new String[] {"sword1", "sword2"}));
        OperationRequest operationRequest = new OperationRequest(USER_ID, OperationTypesEnum.COLLECT, newProperties);
        assertThrows(OperationValidationException.class, () -> {
            operationFactory.execute(operationRequest, oldProperties);}  );
    }
}