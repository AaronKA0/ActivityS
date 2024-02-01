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
	
	
	// 標題查詢
	public List<NotifyVO> findByTitle(String notifyTitle) {
        List<NotifyVO> notifies = repository.findByTitle(notifyTitle);
        return notifies;    
    }
	
	// 會員個人通知查詢
	public List<NotifyVO> findByMemId(Integer memId) {
        List<NotifyVO> notifies = repository.findByMemId(memId);
        return notifies;    
    }
	
	// 會員通知未讀查詢
	public List<NotifyVO> findUnread(Integer memId) {
	    List<NotifyVO> notifies = repository.findUnread(memId);
	    return notifies;    
	}
	
	// 會員已讀通知
	public List<NotifyVO> readAll(Integer memId) {
	    List<NotifyVO> notifies = repository.findUnread(memId);
	    for (NotifyVO notify : notifies) {
	        notify.setNotifyStatus((byte)2);
	        repository.save(notify);        
	    }
	    return repository.findAll();    
	}

	
	
}
