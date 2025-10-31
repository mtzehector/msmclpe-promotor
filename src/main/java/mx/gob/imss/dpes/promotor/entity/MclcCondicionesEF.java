/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author edgar.arenas
 */
@Entity
@Table(name = "MCLC_CONDICION_ENTFED")
public class MclcCondicionesEF extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_CONDICION_ENTFED")
    @GeneratedValue(generator = "SEQ_GEN_MCLS_CONDICION_ENTFED")
    @GenericGenerator(
            name = "SEQ_GEN_MCLS_CONDICION_ENTFED",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_CONDICION_ENTFED")
                ,
        @Parameter(name = "initial_value", value = "1")
                ,
        @Parameter(name = "increment_size", value = "1")
            }
    )
    @Getter
    @Setter
    private Long id;

    @Basic(optional = false)
    @Column(name = "CVE_DELEGACION")
    @Getter
    @Setter
    private Long cveDelegacion;

    @Column(name = "NUM_EDAD_LIMITE")
    @Getter
    @Setter
    private Integer edadLimite;

    @Column(name = "MON_MINIMO")
    @Getter
    @Setter
    private Double MontoMinimo;

    @Column(name = "MON_MAXIMO")
    @Getter
    @Setter
    private Double MontoMaximo;

    @Column(name = "CVE_ENTIDAD_FINANCIERA")
    @Getter
    @Setter
    private Long cveEntidadFinanciera;

    @Column(name = "CVE_SEXO")
    @Getter
    @Setter
    private Integer cveSexo;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MclcCondicionesEF)) {
            return false;
        }
        MclcCondicionesEF other = (MclcCondicionesEF) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

}
