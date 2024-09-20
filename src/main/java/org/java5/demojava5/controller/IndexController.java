package org.java5.demojava5.controller;

import org.java5.demojava5.model.Product;
import org.java5.demojava5.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private ProductServices productServices;

    @GetMapping
    public String listActiveProducts(Model model) {
        List<Product> activeProducts = productServices.findActiveProducts();
        model.addAttribute("products", activeProducts);
        return "index";
    }
}
