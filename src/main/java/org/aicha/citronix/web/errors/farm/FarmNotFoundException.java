package org.aicha.citronix.web.errors.farm;

import java.util.UUID;

public class FarmNotFoundException extends RuntimeException {

    public FarmNotFoundException(UUID id) {
        super("Farm with ID " + id + " not found.");
    }
}
