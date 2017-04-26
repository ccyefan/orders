package com.bonc.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.bonc.product.domain.ActivityTmp;

@RepositoryRestResource(collectionResourceRel = "activitytmp", path = "activitytmp")
public interface ActivityTmpRepository extends JpaRepository<ActivityTmp, Long> , JpaSpecificationExecutor<ActivityTmp>{

}
