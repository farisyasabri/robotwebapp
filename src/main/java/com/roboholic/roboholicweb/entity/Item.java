package com.roboholic.roboholicweb.entity;

import java.beans.ConstructorProperties;
import java.beans.Transient;
import java.nio.channels.MulticastChannel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
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
    private Long itemID;

    @Column(name = "product_name")
    private String itemName;

    @Column(name = "product_description", length = 2000)
    @Size(max = 2000, message = "Description muct be less than 2000 characters")
    private String itemDescription;

    @Column(name = "product_price")
    private double itemPrice;

    @Column(name = "product_stock")
    private int itemStock;

    @Column(name = "product_categoryID")
    private int itemCategoryID;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;

    // Getters and setters
    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

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

    @ManyToMany(mappedBy = "productOrder")
    private Set<Cart> carts = new HashSet<>();

    //First method
    // @OneToOne
    // @MapsId //indicates that the primary key values will be copied from the Item entity
    // @JoinColumn(name="product_id")
    // // @OneToOne(mappedBy = "item", cascade = CascadeType.ALL)
    // private Resource resources;

    //Second method using join table
    // @OneToOne(mappedBy = "item")
    // private Resource resources;
    // @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) 
    // @JoinColumn(name = "resource_id")
    // private Resource resource;
}
