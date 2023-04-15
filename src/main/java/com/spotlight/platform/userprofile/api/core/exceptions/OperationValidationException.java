package com.spotlight.platform.userprofile.api.core.exceptions;

import com.spotlight.platform.userprofile.api.core.enums.OperationTypesEnum;
import java.util.List;

public class OperationValidationException extends OperationException {
    private OperationTypesEnum operationTypesEnum;
    public OperationValidationException() {
        super();
    }
    public OperationValidationException(OperationTypesEnum operationTypesEnum, String message) {
        super(operationTypesEnum, message);
        this.operationTypesEnum = operationTypesEnum;
    }
//    public OperationValidationException(OperationTypesEnum operationTypesEnum, List<String> message) {
//        super(operationTypesEnum,String.join("\n",message));
//        this.operationTypesEnum = operationTypesEnum;
//    }
//    public OperationValidationException(OperationTypesEnum operationTypesEnum, Throwable throwable) {
//        super(operationTypesEnum, throwable);
//        this.operationTypesEnum = operationTypesEnum;
//    }
}
