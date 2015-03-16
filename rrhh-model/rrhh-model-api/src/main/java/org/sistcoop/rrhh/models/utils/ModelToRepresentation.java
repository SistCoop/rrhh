package org.sistcoop.rrhh.models.utils;

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

public class ModelToRepresentation {

	/**
	 * Devuelve un objeto SucursalRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static SucursalRepresentation toRepresentation(SucursalModel model) {
		if (model == null)
			return null;
		SucursalRepresentation rep = new SucursalRepresentation();

		rep.setId(model.getId());
		rep.setAbreviatura(model.getAbreviatura());
		rep.setDenominacion(model.getDenominacion());
		rep.setEstado(model.getEstado());

		return rep;
	}

	/**
	 * Devuelve un objeto AgenciaRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static AgenciaRepresentation toRepresentation(AgenciaModel model) {
		if (model == null)
			return null;
		AgenciaRepresentation rep = new AgenciaRepresentation();
		rep.setId(model.getId());
		rep.setAbreviatura(model.getAbreviatura());
		rep.setDenominacion(model.getDenominacion());
		rep.setCodigo(model.getCodigo());
		rep.setEstado(model.getEstado());
		rep.setUbigeo(model.getUbigeo());

		SucursalModel sucursalModel = model.getSucursal();
		SucursalRepresentation sucursalRepresentation = toRepresentation(sucursalModel);
		rep.setSucursal(sucursalRepresentation);
		return rep;
	}

	/**
	 * Devuelve un objeto TrabajadorRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static TrabajadorRepresentation toRepresentation(TrabajadorModel model) {
		if (model == null)
			return null;
		TrabajadorRepresentation rep = new TrabajadorRepresentation();
		rep.setId(model.getId());
		rep.setTipoDocumento(model.getTipoDocumento());
		rep.setNumeroDocumento(model.getNumeroDocumento());

		AgenciaModel agenciaModel = model.getAgencia();
		AgenciaRepresentation agenciaRepresentation = toRepresentation(agenciaModel);
		rep.setAgencia(agenciaRepresentation);

		return rep;
	}

}
