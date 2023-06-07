package com.roboholic.roboholicweb.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartID;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "added_to", referencedColumnName = "cart_id")
    private List<Item> items = new ArrayList<>();

    // maybe need to save in the form of 2d list <Long(itemID), Integer(Quantity)>
    // private int itemQuantity;

    // @ManyToOne
    // private User user;


    /*
     * cart ||------|< item
     */
}
