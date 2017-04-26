package com.bonc.wechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bonc.wechat.domain.Wechat;

@RepositoryRestResource(collectionResourceRel = "wechat", path = "wechat")
public interface WechatRepository extends JpaRepository<Wechat, Long>,
		JpaSpecificationExecutor<Wechat> {

}
