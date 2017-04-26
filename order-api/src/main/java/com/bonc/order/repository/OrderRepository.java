package com.bonc.order.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import com.bonc.order.domain.Order;


@RepositoryRestResource(collectionResourceRel = "order", path = "order")
public interface OrderRepository extends JpaRepository<Order,Long> , JpaSpecificationExecutor<Order>{
	
	@Transactional
	@Modifying(clearAutomatically = true)
//mysql	@Query(value="update t_order o set o.date=NOW() where o.id = :uid",nativeQuery = true)
	@Query(value="update Order o set o.date=:udate where o.id = :uid")
	public void updateTime(@Param("uid") Long id,@Param("udate") Date date);
	
//	 @Query(value = "select * from Channel where name like %:name or code like %:code ", nativeQuery = true)
//	    List<Channel> findByName(@Param("name") String name1, @Param("code") String name2);
	
}