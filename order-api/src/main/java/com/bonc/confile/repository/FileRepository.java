package com.bonc.confile.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.bonc.confile.domain.FileInfo;
@RepositoryRestResource(collectionResourceRel="file",path="file")
public interface FileRepository extends JpaRepository<FileInfo,Long>{
	
}
