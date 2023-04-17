package com.spotlight.platform.userprofile.api.web.resources;

import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.core.request.BatchOperationRequest;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.operations.ExecuteOperationFactory;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyMap;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

    private final UserProfileService userProfileService;
    private ProfileUtils profileUtils;

    @Inject
    public ProfileResource(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
        this.profileUtils = new ProfileUtils(userProfileService);
    }

    @POST
    @Consumes("application/json")
    public List<UserProfile> postUserProfile(BatchOperationRequest batchOperationRequest) {
        List<OperationRequest> operationRequests = batchOperationRequest.batchOperation();
        for (OperationRequest operationRequest : operationRequests) {
            profileUtils.postSingleUserProfile(operationRequest);
        }
        return profileUtils.getUpdatedUsersList(operationRequests);
    }
}
