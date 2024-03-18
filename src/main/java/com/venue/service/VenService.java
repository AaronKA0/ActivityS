package com.venue.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.transaction.annotation.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ventype.service.VenTypeRepository;
import com.venue.model.VenVO;

@Service("venService")
public class VenService {

	@Autowired
	VenRepository repository;

	@Autowired
	VenTypeRepository venTypeRepository;

	@Transactional(rollbackFor = Exception.class)
	public void addVen(VenVO venVO) throws Exception {
		try {
			venTypeRepository.save(venVO.getVenType());

			venVO.setVenModTime(Timestamp.valueOf(LocalDateTime.now()));
			venVO.setVenTotRating(0.0);
			venVO.setVenRateCount(0);
			
			repository.save(venVO);

		} catch (Exception e) {
			throw new Exception("Ven Insert Transaction Failed");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	public void updateVen(VenVO ven) throws Exception {
		try {
			venTypeRepository.save(ven.getVenType());

			VenVO ven2 = getOneVen(ven.getVenId());

			ven2.setVenModTime(Timestamp.valueOf(LocalDateTime.now()));
			ven2.setVenName(ven.getVenName());
			ven2.setVenType(ven.getVenType());
			ven2.setVenCity(ven.getVenCity());
			ven2.setVenDistrict(ven.getVenDistrict());
			ven2.setVenLoc(ven.getVenLoc());
			ven2.setVenDescr(ven.getVenDescr());
			ven2.setVenPrice(ven.getVenPrice());
			ven2.setVenPic(ven.getVenPic());
		} catch (Exception e) {
			throw new Exception("Ven Update Transaction Failed");
		}
	}

	public void deleteVen(Integer venId) {
		if (repository.existsById(venId)) {
//			repository.deleteByVenId(venId);
		}
	}

	public VenVO getOneVen(Integer venId) {
		Optional<VenVO> optional = repository.findById(venId);
//		return optional.get();
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
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

	public List<VenVO> pickByOrderDate(Date date) {
		return repository.pickByOrderDate(date);
	}

	public void scheduleUpdate(VenVO ven) {
		TimerTask task = new TimerTask() {
			public void run() {

				VenVO ven2 = getByName(ven.getVenName());

				if (ven.getVenDowntime() != null) {
					if (ven2.getVenDowntime() == null || ven.getVenDowntime().compareTo(ven2.getVenDowntime()) != 0) {

						return;
					} else {
//						sendEmail();
					}

				} else {
					if (ven2.getVenUptime() == null || ven.getVenUptime().compareTo(ven2.getVenUptime()) != 0) {

						return;
					}
				}

				if (ven.getVenStatus() == (byte) 1) {
					ven2.setVenStatus((byte) 2);
				} else {
					ven2.setVenStatus((byte) 1);
				}

				ven2.setVenUptime(null);
				ven2.setVenDowntime(null);
				ven2.setVenModTime(Timestamp.valueOf(LocalDateTime.now()));

				try {
					updateVen(ven2);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		Timer timer = new Timer("Timer");

		Date date;
		if (ven.getVenUptime() != null) {
			date = new Date(ven.getVenUptime().getTime());
		} else {
			date = new Date(ven.getVenDowntime().getTime());
		}

		timer.schedule(task, date);
	}

}