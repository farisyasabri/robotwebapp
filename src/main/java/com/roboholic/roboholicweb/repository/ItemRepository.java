package com.roboholic.roboholicweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.roboholic.roboholicweb.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Long>{
    List<Item> findByItemNameContaining(String productName);

    @Query(value = "SELECT 'product_name' FROM product", nativeQuery = true)
    List<Item> getItemsName();
}
