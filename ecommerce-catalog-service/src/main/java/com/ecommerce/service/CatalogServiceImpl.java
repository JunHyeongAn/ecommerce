package com.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.jpa.CatalogEntity;
import com.ecommerce.jpa.CatalogRepository;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Data
@Slf4j
public class CatalogServiceImpl implements CatalogService {
	@Autowired
	CatalogRepository catalogRepository;
	
	@Override
		public Iterable<CatalogEntity> getAllCatalogs() {
			return catalogRepository.findAll();
		}
}
