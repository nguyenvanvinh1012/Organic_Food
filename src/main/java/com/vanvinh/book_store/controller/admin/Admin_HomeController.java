package com.vanvinh.book_store.controller.admin;

import com.vanvinh.book_store.entity.OrderDetails;
import com.vanvinh.book_store.services.OrderDetailsService;
import com.vanvinh.book_store.services.OrderService;
import com.vanvinh.book_store.services.ProductService;
import com.vanvinh.book_store.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Admin_HomeController {
    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    OrderDetailsService orderDetailsService;
    @GetMapping("")
    public String home(Model model){
        model.addAttribute("totalSales", orderService.getTotalSales());
        model.addAttribute("totalItems", productService.getAllProducts().size());
        model.addAttribute("users", userService.getAllUsers().size());
        model.addAttribute("orderDelivery", orderService.getOrderDelivery());
        model.addAttribute("getTopRevenue", orderDetailsService.getTop5ProductsByRevenue());
        return "admin/home/index";
    }
}
