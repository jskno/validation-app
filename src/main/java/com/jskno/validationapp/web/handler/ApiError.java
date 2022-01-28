package com.jskno.validationapp.web.handler;

import java.util.ArrayList;
import java.util.List;

public class ApiError {

    private final String message;
    private final String description;
    private final List<ApiFieldError> apiFieldErrors;

    public ApiError(String message) {
        this(message, null);
    }

    public ApiError(String message, String description) {
        this(message, description, new ArrayList<>());
    }

    public ApiError(String message, String description, List<ApiFieldError> apiFieldErrors) {
        this.message = message;
        this.description = description;
        this.apiFieldErrors = apiFieldErrors;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public List<ApiFieldError> getApiFieldErrors() {
        return apiFieldErrors;
    }

    public void add(String objectName, String field, String defaultMessage) {
        getApiFieldErrors().add(new ApiFieldError(objectName, field, defaultMessage));
    }

    public void add(String objectName, String defaultMessage) {
        getApiFieldErrors().add(new ApiFieldError(objectName, defaultMessage));
    }
}
