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
import mx.gob.imss.dpes.promotor.exeption.ServiceException;
import mx.gob.imss.dpes.promotor.model.RequestPersona;
import mx.gob.imss.dpes.promotor.model.RequestPromotorModel;
import mx.gob.imss.dpes.promotor.restclient.PersonaBDTUOnlyClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

/**
 *
 * @author osiris.hernandez
 */
@Provider
public class PersonaBDTUOnlyService extends ServiceDefinition<RequestPromotorModel, RequestPromotorModel> {

    @Inject
    @RestClient
    private PersonaBDTUOnlyClient personaClient;

    @Override
    public Message<RequestPromotorModel> execute(Message<RequestPromotorModel> request) throws BusinessException {
        log.log(Level.INFO, ">>> promotorBack PersonaBDTUOnlyService.execute request.getPayload()= {0}", request.getPayload());
        RequestPersona peticion = new RequestPersona();
        peticion.setCurp(request.getPayload().getCurp());
        peticion.setNss(request.getPayload().getNss());
        log.log(Level.INFO, ">>> promotorBack PersonaBDTUOnlyService.execute peticion= {0}", peticion.getNss());
        try {
            Response responseLoad = personaClient.load(peticion);
            log.log(Level.INFO, ">>> promotorBack PersonaBDTUOnlyService.execute responseLoad= {0}", responseLoad);

            if(responseLoad != null && responseLoad.getStatus() == Response.Status.OK.getStatusCode())
                return response(responseLoad, request);
            else
                throw new ServiceException();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.log(Level.SEVERE,"ERROR PersonaBDTUOnlyService.execute ", e);
            throw new ServiceException();
        }
    }

    @Override
    protected Message<RequestPromotorModel> onOk(Response response, Message<RequestPromotorModel> request) {
        RequestPromotorModel model = response.readEntity(RequestPromotorModel.class);
        model.setIdPersonaEF(request.getPayload().getId());
        model.setCurp(request.getPayload().getCurp());
        model.setCveEntidadFinanciera(request.getPayload().getCveEntidadFinanciera());
        model.setNumEmpleado(request.getPayload().getNumEmpleado());
        if(model.getBdtuOut() != null){
            model.setPrimerApellido(model.getBdtuOut().getPrimerApellido());
            model.setSegundoApellido(model.getBdtuOut().getSegundoApellido());
            model.setNombre(model.getBdtuOut().getNombre());
        }
        return new Message<>(model);
    }
}
