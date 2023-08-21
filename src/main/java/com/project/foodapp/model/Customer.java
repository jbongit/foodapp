package com.project.foodapp.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long custId;

	private String custName;

	@Column(unique = true)
	private String custEmailId;

	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="address_id")
	private Address custAddress;

	private String custMobileno;

	private String custPassword;

	@NotBlank(message = "Role Can Not Be Empty !!")
	private String role;

	@OneToMany(mappedBy = "custId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<CartItem> cartItems;
	
	@OneToMany(mappedBy = "custId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	private List<Order> orderItems;
}
