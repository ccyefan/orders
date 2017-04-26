package com.bonc.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.bonc.product.domain.ActivityDetailTmp;

@RepositoryRestResource(collectionResourceRel = "activitydetailtmp", path = "activitydetailtmp")
public interface ActivityDetailTmpRepository extends JpaRepository<ActivityDetailTmp, Long> , JpaSpecificationExecutor<ActivityDetailTmp>{

}
