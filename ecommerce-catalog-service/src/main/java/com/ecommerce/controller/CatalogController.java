package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.jpa.CatalogEntity;
import com.ecommerce.service.CatalogService;
import com.ecommerce.vo.ResponseCatalog;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {
	@Autowired
	CatalogService catalogService;
	@Autowired
	Environment env;
	
	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in User Service on PORT %s", env.getProperty("local.server.port"));
	}
	
	@GetMapping("/catalogs")
	public ResponseEntity<List<ResponseCatalog>> getAllCatalogs() {
		Iterable<CatalogEntity> catalogs = catalogService.getAllCatalogs();
		List<ResponseCatalog> result = new ArrayList<ResponseCatalog>();
		
		catalogs.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseCatalog.class));
		});
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
