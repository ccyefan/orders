package com.bonc.instankill.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.supermy.domain.BaseObj;
/**
 * 秒杀商品表
 */
@Entity
@Table(name = "t_commodity")
public class Commodity extends BaseObj{

	@Column(length = 22)
	private String name;//商品名称 	
	private int amount;//商品数量	
	private int price;//价格  
	@Column(length = 100)
	private String commodityDetail;//商品详情
	//private String commodityOrderId;//订购ID
	@Column(length = 20)
	private String period;//期数
	@Column(length = 20)
	private String type;

	public Commodity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}


	public String getCommodityDetail() {
		return commodityDetail;
	}
	public void setCommodityDetail(String commodityDetail) {
		this.commodityDetail = commodityDetail;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}

}
