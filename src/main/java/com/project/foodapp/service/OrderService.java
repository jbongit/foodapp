package com.project.foodapp.service;

import java.util.List;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.model.Order;

public interface OrderService {
	List<Order> getAllOrdersByCustId(Long custId) throws CartItemNotFoundException;
	Order addOrder(Long custId) throws CartItemNotFoundException;
}
