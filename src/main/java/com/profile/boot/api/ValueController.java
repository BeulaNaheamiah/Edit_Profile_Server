package com.profile.boot.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.profile.boot.model.City;
import com.profile.boot.model.KeyValue;

@RestController
@RequestMapping("/get")
public class ValueController {

	
	@GetMapping("/singleChoice")
	public ResponseEntity<HashMap<String, List<KeyValue>>> getSingleChoice() {
		HashMap<String, List<KeyValue>> sc= null;
		TypeReference<HashMap<String,List<KeyValue>>> personList =  new   TypeReference<HashMap<String, List<KeyValue>>>() {};

		try {
			ObjectMapper mapper = new ObjectMapper();
				 sc = mapper.readValue(ClassLoader.getSystemResource("single_choice_attributes.json"), personList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(sc);
	}

	@GetMapping("/cities")
	public ResponseEntity<HashMap<String, List<City>>> getCities() {
		HashMap<String, List<City>> sc= null;
		TypeReference<HashMap<String,List<City>>> cityList =  new   TypeReference<HashMap<String, List<City>>>() {};

		try {
			ObjectMapper mapper = new ObjectMapper();
				 sc = mapper.readValue(ClassLoader.getSystemResource("cities.json"), cityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(sc);
	}

	
	
}
