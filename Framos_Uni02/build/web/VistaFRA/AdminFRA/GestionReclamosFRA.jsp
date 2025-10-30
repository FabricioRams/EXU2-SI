<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.ClsUsuarioFRA"%>
<%@page import="Modelo.ClsReclamoFRA"%>
<%@page import="Modelo.ClsCategoriaFRA"%>
<%@page import="java.util.List"%>
<%
    ClsUsuarioFRA usuarioSesion = (ClsUsuarioFRA) session.getAttribute("usuarioSesion");
    if (usuarioSesion == null || usuarioSesion.getIdRol() != 1) {
        response.sendRedirect(request.getContextPath() + "/VistaFRA/LoginFRA.jsp");
        return;
    }
    List<ClsReclamoFRA> listaReclamos = (List<ClsReclamoFRA>) request.getAttribute("listaReclamos");
    List<ClsCategoriaFRA> listaCategorias = (List<ClsCategoriaFRA>) request.getAttribute("listaCategorias");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
         <meta charset="UTF-8">
        <title>Gestión de Reclamos FRA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">FRA Admin</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=panelAdmin">Inicio</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorUsuarioFRA?accion=listar">Usuarios</a></li>
                        <li class="nav-item"><a class="nav-link active" href="#">Reclamos</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=reporte">Reportes</a></li>
                    </ul>
                    <a class="btn btn-outline-light" href="<%=request.getContextPath()%>/ControladorLoginFRA?accion=CerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </nav>
        <div class="container py-4">
            <% if (listaCategorias != null && !listaCategorias.isEmpty()) { %>
            <div class="alert alert-secondary">
                Categorías FRA disponibles:
                <% for (ClsCategoriaFRA categoria : listaCategorias) { %>
                <span class="badge bg-light text-dark border border-secondary"><%=categoria.getNombreCategoria()%></span>
                <% } %>
            </div>
            <% } %>
            <div class="card">
                <div class="card-header bg-light">Listado de reclamos FRA</div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-striped mb-0">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Asunto</th>
                                    <th>Usuario</th>
                                    <th>Categoría</th>
                                    <th>Estado</th>
                                    <th>Registro</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <% if (listaReclamos != null && !listaReclamos.isEmpty()) {
                                       for (ClsReclamoFRA reclamo : listaReclamos) { %>
                                <tr>
                                    <td><%=reclamo.getCodigoReclamo()%></td>
                                    <td><%=reclamo.getAsunto()%></td>
                                    <td><%=reclamo.getNombreUsuario()%></td>
                                    <td><%=reclamo.getNombreCategoria()%></td>
                                    <td><span class="badge bg-secondary"><%=reclamo.getEstado()%></span></td>
                                    <td><%=reclamo.getFechaRegistro()%></td>
                                    <td class="text-end">
                                        <a class="btn btn-sm btn-outline-primary" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=detalle&idReclamo=<%=reclamo.getIdReclamo()%>">Gestionar</a>
                                    </td>
                                </tr>
                                <%       }
                                   } else { %>
                                <tr>
                                    <td colspan="7" class="text-center py-3">No se encontraron reclamos registrados.</td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
