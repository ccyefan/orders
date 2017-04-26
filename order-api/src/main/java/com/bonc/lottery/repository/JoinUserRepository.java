package com.bonc.lottery.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bonc.lottery.domain.AwardUser;
import com.bonc.lottery.domain.JoinUser;
import com.bonc.lottery.domain.Lottery;
import com.bonc.lottery.domain.Award;

@RepositoryRestResource(collectionResourceRel = "join_user", path = "join_user")
public interface JoinUserRepository extends  JpaRepository<JoinUser, Long> {
	List<JoinUser> findByTelNumber(@Param("telNumber") String telNumber);

}
