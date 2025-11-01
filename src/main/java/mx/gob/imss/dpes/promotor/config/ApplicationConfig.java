/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.promotor.config;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 *
 * @author antonio
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
    resources.add(mx.gob.imss.dpes.common.exception.AlternateFlowMapper.class);
    resources.add(mx.gob.imss.dpes.common.exception.BusinessMapper.class);
    resources.add(mx.gob.imss.dpes.common.rule.MontoTotalRule.class);
    resources.add(mx.gob.imss.dpes.common.rule.PagoMensualRule.class);
    resources.add(mx.gob.imss.dpes.promotor.endpoint.CondicionesOfertasEndPoint.class);
    resources.add(mx.gob.imss.dpes.promotor.endpoint.PromotorEndPoint.class);
    resources.add(mx.gob.imss.dpes.promotor.service.CondicionesEntidadFinancieraService.class);
        resources.add(mx.gob.imss.dpes.promotor.service.CondicionesOfertasService.class);
        resources.add(mx.gob.imss.dpes.promotor.service.EntidadFinancieraService.class);
        resources.add(mx.gob.imss.dpes.promotor.service.ObtenerPromotorPesistenceServiceByCurp.class);
        resources.add(mx.gob.imss.dpes.promotor.service.ObtenerPromotorPersistenceService.class);
        resources.add(mx.gob.imss.dpes.promotor.service.PensionadoService.class);
        resources.add(mx.gob.imss.dpes.promotor.service.PersonaBDTUOnlyService.class);
        resources.add(mx.gob.imss.dpes.promotor.service.PersonaService.class);
        resources.add(mx.gob.imss.dpes.promotor.service.PromotorPersonaService.class);
        resources.add(mx.gob.imss.dpes.promotor.service.PromotorService.class);
        resources.add(mx.gob.imss.dpes.promotor.service.PromotorServiceClave.class);
        resources.add(mx.gob.imss.dpes.promotor.service.PromotorServiceCurp.class);
        resources.add(mx.gob.imss.dpes.promotor.service.EntidadFinancieraOperadorService.class);
    }

}
