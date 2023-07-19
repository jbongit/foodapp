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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Vendor {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private long vendorId;
	private String vendorName;
	private String vendorEmail;
	private String vendorAddress;
	private String role;
	
	

}
