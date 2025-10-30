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
    List<ClsReclamoFRA> reclamosRecientes = (List<ClsReclamoFRA>) request.getAttribute("reclamosRecientes");
    long pendientes = request.getAttribute("pendientes") != null ? (Long) request.getAttribute("pendientes") : 0;
    long enProceso = request.getAttribute("enProceso") != null ? (Long) request.getAttribute("enProceso") : 0;
    long resueltos = request.getAttribute("resueltos") != null ? (Long) request.getAttribute("resueltos") : 0;
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Panel del Usuario FRA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">FRA Usuario</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link active" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=panelUsuario">Inicio</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=formulario">Registrar reclamo</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=listarUsuario">Mis reclamos</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=historial">Historial</a></li>
                    </ul>
                    <span class="navbar-text me-3">Hola, <strong><%=usuarioSesion.getNombres()%></strong></span>
                    <a class="btn btn-outline-light" href="<%=request.getContextPath()%>/ControladorLoginFRA?accion=CerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </nav>
        <div class="container py-4">
            <div class="row g-3">
                <div class="col-md-4">
                    <div class="card text-bg-warning text-center">
                        <div class="card-body">
                            <h5>Pendientes</h5>
                            <p class="display-6"><%=pendientes%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-bg-info text-center">
                        <div class="card-body">
                            <h5>En Proceso</h5>
                            <p class="display-6"><%=enProceso%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card text-bg-success text-center">
                        <div class="card-body">
                            <h5>Resueltos</h5>
                            <p class="display-6"><%=resueltos%></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card mt-4">
                <div class="card-header bg-light">Últimos reclamos FRA</div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-striped mb-0">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Asunto</th>
                                    <th>Estado</th>
                                    <th>Fecha</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <% if (reclamosRecientes != null && !reclamosRecientes.isEmpty()) {
                                       for (ClsReclamoFRA reclamo : reclamosRecientes) { %>
                                <tr>
                                    <td><%=reclamo.getCodigoReclamo()%></td>
                                    <td><%=reclamo.getAsunto()%></td>
                                    <td><span class="badge bg-secondary"><%=reclamo.getEstado()%></span></td>
                                    <td><%=reclamo.getFechaRegistro()%></td>
                                    <td><a class="btn btn-sm btn-outline-primary" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=detalle&idReclamo=<%=reclamo.getIdReclamo()%>">Ver</a></td>
                                </tr>
                                <%       }
                                   } else { %>
                                <tr>
                                    <td colspan="5" class="text-center py-3">No registraste reclamos aún.</td>
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