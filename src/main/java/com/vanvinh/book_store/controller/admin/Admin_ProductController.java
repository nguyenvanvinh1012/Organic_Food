package com.vanvinh.book_store.controller.admin;

import com.vanvinh.book_store.entity.Category;
import com.vanvinh.book_store.entity.Product;
import com.vanvinh.book_store.services.CategoryService;
import com.vanvinh.book_store.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class Admin_ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping("")
    public String index(Model model){
        if(model.containsAttribute("message")){
            model.addAttribute("message", model.getAttribute("message"));
        }
        return findPaginated(1, model);
    }
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<Product> page = productService.findPaginated(pageNo, pageSize);
        List<Product> listProduct = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("products", listProduct);
        return "admin/product/index";
    }
    @GetMapping("/add")
    public String addNew(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/product/add";
    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("product")Product product,
                      BindingResult bindingResult, Model model,
                      @RequestParam("image") MultipartFile multipartFile,
                      RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product/add";
        }
        productService.addProduct(product,multipartFile);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/product";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        Product editProduct = null;
        for(Product product: productService.getAllProducts()){
            if(product.getId() == id){
                editProduct = product;
            }
        }
        if(editProduct != null){
            model.addAttribute("product", editProduct);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product/edit";
        }else {
            return "not-found";
        }
    }

    @PostMapping("edit")
    public String edit(@Valid @ModelAttribute("product") Product updateProduct,
                       BindingResult bindingResult, Model model,
                       @RequestParam("image") MultipartFile multipartFile,
                       RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                model.addAttribute(error.getField() + "_error",
                        error.getDefaultMessage());
            }
            model.addAttribute("product",updateProduct);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "admin/product/edit";
        }
        productService.updateProduct(updateProduct, multipartFile);
        redirectAttributes.addFlashAttribute("message", "Save successfully!");
        return "redirect:/admin/product";
    }
}
