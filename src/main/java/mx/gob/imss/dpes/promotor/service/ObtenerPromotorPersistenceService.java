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
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.baseback.persistence.BaseSpecification;
import mx.gob.imss.dpes.baseback.service.BaseCRUDService;
import mx.gob.imss.dpes.promotor.entity.McltPersonalEf;
import mx.gob.imss.dpes.promotor.exeption.PersonalEFException;
import mx.gob.imss.dpes.promotor.repository.PromotorByCvePromotor;
import mx.gob.imss.dpes.promotor.repository.PromotorRepository;
import mx.gob.imss.dpes.support.config.CustomSpringBeanAutowiringInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Provider
@Interceptors(CustomSpringBeanAutowiringInterceptor.class)
public class ObtenerPromotorPersistenceService extends BaseCRUDService<McltPersonalEf, McltPersonalEf, Long, Long>{

  @Autowired
  private PromotorRepository repository;
  
  
  public Message<McltPersonalEf> execute(Message<McltPersonalEf> request) throws BusinessException {
    try {
      Collection<BaseSpecification> constraints = new ArrayList<>();
      constraints.add(new PromotorByCvePromotor(request.getPayload().getId()));
      McltPersonalEf entity = findOne(constraints);
      if (entity != null) {
        return new Message<>(entity);
      } else {
        return null;
      }
    } catch (Exception e) {
      log.log(Level.SEVERE, "ERROR ObtenerPromotorPersistenceService.execute ", e);
      throw new PersonalEFException();
    }
  }

  @Override
  public JpaSpecificationExecutor<McltPersonalEf> getRepository() {
    return repository;
  }

  @Override
  public JpaRepository<McltPersonalEf, Long> getJpaRepository() {
    return repository;
  }
  
}
