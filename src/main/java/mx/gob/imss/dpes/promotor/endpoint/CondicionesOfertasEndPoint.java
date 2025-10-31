/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.endpoint;

import java.util.logging.Level;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.exception.AlternateFlowException;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.interfaces.entidadfinanciera.model.CondicionesEntidadFinanciera;
import mx.gob.imss.dpes.promotor.entity.MclcCondicionesEF;
import mx.gob.imss.dpes.promotor.entity.McltCondiciones;
import mx.gob.imss.dpes.promotor.exeption.PromotorEndPointException;
import mx.gob.imss.dpes.promotor.service.CondicionesEntidadFinancieraService;
import mx.gob.imss.dpes.promotor.service.CondicionesOfertasService;

/**
 *
 * @author edgar.arenas
 */
@ApplicationScoped
@Path("/condiciones")
public class CondicionesOfertasEndPoint extends BaseGUIEndPoint<McltCondiciones, McltCondiciones, McltCondiciones>{
    
    @Inject
    CondicionesOfertasService service;
    
    @Inject
    CondicionesEntidadFinancieraService condicionesEF;
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCondicionesOfertas(McltCondiciones request) {
        try {
            //log.log(Level.INFO, "PROMOTOR BACK, ENDPOINT getCondicionesOfertas  {0}", request);
            Message<McltCondiciones> execute = service.execute(new Message<>(request));
            //log.log(Level.INFO, "PROMOTOR BACK TERMINA getCondicionesOfertas  {0}", execute);
            return toResponse(new Message<>(execute.getPayload()));
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR CondicionesOfertasEndPoint.getCondicionesOfertas request = [" + request + "]", e);
        }

        return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
            new PromotorEndPointException(), null));
    }
    
    @Path("/condicionesEF")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validarCondicionesEF(CondicionesEntidadFinanciera request) {

        try {
            //log.log(Level.INFO, ">>>CondicionesOfertasEndPoint|validarCondicionesEF  {0}", request);
            Message<MclcCondicionesEF> execute = condicionesEF.execute(new Message<>(request));
            //log.log(Level.INFO, ">>>CondicionesOfertasEndPoint|validarCondicionesEF  {0}", execute);
            if (execute != null)
                return toResponse(new Message<>(execute.getPayload()));
        } catch (Exception e) {
            log.log(Level.SEVERE,
                "ERROR CondicionesOfertasEndPoint.validarCondicionesEF request = [" + request + "]", e);
        }

        return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                new PromotorEndPointException(),
                null));
    }
}
