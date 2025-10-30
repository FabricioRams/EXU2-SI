<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.ClsUsuarioFRA"%>
<%@page import="Modelo.ClsCategoriaFRA"%>
<%@page import="java.util.List"%>
<%
    ClsUsuarioFRA usuarioSesion = (ClsUsuarioFRA) session.getAttribute("usuarioSesion");
    if (usuarioSesion == null || usuarioSesion.getIdRol() == 1) {
        response.sendRedirect(request.getContextPath() + "/VistaFRA/LoginFRA.jsp");
        return;
    }
    List<ClsCategoriaFRA> listaCategorias = (List<ClsCategoriaFRA>) request.getAttribute("listaCategorias");
    String mensaje = request.getParameter("mensaje");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Registrar Reclamo FRA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">FRA Usuario</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=panelUsuario">Inicio</a></li>
                        <li class="nav-item"><a class="nav-link active" href="#">Registrar reclamo</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=listarUsuario">Mis reclamos</a></li>
                    </ul>
                    <a class="btn btn-outline-light" href="<%=request.getContextPath()%>/ControladorLoginFRA?accion=CerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </nav>
        <div class="container py-4">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <div class="card">
                        <div class="card-header bg-light">Nuevo reclamo FRA</div>
                        <div class="card-body">
                            <% if (mensaje != null) { %>
                            <div class="alert alert-warning"><%=mensaje%></div>
                            <% } %>
                            <form action="<%=request.getContextPath()%>/ControladorReclamoFRA" method="post">
                                <input type="hidden" name="accion" value="registrar" />
                                <div class="mb-3">
                                    <label class="form-label">Asunto</label>
                                    <input type="text" class="form-control" name="asunto" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Descripción</label>
                                    <textarea class="form-control" rows="5" name="descripcion" required></textarea>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Categoría</label>
                                    <select class="form-select" name="idCategoria" required>
                                        <option value="">Seleccione...</option>
                                        <% if (listaCategorias != null) {
                                               for (ClsCategoriaFRA categoria : listaCategorias) { %>
                                        <option value="<%=categoria.getIdCategoria()%>"><%=categoria.getNombreCategoria()%></option>
                                        <%       }
                                           } %>
                                    </select>
                                </div>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary">Registrar reclamo</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>