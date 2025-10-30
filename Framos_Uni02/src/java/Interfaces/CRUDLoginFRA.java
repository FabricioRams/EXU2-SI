package Interfaces;

import Modelo.ClsUsuarioFRA;

/**
 *
 * @author Mi Equipo
 * Operaciones permitidas para la autenticación dentro del sistema FRA.
 */
public interface CRUDLoginFRA {
    

    ClsUsuarioFRA iniciarSesion(String correo, String contrasena);
}