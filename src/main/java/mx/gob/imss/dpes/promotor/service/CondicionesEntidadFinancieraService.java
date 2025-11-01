/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.interfaces.entidadfinanciera.model.CondicionesEntidadFinanciera;
import mx.gob.imss.dpes.promotor.entity.MclcCondicionesEF;
import mx.gob.imss.dpes.promotor.entity.McltCondiciones;
import mx.gob.imss.dpes.promotor.repository.CondicionesByEntidadYPlazo;
import mx.gob.imss.dpes.promotor.repository.CondicionesEFByEdadSexoYDelegacion;
import mx.gob.imss.dpes.promotor.repository.CondicionesEntidadFinancieraRepository;
import mx.gob.imss.dpes.promotor.repository.CondicionesOfertasRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author edgar.arenas
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class CondicionesEntidadFinancieraService extends
    BaseCRUDService<MclcCondicionesEF, MclcCondicionesEF, Long, Long> {

    @Autowired
    private CondicionesEntidadFinancieraRepository repository;

    public Message<MclcCondicionesEF> execute(Message<CondicionesEntidadFinanciera> request) throws BusinessException {
        try {
            Collection<BaseSpecification> constraints = new ArrayList<>();
            constraints.add(new CondicionesEFByEdadSexoYDelegacion(request.getPayload().getCveEntidadFinanciera(),
                request.getPayload().getCveDelegacion(), request.getPayload().getCveSexo()));
            MclcCondicionesEF entity = findOne(constraints);
            if (entity != null)
                return new Message<>(entity);
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR CondicionesEntidadFinancieraService.execute request = [" + request + "]", e);
        }

        return null;
    }

    @Override
    public JpaSpecificationExecutor<MclcCondicionesEF> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<MclcCondicionesEF, Long> getJpaRepository() {
        return repository;
    }
}
