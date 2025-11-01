/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.promotor.entity.McltCondiciones;
import mx.gob.imss.dpes.promotor.entity.McltCondiciones_;

/**
 *
 * @author edgar.arenas
 */
public class CondicionesByEntidadYPlazo extends BaseSpecification<McltCondiciones>{
    
    private final Long entidadFinanciera;
    private final Long plazo;
    
    public CondicionesByEntidadYPlazo(Long entidadFinanciera, Long plazo){
        this.entidadFinanciera=entidadFinanciera;
        this.plazo=plazo;
    }
    
    @Override
    public Predicate toPredicate(Root<McltCondiciones> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        
        Predicate uno = cb.equal(root.get(McltCondiciones_.entidadFinanciera), this.entidadFinanciera);
        Predicate dos = cb.equal(root.get(McltCondiciones_.plazo), this.plazo);
        
        return cb.and(uno , dos);
    }
}
