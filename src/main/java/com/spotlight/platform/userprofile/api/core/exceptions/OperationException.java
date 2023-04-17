package com.spotlight.platform.userprofile.api.core.exceptions;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;

public class OperationException extends RuntimeException {
    private OperationTypesEnum operationTypesEnum;
    public OperationException(OperationTypesEnum operationTypesEnum, String message) {
        super(message);
        this.operationTypesEnum = operationTypesEnum;
    }
}
