package com.spotlight.platform.userprofile.api.core.enums;

import org.junit.jupiter.api.Test;

import static com.spotlight.platform.userprofile.api.core.enums.StatusEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class StatusEnumTest {
    @Test
    void valueOf() {
        assertEquals(StatusEnum.valueOf("SUCCESS"), SUCCESS);
        assertEquals(StatusEnum.valueOf("FAIL"), FAIL);
    }
}