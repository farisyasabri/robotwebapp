package com.roboholic.roboholicweb.entity;

import java.util.HashSet;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int itemID;

    @Column(name = "product_name")
    private String itemName;

    @Column(name = "product_description")
    private String itemDescription;

    @Column(name = "product_price")
    private double itemPrice;

    @Column(name = "product_stock")
    private int itemStock;

    @Column(name = "product_categoryID")
    private int itemCategoryID;

    @Column(name = "imageproduct")
    private byte[] imageItem;

    public Item(String itemName, String itemDescription, double itemPrice, int itemStock, int itemCategoryID) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.itemCategoryID = itemCategoryID;
    }

    public String toString(){
        return itemID +" " + itemName+" " + itemDescription+ " " +itemPrice+" " +itemCategoryID+" " +itemStock;
    }
    // @ManyToMany
    // @JoinTable(name = "order",
    //     joinColumns = @JoinColumn(name="item_id"),
    //     inverseJoinColumns = @JoinColumn(name="cart_id"))
    // private Set<Cart> productOrder = new HashSet<>();
    @ManyToMany(mappedBy = "productOrder")
    private Set<Cart> carts = new HashSet<>();
}
