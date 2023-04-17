package com.spotlight.platform.userprofile.api.web.resources;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import com.spotlight.platform.userprofile.api.core.exceptions.OperationValidationException;
import com.spotlight.platform.userprofile.api.core.profile.persistence.UserProfileDao;
import com.spotlight.platform.userprofile.api.core.request.BatchOperationRequest;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.profile.primitives.*;
import com.spotlight.platform.userprofile.api.web.UserProfileApiApplication;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import ru.vyarus.dropwizard.guice.test.ClientSupport;
import ru.vyarus.dropwizard.guice.test.jupiter.ext.TestDropwizardAppExtension;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.*;

import static com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures.USER_ID;
import static com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfileFixtures.USER_PROFILE_INCREMENT;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Execution(ExecutionMode.SAME_THREAD)
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
    @Nested
    @DisplayName("postUserProfile")
    class PostUserProfile {
        private static final String URL = "/profile";
        private static OperationRequest OPERATION_REQUEST = new OperationRequest(USER_ID,
                OperationTypesEnum.INCREMENT,
                new UserProfilePropertyMap( new HashMap<>(
                Map.of(UserProfilePropertyName.valueOf("currentGold"), UserProfilePropertyValue.valueOf(100)))));
        private static  List<OperationRequest> OPERATION_REQUEST_LIST = new ArrayList<>(){{ add(OPERATION_REQUEST); }};
        private static  BatchOperationRequest batchOperationRequest= new BatchOperationRequest(OPERATION_REQUEST_LIST);
        @Test
        void userProfile_IncrementOperation_Success(ClientSupport client, UserProfileDao userProfileDao) {
            when(userProfileDao.get(any(UserId.class))).thenReturn(Optional.of(USER_PROFILE_INCREMENT));
            var response = client.targetRest().path(URL)
                    .request().post(Entity.entity(batchOperationRequest, MediaType.APPLICATION_JSON));

            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK_200);
            assertThatJson(response.readEntity(String.class)).isEqualTo(UserProfileFixtures.SERIALIZED_USER_INCREMENT_PROFILE);
        }

        @Test
        void operationValidationException_returns500(ClientSupport client, UserProfileDao userProfileDao) {
            when(userProfileDao.get(any(UserId.class))).thenThrow(new RuntimeException("Some unhandled exception"));
//                    new OperationValidationException(OperationTypesEnum.INCREMENT, "Some unhandled exception"));
            var response = client.targetRest().path(URL)
                    .request().post(Entity.entity(batchOperationRequest, MediaType.APPLICATION_JSON));
            System.out.println("##############");
            System.out.println(response.getStatus());
            System.out.println(response.readEntity(String.class));
            assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR_500);
        }
    }
}