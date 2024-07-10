import org.junit.jupiter.api.Test;
import uah.src.ModeloDatos;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ModeloDatosTest {
    @Test
    public void testExisteJugador() {
        System.out.println("Prueba de existeJugador");
        String nombre = "";
        ModeloDatos instance = new ModeloDatos();
        instance.abrirConexion();
        boolean expResult = false;
        boolean result = instance.existeJugador(nombre);
        instance.cerrarConexion();
        assertEquals(expResult, result);
    }
    @Test
    public void testActualizarJugador() {
        System.out.println("Prueba de actualizarJugador");
        String nombre = "Llull";
        ModeloDatos instance = new ModeloDatos();
        instance.abrirConexion();
        int expResult = 1;
        instance.actualizarVotosCero();
        instance.actualizarJugador(nombre);
        int votos = instance.getVotosJugador(nombre);
        instance.cerrarConexion();
        System.out.println("votos: " + votos);
        assertEquals(expResult, votos);
    }
    public void testListJugadores() {
        System.out.println("Prueba de actualizarJugador");
        String nombre = "Llull";
        ModeloDatos instance = new ModeloDatos();
        instance.abrirConexion();
        int expResult = 1;
        instance.actualizarVotosCero();
        instance.actualizarJugador(nombre);
        List<Map<String, Object>> jugadores = instance.listVotosJugador();
        instance.cerrarConexion();
        System.out.println("Jugadores: " + jugadores);
    }
}