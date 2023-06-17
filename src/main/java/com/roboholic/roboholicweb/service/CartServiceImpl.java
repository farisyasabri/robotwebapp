package com.roboholic.roboholicweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roboholic.roboholicweb.entity.Cart;
import com.roboholic.roboholicweb.repository.CartRepository;

import jakarta.transaction.Transactional;
@Service
public class CartServiceImpl implements CartService{
    
    @Autowired
    private CartRepository cartrepo;
    @Override
    @Transactional
    public Long addCart(Cart newCart){
        Long cart_id = cartrepo.save(newCart).getCartID();
        return cart_id;
    }

    public void removeCart(Long cart_id){
        cartrepo.deleteById(cart_id);
    }

}
