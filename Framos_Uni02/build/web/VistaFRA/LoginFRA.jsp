<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Ingreso - Sistema FRA</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body class="bg-light">
        <div class="container py-5">
            <div class="row justify-content-center">
                <div class="col-md-5">
                    <div class="card shadow-sm">
                        <div class="card-header bg-primary text-white text-center">
                            <h4 class="mb-0">Sistema de Reclamos FRA</h4>
                        </div>
                        <div class="card-body">
                            <form action="<%=request.getContextPath()%>/ControladorLoginFRA" method="post">
                                <input type="hidden" name="accion" value="Ingresar" />
                                <div class="mb-3">
                                    <label for="correo" class="form-label">Correo electrónico</label>
                                    <input type="email" class="form-control" id="correo" name="correo" required>
                                </div>
                                <div class="mb-3">
                                    <label for="contrasena" class="form-label">Contraseña</label>
                                    <input type="password" class="form-control" id="contrasena" name="contrasena" required>
                                </div>
                                <% String mensaje = (String) request.getAttribute("mensaje");
                                   if (mensaje != null) { %>
                                <div class="alert alert-warning" role="alert">
                                    <%= mensaje %>
                                </div>
                                <% } %>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-primary">Ingresar</button>
                                </div>
                            </form>
                        </div>
                        <div class="card-footer text-muted text-center">
                            &copy; <%= java.time.Year.now() %> FRA - Gestión de Reclamos
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>