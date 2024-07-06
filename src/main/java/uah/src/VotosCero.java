package uah.src;

import uah.src.ModeloDatos;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VotosCero extends HttpServlet {

    private ModeloDatos bd;
    @Override
    public void init(ServletConfig cfg) throws ServletException {
        bd = new ModeloDatos();
        bd.abrirConexion();
    }

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession s = req.getSession(true);

            bd.actualizarVotosCero();

        // Llamada a la página jsp que nos da las gracias
        res.sendRedirect("/Baloncesto");
    }

    public void destroy() {
        bd.cerrarConexion();
        super.destroy();
    }
}
