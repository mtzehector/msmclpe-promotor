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
import mx.gob.imss.dpes.promotor.entity.McltPersona;
import mx.gob.imss.dpes.promotor.entity.McltPersona_;

/**
 *
 * @author edgar.arenas
 */
public class PensionadoByCurpYNss extends BaseSpecification<McltPersona>{
    private final String curp;
    private final String nss;
    
    public PensionadoByCurpYNss(String curp, String nss){
        this.curp=curp;
        this.nss=nss;
    }
    
    @Override
    public Predicate toPredicate(Root<McltPersona> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        
        Predicate uno = cb.equal(root.get(McltPersona_.curp), this.curp);
        Predicate dos = cb.equal(root.get(McltPersona_.nss), this.nss);
        
        return cb.and(uno , dos);
    }
}
