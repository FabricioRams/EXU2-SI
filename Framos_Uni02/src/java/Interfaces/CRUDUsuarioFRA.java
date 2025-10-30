package Interfaces;

import Modelo.ClsRolFRA;
import Modelo.ClsUsuarioFRA;
import java.util.List;

/**
 *
 * @author Mi Equipo
 * Contrato para las operaciones relacionadas a los usuarios del sistema FRA.
 */
public interface CRUDUsuarioFRA {
    

    List<ClsUsuarioFRA> listarUsuarios();

    ClsUsuarioFRA obtenerPorId(int idUsuario);

    boolean registrar(ClsUsuarioFRA usuario);

    boolean actualizar(ClsUsuarioFRA usuario);

    boolean actualizarEstado(int idUsuario, boolean activo);

    List<ClsRolFRA> listarRoles();
}