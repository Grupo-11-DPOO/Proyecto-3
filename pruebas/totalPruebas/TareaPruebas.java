package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Estado;
import actividades.Tarea;

class TareaPruebas {
    Tarea tarea;

    @BeforeEach
    void setUp() {
        // Inicializamos una nueva tarea antes de cada prueba
        tarea = new Tarea(
                "Tarea de Programación", 
                "Practicar conceptos básicos de programación", 
                "Implementar un programa en Java", 
                "Intermedio", 
                120, 
                true, 
                "123454321"
        );
    }

    @AfterEach
    void tearDown() {
        tarea = null;
    }

    @Test
    void testGetIdActividadesARealizar() {
    	assertEquals("123454321", tarea.getIdActividadesARealizar(), "El id de la actividad a realizar no es correcto.");
    }
    
    @Test
    void testSetRespuestasYGetRespuestas(){
    	HashMap<String,String> respuestasPrueba = new HashMap<String, String>();
    	respuestasPrueba.put("123", "PDF");
    	tarea.setRespuestas(respuestasPrueba);
    	HashMap<String,String> respuestas = tarea.getRespuestas();
    	assertTrue(respuestas.containsKey("123"));

    	
    }
    
    @Test
    void testRealizarTarea() {
    	assertEquals(Estado.ENVIADA, tarea.realizarTarea("Javier", "Video"),"Realizar tarea no devolvio estado enviada");
    	HashMap<String,String> respuestas = tarea.getRespuestas();
    	assertTrue(respuestas.containsKey("Javier"));
    }
    
    @Test
    void testClone() {
    	assertNotEquals(tarea.getId(),tarea.clone().getId(),"El id es el mismo en ambas");
    }
}
