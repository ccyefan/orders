package com.bonc.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.bonc.product.domain.ProductTmp;

@RepositoryRestResource(collectionResourceRel = "producttmp" ,path = "producttmp")
public interface ProductTmpRepository extends JpaRepository<ProductTmp, Long> , JpaSpecificationExecutor<ProductTmp>{

}
