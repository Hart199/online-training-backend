package com.future.onlinetraining.utility;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.xml.validation.ValidatorHandler;
import java.util.ArrayList;
import java.util.List;

public class ValidationHandler {
    public static void validate(BindingResult bindingResult) {
        ArrayList<String> errors = new ArrayList();
        for (Object objectError : bindingResult.getAllErrors())
            if(objectError instanceof FieldError) {
                FieldError fieldError = (FieldError) objectError;
                errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
        }
        throw new ValidationException(errors.toArray(new String[0]));
    }
}
