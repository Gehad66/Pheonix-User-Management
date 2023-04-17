package com.spotlight.platform.userprofile.api.model.profile.primitives;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class UserProfileFixtures {
    public static final UserId USER_ID = UserId.valueOf("existing-user-id");
    public static final UserId NON_EXISTING_USER_ID = UserId.valueOf("non-existing-user-id");
    public static final UserId INVALID_USER_ID = UserId.valueOf("invalid-user-id-%");

    public static final Instant LAST_UPDATE_TIMESTAMP = Instant.parse("2021-06-01T09:16:36.123Z");

    public static final UserProfile USER_PROFILE = new UserProfile(USER_ID, LAST_UPDATE_TIMESTAMP,
            new UserProfilePropertyMap( new HashMap<>(
            Map.of(UserProfilePropertyName.valueOf("property1"), UserProfilePropertyValue.valueOf("property1Value")))));
    public static final UserProfile NEW_USER_PROFILE = new UserProfile(NON_EXISTING_USER_ID, LAST_UPDATE_TIMESTAMP,
            new UserProfilePropertyMap( new HashMap<>(
            Map.of(UserProfilePropertyName.valueOf("property1"), UserProfilePropertyValue.valueOf("property1Value")))));
    public static final UserProfile USER_PROFILE_INCREMENT = new UserProfile(USER_ID, LAST_UPDATE_TIMESTAMP,
            new UserProfilePropertyMap( new HashMap<>(
            Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(100)))));
    public static final String SERIALIZED_USER_PROFILE = FixtureHelpers.fixture("/fixtures/model/profile/userProfile.json");
    public static final String SERIALIZED_NEW_USER_PROFILE = FixtureHelpers.fixture("/fixtures/model/profile/newUserProfile.json");
    public static final String SERIALIZED_USER_INCREMENT_PROFILE = FixtureHelpers.fixture("/fixtures/model/profile/incrementUserProfile.json");

}
