package com.spotlight.platform.userprofile.api.web.resources;

import com.spotlight.platform.userprofile.api.core.profile.UserProfileService;
import com.spotlight.platform.userprofile.api.core.request.OperationRequest;
import com.spotlight.platform.userprofile.api.model.operations.ExecuteOperationFactory;
import com.spotlight.platform.userprofile.api.model.profile.UserProfile;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserId;
import com.spotlight.platform.userprofile.api.model.profile.primitives.UserProfilePropertyMap;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileUtils {
    private final UserProfileService userProfileService;
    public ProfileUtils(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    List<OperationRequest> removeDuplicateUsersResponse(List<OperationRequest> operationRequests){
        Map<UserId, OperationRequest> map = new HashMap<>();
        for (OperationRequest request : operationRequests) {
            map.put(request.userId(), request);
        }
        List<OperationRequest> operationUniqueRequestList = new ArrayList<>();
        for (Map.Entry<UserId, OperationRequest> entry : map.entrySet()){
            operationUniqueRequestList.add(entry.getValue());
        }
        return operationUniqueRequestList;
    }
    List<UserProfile> getUpdatedUsersList(List<OperationRequest> operationRequests){
        List<UserProfile> userProfiles = new ArrayList<>();
        List<OperationRequest> operationUniqueRequestList = removeDuplicateUsersResponse(operationRequests);
        for (OperationRequest operationRequest : operationUniqueRequestList) {
            userProfiles.add(userProfileService.get(operationRequest.userId()));
        }
        return userProfiles;
    }
    void updateUserProfile(UserId userId, Instant instant, UserProfilePropertyMap result){
        UserProfile updatedUser = new UserProfile(userId,instant,result);
        userProfileService.put(updatedUser);
    }
    void  postSingleUserProfile(OperationRequest operationRequest) {
        ExecuteOperationFactory operationFactory = new ExecuteOperationFactory();
        UserProfile user = userProfileService.get(operationRequest.userId());
        UserProfilePropertyMap oldProperties = userProfileService
                .get(operationRequest.userId()).userProfileProperties();
        UserProfilePropertyMap result = operationFactory
                .execute(operationRequest, oldProperties);
        updateUserProfile(user.userId(),user.latestUpdateTime(),result);
    }
}
