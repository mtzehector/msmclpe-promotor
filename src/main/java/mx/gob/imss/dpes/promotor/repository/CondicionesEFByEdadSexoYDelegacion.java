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
import mx.gob.imss.dpes.baseback.persistence.RegistroActivoSpecification;
import mx.gob.imss.dpes.promotor.entity.MclcCondicionesEF;
import mx.gob.imss.dpes.promotor.entity.MclcCondicionesEF_;


/**
 *
 * @author edgar.arenas
 */
public class CondicionesEFByEdadSexoYDelegacion extends RegistroActivoSpecification<MclcCondicionesEF> {

    private final Long cveEntidadFinanciera;
    private final Long cveDelegacion;
    private final Integer cveSexo;

    public CondicionesEFByEdadSexoYDelegacion(Long cveEntidadFinanciera, Long cveDelegacion, Integer cveSexo) {
        this.cveEntidadFinanciera = cveEntidadFinanciera;
        this.cveDelegacion = cveDelegacion;
        this.cveSexo = cveSexo;
    }

    @Override
    public Predicate toPredicate(Root<MclcCondicionesEF> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate uno = cb.equal(root.get(MclcCondicionesEF_.cveEntidadFinanciera), this.cveEntidadFinanciera);
        Predicate dos = cb.equal(root.get(MclcCondicionesEF_.cveDelegacion), this.cveDelegacion);
        Predicate tres = cb.equal(root.get(MclcCondicionesEF_.cveSexo), this.cveSexo);

        return cb.and(uno, dos, tres);
    }

}
