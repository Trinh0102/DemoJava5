package org.java5.demojava5.repository;

import org.java5.demojava5.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    void deleteByCategoryId(Long categoryId);
    List<Product> findByStatus(boolean status);
}