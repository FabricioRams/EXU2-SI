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
%>
<!DOCTYPE html>
<html lang="es">
    <head>
         <meta charset="UTF-8">
        <title>Historial de Reclamos FRA</title>
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
                        <li class="nav-item"><a class="nav-link active" href="#">Historial</a></li>
                    </ul>
                    <a class="btn btn-outline-light" href="<%=request.getContextPath()%>/ControladorLoginFRA?accion=CerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </nav>
        <div class="container py-4">
            <h3 class="mb-4">Historial de reclamos FRA</h3>
            <% if (listaReclamos != null && !listaReclamos.isEmpty()) {
                   for (ClsReclamoFRA reclamo : listaReclamos) { %>
            <div class="card mb-3">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <div>
                        <strong><%=reclamo.getCodigoReclamo()%></strong> - <%=reclamo.getAsunto()%>
                    </div>
                    <span class="badge bg-secondary"><%=reclamo.getEstado()%></span>
                </div>
                <div class="card-body">
                    <p class="mb-2"><strong>Categoría:</strong> <%=reclamo.getNombreCategoria()%></p>
                    <p class="mb-2"><strong>Registrado:</strong> <%=reclamo.getFechaRegistro()%></p>
                    <p class="mb-3"><strong>Descripción:</strong><br><%=reclamo.getDescripcion()%></p>
                    <a class="btn btn-sm btn-outline-primary" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=detalle&idReclamo=<%=reclamo.getIdReclamo()%>">Ver detalle</a>
                </div>
            </div>
            <%       }
               } else { %>
            <div class="alert alert-info">No existen registros de reclamos para mostrar.</div>
            <% } %>
        </div>
    </body>
</html>