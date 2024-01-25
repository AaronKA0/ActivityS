package com.venorder.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venclosed.model.VenClosedVO;
import com.venorder.model.VenOrderVO;
import com.venue.model.VenVO;

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
	
	// getByOrderDate 依日期查詢
	public List<VenOrderVO> getByOrderDate(Date orderDate) {
        List<VenOrderVO> VenOrders = repository.getByOrderDate(orderDate);
        return VenOrders; 
    }
	
	// getVenCom 利用場地取得訂單資訊
	public List<VenOrderVO> getVenCom(VenVO venVO) {
	    List<VenOrderVO> VenOrders = repository.getVenCom(venVO);
	    return VenOrders; 
	}
	
	public List<VenOrderVO> getLessDay(Date orderDate) {
        List<VenOrderVO> VenOrders = repository.getLessDay(orderDate);
        return VenOrders; 
    }
}
