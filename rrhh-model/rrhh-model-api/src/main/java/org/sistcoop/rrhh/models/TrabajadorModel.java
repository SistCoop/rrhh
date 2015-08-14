package org.sistcoop.rrhh.models;

public interface TrabajadorModel extends Model {

    String getId();

    String getTipoDocumento();

    String getNumeroDocumento();

    AgenciaModel getAgencia();

    void setAgencia(AgenciaModel agenciaModel);

    AreaModel getArea();

    void setArea(AreaModel areaModel);

    CargoModel getCargo();

    void setCargo(CargoModel cargoModel);

    TrabajadorUsuarioModel getTrabajadorUsuarioModel();

}