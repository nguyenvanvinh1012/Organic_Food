package com.vanvinh.book_store.repository;

import com.vanvinh.book_store.entity.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStatusOrderRepository extends JpaRepository<StatusOrder, Long> {
}
