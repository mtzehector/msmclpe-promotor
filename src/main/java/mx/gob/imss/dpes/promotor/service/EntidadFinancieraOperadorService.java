package mx.gob.imss.dpes.promotor.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.interfaces.entidadfinanciera.model.EntidadFinanciera;
import mx.gob.imss.dpes.promotor.exeption.ServiceException;
import mx.gob.imss.dpes.promotor.model.RequestPromotorModel;
import mx.gob.imss.dpes.promotor.restclient.EntidadFinancieraClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

@Provider
public class EntidadFinancieraOperadorService extends ServiceDefinition<RequestPromotorModel, RequestPromotorModel> {
    
    @Inject
    @RestClient
    private EntidadFinancieraClient entidadFinancieraClient;
    
    @Override
    public Message<RequestPromotorModel> execute(Message<RequestPromotorModel> request)  throws
          BusinessException {
        log.log(Level.INFO, ">>> promotorBack EntidadFinancieraOperadorService.execute request.getPayload()= {0}", request.getPayload());
        if (request.getPayload().getCveEntidadFinanciera() != null) {

            try {
                Response load = entidadFinancieraClient.getEntidadFinanciera(request.getPayload().getCveEntidadFinanciera());

                if (load.getStatus() == 200) {
                    EntidadFinanciera response = load.readEntity(EntidadFinanciera.class);
                    request.getPayload().setEntidadFinanciera(response);
                    return new Message<>(request.getPayload());
                }
                else
                    throw new ServiceException();
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                log.log(Level.SEVERE,"ERROR EntidadFinancieraOperadorService.execute ", e);
                throw new ServiceException();
            }
        } else {
            return request;
        }
    }
    
}
