package com.roboholic.roboholicweb.service;

import com.roboholic.roboholicweb.entity.Cart;

public interface CartService {
    public Long addCart(Cart newcart);
    public void removeCart(Long id);
}
