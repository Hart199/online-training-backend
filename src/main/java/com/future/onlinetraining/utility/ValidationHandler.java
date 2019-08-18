package com.future.onlinetraining.utility;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.xml.validation.ValidatorHandler;
import java.util.ArrayList;
import java.util.List;

public class ValidationHandler {
    public static void validate(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ArrayList<String> errors = new ArrayList();
            for (Object object : bindingResult.getAllErrors()) {
                if(object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
                }
            }
            throw new ValidationException(errors.toArray(new String[0]));
        }
    }
}
