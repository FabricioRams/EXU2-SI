package Modelo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Mi Equipo
 * Mantiene la traza de cambios que experimenta un reclamo dentro del sistema.
 */   
public class ClsSeguimientoFRA implements Serializable {

    private int idSeguimiento;
    private int idReclamo;
    private Timestamp fechaMovimiento;
    private String estado;
    private String comentario;
    private String usuarioRegistro;

    public ClsSeguimientoFRA() {
    }

    public int getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(int idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public int getIdReclamo() {
        return idReclamo;
    }

    public void setIdReclamo(int idReclamo) {
        this.idReclamo = idReclamo;
    }

    public Timestamp getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Timestamp fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuarioRegistro() {
        return usuarioRegistro;
    }

    public void setUsuarioRegistro(String usuarioRegistro) {
        this.usuarioRegistro = usuarioRegistro;
    }
}