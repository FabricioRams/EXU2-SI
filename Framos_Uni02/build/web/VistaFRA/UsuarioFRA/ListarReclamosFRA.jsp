<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.ClsUsuarioFRA"%>
<%@page import="Modelo.ClsReclamoFRA"%>
<%@page import="java.util.List"%>
<%
    ClsUsuarioFRA usuarioSesion = (ClsUsuarioFRA) session.getAttribute("usuarioSesion");
    if (usuarioSesion == null || usuarioSesion.getIdRol() == 1) {
        response.sendRedirect(request.getContextPath() + "/VistaFRA/LoginFRA.jsp");
        return;
    }
    List<ClsReclamoFRA> listaReclamos = (List<ClsReclamoFRA>) request.getAttribute("listaReclamos");
    String mensaje = request.getParameter("mensaje");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Mis Reclamos FRA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">FRA Usuario</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=panelUsuario">Inicio</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=formulario">Registrar reclamo</a></li>
                        <li class="nav-item"><a class="nav-link active" href="#">Mis reclamos</a></li>
                    </ul>
                    <a class="btn btn-outline-light" href="<%=request.getContextPath()%>/ControladorLoginFRA?accion=CerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </nav>
        <div class="container py-4">
            <% if (mensaje != null) { %>
            <div class="alert alert-success"><%=mensaje%></div>
            <% } %>
            <div class="card">
                <div class="card-header bg-light">Listado de mis reclamos FRA</div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-striped mb-0">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Asunto</th>
                                    <th>Categoría</th>
                                    <th>Estado</th>
                                    <th>Fecha</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <% if (listaReclamos != null && !listaReclamos.isEmpty()) {
                                       for (ClsReclamoFRA reclamo : listaReclamos) { %>
                                <tr>
                                    <td><%=reclamo.getCodigoReclamo()%></td>
                                    <td><%=reclamo.getAsunto()%></td>
                                    <td><%=reclamo.getNombreCategoria()%></td>
                                    <td><span class="badge bg-secondary"><%=reclamo.getEstado()%></span></td>
                                    <td><%=reclamo.getFechaRegistro()%></td>
                                    <td><a class="btn btn-sm btn-outline-primary" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=detalle&idReclamo=<%=reclamo.getIdReclamo()%>">Ver</a></td>
                                </tr>
                                <%       }
                                   } else { %>
                                <tr>
                                    <td colspan="6" class="text-center py-3">No tienes reclamos registrados.</td>
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