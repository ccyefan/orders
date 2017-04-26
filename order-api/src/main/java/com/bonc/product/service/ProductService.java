package com.bonc.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bonc.product.domain.Product;
import com.bonc.product.repository.ProductRepository;

@Component
@Transactional
public class ProductService {
	
	 @Autowired
     private ProductRepository productRepository;
	 
	 
}
