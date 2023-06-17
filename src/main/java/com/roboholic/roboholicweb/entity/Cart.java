package com.roboholic.roboholicweb.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    // @OneToMany(cascade = CascadeType.ALL)
    // private List<Item> items = new ArrayList<>();

    // @ManyToMany(mappedBy = "productOrder")
    // private Set<Item> itemSet = new HashSet<>();
    // maybe need to save in the form of 2d list <Long(itemID), Integer(Quantity)>
    // private int itemQuantity;

    @ManyToMany
    @JoinTable(name = "cart_item",
    joinColumns = @JoinColumn(name = "cart_id"),
    inverseJoinColumns = @JoinColumn(name="item_id"))
    private Set<Item> productOrder = new HashSet<>();

    // @ManyToOne
    // private User user;


    /*
     * cart ||------|< item
     */
}
