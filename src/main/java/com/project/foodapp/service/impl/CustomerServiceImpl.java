package com.project.foodapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.project.foodapp.exceptions.CustomerNotFoundException;
import com.project.foodapp.exceptions.IllegalArgumentException;
import com.project.foodapp.model.Address;
import com.project.foodapp.model.Customer;
import com.project.foodapp.model.CustomerDTO;
import com.project.foodapp.model.RegistrationDTO;
import com.project.foodapp.repository.CustomerRepo;
import com.project.foodapp.repository.DeliveryPartnerRepo;
import com.project.foodapp.repository.RestaurantRepo;
import com.project.foodapp.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerRepo customerRepo;
	
	@Autowired
	private RestaurantRepo restaurantRepo;
	
	@Autowired
	private DeliveryPartnerRepo deliveryPartnerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Customer createCustomer(CustomerDTO customerDTO,BindingResult bindingResult) throws IllegalArgumentException {
		if(bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage="";
			for (FieldError error : errors) {
				errorMessage+=error.getDefaultMessage()+"\n";
			}
			throw new IllegalArgumentException(errorMessage);
		}
		
		if(customerRepo.findByCustEmailId(customerDTO.getCustEmailId())!=null || restaurantRepo.findByRestEmailId(customerDTO.getCustEmailId())!=null || deliveryPartnerRepo.findByDpEmailId(customerDTO.getCustEmailId())!=null) {
			throw new IllegalArgumentException("Customer Email Id Already Exist");
		}
		
		Address address=Address.builder().houseNo(customerDTO.getHouseNo()).area(customerDTO.getArea()).city(customerDTO.getCity()).state(customerDTO.getState()).pincode(customerDTO.getPincode()).latitude(customerDTO.getLatitude()).longitude(customerDTO.getLongitude()).build();
		
		Customer customer=Customer.builder().custName(customerDTO.getCustName()).custEmailId(customerDTO.getCustEmailId()).custMobileno(customerDTO.getCustMobileno()).custPassword(passwordEncoder.encode(customerDTO.getCustPassword())).custAddress(address).role("Customer").build();
		
		return customerRepo.save(customer);
	}

	@Override
	public Customer fetchCustomer(Long id) throws CustomerNotFoundException{
		Customer customer=customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException(id));
		return customer;
	}

	@Override
	public Customer updateCustomer(Long id,CustomerDTO updatedCustomer,BindingResult bindingResult) throws CustomerNotFoundException, IllegalArgumentException {
		if (bindingResult.hasErrors()) {
			List<FieldError> errors = bindingResult.getFieldErrors();
			String errorMessage = "";
			for (FieldError error : errors) {
				errorMessage += error.getDefaultMessage() + "\n";
			}
			throw new IllegalArgumentException(errorMessage);
		}
		Customer existingCustomer = customerRepo.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException(id));
		
		existingCustomer.setCustName(updatedCustomer.getCustName());
		Address address = existingCustomer.getCustAddress();
		
		address.setHouseNo(updatedCustomer.getHouseNo());
		address.setArea(updatedCustomer.getArea());
		address.setCity(updatedCustomer.getCity());
		address.setState(updatedCustomer.getState());
		address.setPincode(updatedCustomer.getPincode());
		
		existingCustomer.setCustAddress(address);
		existingCustomer.setCustMobileno(updatedCustomer.getCustMobileno());
		return customerRepo.save(existingCustomer);
	}

	@Override
	public Customer deleteCustomer(Long id) throws CustomerNotFoundException {
		Customer existingCustomer=customerRepo.findById(id).orElseThrow(()->new CustomerNotFoundException(id));
		customerRepo.deleteById(id);
		return existingCustomer;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepo.findAll();
	}

	@Override
	public RegistrationDTO findByEmailId(String emailId){
		Customer customer=customerRepo.findByCustEmailId(emailId);
		if(customer!=null) {
			RegistrationDTO user=RegistrationDTO.builder().userId(customer.getCustId()).userName(customer.getCustName()).userEmailId(customer.getCustEmailId()).userMobileno(customer.getCustMobileno()).
					userPassword(customer.getCustPassword()).houseNo(customer.getCustAddress().getHouseNo()).area(customer.getCustAddress().getArea()).role(customer.getRole()).build();
			return user;
		}
		return null;
	}	
	
}
