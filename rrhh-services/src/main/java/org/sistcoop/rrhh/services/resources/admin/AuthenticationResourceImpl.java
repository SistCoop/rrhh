package org.sistcoop.rrhh.services.resources.admin;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpRequest;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.sistcoop.rrhh.admin.client.resource.AuthenticationResource;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;
import org.sistcoop.rrhh.models.search.SearchCriteriaFilterOperator;
import org.sistcoop.rrhh.models.search.SearchCriteriaModel;
import org.sistcoop.rrhh.models.search.SearchResultsModel;
import org.sistcoop.rrhh.models.search.filters.TrabajadorUsuarioFilterProvider;
import org.sistcoop.rrhh.services.resources.AuthenticationService;
import org.sistcoop.rrhh.services.resources.Secured;

@Stateless
public class AuthenticationResourceImpl implements AuthenticationResource {

    @Context
    private HttpRequest httpRequest;

    @Inject
    private TrabajadorUsuarioProvider trabajadorUsuarioProvider;

    @Inject
    private TrabajadorUsuarioFilterProvider trabajadorUsuarioFilterProvider;

    @Secured
    @Override
    public String login(String sucursal, String agencia) {
        // Just to show how to user info from access token in REST endpoint
        KeycloakSecurityContext securityContext = (KeycloakSecurityContext) httpRequest
                .getAttribute(KeycloakSecurityContext.class.getName());
        AccessToken accessToken = securityContext.getToken();

        // Obtener Usuario
        String usuario = accessToken.getPreferredUsername();

        SearchCriteriaModel searchCriteriaBean = new SearchCriteriaModel();
        // add filter
        searchCriteriaBean.addFilter(trabajadorUsuarioFilterProvider.getUsuarioFilter(), usuario,
                SearchCriteriaFilterOperator.eq);

        // search
        SearchResultsModel<TrabajadorUsuarioModel> results = trabajadorUsuarioProvider
                .search(searchCriteriaBean);
        if (results.getTotalSize() == 0) {
            throw new BadRequestException("Usuario no encontrado en RRHH");
        }

        // Verificar
        TrabajadorUsuarioModel trabajadorUsuarioModel = results.getModels().get(0);
        TrabajadorModel trabajadorModel = trabajadorUsuarioModel.getTrabajador();
        AgenciaModel agenciaModel = trabajadorModel.getAgencia();
        SucursalModel sucursalModel = agenciaModel.getSucursal();

        if (!sucursal.equals(sucursalModel.getDenominacion())
                || !agencia.equals(agenciaModel.getDenominacion())) {
            throw new BadRequestException("Usuario no autorizado para la agencia y sucursal indicada");
        }

        // Crear persitencia de autenticacion
        return AuthenticationService.createJWT(AuthenticationService.apiKey_id, accessToken.getIssuer(),
                accessToken.getSubject(), 300000L);
    }

}
