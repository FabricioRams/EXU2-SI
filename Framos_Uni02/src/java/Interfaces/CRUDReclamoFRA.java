package Interfaces;

import Modelo.ClsCategoriaFRA;
import Modelo.ClsReclamoFRA;
import Modelo.ClsResumenFRA;
import Modelo.ClsSeguimientoFRA;
import java.util.List;

/**
 *
 * @author Mi Equipo
 * Contrato para las operaciones de gestión de reclamos dentro del sistema FRA.
 */
public interface CRUDReclamoFRA {
    

    List<ClsReclamoFRA> listarParaAdministracion();

    List<ClsReclamoFRA> listarPorUsuario(int idUsuario);

    ClsReclamoFRA obtenerPorId(int idReclamo);

    boolean registrar(ClsReclamoFRA reclamo);

    boolean actualizar(ClsReclamoFRA reclamo);

    boolean registrarSeguimiento(ClsSeguimientoFRA seguimiento);

    List<ClsSeguimientoFRA> listarSeguimiento(int idReclamo);

    List<ClsCategoriaFRA> listarCategoriasActivas();

    ClsResumenFRA obtenerResumen();
}