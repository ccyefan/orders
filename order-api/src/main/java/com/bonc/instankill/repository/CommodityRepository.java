package com.bonc.instankill.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import com.bonc.instankill.domain.Commodity;
import com.bonc.product.domain.Product;
        @RepositoryRestResource(collectionResourceRel = "commodity", path = "commodity")
public interface CommodityRepository extends  JpaRepository<Commodity, Long>{
        	//List<Commodity> findByPeriod(@Param("period") String period);        	
        	List<Commodity> findByPeriod(@Param("period") String period);
}
