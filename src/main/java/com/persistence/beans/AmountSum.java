package com.persistence.beans;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Bean capturing the transaction amount's sum information
 */
@XmlRootElement(name = "AmountSum")
@XmlType(propOrder = { "sum" })// Output order
public class AmountSum {

    private Double sum;

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }
}
