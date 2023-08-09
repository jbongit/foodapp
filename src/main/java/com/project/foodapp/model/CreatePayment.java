package com.project.foodapp.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class CreatePayment {
	@SerializedName("items")
	private CartItem[] items;
}
