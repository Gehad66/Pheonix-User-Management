package com.spotlight.platform.userprofile.api.core.request;

import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class OperationResponseTest {
    @Test
    void serialization_WorksAsExpected() {
        assertThatJson(OperationResponseFixture.OPERATION_RESPONSE).isEqualTo(OperationResponseFixture.SERIALIZED_OPERATION_RESPONSE);
    }
}