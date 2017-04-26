package com.bonc.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.method.P;

import com.bonc.product.domain.ActivityDetail;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "activitydetail", path = "activitydetail")
public interface ActivityDetailRepository extends JpaRepository<ActivityDetail, Long> , JpaSpecificationExecutor<ActivityDetail>{

	@Query(value="select * from t_activity_detail where activity_id=:acid", nativeQuery = true)
	List<ActivityDetail> findActi(@Param("acid") Long id);
	
	@Query(value="select acd from ActivityDetail acd where acd.activityStep =:activityStep")
	ActivityDetail findByAcStep(@Param("activityStep") String activityStep);
}
