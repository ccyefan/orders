package com.bonc.lottery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource; 
import com.bonc.lottery.domain.Lottery;
import com.superman.domain.SuperMan;
import com.supermy.domain.Channel;
/**
 * 
 * @author 
 * DAO 
 * 奖品库中查找相同期数的奖品
 *
 */
@RepositoryRestResource(collectionResourceRel = "lottery", path = "lottery")
public interface LotteryRepository extends JpaRepository<Lottery, Long>{
			
	List<Lottery> findByPeriod(@Param("period") String period);

	

}