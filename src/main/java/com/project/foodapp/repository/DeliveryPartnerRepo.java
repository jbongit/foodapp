package com.project.foodapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foodapp.model.DeliveryPartner;

public interface DeliveryPartnerRepo extends JpaRepository<DeliveryPartner, Long>{

	DeliveryPartner findByDpEmailId(String dpEmailId);

}
