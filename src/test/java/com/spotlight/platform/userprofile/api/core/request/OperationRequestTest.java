package com.spotlight.platform.userprofile.api.core.request;

import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class OperationRequestTest {
    @Test
    void serialization_WorksAsExpected() {
        assertThatJson(OperationRequestFixture.OPERATION_REQUEST).isEqualTo(OperationRequestFixture.SERIALIZED_OPERATION_REQUEST);
    }
}