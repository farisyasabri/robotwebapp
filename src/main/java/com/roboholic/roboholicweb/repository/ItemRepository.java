package com.roboholic.roboholicweb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.roboholic.roboholicweb.entity.Item;

import jakarta.transaction.Transactional;

public interface ItemRepository extends JpaRepository<Item,Long>{

    @Query(value = "SELECT * FROM product", nativeQuery = true)
    List<Item> getItemsName();

    // //search item name
    // @Query(value = "SELECT * FROM product WHERE 'product_name' = :productName", nativeQuery = true)
    // List<Item> findByItemNameContaining(@Param("productName") String productName);
    @Transactional
    List<Item> findByItemNameContaining(String itemName);

    //filter item by price
    // @Query(value = "Select * FROM product WHERE 'product_price' BETWEEN :from AND :to", nativeQuery = true)
    List<Item> findByItemPriceBetween(@Param("from") Double minPrice, @Param("to") Double maxPrice);
    
    @Query("SELECT i FROM Item i LEFT JOIN FETCH i.imageUrls WHERE i.itemID = :id")
    Optional<Item> findByIdWithImages(@Param("id") Long id);

}
