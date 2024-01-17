package com.venorder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venorder.model.VenOrderVO;

@Service("VenOrderService")
public class VenOrderService{
	
	@Autowired
	VenOrderRepository repository;
	
	// add 新增
	public void addVenOrder(VenOrderVO venOrderVO) {
		repository.save(venOrderVO);
	}
	
	// update 修改
	public void updateVenOrder(VenOrderVO venOrderVO) {
		repository.save(venOrderVO);
	}
	
	// getOne 單筆查詢
	public VenOrderVO getOneVenOrder(Integer venOrderId) {
		Optional<VenOrderVO> optional = repository.findById(venOrderId);
		return optional.orElse(null);
	}
	
	// getAll 全部查詢
	public List<VenOrderVO> getAll(){
		return repository.findAll();
	}
	
}
