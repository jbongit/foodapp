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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Restaurant {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long restId;
	private String restName;
	
	@Column(unique = true)
	private String restEmailId;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="address_id")
	private Address restAddress;
	private String restMobileno;
	private String restPassword;
	private String role;
	
	@OneToMany(mappedBy = "restId",cascade = CascadeType.ALL)
	private List<Product> productsList;

}
