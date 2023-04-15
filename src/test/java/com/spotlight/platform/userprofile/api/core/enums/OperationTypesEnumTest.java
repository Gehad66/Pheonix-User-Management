package com.spotlight.platform.userprofile.api.core.enums;

import org.junit.jupiter.api.Test;

import static com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum.*;
import static org.junit.jupiter.api.Assertions.*;

class OperationTypesEnumTest {
    @Test
    void valueOf() {
        assertEquals(OperationTypesEnum.valueOf("REPLACE"), REPLACE);
        assertEquals(OperationTypesEnum.valueOf("INCREMENT"), INCREMENT);
        assertEquals(OperationTypesEnum.valueOf("COLLECT"), COLLECT);
    }
}