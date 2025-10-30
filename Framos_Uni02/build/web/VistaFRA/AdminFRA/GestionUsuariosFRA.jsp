<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.ClsUsuarioFRA"%>
<%@page import="Modelo.ClsRolFRA"%>
<%@page import="java.util.List"%>
<%
    ClsUsuarioFRA usuarioSesion = (ClsUsuarioFRA) session.getAttribute("usuarioSesion");
    if (usuarioSesion == null || usuarioSesion.getIdRol() != 1) {
        response.sendRedirect(request.getContextPath() + "/VistaFRA/LoginFRA.jsp");
        return;
    }
    List<ClsUsuarioFRA> listaUsuarios = (List<ClsUsuarioFRA>) request.getAttribute("listaUsuarios");
    List<ClsRolFRA> listaRoles = (List<ClsRolFRA>) request.getAttribute("listaRoles");
    ClsUsuarioFRA usuarioEditar = (ClsUsuarioFRA) request.getAttribute("usuarioEditar");
    String mensaje = request.getParameter("mensaje");
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Usuarios FRA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
            <div class="container-fluid">
                <a class="navbar-brand" href="#">FRA Admin</a>
                <div class="collapse navbar-collapse">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=panelAdmin">Inicio</a></li>
                        <li class="nav-item"><a class="nav-link active" href="#">Usuarios</a></li>
                        <li class="nav-item"><a class="nav-link" href="<%=request.getContextPath()%>/ControladorReclamoFRA?accion=listarAdmin">Reclamos</a></li>
                    </ul>
                    <a class="btn btn-outline-light" href="<%=request.getContextPath()%>/ControladorLoginFRA?accion=CerrarSesion">Cerrar sesión</a>
                </div>
            </div>
        </nav>
        <div class="container py-4">
            <% if (mensaje != null) { %>
            <div class="alert alert-info"><%=mensaje%></div>
            <% } %>
            <div class="row g-3">
                <div class="col-lg-5">
                    <div class="card">
                        <div class="card-header bg-light">
                            <%= usuarioEditar != null ? "Editar usuario FRA" : "Nuevo usuario FRA" %>
                        </div>
                        <div class="card-body">
                            <form action="<%=request.getContextPath()%>/ControladorUsuarioFRA" method="post">
                                <input type="hidden" name="accion" value="<%= usuarioEditar != null ? "actualizar" : "guardar" %>" />
                                <% if (usuarioEditar != null) { %>
                                <input type="hidden" name="idUsuario" value="<%=usuarioEditar.getIdUsuario()%>" />
                                <% } %>
                                <div class="mb-3">
                                    <label class="form-label">Documento</label>
                                    <input type="text" class="form-control" name="documento" required value="<%= usuarioEditar != null ? usuarioEditar.getDocumento() : "" %>">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Nombres</label>
                                    <input type="text" class="form-control" name="nombres" required value="<%= usuarioEditar != null ? usuarioEditar.getNombres() : "" %>">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Apellidos</label>
                                    <input type="text" class="form-control" name="apellidos" required value="<%= usuarioEditar != null ? usuarioEditar.getApellidos() : "" %>">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Correo</label>
                                    <input type="email" class="form-control" name="correo" required value="<%= usuarioEditar != null ? usuarioEditar.getCorreo() : "" %>">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Teléfono</label>
                                    <input type="text" class="form-control" name="telefono" value="<%= usuarioEditar != null ? usuarioEditar.getTelefono() : "" %>">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Contraseña</label>
                                    <input type="text" class="form-control" name="contrasena" required value="<%= usuarioEditar != null ? usuarioEditar.getContrasena() : "" %>">
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Rol</label>
                                    <select class="form-select" name="idRol" required>
                                        <option value="">Seleccione...</option>
                                        <% if (listaRoles != null) {
                                               for (ClsRolFRA rol : listaRoles) {
                                                   boolean seleccionado = usuarioEditar != null && rol.getIdRol() == usuarioEditar.getIdRol(); %>
                                        <option value="<%=rol.getIdRol()%>" <%= seleccionado ? "selected" : "" %>><%=rol.getNombreRol()%></option>
                                        <%       }
                                           } %>
                                    </select>
                                </div>
                                <div class="d-grid gap-2">
                                    <button type="submit" class="btn btn-primary">Guardar</button>
                                    <% if (usuarioEditar != null) { %>
                                    <a class="btn btn-outline-secondary" href="<%=request.getContextPath()%>/ControladorUsuarioFRA?accion=listar">Cancelar</a>
                                    <% } %>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-lg-7">
                    <div class="card">
                        <div class="card-header bg-light">Usuarios registrados</div>
                        <div class="card-body p-0">
                            <div class="table-responsive">
                                <table class="table table-hover mb-0">
                                    <thead>
                                        <tr>
                                            <th>Documento</th>
                                            <th>Nombres</th>
                                            <th>Correo</th>
                                            <th>Rol</th>
                                            <th>Estado</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                                               for (ClsUsuarioFRA usuario : listaUsuarios) { %>
                                        <tr>
                                            <td><%=usuario.getDocumento()%></td>
                                            <td><%=usuario.getNombres()%> <%=usuario.getApellidos()%></td>
                                            <td><%=usuario.getCorreo()%></td>
                                            <td><%=usuario.getNombreRol()%></td>
                                            <td>
                                                <span class="badge <%=usuario.isEstado() ? "bg-success" : "bg-secondary"%>">
                                                    <%=usuario.isEstado() ? "Activo" : "Inactivo"%>
                                                </span>
                                            </td>
                                            <td class="text-end">
                                                <a class="btn btn-sm btn-outline-primary" href="<%=request.getContextPath()%>/ControladorUsuarioFRA?accion=editar&idUsuario=<%=usuario.getIdUsuario()%>">Editar</a>
                                                <a class="btn btn-sm btn-outline-<%=usuario.isEstado() ? "danger" : "success"%>" href="<%=request.getContextPath()%>/ControladorUsuarioFRA?accion=cambiarEstado&idUsuario=<%=usuario.getIdUsuario()%>&estado=<%=!usuario.isEstado()%>">
                                                    <%=usuario.isEstado() ? "Desactivar" : "Activar"%>
                                                </a>
                                            </td>
                                        </tr>
                                        <%       }
                                           } else { %>
                                        <tr>
                                            <td colspan="6" class="text-center py-3">No hay usuarios registrados.</td>
                                        </tr>
                                        <% } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>