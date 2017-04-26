package com.bonc.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.bonc.product.domain.Activity;

@RepositoryRestResource(collectionResourceRel = "activity", path = "activity")
public interface ActivityRepository extends JpaRepository<Activity, Long> , JpaSpecificationExecutor<Activity>{
	
}
