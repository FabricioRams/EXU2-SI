package Modelo;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Mi Equipo
 * Representa el reclamo registrado por un usuario dentro del sistema FRA.
 */
    
public class ClsReclamoFRA implements Serializable {

    private int idReclamo;
    private String codigoReclamo;
    private String asunto;
    private String descripcion;
    private Date fechaRegistro;
    private Date fechaActualizacion;
    private String estado;
    private int idUsuario;
    private int idCategoria;
    private String nombreUsuario;
    private String nombreCategoria;

    public ClsReclamoFRA() {
    }

    public int getIdReclamo() {
        return idReclamo;
    }

    public void setIdReclamo(int idReclamo) {
        this.idReclamo = idReclamo;
    }

    public String getCodigoReclamo() {
        return codigoReclamo;
    }

    public void setCodigoReclamo(String codigoReclamo) {
        this.codigoReclamo = codigoReclamo;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}