package com.venclosed.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venclosed.model.VenClosedVO;

@Service("VenClosedService")
public class VenClosedService{
	
	@Autowired
	VenClosedRepository repository;
	
	// add 新增
	public void addVenClosed(VenClosedVO venClosedVO) {
		repository.save(venClosedVO);
	}
	
	// update 修改
	public void updateVenClosed(VenClosedVO venClosedVO) {
		repository.save(venClosedVO);
	}
	
	// getOne 單筆查詢
	public VenClosedVO getOneVenClosed(Integer closedDateId) {
		Optional<VenClosedVO> optional = repository.findById(closedDateId);
		return optional.orElse(null);
	}
	
	// getAll 全部查詢
	public List<VenClosedVO> getAll(){
		return repository.findAll();
	}
	
}
