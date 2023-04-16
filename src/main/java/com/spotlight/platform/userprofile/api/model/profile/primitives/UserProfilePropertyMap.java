package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public class UserProfilePropertyMap {
    public Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties;
    @JsonCreator
    public UserProfilePropertyMap(Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties) {
        this.userProfileProperties = userProfileProperties;
    }
    public UserProfilePropertyMap() {
        this.userProfileProperties = new HashMap<>();
    }
    public static UserProfilePropertyMap valueOf(Map<UserProfilePropertyName, UserProfilePropertyValue> userProfileProperties) {
        return new UserProfilePropertyMap(userProfileProperties);
    }
    @JsonValue
    protected Map<UserProfilePropertyName, UserProfilePropertyValue> getPropertyMap() {
        return userProfileProperties;
    }


}
