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
public class PromotorByCvePromotor extends BaseSpecification<McltPersonalEf>{
    private final Long id;

    public PromotorByCvePromotor(Long id){
        this.id=id;
    }
    @Override
    public Predicate toPredicate(Root<McltPersonalEf> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get(McltPersonalEf_.id), this.id);
    }
}
