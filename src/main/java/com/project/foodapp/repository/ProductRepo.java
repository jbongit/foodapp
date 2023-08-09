package com.project.foodapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.foodapp.model.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{
	List<Product> findAllByRestId(Long restId);
	
	@Query("SELECT p FROM Product p where p.restId= :restId AND p.productId= :productId")
	Product findByProductIdandRestId(@Param("productId")Long productId,@Param("restId")Long restId);
}
