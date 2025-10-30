<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.ClsUsuarioFRA"%>
<%@page import="Modelo.ClsResumenFRA"%>
<%@page import="Modelo.ClsReclamoFRA"%>
<%@page import="java.util.List"%>
<%
    ClsUsuarioFRA usuarioSesion = (ClsUsuarioFRA) session.getAttribute("usuarioSesion");
    if (usuarioSesion == null || usuarioSesion.getIdRol() != 1) {
        response.sendRedirect(request.getContextPath() + "/VistaFRA/LoginFRA.jsp");
        return;
    }
    ClsResumenFRA resumen = (ClsResumenFRA) request.getAttribute("resumen");
    List<ClsReclamoFRA> reclamosRecientes = (List<ClsReclamoFRA>) request.getAttribute("reclamosRecientes");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Panel Administrativo FRA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">FRA Admin</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link active" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=panelAdmin">Inicio</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorUsuarioFRA?accion=listar">Usuarios</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=listarAdmin">Reclamos</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=reporte">Reportes</a></li>
                    </ul>
                    <span class="navbar-text me-3">Hola, <strong><%=usuarioSesion.getNombres()%></strong></span>
                    <a class="btn btn-outline-light" href="<%=request.getContextPath()%>/ControladorLoginFRA?accion=CerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </nav>
        <div class="container py-4">
            <h2 class="mb-4">Resumen general FRA</h2>
            <div class="row g-3">
                <div class="col-md-3">
                    <div class="card text-bg-primary text-center">
                        <div class="card-body">
                            <h5>Total reclamos</h5>
                            <p class="display-6"><%=resumen != null ? resumen.getTotalReclamos() : 0%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-bg-warning text-center">
                        <div class="card-body">
                            <h5>Pendientes</h5>
                            <p class="display-6"><%=resumen != null ? resumen.getPendientes() : 0%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-bg-info text-center">
                        <div class="card-body">
                            <h5>En Proceso</h5>
                            <p class="display-6"><%=resumen != null ? resumen.getEnProceso() : 0%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-bg-success text-center">
                        <div class="card-body">
                            <h5>Resueltos</h5>
                            <p class="display-6"><%=resumen != null ? resumen.getResueltos() : 0%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-bg-secondary text-center">
                        <div class="card-body">
                            <h5>Cerrados</h5>
                            <p class="display-6"><%=resumen != null ? resumen.getCerrados() : 0%></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card mt-4">
                <div class="card-header bg-light">
                    Últimos reclamos registrados
                </div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-striped mb-0">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Asunto</th>
                                    <th>Usuario</th>
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
                                    <td><%=reclamo.getNombreUsuario()%></td>
                                    <td><span class="badge bg-secondary"><%=reclamo.getEstado()%></span></td>
                                    <td><%=reclamo.getFechaRegistro()%></td>
                                    <td><a class="btn btn-sm btn-outline-primary" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=detalle&idReclamo=<%=reclamo.getIdReclamo()%>">Ver</a></td>
                                </tr>
                                <%       }
                                   } else { %>
                                <tr>
                                    <td colspan="6" class="text-center py-3">No hay reclamos registrados aún.</td>
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