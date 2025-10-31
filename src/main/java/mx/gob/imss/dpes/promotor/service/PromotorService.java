/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.service;

import java.util.logging.Level;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.promotor.entity.McltPersona;
import mx.gob.imss.dpes.promotor.entity.McltPersonalEf;
import mx.gob.imss.dpes.promotor.model.RequestPromotorModel;
import mx.gob.imss.dpes.promotor.repository.PromotorPersonaByCve;
import mx.gob.imss.dpes.promotor.restclient.PromotorClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author edgar.arenas
 */
@Provider
public class PromotorService extends ServiceDefinition<RequestPromotorModel, RequestPromotorModel>{
    
    @Inject
    private PromotorPersonaService service;

    @Override
    public Message<RequestPromotorModel> execute(Message<RequestPromotorModel> request) throws BusinessException {
        
        log.log( Level.INFO, "ObteniendoPromotor: {0}", request.getPayload());
        McltPersona p = new McltPersona();
        p.setCvePersonalEf(request.getPayload().getId());
        Message<McltPersona> exec = service.execute(new Message<McltPersona>(p));
        request.getPayload().setCurp(exec.getPayload().getCurp());
        request.getPayload().setNss(exec.getPayload().getNss().toString());
        log.log( Level.INFO, "ResponseObteniendoPromotor: {0}", request.getPayload());
        return request;
    }

}
