package com.rundeck.webhook.plugin;

import com.dtolabs.rundeck.core.plugins.configuration.PropertyValidator;
import com.dtolabs.rundeck.core.plugins.configuration.ValidationException;
import java.util.Arrays;

public class HttpValidator implements PropertyValidator {

    public boolean isValid(String method) throws ValidationException {
        try {
            HttpMethod.valueOf(method);
            return true;
        } catch(Exception ex) {
            throw new ValidationException("Should be one of: "+ Arrays.toString(HttpMethod.values()));
        }
    }
}
