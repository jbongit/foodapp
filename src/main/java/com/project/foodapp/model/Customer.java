package com.project.foodapp.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
 
 private String custEmailId;
 
 private String custAddress;
 
 private String custMobileno;
 
 private String custPassword;
 
 private String role;
 
 @OneToMany(mappedBy = "custId")
 private List<CartItem> cartItems;
}
