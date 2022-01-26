package com.falsepattern.json.schema;

public enum ValidationStatus {
    UNVALIDATED, VALIDATED;

    public boolean isValidated() {
        return this == VALIDATED;
    }
}
