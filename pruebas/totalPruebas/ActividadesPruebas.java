package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Actividad;
import actividades.Encuesta;
import actividades.Estado;
import actividades.Examen;
import actividades.Quiz;
import actividades.QuizVerdad;
import actividades.Recurso;
import actividades.Tarea;
import learningPaths.LearningPath;
import usuarios.Estudiante;

class ActividadesPruebas {
	private Tarea tarea;
    private Quiz quiz;
    private Recurso recurso;
    private Encuesta encuesta;
    private QuizVerdad quizV;
    private Examen examen;
    private Estudiante estudiante;
    private HashMap<String, Actividad> actividades;
    private HashMap<String, LearningPath> learningPaths;
    private List<String> intereses;
	@BeforeEach
	void setUp() throws Exception {
		actividades = new HashMap<String, Actividad>();
        learningPaths = new HashMap<>();
        intereses = new ArrayList<>();
        intereses.add("Java");
        intereses.add("Historia");
        intereses.add("programar");

        estudiante = new Estudiante(actividades, learningPaths, "estudiante1", "password123", intereses);
        tarea = new Tarea("Tarea 1", "Resolver ejercicios", "Problemas matemáticos", "Básico", 60, true, "PDF");
    	quiz = new Quiz("Quiz Historia Colombia", "Demostrar conocimiento sobre Colombia", "Opción múltiple", "Principiante", 30, false, 60);
    	encuesta = new Encuesta("Encuesta 1", "Evaluar satisfacción", "Preguntas abiertas", "Básico", 15, true);
    	recurso = new Recurso("Lectura sobre Napoleón", "Aprender sobre el inicio de la conquista", "En esta lectura aprenderás sobre Napoleón y el inicio de su conquista.", "Fácil", 40, true, "RecursoNapoleon.PDF");
    	quizV = new QuizVerdad("Quiz Java", "Demostrar conocimiento en Java a través de preguntas verdad o falso", "Preguntas Verdadero o Falso", "Básico", 15, true, 80);
    	examen = new Examen("Examen Final", "Evaluar el aprendizaje del curso", "Examen escrito", "Avanzado", 60, true);
	}

	@AfterEach
	void tearDown() throws Exception {
		tarea = null;
		quiz = null;
		recurso = null;
		encuesta = null;
		quizV = null;
		examen = null;
	}

	@Test
	void testCrearActividad() {
		assertNotNull(tarea, "La tarea no se creó correctamente.");
		assertNotNull(quiz, "El quiz no se creó correctamente.");
		assertNotNull(encuesta, "La encuesta no se creó correctamente.");
		assertNotNull(recurso, "El recurso no se creó correctamente.");
		assertNotNull(quizV, "El quiz verdadero/falso no se creó correctamente.");
		assertNotNull(examen, "El examen no se creó correctamente.");
	}

	@Test
	void testSetAndGetTitulo() {
		tarea.setTitulo("Nueva Tarea");
		assertEquals("Nueva Tarea", tarea.getTitulo(), "El título de la tarea no se actualizó correctamente.");
	}

	@Test
	void testSetAndGetDescripcion() {
		quiz.setDescripcion("Nuevo contenido sobre historia");
		assertEquals("Nuevo contenido sobre historia", quiz.getDescripcion(), "La descripción del quiz no se actualizó correctamente.");
	}

	@Test
	void testSetAndGetNivel() {
		examen.setNivel("Intermedio");
		assertEquals("Intermedio", examen.getNivel(), "El nivel del examen no se actualizó correctamente.");
	}

	@Test
	void testSetAndGetDuracion() {
		recurso.setDuracionMinutos(50);
		assertEquals(50, recurso.getDuracionMinutos(), "La duración del recurso no se actualizó correctamente.");
	}

	@Test
	void testSetAndGetObligatorio() {
		tarea.setObligatorio(false);
		assertFalse(tarea.isObligatorio(), "El campo obligatorio de la tarea no se actualizó correctamente.");
	}

	@Test
	void testAgregarRating() throws Exception {
		tarea.agregarRating(5);
		tarea.agregarRating(4);
		assertEquals(4.5, tarea.getRating(), 0.01, "El cálculo del rating no es correcto.");
	}

	@Test
	void testAgregarResena() {
		String resena = "Esta actividad fue muy útil.";
		encuesta.agregarResena(resena);
		assertTrue(encuesta.getResenas().contains(resena), "La reseña no se agregó correctamente.");
	}

	@Test
	void testPrerequisitos() {
		tarea.agregarPrerrequisito(quiz);
		assertTrue(tarea.getPrerequisitos().contains(quiz), "El prerequisito no se agregó correctamente.");
	}



	@Test
	void testCloneActividad() {
		Quiz quizClonado = (Quiz) quiz.clone();
		assertNotNull(quizClonado, "La actividad no se clonó correctamente.");
		assertEquals(quiz.getTitulo(), quizClonado.getTitulo(), "El título del quiz clonado no coincide.");
		assertNotEquals(quiz.getId(), quizClonado.getId(), "El ID del quiz clonado no debería ser igual al original.");
	}

	@Test
	void testVerActividad() {
		String activi = recurso.verActividad();
		assertTrue(activi.contains(recurso.getTitulo()), "Ver actividad no contiene el titulo");
		assertTrue(activi.contains(recurso.descripcion),"Ver actividad no contiene la decripcion esperada");
	}

	@Test
	void testAdvertenciaPrerequisitos() {
		
		
		HashMap<String, Estado> registro = new HashMap<String, Estado>();
		registro.put(tarea.getId(), Estado.EXITOSA);
		estudiante.setRegistroActividades(registro);
		assertTrue(tarea.advertenciaPrerequisitos(estudiante), "La advertencia de prerequisitos no funcionó correctamente.");
		ArrayList<Actividad> pre = new ArrayList<Actividad>();
		pre.add(encuesta);
		tarea.setPrerequisitos(pre);
		assertFalse(tarea.advertenciaPrerequisitos(estudiante), "La advertencia de prerequisitos no funcionó correctamente.");
		
	}
}
