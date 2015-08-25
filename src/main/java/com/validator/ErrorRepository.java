package com.validator;

/**
 * Error enums
 */
public enum ErrorRepository {

    MANDATORY_PARAMETER_MISSING("Mandatory '%s' parameter missing."),
    INVALID_PARAMETER("Invalid '%s' parameter."),
    DOES_NOT_EXIST_PARAMETER("Provided '%s' parameter. Does not exist.");
    private String error = "";

    ErrorRepository(String error) {
        this.error = error;
    }

    public String getErrorMessage() {
        return error;
    }

}
