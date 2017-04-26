package com.bonc.product.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bonc.product.domain.Product;
import com.bonc.product.service.ProductService;

@RestController
public class ProductEntityController {
	
	 @Autowired
     private ProductService productService;
	 
}
