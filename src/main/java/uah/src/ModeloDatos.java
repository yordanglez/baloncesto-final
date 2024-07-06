package uah.src;

import java.sql.*;
import java.util.logging.Logger;
import java.util.logging.Level;


public class ModeloDatos {

    private Connection con;
    private Statement set;
    private ResultSet rs;
    private static final Logger LOGGER = Logger.getLogger(ModeloDatos.class.getName());
    private static final String ERROR_MESSAGE = "El error es: {}";

    public void abrirConexion() {

        try {
            LOGGER.log(Level.INFO,"abrir conexion");
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Con variables de entorno
            String dbHost = System.getenv().get("DATABASE_HOST");
            String dbPort = System.getenv().get("DATABASE_PORT");
            String dbName = System.getenv().get("DATABASE_NAME");
            String dbUser = System.getenv().get("DATABASE_USER");
            String dbPass = System.getenv().get("DATABASE_PASS");

            String url = dbHost + ":" + dbPort + "/" + dbName;
            con = DriverManager.getConnection(url, dbUser, dbPass);

        } catch (Exception e) {
            // No se ha conectado
            LOGGER.log(Level.INFO,"No se ha podido conectar");
            LOGGER.log(Level.INFO,String.format(ERROR_MESSAGE, e.getMessage()));
        }
    }

    public boolean existeJugador(String nombre) {
        boolean existe = false;
        String cad;
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT * FROM Jugadores");
            while (rs.next()) {
                cad = rs.getString("Nombre");
                cad = cad.trim();
                if (cad.compareTo(nombre.trim()) == 0) {
                    existe = true;
                }
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            // No lee de la tabla
            LOGGER.log(Level.INFO,"No lee de la tabla");
            LOGGER.log(Level.INFO,String.format(ERROR_MESSAGE, e.getMessage()));
        }
        return (existe);
    }
    public int getVotosJugador(String nombre) {
        int votos = 0;
        String cad;
        try {
            set = con.createStatement();
            rs = set.executeQuery("SELECT * FROM Jugadores");
            while (rs.next()) {
                cad = rs.getString("Nombre");
                cad = cad.trim();
                if (cad.compareTo(nombre.trim()) == 0) {
                    votos = rs.getInt("Votos");
                    break;
                }
            }
            rs.close();
            set.close();
        } catch (Exception e) {
            // No lee de la tabla
            LOGGER.log(Level.INFO,"No lee de la tabla");
            LOGGER.log(Level.INFO,String.format(ERROR_MESSAGE, e.getMessage()));
        }
        return (votos);
    }

    public void actualizarJugador(String nombre) {
        String UPDATE_JUGADOR_QUERY = "UPDATE Jugadores SET votos = votos + 1 WHERE nombre LIKE ?";
        try(PreparedStatement set = con.prepareStatement(UPDATE_JUGADOR_QUERY)) {
            set.setString(1, "%" + nombre + "%");
            set.executeUpdate();
            rs.close();
            set.close();
        } catch (Exception e) {
            // No modifica la tabla
            LOGGER.log(Level.INFO,"No modifica la tabla");
            LOGGER.log(Level.INFO,String.format(ERROR_MESSAGE, e.getMessage()));
        }
    }
    public void actualizarVotosCero() {
        try {
            set = con.createStatement();
            set.executeUpdate("UPDATE Jugadores SET votos=0");
            rs.close();
            set.close();
        } catch (Exception e) {
            // No modifica la tabla
            LOGGER.log(Level.INFO,"No modifica la tabla");
            LOGGER.log(Level.INFO,String.format(ERROR_MESSAGE, e.getMessage()));
        }
    }

    public void insertarJugador(String nombre) {
        String INSERT_JUGADOR_QUERY = "INSERT INTO Jugadores (nombre, votos) VALUES (?, 1)";
        try(PreparedStatement set = con.prepareStatement(INSERT_JUGADOR_QUERY)) {
            set.setString(1, nombre);
            set.executeUpdate();
            rs.close();
            set.close();
        } catch (Exception e) {
            // No inserta en la tabla
            LOGGER.log(Level.INFO,"No inserta en la tabla");
            LOGGER.log(Level.INFO,String.format(ERROR_MESSAGE, e.getMessage()));
        }
    }

    public void cerrarConexion() {
        try {
            con.close();
        } catch (Exception e) {
            LOGGER.log(Level.INFO,e.getMessage());
        }
    }
}
