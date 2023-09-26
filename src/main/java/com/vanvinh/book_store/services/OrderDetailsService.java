package com.vanvinh.book_store.services;

import com.vanvinh.book_store.entity.OrderDetails;
import com.vanvinh.book_store.repository.IOderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {
    @Autowired
    IOderDetailsRepository oderDetailsRepository;
    public List<OrderDetails> getAllOrderDetailsByOrderId(Long id){
        return oderDetailsRepository.findAllOrderDetailsByOrderId(id);
    }
}
