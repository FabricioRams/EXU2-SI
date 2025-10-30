package Modelo;

import java.io.Serializable;

/**
 *
 * @author Mi Equipo
 * Representa un rol permitido dentro de la aplicación FRA.
 */
    
public class ClsRolFRA implements Serializable {

    private int idRol;
    private String nombreRol;
    private String descripcion;

    public ClsRolFRA() {
    }

    public ClsRolFRA(int idRol, String nombreRol, String descripcion) {
        this.idRol = idRol;
        this.nombreRol = nombreRol;
        this.descripcion = descripcion;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}