package org.java5.demojava5.services;

import org.java5.demojava5.model.Cart;
import org.java5.demojava5.model.User;
import org.java5.demojava5.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    public Cart getCartByUser(User user) {
        return cartRepository.findByUserId(user.getId());
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }
}