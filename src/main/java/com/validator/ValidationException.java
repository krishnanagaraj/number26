package com.validator;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Custom API response message for HTTP_BAD_REQUEST, HTTP_NOT_FOUND.
 */
public class ValidationException extends WebApplicationException
{
    String errorResponse;

    public ValidationException(String error, Response.Status responseStatus) {
        super(Response.status(responseStatus).entity(error).type(MediaType.TEXT_PLAIN_TYPE).build());
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }
}
