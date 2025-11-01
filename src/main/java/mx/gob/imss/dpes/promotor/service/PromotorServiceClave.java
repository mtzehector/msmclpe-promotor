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
import mx.gob.imss.dpes.promotor.exeption.PromotorEndPointException;
import mx.gob.imss.dpes.promotor.model.RequestPromotorModel;
import mx.gob.imss.dpes.promotor.restclient.PromotorClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author osiris.hernandez
 */
@Provider
public class PromotorServiceClave extends ServiceDefinition<RequestPromotorModel, RequestPromotorModel> {
    
    @Inject
    @RestClient
    private PromotorClient service;

    @Override
    public Message<RequestPromotorModel> execute(Message<RequestPromotorModel> request) throws BusinessException {
        try {
            //se inserta solicitud
            //log.log( Level.INFO, ">>> PromotorServiceClave  Request hacia PromotorBack: {0}", request.getPayload());
            Response responseLoad = service.obtenerPorClave(request.getPayload());
            //log.log( Level.INFO, ">>> PromotorServiceClave  Request hacia PromotorBack responseLoad: {0}", responseLoad);
            if(responseLoad.getStatus() == Response.Status.OK.getStatusCode())
                return response(responseLoad, request);
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PromotorServiceClave.execute ", e);
        }

        throw new PromotorEndPointException();
    }

    @Override
    protected Message<RequestPromotorModel> onOk(Response response, Message<RequestPromotorModel> request) {
        RequestPromotorModel model = response.readEntity(RequestPromotorModel.class);
        model.setIdPersonaEF(request.getPayload().getId());
        if(request.getPayload().getNss()!=null && request.getPayload().getNss().trim().length()>0){
            model.setNss(request.getPayload().getNss());
        }
        if(request.getPayload().getCurp()!=null && request.getPayload().getCurp().trim().length()>0){
             model.setCurp(request.getPayload().getCurp());
        }
        return new Message<>(model);
    }
}