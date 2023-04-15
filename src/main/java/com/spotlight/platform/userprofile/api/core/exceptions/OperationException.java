package com.spotlight.platform.userprofile.api.core.exceptions;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;

public class OperationException extends RuntimeException {
    private OperationTypesEnum operationTypesEnum;
    public OperationException() {
        super();
    }
    public OperationException(OperationTypesEnum operationTypesEnum, String message) {
        super(message);
        this.operationTypesEnum = operationTypesEnum;
    }
//    public OperationException(OperationTypesEnum operationTypesEnum, Throwable throwable) {
//        super(throwable);
//        this.operationTypesEnum = operationTypesEnum;
//    }

//    public OperationTypesEnum getOperationTypesEnum() {
//        return operationTypesEnum;
//    }
}
