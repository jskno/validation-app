package com.jskno.validationapp.web.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GeneralAdviceController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationExceptions(MethodArgumentNotValidException ex) {
        ApiError apiError = new ApiError(ErrorConstants.ERR_VALIDATION);
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        List<ObjectError> globalErrors = result.getGlobalErrors();
        processFieldErrors(apiError, fieldErrors);
        processGlobalErrors(apiError, globalErrors);
        return apiError;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationEx(ConstraintViolationException ex) {
        ApiError apiError = new ApiError(ErrorConstants.ERR_VALIDATION);
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        processConstraintViolations(apiError, violations);
        return apiError;
    }

    private void processConstraintViolations(ApiError apiError, Set<ConstraintViolation<?>> violations) {
        violations.forEach(constraintViolation -> apiError.add(
                constraintViolation.getRootBeanClass().getName(),
                constraintViolation.getPropertyPath().toString(),
                constraintViolation.getMessage()));
    }

    private void processFieldErrors(ApiError apiError, List<FieldError> fieldErrors) {
        fieldErrors.forEach(fieldError -> apiError.add(fieldError.getObjectName(),
                fieldError.getField(), fieldError.getDefaultMessage()));
    }

    private void processGlobalErrors(ApiError apiError, List<ObjectError> globalErrors) {
        globalErrors.forEach(objectError -> apiError.add(objectError.getObjectName(),
                objectError.getDefaultMessage()));
    }

}
