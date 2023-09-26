package com.vanvinh.book_store.repository;

import com.vanvinh.book_store.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOderDetailsRepository extends JpaRepository<OrderDetails, Long> {
    @Query("SELECT o from OrderDetails o WHERE o.orders.id = ?1")
    List<OrderDetails> findAllOrderDetailsByOrderId(Long id);
}
