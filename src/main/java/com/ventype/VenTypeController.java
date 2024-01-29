package com.ventype;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


import com.ventype.VenTypeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ven.VenVO;
import com.ventype.*;


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