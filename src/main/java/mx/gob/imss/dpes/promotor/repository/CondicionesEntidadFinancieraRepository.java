/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.repository;

import mx.gob.imss.dpes.promotor.entity.MclcCondicionesEF;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author edgar.arenas
 */
public interface CondicionesEntidadFinancieraRepository extends JpaRepository<MclcCondicionesEF, Long>,
JpaSpecificationExecutor<MclcCondicionesEF>{
    
}
