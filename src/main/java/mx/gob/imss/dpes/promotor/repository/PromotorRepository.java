package mx.gob.imss.dpes.promotor.repository;

import mx.gob.imss.dpes.promotor.entity.McltPersonalEf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;



public interface PromotorRepository extends JpaRepository<McltPersonalEf, Long>,
JpaSpecificationExecutor<McltPersonalEf> {
	

}
