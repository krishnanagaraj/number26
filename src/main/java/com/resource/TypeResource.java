package com.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.persistence.service.TypeService;
import com.util.TransactionServiceConstants;
import com.validator.ErrorRepository;
import com.validator.TransactionValidator;
import com.validator.ValidationException;

/**
 * This class exposes read operation on type resources.
 */
@Path(TypeResource.TYPE_RESOURCE_PATH)
public class TypeResource {

    public static final String TYPE_RESOURCE_PATH = "/types";
    private static final String TYPE_NAME_PATH_PARAM = "/{type}";

    /**
     * curl -X GET http://127.0.0.1:9090/transactionservice/types/{type}
     */
    @GET
    @Path(TYPE_NAME_PATH_PARAM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    public Response getDocument(@PathParam("type") String type) {
        try {
            if (TransactionValidator.isNullOrEmpty(type)) {
                throw new ValidationException(String.format(ErrorRepository.INVALID_PARAMETER.getErrorMessage(), TransactionServiceConstants.TYPE), Response.Status.BAD_REQUEST);
            }

            List<Long> transactionIds = getTypeService().getTransactionIds(type);
            if (transactionIds.isEmpty()) {
                throw new ValidationException(String.format(ErrorRepository.DOES_NOT_EXIST_PARAMETER.getErrorMessage(), TransactionServiceConstants.TYPE), Response.Status.NOT_FOUND);
            }
            return Response.ok(transactionIds).build();
        } catch (ValidationException ve) {
            return ve.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private TypeService getTypeService() {
        return new TypeService();
    }
}
