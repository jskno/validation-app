package com.jskno.validationapp.web.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsValueMatchValidator
        implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value,
           ConstraintValidatorContext constraintValidatorContext) {

        Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
        Object fieldValueMatch = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

        if(fieldValue != null) {
            return fieldValue.equals(fieldValueMatch);
        } else {
            return fieldValueMatch == null;
        }
    }
}
