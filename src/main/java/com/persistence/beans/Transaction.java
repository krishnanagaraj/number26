package com.persistence.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Bean capturing the Transaction details
 */
@XmlRootElement(name = "Transaction")
@XmlType(propOrder = { "amount", "type", "parentId" })// Output order
public class Transaction {

    private final static String COMMA = ",";

    private long transactionId;
    private double amount;
    private String type;
    private long parentId;

    @XmlElement(name="transaction_id")
    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    @XmlElement
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    @XmlElement
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name="parent_id")
    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("transactionId : " + this.transactionId + COMMA);
        sb.append("amount : " + this.amount + COMMA);
        sb.append("type : " + this.type + COMMA);
        sb.append("parentId : " + this.parentId + COMMA);
        return sb.toString();
    }
}
