package com.ecommerce.jpa;

import org.springframework.data.repository.CrudRepository;

public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
	public CatalogEntity findByProductId(String productId);
}
