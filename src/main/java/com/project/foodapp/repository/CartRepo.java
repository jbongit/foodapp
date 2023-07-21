package com.project.foodapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.foodapp.model.CartItem;

import jakarta.transaction.Transactional;

public interface CartRepo extends JpaRepository<CartItem, Long>{
	
	@Transactional
	@Modifying
	@Query("DELETE FROM CartItem c where c.custId= :customerId AND c.product.productId= :productId")
	void deleteByProductIdandCustId(@Param("customerId")Long custId,@Param("productId")Long productId);
	
	@Query("SELECT c FROM CartItem c where c.custId= :customerId AND c.product.productId= :productId")
	Optional<CartItem> findByProductIdandCustId(@Param("customerId")Long custId,@Param("productId")Long productId);

}
