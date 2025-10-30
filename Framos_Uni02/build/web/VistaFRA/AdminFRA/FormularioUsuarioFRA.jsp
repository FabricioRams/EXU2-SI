<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Modelo.ClsUsuarioFRA"%>
<%
    ClsUsuarioFRA usuarioSesion = (ClsUsuarioFRA) session.getAttribute("usuarioSesion");
    if (usuarioSesion == null || usuarioSesion.getIdRol() != 1) {
        response.sendRedirect(request.getContextPath() + "/VistaFRA/LoginFRA.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Gestión de Usuarios FRA</title>
        <meta http-equiv="refresh" content="0;URL=<%=request.getContextPath()%>/ControladorUsuarioFRA?accion=listar">
    </head>
    <body>
        Redirigiendo a la gestión de usuarios FRA...
    </body>
</html>