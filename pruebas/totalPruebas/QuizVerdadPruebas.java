package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Estado;
import actividades.QuizVerdad;
import actividades.VerdaderoFalso;

class QuizVerdadPruebas {
	
	QuizVerdad quiz;

	@BeforeEach
	void setUp() throws Exception {
		quiz = new QuizVerdad("Quiz Java", "Demostrar conocimiento en Java a traves de preguntas verdad o falso", "Preguntas Verdadero o Falso", "Fácil", 15, true, 80);
	}

	@AfterEach
	void tearDown() throws Exception {
	quiz = null;
	}

	@Test
	void testAgregarPregunta() {
		quiz.agregarPregunta("Java es un lenguaje orientado a objetos?", VerdaderoFalso.Verdadero);
		assertEquals(1, quiz.getPreguntas().size(), "La pregunta no fue agregada correctamente.");
		assertEquals("Java es un lenguaje orientado a objetos?", quiz.getPreguntas().get(0), "La pregunta 1 no fue agregada correctamente");
	}
	
	@Test
	void testVerPreguntas() {
		
		assertEquals("No hay Preguntas por mostrar", quiz.verPreguntas(),"El mensaje no es el esperado");
		quiz.agregarPregunta("¿Java es un lenguaje orientado a objetos?", VerdaderoFalso.Verdadero);
		quiz.agregarPregunta("¿Para aprender Java debes saber Python?", VerdaderoFalso.Falso);
		StringBuilder sb =  new StringBuilder();
		sb.append("Preguntas:\n\n");
		sb.append(1).append(". ").append("¿Java es un lenguaje orientado a objetos?").append("\n");
		sb.append(2).append(". ").append("¿Para aprender Java debes saber Python?").append("\n");
		
		assertEquals(sb.toString(),quiz.verPreguntas(),"El mensaje no es el esperado");
	}
	
	@Test
    void testCalificarRespuestasCorrectas() throws Exception {
        
        quiz.agregarPregunta("Java se usó para crear Minecraft.", VerdaderoFalso.Verdadero);
        quiz.agregarPregunta("Los comentarios en Java se escriben con '//'.", VerdaderoFalso.Verdadero);

       
        ArrayList<VerdaderoFalso> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(VerdaderoFalso.Verdadero);
        respuestasEstudiante.add(VerdaderoFalso.Verdadero); 

        Estado estado = quiz.calificar("estudiante1", respuestasEstudiante);
        assertEquals(Estado.EXITOSA, estado, "El estado debería ser EXITOSA.");
    }

	
	@Test
	
	void testCalificarRespuestasIncorrectas() throws Exception {
        
        quiz.agregarPregunta("Java es lo mismo que JavaScript.", VerdaderoFalso.Falso);
        quiz.agregarPregunta("El método main() es opcional en un programa Java.", VerdaderoFalso.Falso);

        ArrayList<VerdaderoFalso> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(VerdaderoFalso.Verdadero); 
        respuestasEstudiante.add(VerdaderoFalso.Verdadero); 

        Estado estado = quiz.calificar("estudiante2", respuestasEstudiante);
        assertEquals(Estado.NO_EXITOSA, estado, "El estado debería ser NO_EXITOSA.");
	}
	@Test
    void testCalificarConCantidadIncorrectaDeRespuestas() {
        quiz.agregarPregunta("Java se ejecuta en la Máquina Virtual de Java (JVM).", VerdaderoFalso.Verdadero);
        quiz.agregarPregunta("El método main() es opcional en un programa Java.", VerdaderoFalso.Falso);

        ArrayList<VerdaderoFalso> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(VerdaderoFalso.Falso); 

        Exception exception = assertThrows(Exception.class, () -> {
            quiz.calificar("estudiante3", respuestasEstudiante);
        });

        assertEquals("La cantidad de respuestas no coincide con el número de preguntas.", exception.getMessage(), "El mensaje de excepción no es el esperado.");
    }
	
	@Test
    void testGetCalificacionMinima() {
        assertEquals(80.0f, quiz.getCalificacionMinima(), "La calificación mínima no es la esperada.");
    }

    @Test
    void testGetPreguntas() {
        quiz.agregarPregunta("Java fue desarrollado por Notch.", VerdaderoFalso.Falso);
        ArrayList<String> preguntas = quiz.getPreguntas();
        assertEquals(1, preguntas.size(), "El número de preguntas no es el esperado.");
        assertEquals("Java fue desarrollado por Notch.", preguntas.get(0));
    }
    
    @Test
    void testSetYGetRespuestasEstudiantes() {
    	ArrayList<VerdaderoFalso> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(VerdaderoFalso.Falso); 
        
        HashMap<String, ArrayList<VerdaderoFalso>> mapa = new HashMap<String, ArrayList<VerdaderoFalso>>();
        mapa.put("Estudiante3", respuestasEstudiante);
        quiz.setRespuestasEstudiantes(mapa);
        assertTrue(quiz.getRespuestasEstudiantes().containsKey("Estudiante3"),"EL getter y setter de respuestas estudiantes funciona mal");
    }
    
    @Test
    
    void testSetPreguntas() {
    	ArrayList <String> pregun = new ArrayList<String>();
    	pregun.add("¿En Java para imprimir en consola se usa printf()?");
    	quiz.setPreguntas(pregun);
    	assertEquals(1,quiz.getPreguntas().size(),"La pregunta no fue correctamente agregada");
    	
    }
    @Test
    void testGetRespuestas() {
    	ArrayList<VerdaderoFalso> lista = new ArrayList<VerdaderoFalso>();
    	lista.add(VerdaderoFalso.Falso);
    	lista.add(VerdaderoFalso.Verdadero);
    	quiz.setRespuestasCorrectas(lista);
    	assertEquals(2, quiz.getRespuestasCorrectas().size(),"No se añadio correctamente la respuesta");
    	assertEquals(VerdaderoFalso.Falso,quiz.getRespuestasCorrectas().get(0),"La respuesta no era la esperada.");
    }
    
    @Test
    void testGetCalificacionMin() {
    	assertEquals(80, quiz.getCalificacionMinima(),"La calificacion minima no es la esperada.");
    }
    @Test
    void testClone() {
    	assertNotEquals(quiz.getId(),quiz.clone().getId(),"El id es el mismo en ambas");
    }
}
