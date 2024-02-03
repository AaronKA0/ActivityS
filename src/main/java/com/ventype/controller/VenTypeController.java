package com.ventype.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ventype.model.VenTypeVO;
import com.ventype.service.VenTypeService;


@Controller
@RequestMapping("/ventype")
public class VenTypeController {

	@Autowired
	VenTypeService venTypeSvc;
	
	@RequestMapping("/all")
	public @ResponseBody List<VenTypeVO> getAllVenTypes() {		
		List<VenTypeVO> venTypes = venTypeSvc.getAll();
		return venTypes;
	}
	
	@PostMapping("insert")
	public @ResponseBody VenTypeVO insertVenType(@RequestBody String jsonString) {
		VenTypeVO venType = null;
		try {
			venType = new ObjectMapper().readValue(jsonString, VenTypeVO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		venTypeSvc.addVenType(venType);

		return null;
	}
	
	@PostMapping("update")
	public @ResponseBody VenTypeVO updateVenType(@RequestBody String jsonString) {
		VenTypeVO venType = null;
		try {
			venType = new ObjectMapper().readValue(jsonString, VenTypeVO.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		VenTypeVO venType2 = venTypeSvc.getOneVenType(venType.getVenTypeId());
		
		venType2.setVenTypeName(venType.getVenTypeName());
		venType2.setVenTypeDescr(venType.getVenTypeDescr());
		
		venTypeSvc.updateVenType(venType2);

		return null;
	}
	
	@RequestMapping("getByName")
	public @ResponseBody VenTypeVO validateName(@RequestBody String json) {

		ObjectMapper mapper = new ObjectMapper();
		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
		};
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			data = mapper.readValue(json, typeRef);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String venTypeName = (String) data.get("venTypeName");
		return venTypeSvc.getByName(venTypeName);
	}
	

}