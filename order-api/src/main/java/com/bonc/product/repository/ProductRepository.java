package com.bonc.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bonc.product.domain.Product;

@RepositoryRestResource(collectionResourceRel = "product" ,path = "product")
public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product>{
	@Query(value="select p from Product p where p.productName =:proName")
	Product findByProName(@Param("proName") String proName);
	Product findByProductOrderId(@Param("productOrderId")String productOrderId);
}
