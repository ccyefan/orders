package com.bonc.order.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bonc.order.domain.Work;
@RepositoryRestResource(collectionResourceRel = "work", path = "work")
public interface WorkRepository extends JpaRepository<Work, Long>{
	@Query(value="select * from t_work_order w where w.telNu like %?1%",nativeQuery=true)
	List<Work> findbykeyword(String keyword);
	//List<Work> findBytelNuOrpublicidContaining(String telpub);
	/*List<Work> findBypublicidContaining(String publicid);
	List<Work> findBytelNuContaining(String telnum); */
	//List<Work> findByWXhshLike(String wxhsh);
	Page<Work> findBytelNu(String tel,Pageable pageable);
	//作废
	@Query(value="select * from t_work_order where user_id =:uid and Type = :type ",nativeQuery=true)
	Work findByOuserAndType(@Param("uid") Long uid,@Param("type") String type);

	@Query(value="select w from Work w where w.telNu =:tel")
	List<Work> findByUserTelNumber(@Param("tel") String tel);
	
//	@Query(value="select w from Work w where w.telNu =:tel and w.product.product_type=:type")
//	Work findByTelandProtype(@Param("tel") String tel,@Param("type") String type);
	
	/*
	 * 保存任务id,解决保存任务时的id不能插入的问题，默认的保存方式id是自增长的
	 */
	@Modifying
	@Query(value="insert into t_work_order(id,EndTime,StartTime,WXhsh," +
			"elementid,packageid,productid,telNu,msgAcceptance,msgReach) value(" +
			"?,?,?,?," +
			"?,?,?,?,?,?)",nativeQuery=true)
	void saveOneWork(@Param("id") String id,@Param("EndTime") Date EndTime,
			@Param("StartTime") Date StartTime,@Param("WXhsh") String WXhsh,@Param("elementid") String elementid,
			@Param("packageid") String packageid,
			@Param("productid") String productid,@Param("telNu") String telNu,@Param("msgAcceptance")boolean msgAcceptance,@Param("msgReach")boolean msgReach);
}