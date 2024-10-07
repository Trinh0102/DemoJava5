package org.java5.demojava5.controller;

import org.java5.demojava5.model.Cart;
import org.java5.demojava5.model.CartItem;
import org.java5.demojava5.model.Product;
import org.java5.demojava5.model.User;
import org.java5.demojava5.services.CartItemService;
import org.java5.demojava5.services.CartService;
import org.java5.demojava5.services.ProductServices;
import org.java5.demojava5.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductServices productServices;

    @Autowired
    private UserService userService;

    @GetMapping
    public String viewCart(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        Cart cart = cartService.getCartByUser(user);
        model.addAttribute("cart", cart);
        return "cart/view";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addToCart(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam Long productId, @RequestParam int quantity) {
        User user = userService.findByUsername(userDetails.getUsername());
        Cart cart = cartService.getCartByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartService.saveCart(cart);
        }
        Product product = productServices.getProductById(productId);
        if (product == null) {
            return "Product not found";
        }
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        if (cartItem.getQuantity() > product.getQuantity()) {
            return "Out of stock";
        }
        cartItemService.saveCartItem(cartItem);
        return "Product added to cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam Long cartItemId) {
        cartItemService.deleteCartItem(cartItemId);
        return "redirect:/cart";
    }
}