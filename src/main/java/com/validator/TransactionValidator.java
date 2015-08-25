package com.validator;

import javax.ws.rs.core.Response;

import com.persistence.beans.Transaction;
import com.util.TransactionServiceConstants;

/**
 * Used in transaction API validations.
 */
public class TransactionValidator {

    private Transaction transaction;
    public String errors;

    public TransactionValidator(Transaction transaction) {
        this.transaction = transaction;
    }

    public void validate() {
        this.processInputValidation();
        this.postValidation();
    }

    public void processInputValidation() {
        if(transaction.getAmount() <= 0) {
            errors = String.format(ErrorRepository.MANDATORY_PARAMETER_MISSING.getErrorMessage(), TransactionServiceConstants.AMOUNT);
        }
        if (isNullOrEmpty(transaction.getType())) {
            errors = String.format(ErrorRepository.MANDATORY_PARAMETER_MISSING.getErrorMessage(), TransactionServiceConstants.TYPE);
        }
        if (errors != null) {
            throw new ValidationException(errors, Response.Status.BAD_REQUEST);
        }
    }

    public void postValidation() {
        if (errors != null) {
            throw new ValidationException(errors, Response.Status.BAD_REQUEST);
        }
    }

    /**
     * Validate a string.
     *
     * @param val
     * @return
     */
    public static boolean isNullOrEmpty(String val) {
        boolean flag = false;
        if (val == null || "".equals(val)) {
            flag = true;
        }
        return flag;
    }
}

