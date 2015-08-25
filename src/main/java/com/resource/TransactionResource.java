package com.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.WebApplicationException;

import com.persistence.beans.Transaction;
import com.persistence.service.TransactionService;
import com.util.TransactionServiceConstants;
import com.validator.ErrorRepository;
import com.validator.TransactionValidator;
import com.validator.ValidationException;


/**
 * This class exposes CRUD operations on transaction(s) resources.
 */
@Path(TransactionResource.TRANSACTION_RESOURCE_PATH)
public class TransactionResource {

    public static final String TRANSACTION_RESOURCE_PATH = "/transaction";
    public static final String TRANSACTION_ID_PATH_PARAM = "/{transactionId}";
    private TransactionService transactionService = getDocumentService();

    /**
     * curl -X GET http://127.0.0.1:9090/transactionservice/transaction/{transactionId}
     */
    @GET
    @Path(TRANSACTION_ID_PATH_PARAM)
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    public Response getDocument(@PathParam("transactionId") long transactionId) {
        Transaction transaction = new Transaction();
        try {
            // Validate the provided input
            if (transactionId <= 0) {
                throw new ValidationException(String.format(ErrorRepository.INVALID_PARAMETER.getErrorMessage(), TransactionServiceConstants.TRANSACTION_ID), Response.Status.BAD_REQUEST);
            }
            if (transactionService.isTransactionIdAvailable(transactionId)) {
                transaction = transactionService.getTransaction(transactionId);
            } else {
                throw new ValidationException(String.format(ErrorRepository.DOES_NOT_EXIST_PARAMETER.getErrorMessage(), TransactionServiceConstants.TRANSACTION_ID), Response.Status.NOT_FOUND);
            }
        } catch (ValidationException ve) {
            return ve.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
        return Response.ok(transaction).build();
    }

    /**
     * curl -X POST http://127.0.0.1:9090/transactionservice/transaction
     * <p/>
     * {"amount":3000,"type":"retail"}
     */
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })
    public Response addDocument(Transaction transaction) {
        try {
            Transaction transactionAdded = new Transaction();

            // Validate the provided input
            new TransactionValidator(transaction).validate();

            if((transaction.getParentId() > 0) && !transactionService.isTransactionIdAvailable(transaction.getParentId())) {
                throw new ValidationException(String.format(ErrorRepository.INVALID_PARAMETER.getErrorMessage(), TransactionServiceConstants.PARENT_ID), Response.Status.BAD_REQUEST);
            }

            // create a transaction
            transactionAdded = transactionService.addTransaction(transaction);
            if (null == transactionAdded) {
                throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
            }
            return Response.ok(transactionAdded).build();
        } catch (ValidationException ve) {
            return ve.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * curl -X PUT http://127.0.0.1:9090/transactionservice/transaction/{transactionId}
     */
    @PUT
    @Path(TRANSACTION_ID_PATH_PARAM)
    @Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    public Response updateDocument(@PathParam("transactionId") Long transactionId, Transaction transaction) {
        Transaction transactionUpdated = new Transaction();
        try {
            // Validate the provided input
            if (transactionId <= 0) {
                throw new ValidationException(String.format(ErrorRepository.INVALID_PARAMETER.getErrorMessage(), TransactionServiceConstants.TRANSACTION_ID), Response.Status.BAD_REQUEST);
            }
            new TransactionValidator(transaction).validate();
            if (transaction.getParentId() >= 0 && transactionId == transaction.getParentId() && !transactionService.isTransactionIdAvailable(transaction.getParentId())) {
                throw new ValidationException(String.format(ErrorRepository.INVALID_PARAMETER.getErrorMessage(), TransactionServiceConstants.PARENT_ID), Response.Status.BAD_REQUEST);
            }

            // Update the provided transaction
            if (transactionService.isTransactionIdAvailable(transactionId)) {
                transactionUpdated = transactionService.updateTransaction(transactionId, transaction);
            } else {
                throw new ValidationException(String.format(ErrorRepository.DOES_NOT_EXIST_PARAMETER.getErrorMessage(), TransactionServiceConstants.TRANSACTION_ID), Response.Status.NOT_FOUND);
            }
            return Response.ok(transactionUpdated).build();
        } catch (ValidationException ve) {
            return ve.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * curl -X DELETE http://127.0.0.1:9090/transactionservice/transaction/{transactionId}
     */
    @DELETE
    @Path(TRANSACTION_ID_PATH_PARAM)
    @Produces({ MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.APPLICATION_JSON })
    public Response deleteDocument(@PathParam("transactionId") Long transactionId) {
        Transaction transactionDeleted = new Transaction();
        try {
            if (transactionId <= 0) {
                throw new ValidationException(String.format(ErrorRepository.INVALID_PARAMETER.getErrorMessage(), TransactionServiceConstants.TRANSACTION_ID), Response.Status.BAD_REQUEST);
            }

            if (transactionService.isTransactionIdAvailable(transactionId)) {
                transactionDeleted = transactionService.deleteTransaction(transactionId);
            } else {
                throw new ValidationException(String.format(ErrorRepository.DOES_NOT_EXIST_PARAMETER.getErrorMessage(), TransactionServiceConstants.TRANSACTION_ID), Response.Status.NOT_FOUND);
            }
            return Response.ok(transactionDeleted).build();
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
