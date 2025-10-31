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
import mx.gob.imss.dpes.promotor.entity.McltPersona;
import mx.gob.imss.dpes.promotor.repository.PensionadoByCurpYNss;
import mx.gob.imss.dpes.promotor.repository.PensionadoRepository;
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
public class PensionadoService extends BaseCRUDService<McltPersona, McltPersona, Long, Long>{
    
  @Autowired
  private PensionadoRepository repository;
  
    public Message<McltPersona> execute(Message<McltPersona> request) throws
          BusinessException {
    Collection<BaseSpecification> constraints = new ArrayList<>();
    constraints.add( new PensionadoByCurpYNss( request.getPayload().getCurp(), request.getPayload().getNss()) );
    McltPersona entity = findOne(constraints);
    if( entity != null ){
      return new Message<>(entity);
    }else{
        McltPersona e = new McltPersona();
        e.setCorreoElectronico("sinregistro");
        return new Message<>(e); 
    }
          
  }
    
  public McltPersona persistirPersona(McltPersona persona)throws BusinessException{
        
        log.log(Level.INFO, ">>>>PROMOTOR BACK persistirPersona {0}", persona);
        McltPersona saved = save(persona);
        
        return saved;
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
