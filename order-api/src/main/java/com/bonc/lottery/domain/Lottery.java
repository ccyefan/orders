package com.bonc.lottery.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.supermy.domain.BaseObj;
@Entity
@Table(name = "l_lottery")
public class Lottery extends BaseObj{	

	@Column(length = 3)
    private String name;//奖品名称 
	@Column(length = 20,name="desc_")
    private String desc;//奖品描述 
	
    private int amount;//奖品数量		
	private int minangle;//最小角度	
	private int maxangle;//最大角度    
	private int probability;//中奖概率  
	@Column(length = 20)
	private String period;//期数
	
	public Lottery() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getMinangle() {
		return minangle;
	}
	public void setMinangle(int minangle) {
		this.minangle = minangle;
	}
	public int getMaxangle() {
		return maxangle;
	}
	public void setMaxangle(int maxangle) {
		this.maxangle = maxangle;
	}
	public int getProbability() {
		return probability;
	}
	public void setProbability(int probability) {
		this.probability = probability;
   
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
}

    

	
