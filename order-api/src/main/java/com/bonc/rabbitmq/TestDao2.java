package com.bonc.rabbitmq;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.bonc.order.domain.OUser;
import com.bonc.order.domain.Work;
import com.bonc.product.domain.Product;
import com.bonc.product.domain.WechatUser;
import com.supermy.db.DataBaseConfig;

public class TestDao2 {
    
	DbConfiguration dbConfiguration = new DbConfiguration();
	 
	 public void insertValue(Product product){
		 final String sql = "insert into t_product (price,id,productName,type) values(?,?,?,?)";
		 dbConfiguration.jdbcTemplate().update(sql,new Object[]{
				 product.getPrice(),product.getId(),product.getProductName(),product.getProduct_type()});
	 }
	 
	 public void insertTaskValue(Work work){
		 final String sql = "insert into t_work_order (WXhsh,id,telNu,product_id,elementid,packageid,starttime,endtime,msgAcceptance,msgReach) values(?,?,?,?,?,?,?,?,?,?)";
//		 dbConfiguration.jdbcTemplate().update(sql,new Object[]{
//				 work.getWXhsh(),work.getId(),work.getTelNu(),null,work.getElementid(),work.getPackageid(),work.getStartTime(),work.getEndTime(),true,true});
	 }
	 
	 public void insertWechatUserValue(WechatUser wechatUser){
		 final String sql = "insert into t_wechat_user (openId,telphone) values(?,?)";
		 dbConfiguration.jdbcTemplate().update(sql,new Object[]{
				 wechatUser.getOpenId(),wechatUser.getTelphone()});
	 }
	 
//	 public Integer getList(){
//		 final String sql = "SELECT id from t_product WHERE price=66";
//		 Product product = new Product();
//		 return (Integer) dbConfiguration.jdbcTemplate().query(sql, product.getId());
//	}
	 
	 
	 
	 
	 public void updateValue(final Product product){
		 final String sql = "update t_product set productName=?,productDetail=?,price=?,type=? where id = ?";
		 dbConfiguration.jdbcTemplate().update(sql, new PreparedStatementSetter(){
			@Override
			public void setValues(java.sql.PreparedStatement ps)
					throws SQLException {
				ps.setString(1, product.getProductName());
				ps.setString(2, product.getProductDetail());
				ps.setInt(3, product.getPrice());
				ps.setString(4, product.getProduct_type());
				ps.setString(5, (product.getProductOrderId()));
			}
		 });
	 }
	 
	 public void delete(Product product){
		 final String sql = "delete from t_product where id = ?";
//		 dbConfiguration.jdbcTemplate().update(sql,new O)
	 }
}
