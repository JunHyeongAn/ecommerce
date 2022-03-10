package com.ecommerce.service;

import com.ecommerce.jpa.CatalogEntity;

public interface CatalogService {
	Iterable<CatalogEntity> getAllCatalogs();	
}
