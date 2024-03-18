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
import com.membership.model.MembershipVO;
import com.ventype.service.VenTypeService;
import com.venue.model.RecentVen;
import com.venue.model.VenVO;
import com.venue.service.RecentVenService;
import com.venue.service.VenService;

@Controller
@RequestMapping("/ven")
public class VenBackendController {

	@Autowired
	VenService venSvc;

	@Autowired
	VenTypeService venTypeSvc;
	
	@Autowired
	RecentVenService recentVenSvc;

	@Autowired
	Gson gson;

//	@Autowired
//	EmailService emailService;

	@PostMapping("insert")
	public @ResponseBody VenVO insertVenue(@RequestBody VenVO ven) {

		try {
			venSvc.addVen(ven);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (ven.getVenUptime() == null && ven.getVenDowntime() == null)
			return null;

		venSvc.scheduleUpdate(ven);
		return null;
	}

	@PostMapping("update")
	public @ResponseBody VenVO updateVenue(@RequestBody VenVO ven) {

		try {
			venSvc.updateVen(ven);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	@RequestMapping("getByName")
	public @ResponseBody VenVO validateName(@RequestBody String json) {
		VenVO ven = gson.fromJson(json, VenVO.class);
		String venName = ven.getVenName();
		return venSvc.getByName(venName);
	}

	@RequestMapping("getById")
	public @ResponseBody VenVO getById(@RequestBody String json) {
		VenVO ven = gson.fromJson(json, VenVO.class);
		return venSvc.getOneVen(ven.getVenId());
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

	@PostMapping("recent")
	public @ResponseBody List<RecentVen> getRecentVens(@RequestBody String json) {
		MembershipVO mem = gson.fromJson(json, MembershipVO.class);
		List<RecentVen> vens = recentVenSvc.getVens(mem.getMemId());
		return vens;
	}

	@PostMapping("addRecent")
	public @ResponseBody RecentVen addRecentVen(@RequestBody String json) {
		RecentVen ven = gson.fromJson(json, RecentVen.class);
		recentVenSvc.addVen(ven);
		return ven;
	}
	
	@PostMapping("updateTime")
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
				try {
					venSvc.updateVen(ven);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				try {
					venSvc.updateVen(ven);
				} catch (Exception e) {
					e.printStackTrace();
				}
				venSvc.scheduleUpdate(ven);
			}
		}

		return null;
	}

}