/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.imss.dpes.promotor.service;

import java.util.ArrayList;
import java.util.Collection;
import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.promotor.entity.McltCondiciones;
import mx.gob.imss.dpes.promotor.entity.McltPersona;
import mx.gob.imss.dpes.promotor.repository.CondicionesByEntidadYPlazo;
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
public class CondicionesOfertasService extends BaseCRUDService<McltCondiciones, McltCondiciones, Long, Long>{
    
  @Autowired
  private CondicionesOfertasRepository repository;
  
    public Message<McltCondiciones> execute(Message<McltCondiciones> request) throws BusinessException {
    Collection<BaseSpecification> constraints = new ArrayList<>();
    constraints.add( new CondicionesByEntidadYPlazo( request.getPayload().getEntidadFinanciera(), request.getPayload().getPlazo()) );
    McltCondiciones entity = findOne(constraints);
    return new Message<>(entity);
    }
    
      @Override
    public JpaSpecificationExecutor<McltCondiciones> getRepository() {
        return repository;
    }

    @Override
    public JpaRepository<McltCondiciones, Long> getJpaRepository() {
        return repository;
    }
}
