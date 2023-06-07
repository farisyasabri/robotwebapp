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
@Table(name = "item")
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private int itemID;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_description")
    private String itemDescription;

    @Column(name = "item_price")
    private double itemPrice;

    @Column(name = "item_stock")
    private int itemStock;

    @Column(name = "item_categoryID")
    private int itemCategoryID;


    public Item(String itemName, String itemDescription, double itemPrice, int itemStock, int itemCategoryID) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.itemCategoryID = itemCategoryID;
    }
    
}
