package com.spotlight.platform.userprofile.api.core.request;

import com.spotlight.platform.helpers.FixtureHelpers;
import com.spotlight.platform.userprofile.api.core.enums.StatusEnum;

public class OperationResponseFixture {
    public static final String MSG = "test msg";
    public static final StatusEnum STATUS_ENUM = StatusEnum.valueOf("SUCCESS");
    public static final OperationResponse OPERATION_RESPONSE = new OperationResponse(MSG, STATUS_ENUM);
    public static final String SERIALIZED_OPERATION_RESPONSE = FixtureHelpers.fixture("/fixtures/core/request/operationResponse.json");
}
