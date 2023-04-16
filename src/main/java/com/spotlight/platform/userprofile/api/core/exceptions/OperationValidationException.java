package com.spotlight.platform.userprofile.api.core.exceptions;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;

public class OperationValidationException extends OperationException {
    private OperationTypesEnum operationTypesEnum;
    public OperationValidationException(OperationTypesEnum operationTypesEnum, String message) {
        super(operationTypesEnum, message);
        this.operationTypesEnum = operationTypesEnum;
    }
}
