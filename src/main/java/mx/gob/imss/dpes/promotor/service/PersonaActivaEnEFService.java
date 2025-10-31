/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.service;

import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.promotor.exeption.ServiceException;
import mx.gob.imss.dpes.promotor.model.RequestPromotorModel;
import mx.gob.imss.dpes.promotor.restclient.PromotorClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;

/**
 *
 * @author osiris.hernandez
 */
@Provider
public class PersonaActivaEnEFService extends ServiceDefinition<RequestPromotorModel, RequestPromotorModel> {

    @Inject
    @RestClient
    private PromotorClient promotorClient;

    @Override
    public Message<RequestPromotorModel> execute(Message<RequestPromotorModel> request) throws BusinessException {        
        log.log(Level.INFO, ">>> PersonaActivaEnEFService.execute request.getPayload()= {0}", request.getPayload());
        try {
            Response responseLoad = promotorClient.obtenerInformacionPersona(request.getPayload());
            if(responseLoad != null && responseLoad.getStatus() == Response.Status.OK.getStatusCode())
                return response(responseLoad, request);
            else if (responseLoad != null && responseLoad.getStatus() == Response.Status.NO_CONTENT.getStatusCode())
                throw new ServiceException(ServiceException.INFORMACION_NO_DISPONIBLE);
            else
                throw new ServiceException();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,"ERROR PersonaActivaEnEFService.execute ", e);
            throw new ServiceException();
        }
    }

    @Override
    protected Message<RequestPromotorModel> onOk(Response response, Message<RequestPromotorModel> request) {
        return request;
    }
}
