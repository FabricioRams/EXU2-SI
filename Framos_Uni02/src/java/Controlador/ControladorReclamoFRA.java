package Controlador;

import Modelo.ClsCategoriaFRA;
import Modelo.ClsReclamoFRA;
import Modelo.ClsResumenFRA;
import Modelo.ClsSeguimientoFRA;
import Modelo.ClsUsuarioFRA;
import ModeloDAO.ClsReclamoDAOFRA;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mi Equipo
 * Controlador principal para la gestión de reclamos dentro del sistema FRA.
 */
    
@WebServlet(name = "ControladorReclamoFRA", urlPatterns = {"/ControladorReclamoFRA"})
public class ControladorReclamoFRA extends HttpServlet {

    private final ClsReclamoDAOFRA reclamoDAO = new ClsReclamoDAOFRA();

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
        if (usuarioSesion == null) {
            response.sendRedirect("VistaFRA/LoginFRA.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = usuarioSesion.getIdRol() == 1 ? "panelAdmin" : "panelUsuario";
        }

        switch (accion) {
            case "panelAdmin":
                mostrarPanelAdmin(request, response);
                break;
            case "panelUsuario":
                mostrarPanelUsuario(request, response, usuarioSesion);
                break;
            case "listarAdmin":
                listarParaAdministracion(request, response);
                break;
            case "listarUsuario":
                listarParaUsuario(request, response, usuarioSesion);
                break;
            case "detalle":
                mostrarDetalle(request, response, usuarioSesion);
                break;
            case "registrar":
                registrarReclamo(request, response, usuarioSesion);
                break;
            case "actualizar":
                actualizarReclamo(request, response, usuarioSesion);
                break;
            case "seguimiento":
                registrarSeguimiento(request, response, usuarioSesion);
                break;
            case "formulario":
                mostrarFormulario(request, response, usuarioSesion);
                break;
            case "historial":
                mostrarHistorial(request, response, usuarioSesion);
                break;
            case "reporte":
                mostrarReporte(request, response);
                break;
            default:
                if (usuarioSesion.getIdRol() == 1) {
                    mostrarPanelAdmin(request, response);
                } else {
                    mostrarPanelUsuario(request, response, usuarioSesion);
                }
        }
    }

    private void mostrarPanelAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ClsResumenFRA resumen = reclamoDAO.obtenerResumen();
        List<ClsReclamoFRA> reclamosRecientes = reclamoDAO.listarParaAdministracion()
                .stream().limit(5).collect(Collectors.toList());
        request.setAttribute("resumen", resumen);
        request.setAttribute("reclamosRecientes", reclamosRecientes);
        request.getRequestDispatcher("VistaFRA/AdminFRA/PanelAdminFRA.jsp").forward(request, response);
    }

    private void mostrarPanelUsuario(HttpServletRequest request, HttpServletResponse response, ClsUsuarioFRA usuario)
            throws ServletException, IOException {
        List<ClsReclamoFRA> reclamos = reclamoDAO.listarPorUsuario(usuario.getIdUsuario());
        long pendientes = reclamos.stream().filter(r -> "Pendiente".equalsIgnoreCase(r.getEstado())).count();
        long enProceso = reclamos.stream().filter(r -> "En Proceso".equalsIgnoreCase(r.getEstado())).count();
        long resueltos = reclamos.stream().filter(r -> "Resuelto".equalsIgnoreCase(r.getEstado())).count();
        request.setAttribute("pendientes", pendientes);
        request.setAttribute("enProceso", enProceso);
        request.setAttribute("resueltos", resueltos);
        request.setAttribute("reclamosRecientes", reclamos.stream().limit(5).collect(Collectors.toList()));
        request.getRequestDispatcher("VistaFRA/UsuarioFRA/PanelUsuarioFRA.jsp").forward(request, response);
    }

    private void listarParaAdministracion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ClsReclamoFRA> reclamos = reclamoDAO.listarParaAdministracion();
        List<ClsCategoriaFRA> categorias = reclamoDAO.listarCategoriasActivas();
        request.setAttribute("listaReclamos", reclamos);
        request.setAttribute("listaCategorias", categorias);
        request.getRequestDispatcher("VistaFRA/AdminFRA/GestionReclamosFRA.jsp").forward(request, response);
    }

    private void listarParaUsuario(HttpServletRequest request, HttpServletResponse response, ClsUsuarioFRA usuario)
            throws ServletException, IOException {
        List<ClsReclamoFRA> reclamos = reclamoDAO.listarPorUsuario(usuario.getIdUsuario());
        request.setAttribute("listaReclamos", reclamos);
        request.getRequestDispatcher("VistaFRA/UsuarioFRA/ListarReclamosFRA.jsp").forward(request, response);
    }

    private void mostrarDetalle(HttpServletRequest request, HttpServletResponse response, ClsUsuarioFRA usuario)
            throws ServletException, IOException {
        int idReclamo = Integer.parseInt(request.getParameter("idReclamo"));
        ClsReclamoFRA reclamo = reclamoDAO.obtenerPorId(idReclamo);
        if (reclamo == null) {
            response.sendRedirect("ControladorReclamoFRA?accion=listarUsuario");
            return;
        }
        if (usuario.getIdRol() != 1 && reclamo.getIdUsuario() != usuario.getIdUsuario()) {
            response.sendRedirect("ControladorReclamoFRA?accion=listarUsuario");
            return;
        }
        List<ClsSeguimientoFRA> seguimientos = reclamoDAO.listarSeguimiento(idReclamo);
        request.setAttribute("reclamo", reclamo);
        request.setAttribute("listaSeguimiento", seguimientos);
        request.setAttribute("listaCategorias", reclamoDAO.listarCategoriasActivas());
        request.getRequestDispatcher("VistaFRA/AdminFRA/DetalleReclamoFRA.jsp").forward(request, response);
    }

    private void registrarReclamo(HttpServletRequest request, HttpServletResponse response, ClsUsuarioFRA usuario)
            throws IOException {
        ClsReclamoFRA reclamo = new ClsReclamoFRA();
        reclamo.setCodigoReclamo(reclamoDAO.generarCodigoReclamo());
        reclamo.setAsunto(request.getParameter("asunto"));
        reclamo.setDescripcion(request.getParameter("descripcion"));
        reclamo.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
        reclamo.setIdUsuario(usuario.getIdUsuario());
        reclamo.setEstado("Pendiente");
        Date hoy = Date.valueOf(LocalDate.now());
        reclamo.setFechaRegistro(hoy);
        reclamo.setFechaActualizacion(hoy);
        boolean registrado = reclamoDAO.registrar(reclamo);
        if (registrado) {
            response.sendRedirect("ControladorReclamoFRA?accion=listarUsuario&mensaje=Reclamo%20registrado%20correctamente");
        } else {
            response.sendRedirect("ControladorReclamoFRA?accion=formulario&mensaje=No%20se%20pudo%20registrar");
        }
    }

    private void actualizarReclamo(HttpServletRequest request, HttpServletResponse response, ClsUsuarioFRA usuario)
            throws IOException {
        if (usuario.getIdRol() != 1) {
            response.sendRedirect("ControladorReclamoFRA?accion=listarUsuario");
            return;
        }
        ClsReclamoFRA reclamo = reclamoDAO.obtenerPorId(Integer.parseInt(request.getParameter("idReclamo")));
        if (reclamo == null) {
            response.sendRedirect("ControladorReclamoFRA?accion=listarAdmin");
            return;
        }
        reclamo.setAsunto(request.getParameter("asunto"));
        reclamo.setDescripcion(request.getParameter("descripcion"));
        reclamo.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
        reclamoDAO.actualizar(reclamo);
        response.sendRedirect("ControladorReclamoFRA?accion=detalle&idReclamo=" + reclamo.getIdReclamo());
    }

    private void registrarSeguimiento(HttpServletRequest request, HttpServletResponse response, ClsUsuarioFRA usuario)
            throws IOException {
        if (usuario.getIdRol() != 1) {
            response.sendRedirect("ControladorReclamoFRA?accion=listarUsuario");
            return;
        }
        ClsSeguimientoFRA seguimiento = new ClsSeguimientoFRA();
        seguimiento.setIdReclamo(Integer.parseInt(request.getParameter("idReclamo")));
        seguimiento.setEstado(request.getParameter("estado"));
        seguimiento.setComentario(request.getParameter("comentario"));
        seguimiento.setUsuarioRegistro(usuario.getNombres() + " " + usuario.getApellidos());
        reclamoDAO.registrarSeguimiento(seguimiento);
        response.sendRedirect("ControladorReclamoFRA?accion=detalle&idReclamo=" + seguimiento.getIdReclamo());
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, ClsUsuarioFRA usuario)
            throws ServletException, IOException {
        if (usuario.getIdRol() == 1) {
            response.sendRedirect("ControladorReclamoFRA?accion=listarAdmin");
            return;
        }
        request.setAttribute("listaCategorias", reclamoDAO.listarCategoriasActivas());
        request.getRequestDispatcher("VistaFRA/UsuarioFRA/RegistrarReclamoFRA.jsp").forward(request, response);
    }

    private void mostrarHistorial(HttpServletRequest request, HttpServletResponse response, ClsUsuarioFRA usuario)
            throws ServletException, IOException {
        List<ClsReclamoFRA> reclamos = reclamoDAO.listarPorUsuario(usuario.getIdUsuario());
        request.setAttribute("listaReclamos", reclamos);
        request.getRequestDispatcher("VistaFRA/UsuarioFRA/HistorialReclamoFRA.jsp").forward(request, response);
    }

    private void mostrarReporte(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ClsResumenFRA resumen = reclamoDAO.obtenerResumen();
        request.setAttribute("resumen", resumen);
        request.setAttribute("listaReclamos", reclamoDAO.listarParaAdministracion());
        request.getRequestDispatcher("VistaFRA/AdminFRA/ReporteResumenFRA.jsp").forward(request, response);
    }
}