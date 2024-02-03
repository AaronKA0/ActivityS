package com.faq.service;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.faq.model.FaqVO;

public interface FaqRepository extends JpaRepository<FaqVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from faq where faqId =?1" , nativeQuery = true)
	void deleteByFaqId(int faqId);
}
