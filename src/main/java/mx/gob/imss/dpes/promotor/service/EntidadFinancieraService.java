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
import mx.gob.imss.dpes.interfaces.entidadfinanciera.model.EntidadFinanciera;
import mx.gob.imss.dpes.promotor.exeption.ServiceException;
import mx.gob.imss.dpes.promotor.model.RequestPromotorModel;
import mx.gob.imss.dpes.promotor.restclient.EntidadFinancieraClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author osiris.hernandez
 */
@Provider
public class EntidadFinancieraService extends ServiceDefinition<RequestPromotorModel, RequestPromotorModel> {
    
    @Inject
    @RestClient
    private EntidadFinancieraClient entidadFinancieraClient;
    
    @Override
    public Message<RequestPromotorModel> execute(Message<RequestPromotorModel> request)  throws
          BusinessException {
        log.log(Level.INFO, ">>> promotorBack EntidadFinancieraService.execute BACK STEP 3 request.getPayload()= {0}", request.getPayload());
        if (!request.getPayload().getCurp().isEmpty()) {

            try {
                Response load = entidadFinancieraClient.getEntidadFinanciera(request.getPayload().getCveEntidadFinanciera());

                if (load.getStatus() == 200) {
                    EntidadFinanciera response = load.readEntity(EntidadFinanciera.class);
                    request.getPayload().setEntidadFinanciera(response);
                    request.getPayload().setNombre(request.getPayload().getBdtuOut().getNombre());
                    request.getPayload().setPrimerApellido(request.getPayload().getBdtuOut().getPrimerApellido());
                    request.getPayload().setSegundoApellido(request.getPayload().getBdtuOut().getSegundoApellido());
                    log.log(Level.INFO, ">>> EntidadFinancieraService.execute BACK STEP 3 TERMINA request.getPayload()= {0}", request.getPayload());
                    return new Message<>(request.getPayload());
                }
                else
                    throw new ServiceException();
            } catch (BusinessException e) {
                throw e;
            } catch (Exception e) {
                log.log(Level.SEVERE,"ERROR EntidadFinancieraService.execute ", e);
                throw new ServiceException();
            }
        } else {
            return request;
        }
    }
    
}
