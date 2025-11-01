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
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.dpes.common.entity.LogicDeletedEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 *
 * @author andrea.cervantes
 */
@Entity
@Table(name = "MCLT_PERSONAL_EF")
public class McltPersonalEf extends LogicDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CVE_PERSONAL_EF", updatable = false, nullable = false)

    @Getter
    @Setter
    @GeneratedValue(generator = "SEQ_GEN_MCLS_PERSONAL_EF")
    @GenericGenerator(
            name = "SEQ_GEN_MCLS_PERSONAL_EF",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                @Parameter(name = "sequence_name", value = "MCLS_PERSONAL_EF")
                ,
        @Parameter(name = "initial_value", value = "1")
                ,
        @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    @Size(max = 20)
    @Column(name = "NUM_EMPLEADO")
    @Getter
    @Setter
    private String numEmpleado;
    @Size(max = 2)
    @Column(name = "CVE_DELEGACION")
    @Getter
    @Setter
    private String cveDelegacion;
    @Basic(optional = false)
    @Size(min = 1, max = 18)
    @Column(name = "CVE_CURP")
    @Getter
    @Setter
    private String curp;
    @Basic(optional = false)
    @Size(min = 1, max = 18)
    @Column(name = "CVE_ENTIDAD_FINANCIERA")
    @Getter
    @Setter
    private String cveEntidadFinanciera;
    @Basic(optional = false)
    @Size(min = 1, max = 18)
    @Column(name = "CVE_ESTADO_PERSONA_ENT_FI")
    @Getter
    @Setter
    private String cveEstadoPersonaEntFi;
    @Basic(optional = false)
    @Size(min = 1, max = 18)
    @Column(name = "CVE_TIPO_PER_ENT_FIN")
    @Getter
    @Setter
    private Long tipoPersonaEF;
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof McltPersonalEf)) {
            return false;
        }
        McltPersonalEf other = (McltPersonalEf) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}