package com.ventype;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ven.VenVO;

@Service("venTypeService")
public class VenTypeService {

	@Autowired
	VenTypeRepository repository;

	public void addVenType(VenTypeVO venTypeVO) {
		repository.save(venTypeVO);
	}

	public void updateVenType(VenTypeVO venTypeVO) {
		repository.save(venTypeVO);
	}

	public VenTypeVO getOneVenType(Integer venTypeId) {
		Optional<VenTypeVO> optional = repository.findById(venTypeId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<VenTypeVO> getAll() {
		return repository.findAll();
	}
	
	public VenTypeVO getByName(String name) {
		VenTypeVO venType = repository.getByName(name);
		return venType;	
	}


}
