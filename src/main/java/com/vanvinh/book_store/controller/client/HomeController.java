package com.vanvinh.book_store.controller.client;

import com.vanvinh.book_store.daos.Item;
import com.vanvinh.book_store.entity.Product;
import com.vanvinh.book_store.services.CartService;
import com.vanvinh.book_store.services.CategoryService;
import com.vanvinh.book_store.services.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @GetMapping("")
    public String home(Model model, HttpSession session){
        model.addAttribute("product_1", productService.getALlProductsByCategoryId(1L));
        model.addAttribute("product_2", productService.getALlProductsByCategoryId(2L));
        model.addAttribute("product_3", productService.getALlProductsByCategoryId(3L));
        model.addAttribute("product_4", productService.getALlProductsByCategoryId(4L));
        return "client/home/index";
    }
}
