package com.codingshuttle.razorpay.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
// embeddable class = reusable sub table can be used different table
// reuse these field in different table
@Getter
@Setter
public class Money {
    @Column(name = "amount_units", nullable = false)
    private int amountUnits;

    @Column(name = "currency", nullable = false)

    private String currency;

    public Money() {
    }

// example: of(100, "INR")
    public static Money of(int amountUnits, String currency) {
       return new Money(amountUnits,currency);
    }

    public Money (int amountUnits,String currency) {
        this.currency = currency;
        this.amountUnits = amountUnits;
    }

    public Money inr(int amountUnits) {
        return new Money(amountUnits, "INR");
    }
    public Money usd(int amountUnits) {
        return new Money(amountUnits, "USD");
    }


    public Money add(Money other){
        if(!this.currency.equals(other.currency)){
            throw new IllegalArgumentException("Currencies do not match");
        }
        return new Money(this.amountUnits + other.amountUnits, this.currency);
    }
    public Money subtract(Money other){
        if(!this.currency.equals(other.currency)){
            throw new IllegalArgumentException("Currencies do not match");
        }
        return new Money(this.amountUnits - other.amountUnits, this.currency);
    }
}
