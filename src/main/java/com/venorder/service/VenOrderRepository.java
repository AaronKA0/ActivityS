package com.venorder.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venorder.model.VenOrderVO;

public interface VenOrderRepository extends JpaRepository<VenOrderVO, Integer>{

}
