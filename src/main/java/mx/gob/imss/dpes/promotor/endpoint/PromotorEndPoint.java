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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import mx.gob.imss.dpes.common.endpoint.BaseGUIEndPoint;
import mx.gob.imss.dpes.common.enums.TipoPersonaEFEnum;
import mx.gob.imss.dpes.common.exception.AlternateFlowException;
import mx.gob.imss.dpes.common.exception.BusinessException;
import mx.gob.imss.dpes.common.model.Message;
import mx.gob.imss.dpes.common.model.ServiceStatusEnum;
import mx.gob.imss.dpes.common.service.ServiceDefinition;
import mx.gob.imss.dpes.promotor.entity.McltPersona;
import mx.gob.imss.dpes.promotor.entity.McltPersonalEf;
import mx.gob.imss.dpes.promotor.exeption.PromotorEndPointException;
import mx.gob.imss.dpes.promotor.model.RequestPromotorModel;
import mx.gob.imss.dpes.promotor.service.EntidadFinancieraService;
import mx.gob.imss.dpes.promotor.service.EntidadFinancieraOperadorService;
import mx.gob.imss.dpes.promotor.service.ObtenerPersonaActivaEnEFByCurp;
import mx.gob.imss.dpes.promotor.service.ObtenerPromotorPesistenceServiceByCurp;
import mx.gob.imss.dpes.promotor.service.ObtenerPromotorPersistenceService;
import mx.gob.imss.dpes.promotor.service.PensionadoService;
import mx.gob.imss.dpes.promotor.service.PersonaActivaEnEFService;
import mx.gob.imss.dpes.promotor.service.PersonaBDTUOnlyService;
import mx.gob.imss.dpes.promotor.service.PersonaService;
import mx.gob.imss.dpes.promotor.service.PromotorService;
import mx.gob.imss.dpes.promotor.service.PromotorServiceClave;
import mx.gob.imss.dpes.promotor.service.PromotorServiceCurp;
import org.eclipse.microprofile.openapi.annotations.Operation;

@ApplicationScoped
@Path("/promotor")
public class PromotorEndPoint extends BaseGUIEndPoint<McltPersonalEf, McltPersonalEf, McltPersonalEf> {

    @Context
    private UriInfo uriInfo;

    @Inject
    private ObtenerPromotorPersistenceService obtenerPromotorPersistenceService;

    @Inject
    private ObtenerPromotorPesistenceServiceByCurp obtenerPromotorPesistenceServiceByCurp;

    @Inject
    PromotorServiceClave promotorServiceClave;

    @Inject
    PromotorServiceCurp promotorServiceCurp;

    @Inject
    PersonaService personaService;

    @Inject
    PersonaBDTUOnlyService personaBDTUOnlyService;

    @Inject
    PensionadoService pensionadoService;

    @Inject
    EntidadFinancieraService entidadFinancieraService;

    @Inject
    PromotorService promotorService;

    @Inject
    ObtenerPersonaActivaEnEFByCurp obtenerPersonaActivaEnEFByCurpAndTipoEmpleado;

    @Inject
    PersonaActivaEnEFService personaActivaEnEFService;

    @Inject
    private EntidadFinancieraOperadorService entidadFinancieraOperadorService;
  
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/clave")
    public Response porClave(McltPersonalEf promotor) {

        try {
            //log.log(Level.INFO, ">>> promotorBack PromotorEndPoint.porClave promotor= {0}", promotor);
            if (promotor.getId() != null) {
                Message<McltPersonalEf> execute = obtenerPromotorPersistenceService.execute(new Message<McltPersonalEf>(promotor));
                //log.log(Level.INFO, ">>> promotorBack PromotorEndPoint.porClave McltPersonalEf= {0}", execute);
                if (execute != null) {
                    return toResponse(execute);
                }
//                else {
//                    throw new AlternateFlowException();
//                }
            }
            return Response.noContent().build();
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PromotorEndPoint.porClave ", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                new PromotorEndPointException(), null));
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/curp")
    public Response porCurp(McltPersonalEf promotor) {
        log.log(Level.INFO, ">>>promotorBACK PromotorEndPoint.porCurp ENDPOINT  promotor={0}", promotor);

        if (!(promotor != null && promotor.getCurp() != null && !promotor.getCurp().isEmpty() &&
                promotor.getTipoPersonaEF() != null && (promotor.getTipoPersonaEF() == 1 || promotor.getTipoPersonaEF() == 2)
        ))
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            Message<McltPersonalEf> execute
                    = obtenerPromotorPesistenceServiceByCurp.execute(new Message<McltPersonalEf>(promotor));
            //log.log(Level.INFO, ">>>promotorBACK PromotorEndPoint.porCurp ENDPOINT RESULT CONSULTA execute= {0}", execute);
            if (execute != null)
                return toResponse(execute);
            else
                return Response.noContent().build();
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PromotorEndPoint.porCurp ", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new PromotorEndPointException(),
                    null));
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/obtenerInformacionPersona")
    public Response obtenerInformacionPersona(RequestPromotorModel persona) {
        log.log(Level.INFO, ">>>promotorBACK PromotorEndPoint.obtenerInformacionPersona ENDPOINT persona={0}", persona);

        if (!(persona != null && persona.getCurp() != null && !persona.getCurp().isEmpty() &&
                persona.getTipoPersonaEF() != null && ((persona.getTipoPersonaEF() == TipoPersonaEFEnum.PROMOTOR ||
                persona.getTipoPersonaEF() == TipoPersonaEFEnum.OPERADOR_ENTIDAD_FINANCIERA))
        ))
            return Response.status(Response.Status.BAD_REQUEST).build();

        try {
            McltPersona input = new McltPersona();
            input.setCurp(persona.getCurp().trim());

            Message<McltPersona> execute
                    = obtenerPersonaActivaEnEFByCurpAndTipoEmpleado.execute(new Message<McltPersona>(input));
            //log.log(Level.INFO, ">>>promotorBACK PromotorEndPoint.obtenerInformacionPersona ENDPOINT RESULT CONSULTA execute= {0}", execute);
            if (execute != null)
                return toResponse(execute);
            else
                return Response.noContent().build();
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PromotorEndPoint.obtenerInformacionPersona ", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new PromotorEndPointException(),
                    null));
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtiene los datos de un promotor", description = "Obtiene los datos de un promotor")
    @Path("/obtenerPorClave")
    public Response obtenerPorClave(RequestPromotorModel request) {
        log.log(Level.INFO, ">>> promotorBack PromotorEndPoint.obtenerPorClave request= {0}", request);
        
        ServiceDefinition[] steps = {promotorService, promotorServiceClave , personaService, entidadFinancieraService};
        try {
            Message<RequestPromotorModel> response = promotorServiceClave.executeSteps(steps, new Message<>(request));
            log.log(Level.INFO, ">>> promotorBack PromotorEndPoint.obtenerPorClave response= {0}", response);
            return toResponse(response);
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PromotorEndPoint.obtenerPorClave ", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new PromotorEndPointException(),
                    null));
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtiene los datos de un promotor", description = "Obtiene los datos de un promotor")
    @Path("/obtenerPorCurp")
    public Response obtenerPorCurp(RequestPromotorModel request) {
        //log.log(Level.INFO, ">>> promotorBack PromotorEndPoint.obtenerPorCurp request= {0}", request);
        ServiceDefinition[] steps = {promotorServiceCurp, personaService, entidadFinancieraService};

        try {
            Message<RequestPromotorModel> response = promotorServiceCurp.executeSteps(steps, new Message<>(request));
            //log.log(Level.INFO, ">>> >>> promotorBack PromotorEndPoint.obtenerPorCurp response= {0}", response);
            return toResponse(response);
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PromotorEndPoint.obtenerPorCurp ", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new PromotorEndPointException(),
                    null));
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtiene los datos de un promotor", description = "Obtiene los datos de un promotor")
    @Path("/obtenerPorCurpOP")
    public Response obtenerPorCurpOP(RequestPromotorModel request) {
        //log.log(Level.INFO, ">>> promotorBack PromotorEndPoint.obtenerPorCurp request= {0}", request);

        if (!(request != null && request.getCurp() != null && !request.getCurp().trim().isEmpty() &&
                request.getTipoPersonaEF() != null &&
                (request.getTipoPersonaEF() == TipoPersonaEFEnum.PROMOTOR ||
                        request.getTipoPersonaEF() == TipoPersonaEFEnum.OPERADOR_ENTIDAD_FINANCIERA)
        ))
            return Response.status(Response.Status.BAD_REQUEST).build();

        ServiceDefinition[] steps = {personaActivaEnEFService, promotorServiceCurp, entidadFinancieraOperadorService};
        try {
            Message<RequestPromotorModel> response = personaActivaEnEFService.executeSteps(steps, new Message<>(request));
            //log.log(Level.INFO, ">>> >>> promotorBack PromotorEndPoint.obtenerPorCurp response= {0}", response);
            return toResponse(response);
        }
        catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PromotorEndPoint.obtenerPorCurpOP ", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new PromotorEndPointException(),
                    null));
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtiene los datos de un promotor", description = "Obtiene los datos de un promotor")
    @Path("/obtenerPorCurpNss")
    public Response obtenerPorCurpNss(RequestPromotorModel request) {
        //log.log(Level.INFO, ">>> promotorFront PromotorEndPoint.obtenerPorCurpNss request= {0}", request);

        if (!(request != null && request.getCurp() != null && !request.getCurp().trim().isEmpty() &&
                request.getTipoPersonaEF() != null &&
                (request.getTipoPersonaEF() == TipoPersonaEFEnum.PROMOTOR ||
                        request.getTipoPersonaEF() == TipoPersonaEFEnum.OPERADOR_ENTIDAD_FINANCIERA) &&
                request.getNss() != null && !request.getNss().trim().isEmpty()
        ))
            return Response.status(Response.Status.BAD_REQUEST).build();

        ServiceDefinition[] steps = {personaActivaEnEFService, promotorServiceCurp, personaBDTUOnlyService, entidadFinancieraService};
        try {
            Message<RequestPromotorModel> response = personaActivaEnEFService.executeSteps(steps, new Message<>(request));
            //og.log(Level.INFO, ">>> >>> promotorFront PromotorEndPoint.obtenerPorCurpNss response= {0}", response);
            return toResponse(response);
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PromotorEndPoint.obtenerPorCurpNss ", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new PromotorEndPointException(),
                    null));
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtiene los datos de un promotor", description = "Obtiene los datos de un promotor")
    @Path("/obtenerPorCurpNssCI")
    public Response obtenerPorCurpNssCI(RequestPromotorModel request) {
        //log.log(Level.INFO, ">>> promotorFront PromotorEndPoint.obtenerPorCurpNssCI request= {0}", request);
        ServiceDefinition[] steps = {promotorServiceClave, personaBDTUOnlyService, entidadFinancieraService};
        try {
            Message<RequestPromotorModel> response = promotorServiceCurp.executeSteps(steps, new Message<>(request));
            //log.log(Level.INFO, ">>> >>> promotorFront PromotorEndPoint.obtenerPorCurpNssCI response= {0}", response);
            return toResponse(response);
        } catch (BusinessException e) {
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION, e, null));
        } catch (Exception e) {
            log.log(Level.SEVERE, "ERROR PromotorEndPoint.obtenerPorCurpNssCI ", e);
            return toResponse(new Message(null, ServiceStatusEnum.EXCEPCION,
                    new PromotorEndPointException(),
                    null));
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/validar/persona")
    public Response validarPersona(McltPersona request)throws BusinessException{
        
        log.log(Level.INFO, "PROMOTOR BACK, ENDPOINT validarPersona  {0}", request);
        Message<McltPersona> execute = pensionadoService.execute(new Message<McltPersona>(request));
        log.log(Level.INFO, "BACK TERMINA validarPersona ENDPOINT {0}", execute);
        return toResponse(execute);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/persistir/persona")
    public Response persistirPersona(McltPersona request)throws BusinessException{
        
        log.log(Level.INFO, "PROMOTOR BACK, ENDPOINT persistirPersona  {0}", request);
        McltPersona execute = pensionadoService.persistirPersona(request);
        log.log(Level.INFO, "PROMOTOR BACK TERMINA persistirPersona  {0}", execute);
        return toResponse(new Message<>(execute));
    }
    
}
