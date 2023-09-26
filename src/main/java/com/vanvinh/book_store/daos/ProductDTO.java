package com.vanvinh.book_store.daos;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String desc;
    private double price;
    private String image;
    private float quantity;
}
