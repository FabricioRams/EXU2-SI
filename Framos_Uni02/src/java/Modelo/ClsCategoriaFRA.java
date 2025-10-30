package Modelo;

import java.io.Serializable;

/**
 *
 * @author Mi Equipo
 * Categoría a la que puede pertenecer un reclamo dentro del sistema FRA.
 */
    
public class ClsCategoriaFRA implements Serializable {

    private int idCategoria;
    private String nombreCategoria;
    private String descripcion;
    private boolean estado;

    public ClsCategoriaFRA() {
    }

    public ClsCategoriaFRA(int idCategoria, String nombreCategoria, String descripcion, boolean estado) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}