package org.aicha.citronix.web.errors.field;

public class FieldNotFoundException extends RuntimeException {
    public FieldNotFoundException(String string) {
        super(string);
    }
}