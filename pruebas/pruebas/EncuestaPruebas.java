package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Encuesta;
import actividades.Estado;

class EncuestaPruebas {
    Encuesta encuesta;

    @BeforeEach
    void setUp() {
        encuesta = new Encuesta(
                "Encuesta de Satisfacción",
                "Evaluar la satisfacción del curso",
                "Encuesta para conocer la opinión de los estudiantes",
                "Principiante",
                30,
                true
        );
    }

    @AfterEach
    void tearDown() {
        encuesta = null;
    }

    @Test
    void testAgregarPregunta() {
        encuesta.agregarPregunta("¿Qué te pareció el curso?");
        assertEquals(1, encuesta.getPreguntas().size(), "La pregunta no fue agregada correctamente.");
        assertTrue(encuesta.getPreguntas().contains("¿Qué te pareció el curso?"));
    }

    @Test
    void agregarPreguntaTestPreguntasNull() {
    	encuesta.setPreguntas(null);
    	assertNull(encuesta.getPreguntas(),"La lista de preguntas no es null");
    	encuesta.agregarPregunta("¿Se siente satisfecho con el curso?");
    	assertEquals(1, encuesta.getPreguntas().size(), "La pregunta no fue agregada correctamente.");
        assertTrue(encuesta.getPreguntas().contains("¿Se siente satisfecho con el curso?"));
    }

    @Test
    void testEliminarPregunta() {
        encuesta.agregarPregunta("¿Qué mejorarías?");
        encuesta.eliminarPregunta("¿Qué mejorarías?");
        assertFalse(encuesta.getPreguntas().contains("¿Qué mejorarías?"));
        assertEquals(0, encuesta.getPreguntas().size(), "La pregunta no fue eliminada correctamente.");
    }
    @Test
    
    void testEliminarPreguntaNoExistente() {
    	encuesta.agregarPregunta("¿Como estas?");
    	assertEquals(1, encuesta.getPreguntas().size(),"La pregunta no se agrego correctamente");
    	encuesta.eliminarPregunta("¿Que día es hoy?");
    	assertEquals(1, encuesta.getPreguntas().size(),"La pregunta se elimino");
    	
    }

    @Test
    void testVerPreguntas() {
        encuesta.agregarPregunta("¿Qué te pareció el curso?");
        encuesta.agregarPregunta("¿Recomendarías este curso?");
        List<String> preguntas = encuesta.getPreguntas();
        assertEquals(2, preguntas.size(), "No se agregaron las preguntas correctamente.");
        assertEquals("¿Qué te pareció el curso?", preguntas.get(0));
        assertEquals("¿Recomendarías este curso?", preguntas.get(1));
    }

    @Test
    void testSetPreguntas() {
        ArrayList<String> nuevasPreguntas = new ArrayList<>();
        nuevasPreguntas.add("¿Cómo fue la calidad del material?");
        encuesta.setPreguntas(nuevasPreguntas);
        assertEquals(1, encuesta.getPreguntas().size());
        assertEquals("¿Cómo fue la calidad del material?", encuesta.getPreguntas().get(0));
    }

    @Test
    void testContestarEncuesta() {
        ArrayList<String> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add("Excelente");
        respuestasEstudiante.add("Sí, lo recomendaría");

        Estado estado = encuesta.contestarEncuesta("12345", respuestasEstudiante);
        HashMap<String, ArrayList<String>> respuestas = encuesta.getRespuestas();

        assertEquals(Estado.EXITOSA, estado, "El estado debería ser EXITOSA.");
        assertTrue(respuestas.containsKey("12345"), "No se guardaron las respuestas correctamente.");
        assertEquals(2, respuestas.get("12345").size(), "La cantidad de respuestas no es la esperada.");
    }

    @Test
    void testSetRespuestas() {
        HashMap<String, ArrayList<String>> nuevasRespuestas = new HashMap<>();
        ArrayList<String> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add("Muy satisfecho");
        nuevasRespuestas.put("67890", respuestasEstudiante);

        encuesta.setRespuestas(nuevasRespuestas);
        assertEquals(1, encuesta.getRespuestas().size());
        assertTrue(encuesta.getRespuestas().containsKey("67890"));
    }

    @Test
    void testGetRespuestas() {
        ArrayList<String> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add("Muy útil");
        encuesta.contestarEncuesta("112233", respuestasEstudiante);

        HashMap<String, ArrayList<String>> respuestas = encuesta.getRespuestas();
        assertNotNull(respuestas);
        assertEquals(1, respuestas.size(), "La cantidad de respuestas guardadas no es la esperada.");
    }
    
    @Test
    void verPreguntasTestConPreguntas() {
        encuesta.agregarPregunta("¿Le parece que se uso una cantidad apropiada de actividades?");
        encuesta.agregarPregunta("¿Siente que con este Learning Path realmente aprendio?");
        StringBuilder pregunta = new StringBuilder();
        pregunta.append("1. ¿Le parece que se uso una cantidad apropiada de actividades?\n");
        pregunta.append("2. ¿Siente que con este Learning Path realmente aprendio?\n");
        
        assertEquals(pregunta.toString(), encuesta.verPreguntas(), "La encuesta no muestra como se espera las preguntas cuando se llama la funcion  ver preguntas");
    }
    @Test 
    void verPreguntasTestSinPreguntas(){
    	assertEquals("No hay preguntas que mostrar", encuesta.verPreguntas(),"No se muestra el mensaje correcto cuando no hay preguntas.");
    }

	@Test
	    void testClone() {
	    	assertNotEquals(encuesta.getId(),encuesta.clone().getId(),"El id es el mismo en ambas");
	    }
    
}
