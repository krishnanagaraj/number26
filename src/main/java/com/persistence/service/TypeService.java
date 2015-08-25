package com.persistence.service;

import java.util.ArrayList;
import java.util.List;

import com.persistence.beans.Transaction;

/**
 * Service layer implementation class for APIs.
 * Provides an implementation for API related methods.
 * These methods include API CRUD Ops and provides additional
 * methods which can be used for validation purpose as well.
 */
public class TypeService {

    public List<Long> getTransactionIds(String type){

        List<Transaction> transactions =  new ArrayList<Transaction>(TransactionService.transactionStore.values());

        List<Long> transactionIds = new ArrayList<Long>();

        for(Transaction transaction : transactions)  {
            if(transaction.getType().equalsIgnoreCase(type)) {
                transactionIds.add(transaction.getTransactionId());
            }
        }

        return transactionIds;
    }
}
