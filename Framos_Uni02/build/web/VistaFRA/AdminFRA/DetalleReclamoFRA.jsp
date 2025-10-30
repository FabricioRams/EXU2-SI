<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.ClsUsuarioFRA"%>
<%@page import="Modelo.ClsReclamoFRA"%>
<%@page import="Modelo.ClsCategoriaFRA"%>
<%@page import="Modelo.ClsSeguimientoFRA"%>
<%@page import="java.util.List"%>
<%
    ClsUsuarioFRA usuarioSesion = (ClsUsuarioFRA) session.getAttribute("usuarioSesion");
    if (usuarioSesion == null) {
        response.sendRedirect(request.getContextPath() + "/VistaFRA/LoginFRA.jsp");
        return;
    }
    ClsReclamoFRA reclamo = (ClsReclamoFRA) request.getAttribute("reclamo");
    List<ClsSeguimientoFRA> listaSeguimiento = (List<ClsSeguimientoFRA>) request.getAttribute("listaSeguimiento");
    List<ClsCategoriaFRA> listaCategorias = (List<ClsCategoriaFRA>) request.getAttribute("listaCategorias");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Detalle de Reclamo FRA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">FRA <%=usuarioSesion.getIdRol() == 1 ? "Admin" : "Usuario"%></a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=<%=usuarioSesion.getIdRol() == 1 ? "panelAdmin" : "panelUsuario"%>">Inicio</a></li>
                        <% if (usuarioSesion.getIdRol() == 1) { %>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=listarAdmin">Reclamos</a></li>
                        <% } else { %>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=listarUsuario">Mis Reclamos</a></li>
                        <% } %>
                    </ul>
                    <a class="btn btn-outline-light" href="<%=request.getContextPath()%>/ControladorLoginFRA?accion=CerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </nav>
        <div class="container py-4">
            <div class="row g-3">
                <div class="col-lg-7">
                    <div class="card">
                        <div class="card-header bg-light">Información del reclamo FRA</div>
                        <div class="card-body">
                            <dl class="row">
                                <dt class="col-sm-4">Código</dt>
                                <dd class="col-sm-8"><%=reclamo.getCodigoReclamo()%></dd>
                                <dt class="col-sm-4">Asunto</dt>
                                <dd class="col-sm-8"><%=reclamo.getAsunto()%></dd>
                                <dt class="col-sm-4">Descripción</dt>
                                <dd class="col-sm-8"><%=reclamo.getDescripcion()%></dd>
                                <dt class="col-sm-4">Categoría</dt>
                                <dd class="col-sm-8"><%=reclamo.getNombreCategoria()%></dd>
                                <dt class="col-sm-4">Estado</dt>
                                <dd class="col-sm-8"><span class="badge bg-secondary"><%=reclamo.getEstado()%></span></dd>
                                <dt class="col-sm-4">Fecha registro</dt>
                                <dd class="col-sm-8"><%=reclamo.getFechaRegistro()%></dd>
                            </dl>
                        </div>
                    </div>
                    <% if (usuarioSesion.getIdRol() == 1) { %>
                    <div class="card mt-3">
                        <div class="card-header bg-light">Actualizar información FRA</div>
                        <div class="card-body">
                            <form action="<%=request.getContextPath()%>/ControladorReclamoFRA" method="post">
                                <input type="hidden" name="accion" value="actualizar" />
                                <input type="hidden" name="idReclamo" value="<%=reclamo.getIdReclamo()%>" />
                                <div class="mb-3">
                                    <label class="form-label">Asunto</label>
                                    <input type="text" class="form-control" name="asunto" value="<%=reclamo.getAsunto()%>" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Descripción</label>
                                    <textarea class="form-control" rows="4" name="descripcion" required><%=reclamo.getDescripcion()%></textarea>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Categoría</label>
                                    <select class="form-select" name="idCategoria" required>
                                        <% if (listaCategorias != null) {
                                               for (ClsCategoriaFRA categoria : listaCategorias) { %>
                                        <option value="<%=categoria.getIdCategoria()%>" <%= categoria.getIdCategoria() == reclamo.getIdCategoria() ? "selected" : "" %>><%=categoria.getNombreCategoria()%></option>
                                        <%       }
                                           } %>
                                    </select>
                                </div>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary">Guardar cambios</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <% } %>
                </div>
                <div class="col-lg-5">
                    <div class="card">
                        <div class="card-header bg-light">Seguimiento FRA</div>
                        <div class="card-body">
                            <% if (listaSeguimiento != null && !listaSeguimiento.isEmpty()) {
                                   for (ClsSeguimientoFRA seguimiento : listaSeguimiento) { %>
                            <div class="border rounded p-3 mb-3">
                                <div class="d-flex justify-content-between">
                                    <strong><%=seguimiento.getEstado()%></strong>
                                    <small><%=seguimiento.getFechaMovimiento()%></small>
                                </div>
                                <p class="mb-1"><%=seguimiento.getComentario()%></p>
                                <small class="text-muted">Registrado por <%=seguimiento.getUsuarioRegistro()%></small>
                            </div>
                            <%       }
                               } else { %>
                            <p class="text-muted">Aún no se registran acciones sobre este reclamo.</p>
                            <% } %>
                        </div>
                    </div>
                    <% if (usuarioSesion.getIdRol() == 1) { %>
                    <div class="card mt-3">
                        <div class="card-header bg-light">Actualizar estado FRA</div>
                        <div class="card-body">
                            <form action="<%=request.getContextPath()%>/ControladorReclamoFRA" method="post">
                                <input type="hidden" name="accion" value="seguimiento" />
                                <input type="hidden" name="idReclamo" value="<%=reclamo.getIdReclamo()%>" />
                                <div class="mb-3">
                                    <label class="form-label">Nuevo estado</label>
                                    <select class="form-select" name="estado" required>
                                        <option value="Pendiente" <%= "Pendiente".equalsIgnoreCase(reclamo.getEstado()) ? "selected" : "" %>>Pendiente</option>
                                        <option value="En Proceso" <%= "En Proceso".equalsIgnoreCase(reclamo.getEstado()) ? "selected" : "" %>>En Proceso</option>
                                        <option value="Resuelto" <%= "Resuelto".equalsIgnoreCase(reclamo.getEstado()) ? "selected" : "" %>>Resuelto</option>
                                        <option value="Cerrado" <%= "Cerrado".equalsIgnoreCase(reclamo.getEstado()) ? "selected" : "" %>>Cerrado</option>
                                    </select>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Comentario</label>
                                    <textarea class="form-control" rows="3" name="comentario" required></textarea>
                                </div>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-success">Registrar seguimiento</button>
                                </div>
                            </form>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>
    </body>
</html>