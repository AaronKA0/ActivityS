package com.faq.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faq.model.FaqVO;


@Service("faqService")
public class FaqService {

	@Autowired
	FaqRepository repository;

	public void addFaq(FaqVO faqVO) {
		repository.save(faqVO);
	}

	public void updateFaq(FaqVO faqVO) {
		repository.save(faqVO);
	}

	public void deleteFaq(Integer faqId) {
		if (repository.existsById(faqId))
			repository.deleteByFaqId(faqId);
//		   repository.deleteByFaqId(faqId);
	}

	public FaqVO getOneFaq(Integer faqId) {
		Optional<FaqVO> optional = repository.findById(faqId);
//		return optional.get();
		return optional.orElse(null);

	}

	public List<FaqVO> getAll() {
		return repository.findAll();
	}

//	-----------------------狀態更改-----------------------
	public FaqVO getStatusFaq(Integer faqId) {
		Optional<FaqVO> optional = repository.findById(faqId);
		return optional.orElse(null);
	}

}
