package com.project.foodapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProductOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
	private Long custId;
	private Long dpId;
	private Long restId;
	private Long productId;
}
