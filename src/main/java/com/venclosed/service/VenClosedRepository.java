package com.venclosed.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.venclosed.model.VenClosedVO;

public interface VenClosedRepository extends JpaRepository<VenClosedVO, Integer>{

}
