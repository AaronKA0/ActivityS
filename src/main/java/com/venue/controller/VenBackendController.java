package com.venue.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ventype.service.VenTypeService;
import com.venue.model.VenVO;
import com.venue.service.VenService;

@Controller
@RequestMapping("/ven")
public class VenBackendController {

	@Autowired
	VenService venSvc;

	@Autowired
	VenTypeService venTypeSvc;
	
	Gson gson = new Gson();

//	@Autowired
//	EmailService emailService;

	@PostMapping("insert")
	public @ResponseBody VenVO insertVenue(@RequestBody String jsonString) {
		VenVO ven = null;
		try {
			ven = new ObjectMapper().readValue(jsonString, VenVO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		venTypeSvc.addVenType(ven.getVenType());

		ven.setVenModTime(Timestamp.valueOf(LocalDateTime.now()));
		ven.setVenTotRating(0.0);
		ven.setVenRateCount(0);
		venSvc.addVen(ven);

		if (ven.getVenUptime() == null && ven.getVenDowntime() == null)
			return null;

		scheduleUpdate(ven);
		return null;
	}

	@PostMapping("update")
	public @ResponseBody VenVO updateVenue(@RequestBody String jsonString) {
		VenVO ven = null;
		try {
			ven = new ObjectMapper().readValue(jsonString, VenVO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		venTypeSvc.addVenType(ven.getVenType());

		VenVO ven2 = venSvc.getOneVen(ven.getVenId());

		ven2.setVenModTime(Timestamp.valueOf(LocalDateTime.now()));
		ven2.setVenName(ven.getVenName());
		ven2.setVenType(ven.getVenType());
		ven2.setVenCity(ven.getVenCity());
		ven2.setVenDistrict(ven.getVenDistrict());
		ven2.setVenLoc(ven.getVenLoc());
		ven2.setVenDescr(ven.getVenDescr());
		ven2.setVenPrice(ven.getVenPrice());
		ven2.setVenPic(ven.getVenPic());

		venSvc.updateVen(ven2);

		return null;
	}

//	private void sendEmail() {
////		 email/notification services
//		System.out.println("sent email");
//		String email = "nathan333.hsu@gmail.com";
//		String title = "<h1>您有被取消的場地預約</h1>";
//		String content = "<p>由於場地被下架，您有被<b>取消</b>的場地預約.</p><p>請點擊下面的連結查看您目前所有的場地預約.</p><p><a href='https://www.w3schools.com'>查看場地預約</a></p>";
//		emailService.sendEmail(email, title, content);
//	}

//	private void scheduleEmail() {
//		TimerTask task = new TimerTask() {
//			public void run() {
//				sendEmail();
//			}
//		};
//		Timer timer = new Timer("Timer");
//		timer.schedule(task, Timestamp.valueOf(LocalDateTime.now()));
//	}

	private void scheduleUpdate(VenVO ven) {
		TimerTask task = new TimerTask() {
			public void run() {

				VenVO ven2 = venSvc.getByName(ven.getVenName());

				if (ven.getVenDowntime() != null) {
					if (ven2.getVenDowntime() == null || ven.getVenDowntime().compareTo(ven2.getVenDowntime()) != 0) {
						System.out.println("different downtime");
						return;
					} else {
//						sendEmail();
					}

				} else {
					if (ven2.getVenUptime() == null || ven.getVenUptime().compareTo(ven2.getVenUptime()) != 0) {
						System.out.println("different uptime");
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

				venSvc.updateVen(ven2);

				System.out.println(ven2);
				System.out.println("modified status");
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

	@RequestMapping("getByName")
	public @ResponseBody VenVO validateName(@RequestBody String json) {
		VenVO ven = null;
		try {
			ven = new ObjectMapper().readValue(json, VenVO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String venName = ven.getVenName();
		return venSvc.getByName(venName);		
	}
	
	@RequestMapping("getById")
	public @ResponseBody VenVO getById(@RequestBody String json) {
		VenVO ven = gson.fromJson(json, VenVO.class);
		return venSvc.getOneVen(ven.getVenId());		
	}

	@RequestMapping("updateTime")
	public @ResponseBody VenVO updateTime(@RequestBody String json) {

		ObjectMapper mapper = new ObjectMapper();
		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
		};
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data = mapper.readValue(json, typeRef);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		List<String> venNames = (ArrayList<String>) data.get("venNames");
		String type = (String) data.get("type");
		Timestamp time = Timestamp.valueOf((String) data.get("time"));

		for (String venName : venNames) {
			VenVO ven = venSvc.getByName(venName);
			if (type.equals("上架")) {
				ven.setVenUptime(time);
				ven.setVenDowntime(null);
			} else {
				ven.setVenDowntime(time);
				ven.setVenUptime(null);
			}

			if ((ven.getVenUptime() != null
					&& ven.getVenUptime().compareTo(Timestamp.valueOf(LocalDateTime.now())) <= 0)
					|| (ven.getVenDowntime() != null
							&& ven.getVenDowntime().compareTo(Timestamp.valueOf(LocalDateTime.now())) <= 0)) {
				ven.setVenDowntime(null);
				ven.setVenUptime(null);
				if (type.equals("上架")) {
					ven.setVenStatus((byte) 2);
				} else {
					ven.setVenStatus((byte) 1);
//					scheduleEmail();
				}
				venSvc.updateVen(ven);
			} else {
				venSvc.updateVen(ven);
				scheduleUpdate(ven);
			}
		}

		System.out.println(venNames.get(0) + " " + type + " " + time);
		return null;
	}

	@RequestMapping("all")
	public @ResponseBody List<VenVO> getAllVens() {
		List<VenVO> vens = venSvc.getAll();

		for (int i = 0; i < vens.size(); i++) {

			vens.get(i).setVenPic(null);
			if (vens.get(i).getVenRateCount() > 0) {
				vens.get(i).setVenRating(vens.get(i).getVenTotRating() / vens.get(i).getVenRateCount());
			} else {
				vens.get(i).setVenRating(0.0);
			}

		}
		return vens;
	}

}