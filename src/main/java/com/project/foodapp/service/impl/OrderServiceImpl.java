package com.project.foodapp.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.foodapp.exceptions.CartItemNotFoundException;
import com.project.foodapp.model.CartItem;
import com.project.foodapp.model.Order;
import com.project.foodapp.repository.OrderRepo;
import com.project.foodapp.service.CartItemService;
import com.project.foodapp.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private OrderRepo orderRepo; 
	
	@Override
	public List<Order> getAllOrdersByCustId(Long custId) throws CartItemNotFoundException {
		return orderRepo.findByCustId(custId);
	}

	@Override
	public Order addOrder(Long custId) throws CartItemNotFoundException {
		List<CartItem> cartItems=cartItemService.getCartItemsByCustId(custId);

		cartItems.stream().forEach(item->{
			Order order=Order.builder().custId(custId).dpId(null).product(item.getProduct()).quantity(item.getQuantity()).localDateTime(LocalDateTime.now()).status("Pending").build();
			orderRepo.save(order);
			try {
				cartItemService.removeFromCartByProductId(custId, item.getProduct().getProductId());
			} catch (CartItemNotFoundException e) {
				e.printStackTrace();
			}
		});
		return null;
	}

}