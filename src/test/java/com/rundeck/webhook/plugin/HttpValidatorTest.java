package com.rundeck.webhook.plugin;

import com.dtolabs.rundeck.core.plugins.configuration.ValidationException;
import junit.framework.TestCase;

public class HttpValidatorTest extends TestCase {
    public void testIsValid() throws Exception {
        assertEquals(true,new HttpValidator().isValid("GET"));
    }
    public void testIsNotValid() throws Exception {
        try {
            new HttpValidator().isValid("PST");
            fail("Expected an exception");
        } catch(ValidationException vex) {
            assertEquals("Should be one of: [GET, POST, PUT, HEAD, PATCH, OPTIONS, DELETE]",vex.getMessage());
        }

    }

}