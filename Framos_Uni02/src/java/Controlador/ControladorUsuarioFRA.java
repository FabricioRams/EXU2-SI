package Controlador;

import Modelo.ClsRolFRA;
import Modelo.ClsUsuarioFRA;
import ModeloDAO.ClsUsuarioDAOFRA;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mi Equipo
 * Controlador encargado de la administración de usuarios para FRA.
 */
    
@WebServlet(name = "ControladorUsuarioFRA", urlPatterns = {"/ControladorUsuarioFRA"})
public class ControladorUsuarioFRA extends HttpServlet {

    private static final String JSP_LISTA = "VistaFRA/AdminFRA/GestionUsuariosFRA.jsp";

    private final ClsUsuarioDAOFRA usuarioDAO = new ClsUsuarioDAOFRA();

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
        HttpSession session = request.getSession(false);
        ClsUsuarioFRA usuarioSesion = session == null ? null : (ClsUsuarioFRA) session.getAttribute("usuarioSesion");
        if (usuarioSesion == null || usuarioSesion.getIdRol() != 1) {
            response.sendRedirect("VistaFRA/LoginFRA.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                mostrarListado(request, response);
                break;
            case "guardar":
                registrarUsuario(request, response);
                break;
            case "editar":
                cargarUsuarioEditar(request, response);
                break;
            case "actualizar":
                actualizarUsuario(request, response);
                break;
            case "cambiarEstado":
                actualizarEstado(request, response);
                break;
            default:
                mostrarListado(request, response);
        }
    }

    private void mostrarListado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ClsUsuarioFRA> usuarios = usuarioDAO.listarUsuarios();
        List<ClsRolFRA> roles = usuarioDAO.listarRoles();
        request.setAttribute("listaUsuarios", usuarios);
        request.setAttribute("listaRoles", roles);
        request.getRequestDispatcher(JSP_LISTA).forward(request, response);
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ClsUsuarioFRA usuario = construirUsuarioDesdeRequest(request);
        usuario.setEstado(true);
        boolean registrado = usuarioDAO.registrar(usuario);
        if (registrado) {
            response.sendRedirect("ControladorUsuarioFRA?accion=listar&mensaje=Usuario%20registrado%20correctamente");
        } else {
            response.sendRedirect("ControladorUsuarioFRA?accion=listar&mensaje=No%20se%20pudo%20registrar");
        }
    }

    private void cargarUsuarioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idUsuario"));
        ClsUsuarioFRA usuarioEditar = usuarioDAO.obtenerPorId(id);
        request.setAttribute("usuarioEditar", usuarioEditar);
        mostrarListado(request, response);
    }

    private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        ClsUsuarioFRA usuario = construirUsuarioDesdeRequest(request);
        usuario.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
        boolean actualizado = usuarioDAO.actualizar(usuario);
        if (actualizado) {
            response.sendRedirect("ControladorUsuarioFRA?accion=listar&mensaje=Usuario%20actualizado%20correctamente");
        } else {
            response.sendRedirect("ControladorUsuarioFRA?accion=listar&mensaje=No%20se%20pudo%20actualizar");
        }
    }

    private void actualizarEstado(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int idUsuario = Integer.parseInt(request.getParameter("idUsuario"));
        boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
        usuarioDAO.actualizarEstado(idUsuario, estado);
        response.sendRedirect("ControladorUsuarioFRA?accion=listar");
    }

    private ClsUsuarioFRA construirUsuarioDesdeRequest(HttpServletRequest request) {
        ClsUsuarioFRA usuario = new ClsUsuarioFRA();
        usuario.setDocumento(request.getParameter("documento"));
        usuario.setNombres(request.getParameter("nombres"));
        usuario.setApellidos(request.getParameter("apellidos"));
        usuario.setCorreo(request.getParameter("correo"));
        usuario.setTelefono(request.getParameter("telefono"));
        usuario.setContrasena(request.getParameter("contrasena"));
        usuario.setIdRol(Integer.parseInt(request.getParameter("idRol")));
        return usuario;
    }
}