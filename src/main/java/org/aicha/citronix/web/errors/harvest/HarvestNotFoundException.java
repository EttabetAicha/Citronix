package org.aicha.citronix.web.errors.harvest;

public class HarvestNotFoundException extends RuntimeException {
    public HarvestNotFoundException(String message) {
        super(message);
    }
}
