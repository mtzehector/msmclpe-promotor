/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.service;

import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.promotor.entity.McltPersona;
import mx.gob.imss.dpes.promotor.exeption.PersonalEFException;
import mx.gob.imss.dpes.promotor.repository.PersonaActivaEnEFByCurp;
import mx.gob.imss.dpes.promotor.repository.PromotorPersonaRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import javax.interceptor.Interceptors;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;

/**
 *
 * @author cesar.romero
 */
@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ObtenerPersonaActivaEnEFByCurp extends BaseCRUDService<McltPersona, McltPersona, Long, Long>{

  @Autowired
  private PromotorPersonaRepository repository;

  
  public Message<McltPersona> execute(Message<McltPersona> request) throws
          BusinessException {
    try {
      Collection<BaseSpecification> constraints = new ArrayList<>();
      constraints.add(new PersonaActivaEnEFByCurp(request.getPayload().getCurp()));
      McltPersona entity = findOne(constraints);
      if (entity != null) {
        return new Message<>(entity);
      }
      return null;
    } catch (Exception e) {
      log.log(Level.SEVERE, "ERROR ObtenerPersonaActivaEnEFByCurp.execute ", e);
      throw new PersonalEFException();
    }
  }

  @Override
  public JpaSpecificationExecutor<McltPersona> getRepository() {
    return repository;
  }

  @Override
  public JpaRepository<McltPersona, Long> getJpaRepository() {
    return repository;
  }
}

