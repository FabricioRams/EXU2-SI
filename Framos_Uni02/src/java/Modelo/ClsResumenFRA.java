package Modelo;

import java.io.Serializable;

/**
 *
 * @author Mi Equipo
 * Resumen estadístico para el tablero de control de la aplicación FRA.
 */
    
public class ClsResumenFRA implements Serializable {

    private int totalReclamos;
    private int pendientes;
    private int enProceso;
    private int resueltos;
    private int cerrados;

    public int getTotalReclamos() {
        return totalReclamos;
    }

    public void setTotalReclamos(int totalReclamos) {
        this.totalReclamos = totalReclamos;
    }

    public int getPendientes() {
        return pendientes;
    }

    public void setPendientes(int pendientes) {
        this.pendientes = pendientes;
    }

    public int getEnProceso() {
        return enProceso;
    }

    public void setEnProceso(int enProceso) {
        this.enProceso = enProceso;
    }

    public int getResueltos() {
        return resueltos;
    }

    public void setResueltos(int resueltos) {
        this.resueltos = resueltos;
    }

    public int getCerrados() {
        return cerrados;
    }

    public void setCerrados(int cerrados) {
        this.cerrados = cerrados;
    }
}