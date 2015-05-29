package org.sistcoop.rrhh.services.util;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.spi.HttpRequest;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.sistcoop.rrhh.admin.client.Roles;
import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioProvider;

@Stateless
public class KeycloakSession {

	@Context
    private HttpRequest httpRequest;
	
	@Context 
	private SecurityContext securityContext;
	
	@Inject
	private TrabajadorUsuarioProvider trabajadorUsuarioProvider;
	
	public void validarAdministrarTrabajadoresPorAgencia(AgenciaModel agenciaModel){
		//keycloak username
        KeycloakSecurityContext securityContextKeycloak = (KeycloakSecurityContext) httpRequest.getAttribute(KeycloakSecurityContext.class.getName());
        AccessToken accessToken = securityContextKeycloak.getToken();       
        
		if(securityContext.isUserInRole(Roles.administrar_trabajadores)){
        	//crear trababajador
        } else if(securityContext.isUserInRole(Roles.administrar_trabajadores_agencia)){
        	//verificar que el usuario tenga la agencia y sucursal correcta
        	String username = accessToken.getPreferredUsername();
        	TrabajadorUsuarioModel trabajadorUsuarioModel = trabajadorUsuarioProvider.getTrabajadorUsuarioByUsuario(username);
        	TrabajadorModel trabajadorModel = trabajadorUsuarioModel.getTrabajador();
        	AgenciaModel agenciaDelUsuarioModel = trabajadorModel.getAgencia();        	
        	if(!agenciaDelUsuarioModel.equals(agenciaModel)) {
        		throw new InternalServerErrorException("El usuario no puede crear trabajadores en esta agencia");
        	}
        } else {        	
        	throw new InternalServerErrorException();
        }   
	}
	
}
