package com.bonc.confile.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bonc.confile.domain.ConfItem;
import com.bonc.confile.domain.FileInfo;
@RepositoryRestResource(collectionResourceRel="confitem",path="confitem")
public interface ConfItemRepository extends JpaRepository<ConfItem, Long> {
	//List<ConfItem> findByConfKey(@Param("confkey") String confkey);  正确
	//List<ConfItem> findByFileInfo(@Param("fileid") long fileid);
	List<ConfItem> findByFileInfo(FileInfo fileInfo);  //成功
/*	@Query(value="select i from ConfItem i where i.fileInfo = : fileId");
	List<ConfItem> findByFileInfo(@Param("fileId") long fileId);*/
}
