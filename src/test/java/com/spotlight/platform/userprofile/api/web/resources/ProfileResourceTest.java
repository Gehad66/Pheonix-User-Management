package com.spotlight.platform.userprofile.api.web.resources;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;
import com.spotlight.platform.userprofile.api.web.UserProfileApiApplication;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import ru.vyarus.dropwizard.guice.test.ClientSupport;
import ru.vyarus.dropwizard.guice.test.jupiter.ext.TestDropwizardAppExtension;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.Map;
import java.util.Optional;

import static com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileResourceTest {

    @RegisterExtension
    static TestDropwizardAppExtension APP = TestDropwizardAppExtension.forApp(UserProfileApiApplication.class)
            .randomPorts()
            .hooks(builder -> builder.modulesOverride(new AbstractModule() {
                @Provides
                @Singleton
                public UserProfileDao getUserProfileDao() {
                    return mock(UserProfileDao.class);
                }
            }))
            .randomPorts()
            .create();
    @BeforeEach
    void beforeEach(UserProfileDao userProfileDao) {
        reset(userProfileDao);
    }

    @Test
    void updateUserProfile_Success(ClientSupport client, UserProfileDao userProfileDao){
    when(userProfileDao.get(any(UserId.class))).thenReturn(Optional.of(UserProfileFixtures.USER_PROFILE_INCREMENT));
    ProfileResource profileResource = new ProfileResource(new UserProfileService(userProfileDao));
        UserProfile updateUserProfile = profileResource.updateUserProfile(USER_ID, LAST_UPDATE_TIMESTAMP,
                Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(100)));
        assertThat(updateUserProfile)
                .isEqualTo(UserProfileFixtures.USER_PROFILE_INCREMENT);
    }
    @Nested
    @DisplayName("postUserProfile")
    class PostUserProfile {
        private static final String URL = "/profile";
        public static final OperationRequest OPERATION_REQUEST = new OperationRequest(USER_ID,
                OperationTypesEnum.REPLACE,
                Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(300)));

//        @Test
        void userProfile_IncrementOperation_Success(ClientSupport client, UserProfileDao userProfileDao) {
//TODO: check
            when(userProfileDao.get(any(UserId.class))).thenReturn(Optional.of(USER_PROFILE_INCREMENT));
            var response = client.targetRest().path(URL)
//                    .resolveTemplate(USER_ID_PATH_PARAM, UserProfileFixtures.NON_EXISTING_USER_ID)
                    .request().post(Entity.entity(OPERATION_REQUEST, MediaType.APPLICATION_JSON));

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
//            UserProfile pr = response.readEntity(UserProfile.class);
            assertThatJson(response.readEntity(UserProfile.class)).isEqualTo(USER_PROFILE_INCREMENT);
        }
    }

}