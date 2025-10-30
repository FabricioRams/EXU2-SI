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
    List<ClsReclamoFRA> listaReclamos = (List<ClsReclamoFRA>) request.getAttribute("listaReclamos");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Reporte FRA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">FRA Reportes</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=panelAdmin">Inicio</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=listarAdmin">Reclamos</a></li>
                    </ul>
                    <a class="btn btn-outline-light" href="<%=request.getContextPath()%>/ControladorLoginFRA?accion=CerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </nav>
        <div class="container py-4">
            <div class="row g-3">
                <div class="col-md-3">
                    <div class="card text-bg-primary text-center">
                        <div class="card-body">
                            <h6>Total reclamos</h6>
                            <p class="display-6"><%=resumen != null ? resumen.getTotalReclamos() : 0%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-bg-warning text-center">
                        <div class="card-body">
                            <h6>Pendientes</h6>
                            <p class="display-6"><%=resumen != null ? resumen.getPendientes() : 0%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-bg-info text-center">
                        <div class="card-body">
                            <h6>En Proceso</h6>
                            <p class="display-6"><%=resumen != null ? resumen.getEnProceso() : 0%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-bg-success text-center">
                        <div class="card-body">
                            <h6>Resueltos</h6>
                            <p class="display-6"><%=resumen != null ? resumen.getResueltos() : 0%></p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-bg-secondary text-center">
                        <div class="card-body">
                            <h6>Cerrados</h6>
                            <p class="display-6"><%=resumen != null ? resumen.getCerrados() : 0%></p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="card mt-4">
                <div class="card-header bg-light">Detalle completo de reclamos FRA</div>
                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-hover mb-0">
                            <thead>
                                <tr>
                                    <th>Código</th>
                                    <th>Asunto</th>
                                    <th>Usuario</th>
                                    <th>Categoría</th>
                                    <th>Estado</th>
                                    <th>Registro</th>
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
                                    <td><%=reclamo.getEstado()%></td>
                                    <td><%=reclamo.getFechaRegistro()%></td>
                                </tr>
                                <%       }
                                   } else { %>
                                <tr>
                                    <td colspan="6" class="text-center py-3">Sin información disponible.</td>
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