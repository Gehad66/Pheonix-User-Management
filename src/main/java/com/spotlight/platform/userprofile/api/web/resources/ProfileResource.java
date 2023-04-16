package com.spotlight.platform.userprofile.api.web.resources;

import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.core.request.BatchOperationRequest;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.operations.ExecuteOperationFactory;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyName;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyValue;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

    private final UserProfileService userProfileService;

    @Inject
    public ProfileResource(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    UserProfile updateUserProfile(UserId userId, Instant instant, Map<UserProfilePropertyName, UserProfilePropertyValue> result){
        UserProfile updatedUser = new UserProfile(userId,instant,result);
        userProfileService.put(updatedUser);
        return userProfileService.get(userId);
    }
    public UserProfile postSingleUserProfile(OperationRequest operationRequest) {
        ExecuteOperationFactory operationFactory = new ExecuteOperationFactory();
        UserProfile user = userProfileService.get(operationRequest.userId());
        Map<UserProfilePropertyName, UserProfilePropertyValue> oldProperties = userProfileService
                .get(operationRequest.userId()).userProfileProperties();
        Map<UserProfilePropertyName, UserProfilePropertyValue> result = operationFactory
                .execute(operationRequest, oldProperties);

        return updateUserProfile(user.userId(),user.latestUpdateTime(),result);
    }
    @POST
    @Consumes("application/json")
    public List<UserProfile> postUserProfile(BatchOperationRequest batchOperationRequest) {
        List<UserProfile> userProfiles = new ArrayList<>();
        List<OperationRequest> operationRequests = batchOperationRequest.batchOperation();
        for (OperationRequest operationRequest : operationRequests) {
            userProfiles.add(postSingleUserProfile(operationRequest));
        }
        return userProfiles;
    }
}
