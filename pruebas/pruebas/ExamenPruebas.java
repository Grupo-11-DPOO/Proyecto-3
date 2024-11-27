package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Estado;
import actividades.Examen;

class ExamenPruebas {
    Examen examen;

    @BeforeEach
    void setUp() {
        examen = new Examen(
                "Examen de Programación",
                "Evaluar conocimientos en programación",
                "Examen sobre fundamentos de programación en Java",
                "Avanzado",
                60,
                true
        );
    }

    @AfterEach
    void tearDown() {
        examen = null;
    }
    
    @Test
    void agregarPreguntaTest() {
        examen.agregarPregunta("¿Qué es una clase en Java?");
        assertEquals(1, examen.getPreguntas().size(), "La pregunta no fue agregada correctamente.");
        assertTrue(examen.getPreguntas().contains("¿Qué es una clase en Java?"));
    }
    
    @Test
    void setPreguntasTest() {
        List<String> nuevasPreguntas = new ArrayList<>();
        nuevasPreguntas.add("¿Qué es el polimorfismo?");
        examen.setPreguntas(nuevasPreguntas);
        assertEquals(1, examen.getPreguntas().size());
        assertEquals("¿Qué es el polimorfismo?", examen.getPreguntas().get(0));
    }
    
    @Test
    void agregarPreguntaTestPreguntasNull() {
    	examen.setPreguntas(null);
    	assertNull(examen.getPreguntas(),"La lista de preguntas no es null");
    	examen.agregarPregunta("¿Que es un Enum en Java?");
    	assertEquals(1, examen.getPreguntas().size(), "La pregunta no fue agregada correctamente.");
        assertTrue(examen.getPreguntas().contains("¿Que es un Enum en Java?"));
    }

    @Test
    void eliminarPreguntaTest() {
        examen.agregarPregunta("Explica el concepto de herencia en Java.");
        examen.eliminarPregunta("Explica el concepto de herencia en Java.");
        assertFalse(examen.getPreguntas().contains("Explica el concepto de herencia en Java."));
        assertEquals(0, examen.getPreguntas().size(), "La pregunta no fue eliminada correctamente.");
    }

    @Test
    void verPreguntasTestConPreguntas() {
        examen.agregarPregunta("¿Qué es un método estático?");
        examen.agregarPregunta("¿Cuál es la diferencia entre una interfaz y una clase abstracta?");
        StringBuilder pregunta = new StringBuilder();
        pregunta.append("1. ¿Qué es un método estático?\n");
        pregunta.append("2. ¿Cuál es la diferencia entre una interfaz y una clase abstracta?\n");
        
        assertEquals(pregunta.toString(), examen.verPreguntas(), "El examen no muestra como se espera las preguntas cuando se llama la funcion  ver preguntas");
    }
    @Test 
    void verPreguntasTestSinPreguntas(){
    	assertEquals("No hay preguntas que mostrar", examen.verPreguntas(),"No se muestra el mensaje correcto cuando no hay preguntas.");
    }

    @Test
    void contestarExamenTest() {
        ArrayList<String> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add("Una clase es una plantilla para crear objetos.");
        respuestasEstudiante.add("La herencia permite reutilizar código.");

        Estado estado = examen.contestarExamen("12345", respuestasEstudiante);
        HashMap<String, ArrayList<String>> respuestas = examen.getRespuestas();

        assertEquals(Estado.PENDIENTE, estado, "El estado debería ser PENDIENTE.");
        assertTrue(respuestas.containsKey("12345"), "No se guardaron las respuestas correctamente.");
        assertEquals(2, respuestas.get("12345").size(), "La cantidad de respuestas no es la esperada.");
    }


    @Test
    void setRespuestasTest() {
        HashMap<String, ArrayList<String>> nuevasRespuestas = new HashMap<>();
        ArrayList<String> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add("Encapsulación es el principio de ocultar los detalles internos.");
        nuevasRespuestas.put("98765", respuestasEstudiante);

        examen.setRespuestas(nuevasRespuestas);
        assertEquals(1, examen.getRespuestas().size());
        assertTrue(examen.getRespuestas().containsKey("98765"));
    }
    
    @Test
    void testClone() {
    	assertNotEquals(examen.getId(),examen.clone().getId(),"El id es el mismo en ambas");
    }
}