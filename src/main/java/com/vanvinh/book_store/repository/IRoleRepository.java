package com.vanvinh.book_store.repository;

import com.vanvinh.book_store.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r.id from Role r where r.name = ?1")
    Long getRoleIdByName(String roleName);
}
