package com.notify.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notify.model.NotifyVO;

@Service("NotifyService")
public class NotifyService{
	
	@Autowired
	NotifyRepository repository;
	
	// add 新增
	public void addNotify(NotifyVO notifyVO) {
		repository.save(notifyVO);
	}
	
	// update 修改
	public void updateNotify(NotifyVO notifyVO) {
		repository.save(notifyVO);
	}
	
	// getOne 單筆查詢
	public NotifyVO getOneNotify(Integer notifyId) {
		Optional<NotifyVO> optional = repository.findById(notifyId);
		return optional.orElse(null);
	}
	
	// getAll 全部查詢
	public List<NotifyVO> getAll(){
		return repository.findAll();
	}
	
	public List<NotifyVO> findByTitle(String notifyTitle) {
        List<NotifyVO> notifies = repository.findByTitle(notifyTitle);
        return notifies;    
    }

	
	
}
