package com.bonc.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bonc.product.domain.WechatUser;
@RepositoryRestResource(collectionResourceRel = "wechatUser" ,path = "wechatuser")
public interface WechatUserRepository extends JpaRepository<WechatUser, Long> , JpaSpecificationExecutor<WechatUser>{
	WechatUser findByTelphone(@Param("telphone")String telphone);
}
