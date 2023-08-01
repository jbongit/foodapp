package com.project.foodapp.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryPartnerDTO {
	
	@NotBlank(message = "Name Can Not Be Empty !!")
	private String dpName;
	
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z_.-]+$", message = "Must Be in Form of 'user@example.com'")
	@NotBlank(message = "Email Can Not Be Empty !!")
	private String dpEmailId;
	
	@NotBlank(message = "Mobile Number Can Not Be Empty !!")
	@Pattern(regexp = "^[0-9]{10}$", message = "Mobile Number must consists of 10 Digits")
	private String dpMobileno;
	
	@NotBlank(message = "Password Can Not Be Empty !!")
	private String dpPassword;
	
	@NotBlank(message = "HouseNo Can Not Be Empty !!")
	private String houseNo;
	
	@NotBlank(message = "Area Can Not Be Empty !!")
	private String area;
	
	@NotBlank(message = "City Can Not Be Empty !!")
	private String city;
	
	@NotBlank(message = "State Can Not Be Empty !!")
	private String state;
	
	@Pattern(regexp = "^[1-9][0-9]{5}$", message = "Pincode must consist of 6 digits")
	private String pincode;
	
	private double latitude;
	
	private double longitude;

}
