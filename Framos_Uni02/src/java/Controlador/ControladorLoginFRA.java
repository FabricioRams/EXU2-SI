package Controlador;

import Modelo.ClsUsuarioFRA;
import ModeloDAO.ClsLoginFRA;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mi Equipo
 * Controlador encargado de la autenticación y cierre de sesión del sistema FRA.
 */
    
@WebServlet(name = "ControladorLoginFRA", urlPatterns = {"/ControladorLoginFRA"})
public class ControladorLoginFRA extends HttpServlet {

    private final ClsLoginFRA loginDAO = new ClsLoginFRA();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            request.getRequestDispatcher("VistaFRA/LoginFRA.jsp").forward(request, response);
            return;
        }

        switch (accion) {
            case "Ingresar":
                autenticar(request, response);
                break;
            case "CerrarSesion":
                cerrarSesion(request, response);
                break;
            default:
                request.getRequestDispatcher("VistaFRA/LoginFRA.jsp").forward(request, response);
        }
    }

    private void autenticar(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        if (correo == null || contrasena == null || correo.isEmpty() || contrasena.isEmpty()) {
            request.setAttribute("mensaje", "Ingrese sus credenciales FRA para continuar.");
            request.getRequestDispatcher("VistaFRA/LoginFRA.jsp").forward(request, response);
            return;
        }

        ClsUsuarioFRA usuario = loginDAO.iniciarSesion(correo, contrasena);
        if (usuario == null) {
            request.setAttribute("mensaje", "Credenciales inválidas o usuario inactivo en FRA.");
            request.getRequestDispatcher("VistaFRA/LoginFRA.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(true);
        session.setAttribute("usuarioSesion", usuario);
        if (usuario.getIdRol() == 1) {
            response.sendRedirect("ControladorReclamoFRA?accion=panelAdmin");
        } else {
            response.sendRedirect("ControladorReclamoFRA?accion=panelUsuario");
        }
    }

    private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("VistaFRA/LoginFRA.jsp");
    }
}