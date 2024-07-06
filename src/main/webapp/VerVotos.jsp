<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head><title>Votaci&oacute;n mejor jugador liga ACB</title></head>
    <body class="resultado">
        <font size=10>
            <h2>Votos de Jugadores</h2>.
            <table border="1">
                <tr>
                    <th>Nombre</th>
                    <th>Votos</th>
                </tr>
                <c:forEach items="${jugadores}" var="jugador">
                    <tr>
                        <td>${jugador['nombre']}</td>
                        <td>${jugador['votos']}</td>
                    </tr>
                </c:forEach>
            </table>
        <hr>
        </font>
        <br>
        <br> <a href="index.html"> Ir al comienzo</a>
    </body>
</html>
