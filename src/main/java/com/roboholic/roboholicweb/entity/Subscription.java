package com.roboholic.roboholicweb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private int subscriptionID;

    @Column(name = "subscription_name")
    private String subscriptionName;

    @Column(name = "subscription_description")
    private String subscriptionDescription;

    @Column(name = "subscription_price")
    private double subscriptionPrice;


    public Subscription(String subscriptionName, String subscriptionDescription, double subscriptionPrice) {
        this.subscriptionName = subscriptionName;
        this.subscriptionDescription = subscriptionDescription;
        this.subscriptionPrice = subscriptionPrice;
    }
    
}
