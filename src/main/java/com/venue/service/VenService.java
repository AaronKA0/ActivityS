package com.venue.service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.venue.model.VenVO;

@Service("venService")
public class VenService {

	@Autowired
	VenRepository repository;
	
	@Autowired
    private SessionFactory sessionFactory;

	public void addVen(VenVO venVO) {
		repository.save(venVO);
	}

	public void updateVen(VenVO venVO) {
		repository.save(venVO);
	}

	public void deleteVen(Integer venId) {
		if (repository.existsById(venId))
			repository.deleteByVenId(venId);
	}

	public VenVO getOneVen(Integer venId) {
		Optional<VenVO> optional = repository.findById(venId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<VenVO> getAll() {
		return repository.findAll();
	}
	
	// 所有上架的場地
	public List<VenVO> getVenueOn() {
	    return repository.getVenueOn();
	}
	
	public VenVO getByName(String name) {
		VenVO ven = repository.getByName(name);
		return ven;	
	}
	
	public List<VenVO> pickByOrderDate(Date date){
	    return repository.pickByOrderDate(date);
	}
	

//	public List<VenVO> getAll(Map<String, String[]> map) {
//		return HibernateUtil_CompositeQuery_Emp3.getAllC(map,sessionFactory.openSession());
//	}

}