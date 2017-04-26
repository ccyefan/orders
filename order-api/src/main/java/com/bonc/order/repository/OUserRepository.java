package com.bonc.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bonc.order.domain.OUser;
@RepositoryRestResource(collectionResourceRel = "ouser", path = "ouser")
public interface OUserRepository extends JpaRepository<OUser, Long>,JpaSpecificationExecutor<OUser> {
	OUser findByTelNumber(@Param("telnum") String telnumber);
}
