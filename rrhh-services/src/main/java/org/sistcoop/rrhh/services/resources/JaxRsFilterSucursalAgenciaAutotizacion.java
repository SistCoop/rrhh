package org.sistcoop.rrhh.services.resources;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
@PreMatching
public class JaxRsFilterSucursalAgenciaAutotizacion implements ContainerRequestFilter {

    public static final String AUTHENTICATION_COOKIE = "SISTCOOP_RRHH_CREDENTIAL";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        Cookie cookieCredentials = requestContext.getCookies().get("SISTCOOP_RRHH_CREDENTIAL");
        // better injected
        boolean authenticationStatus = AuthenticationService.authenticate(cookieCredentials.getValue());

        if (!authenticationStatus) {
            throw new WebApplicationException(Status.UNAUTHORIZED);
        }
    }

}
