package com.jskno.validationapp.web.handler;

public class ApiFieldError {

    private final String objectName;
    private final String field;
    private final String message;

    public ApiFieldError(String objectName, String message) {
        this(objectName, null, message);
    }

    public ApiFieldError(String objectName, String field, String message) {
        this.objectName = objectName;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
