package org.sistcoop.rrhh.admin.client.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.cache.NoCache;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/admin")
public interface AdminResource {

	@GET
	@Path("/sucursales")
	@NoCache
	public List<SucursalRepresentation> findAll();

}
