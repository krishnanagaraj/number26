package com.persistence.service;

import java.util.HashMap;
import java.util.Map;

import com.persistence.beans.Transaction;

/**
 * Service layer implementation class for APIs.
 * Provides an implementation for API related methods.
 * These methods include API CRUD Ops and provides additional
 * methods which can be used for validation purpose as well.
 */
public class TransactionService {

    private static long transactionIdCounter = 1;

    /**
     * For simplicity HashMap is simulated as a database. It's not difficult to replace the HashMap with back-end database
     * <p/>
     * <Key, Value> = <DocumentId, Transaction>
     */
    public static Map<Long, Transaction> transactionStore = new HashMap<Long, Transaction>();

    public Transaction addTransaction(Transaction transaction) {
        transaction.setTransactionId(transactionIdCounter);
        transactionStore.put(transactionIdCounter, transaction);
        transactionIdCounter += 1;
        return transactionStore.get(transactionIdCounter - 1);
    }

    public boolean isTransactionIdAvailable(Long transactionId) {
        if(transactionStore.containsKey(transactionId)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
    public Transaction getTransaction(Long transactionId) {
        return transactionStore.get(transactionId);
    }

    public Transaction deleteTransaction(Long transactionId) {
       return transactionStore.remove(transactionId);
    }

    public Transaction updateTransaction(Long transactionId, Transaction transaction) {
        if (transactionStore.containsKey(transactionId)) {
            Transaction originalTransaction = transactionStore.get(transactionId);
            updateOriginalTransaction(transaction, originalTransaction);
            transactionStore.put(transactionId, originalTransaction);
            return transactionStore.get(transactionId);
        }
        return null;
    }

    private void updateOriginalTransaction(Transaction transaction, Transaction originalTransaction) {
        if (transaction.getAmount() != 0) {
            originalTransaction.setAmount(transaction.getAmount());
        }

        if (transaction.getType() != null) {
            originalTransaction.setType(transaction.getType());
        }

        if (transaction.getParentId() != 0) {
            if(transactionStore.containsKey(transaction.getParentId())) {
                originalTransaction.setParentId(transaction.getParentId());
            }
        }
    }

    public Double getTransactionSum(Long transactionId) {
        Transaction transaction = transactionStore.get(transactionId);
        Double sum = transaction.getAmount();
        while (transaction.getParentId() > 0) {
            transaction = transactionStore.get(transaction.getParentId());
            sum += transaction.getAmount();
        }
        return sum;
    }

}
