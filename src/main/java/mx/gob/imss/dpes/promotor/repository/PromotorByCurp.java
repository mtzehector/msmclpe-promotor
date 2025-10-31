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
import mx.gob.imss.dpes.promotor.entity.McltPersonalEf;
import mx.gob.imss.dpes.promotor.entity.McltPersonalEf_;

/**
 *
 * @author osiris.hernandez
 */
public class PromotorByCurp extends BaseSpecification<McltPersonalEf>{
    private final String curp;
    private final Long tipoPersonaEF;

    public PromotorByCurp(String curp, Long tipoPersonaEF){
        this.curp=curp;
        this.tipoPersonaEF=tipoPersonaEF;
    }
    @Override
    public Predicate toPredicate(Root<McltPersonalEf> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.and(
                cb.equal(root.get(McltPersonalEf_.curp), this.curp),
                cb.equal(root.get(McltPersonalEf_.tipoPersonaEF), this.tipoPersonaEF),
                cb.or(
                        cb.isNull(root.get(McltPersonalEf_.cveEstadoPersonaEntFi)),
                        cb.equal(root.get(McltPersonalEf_.cveEstadoPersonaEntFi), "1")
                )

        );
    }
}

