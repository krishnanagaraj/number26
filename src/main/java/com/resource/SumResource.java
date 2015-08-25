package com.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.persistence.beans.AmountSum;
import com.persistence.service.TransactionService;
import com.util.TransactionServiceConstants;
import com.validator.ErrorRepository;
import com.validator.ValidationException;

/**
 * This class exposes read operation on sum resources.
 */
@Path(SumResource.SUM_RESOURCE_PATH)
public class SumResource {

    public static final String SUM_RESOURCE_PATH = "/sum";
    private TransactionService transactionService = getDocumentService();

    /**
     * curl -X GET http://127.0.0.1:9090/transactionservice/sum/{transaction_id}
     */
    @GET
    @Path(TransactionResource.TRANSACTION_ID_PATH_PARAM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    public Response getTotalAmount(@PathParam("transactionId") Long transactionId) {
        try {
            if (transactionId <= 0) {
                throw new ValidationException(String.format(ErrorRepository.INVALID_PARAMETER.getErrorMessage(), TransactionServiceConstants.TRANSACTION_ID), Response.Status.BAD_REQUEST);
            }
            AmountSum sum = new AmountSum();
            if (transactionService.isTransactionIdAvailable(transactionId)) {
                Double totalSum = transactionService.getTransactionSum(transactionId);
                sum.setSum(totalSum);
            } else {
                throw new ValidationException(String.format(ErrorRepository.DOES_NOT_EXIST_PARAMETER.getErrorMessage(), TransactionServiceConstants.TRANSACTION_ID), Response.Status.NOT_FOUND);
            }
            return Response.ok(sum).build();
        } catch (ValidationException ve) {
            return ve.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    private TransactionService getDocumentService() {
        return new TransactionService();
    }
}
