package com.bonc.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.supermy.domain.BaseObj;
import com.supermy.domain.BaseObjP;

@Entity
@Table(name = "t_product")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class Product extends BaseObj{
	/**
	 * 产品类型,package为套餐分类，flow为流量包分类
	 */
	@Column(length = 20)
	private String product_type;
	
	/**
	 * 产品名称
	 */
	@Column(length = 25)
	private String productName;
	
	/**
	 * 产品详细内容
	 */
	@Column(length = 100)
	private String productDetail;
    
	/**
	 * 产品价格
	 */
	private Integer price;
    
	/**
	 * 产品订购id
	 */
	private String productOrderId;
	
	/**
	 * 流量包类型(10是假日流量包，11是日流量包)
	 */
	private String flow_type;
	
	/**
	 * 总部数据字段(租户)
	 */
	private String tenant_id;
	
	public Product(){
		
	}
	
	

	public Product(String productType, String productName,
			String productDetail, Integer price, String productOrderId,
			String flowType, String tenantId) {
		super();
		product_type = productType;
		this.productName = productName;
		this.productDetail = productDetail;
		this.price = price;
		this.productOrderId = productOrderId;
		flow_type = flowType;
		tenant_id = tenantId;
	}


	public String getProduct_type() {
		return product_type;
	}

	public void setProduct_type(String productType) {
		product_type = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(String productDetail) {
		this.productDetail = productDetail;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getProductOrderId() {
		return productOrderId;
	}

	public void setProductOrderId(String productOrderId) {
		this.productOrderId = productOrderId;
	}

	public String getFlow_type() {
		return flow_type;
	}

	public void setFlow_type(String flowType) {
		flow_type = flowType;
	}

	public String getTenant_id() {
		return tenant_id;
	}



	public void setTenant_id(String tenantId) {
		tenant_id = tenantId;
	}


	
}
