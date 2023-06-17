package com.roboholic.roboholicweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roboholic.roboholicweb.entity.Cart;

public interface CartRepository extends JpaRepository <Cart,Long>{
    
}
