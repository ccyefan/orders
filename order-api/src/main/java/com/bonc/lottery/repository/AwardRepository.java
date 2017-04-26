package com.bonc.lottery.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.bonc.lottery.domain.Lottery;
import com.bonc.lottery.domain.Award;
@RepositoryRestResource(collectionResourceRel = "award", path = "award")
       public interface AwardRepository extends  JpaRepository<Award, Long> {

    		List<Award> findByPeriod(@Param("period") String period);
    		//List<Award> findById(@Param("pkId") Long pkId);

		

	
}
