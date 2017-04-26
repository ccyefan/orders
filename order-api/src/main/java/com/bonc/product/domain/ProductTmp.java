package com.bonc.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.supermy.domain.BaseObj;

//产品临时表
@Entity
@Table(name = "t_product_tmp")
public class ProductTmp extends BaseObj{
	/**
	 * 产品类型,package为套餐分类，flow为流量包分类
	 */
	@Column(length = 20)
	private String type;
	
	/**
	 * 产品名称
	 */
	@Column(length = 25)
	private String productName;
	
	/**
	 * 产品详细内容
	 */
	@Column(length = 40)
	private String productDetail;
    
	/**
	 * 产品价格
	 */
	private Integer price;
	
	/**
	 * 产品订购id
	 */
	private Long productOrderId;

	public ProductTmp(){
		
	}
	
	public ProductTmp(String type, String productName, String productDetail,
			Integer price,Long productOrderId) {
		super();
		this.type = type;
		this.productName = productName;
		this.productDetail = productDetail;
		this.price = price;
		this.productOrderId = productOrderId;
	}



	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	public Long getProductOrderId() {
		return productOrderId;
	}

	public void setProductOrderId(Long productOrderId) {
		this.productOrderId = productOrderId;
	}

}
