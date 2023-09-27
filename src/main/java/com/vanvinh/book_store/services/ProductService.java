package com.vanvinh.book_store.services;

import com.vanvinh.book_store.entity.Comment;
import com.vanvinh.book_store.entity.Product;
import com.vanvinh.book_store.repository.IProductRepository;
import com.vanvinh.book_store.ultils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private IProductRepository productRepository;
    public List<Product> getAllProducts(){
        return  productRepository.findAll();
    }
    public List<Product> getALlProductsByCategoryId(Long id){
        return productRepository.getAllProductById(id);
    }
    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow();
    }
    public void addProduct(Product product, MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        product.setImg(fileName);
        product.setActive(true);
        Product saveProduct = productRepository.save(product);
        String upLoadDir = "/product-images/" + saveProduct.getId();
        FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        productRepository.save(product);
    }
    public void updateProduct(Product product, MultipartFile multipartFile) throws IOException{
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setActive(existingProduct.getActive());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setWeight(product.getWeight());

        if(multipartFile != null && !multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            existingProduct.setImg(fileName);
            String upLoadDir = "/product-images/" + product.getId();
            FileUploadUtil.saveFile(upLoadDir, fileName, multipartFile);
        }
        productRepository.save(existingProduct);
    }
    public void deleteProductById(Long id){
        productRepository.deleteById(id);
    }
    public Page<Product> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.productRepository.findAll(pageable);
    }
    public List<Product> searchProductByName(String keyword){
        Pageable pageable = PageRequest.of(0,3);
        return productRepository.searchProductByNam(keyword, pageable);
    }
    public List<Product> getTopRatedProduct(){
        List<Product> topRatedProduct = new ArrayList<>();
        int count = 0;
        int totalRating,averageRating;
        for(Product product : productRepository.findAll()){
            totalRating = 0;
            averageRating = 0;
            for(Comment comment : product.getComments()){
                totalRating = totalRating + comment.getRating_value();
            }
            if(!product.getComments().isEmpty()){
                averageRating = totalRating / product.getComments().size();
                if(averageRating == 4 || averageRating == 5){
                    topRatedProduct.add(product);
                    count++;
                }
            }
            if(count == 3)
                break;
        }
        return  topRatedProduct;
    }
}
