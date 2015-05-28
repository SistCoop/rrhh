package org.sistcoop.rrhh.models.utils;

import org.sistcoop.rrhh.models.AgenciaModel;
import org.sistcoop.rrhh.models.SucursalModel;
import org.sistcoop.rrhh.models.TrabajadorModel;
import org.sistcoop.rrhh.models.TrabajadorUsuarioModel;
import org.sistcoop.rrhh.representations.idm.AgenciaRepresentation;
import org.sistcoop.rrhh.representations.idm.SucursalRepresentation;
import org.sistcoop.rrhh.representations.idm.TrabajadorRepresentation;

public class ModelToRepresentation {

	public static SucursalRepresentation toRepresentation(SucursalModel model) {
		if (model == null)
			return null;
		SucursalRepresentation rep = new SucursalRepresentation();

		rep.setId(model.getId());		
		rep.setDenominacion(model.getDenominacion());

		return rep;
	}

	public static AgenciaRepresentation toRepresentation(AgenciaModel model) {
		if (model == null)
			return null;
		AgenciaRepresentation rep = new AgenciaRepresentation();
		rep.setId(model.getId());
		rep.setDenominacion(model.getDenominacion());
		rep.setDireccion(model.getDireccion());
		rep.setUbigeo(model.getUbigeo());

		SucursalModel sucursalModel = model.getSucursal();
		SucursalRepresentation sucursalRepresentation = toRepresentation(sucursalModel);
		rep.setSucursal(sucursalRepresentation);
		return rep;
	}

	public static TrabajadorRepresentation toRepresentation(TrabajadorModel model) {
		if (model == null)
			return null;
		TrabajadorRepresentation rep = new TrabajadorRepresentation();
		rep.setId(model.getId());
		rep.setTipoDocumento(model.getTipoDocumento());
		rep.setNumeroDocumento(model.getNumeroDocumento());
		rep.setEstado(model.getEstado());
		
		TrabajadorUsuarioModel trabajadorUsuarioModel = model.getTrabajadorUsuarioModel();
		if(trabajadorUsuarioModel != null) {
			rep.setUsuario(trabajadorUsuarioModel.getUsuario());	
		}		
		
		AgenciaModel agenciaModel = model.getAgencia();
		AgenciaRepresentation agenciaRepresentation = new AgenciaRepresentation();
		agenciaRepresentation.setId(agenciaModel.getId());
		agenciaRepresentation.setDenominacion(agenciaModel.getDenominacion());
		agenciaRepresentation.setDireccion(agenciaModel.getDireccion());
		agenciaRepresentation.setUbigeo(agenciaModel.getUbigeo());

		SucursalModel sucursalModel = agenciaModel.getSucursal();
		SucursalRepresentation sucursalRepresentation = new SucursalRepresentation();
		sucursalRepresentation.setId(sucursalModel.getId());
		sucursalRepresentation.setDenominacion(sucursalModel.getDenominacion());
		
		agenciaRepresentation.setSucursal(sucursalRepresentation);
		
		rep.setAgencia(agenciaRepresentation);

		return rep;
	}

}
