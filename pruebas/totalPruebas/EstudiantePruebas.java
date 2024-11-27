package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.*;
import learningPaths.LearningPath;
import usuarios.Estudiante;

class EstudiantePruebas {
    private Estudiante estudiante;
    private HashMap<String, Actividad> actividades;
    private HashMap<String, LearningPath> learningPaths;
    private List<String> intereses;
    private Tarea tarea;
    private Quiz quiz;
    private Recurso recurso;
    private LearningPath lp1;
    private LearningPath lp2;
    private LearningPath lp3;
    private Encuesta encuesta;
    private QuizVerdad quizV;
    private Examen examen;

    @BeforeEach
    void setUp() {
        
        actividades = new HashMap<String, Actividad>();
        learningPaths = new HashMap<>();
        intereses = new ArrayList<>();
        intereses.add("Java");
        intereses.add("Historia");
        intereses.add("programar");

        estudiante = new Estudiante(actividades, learningPaths, "estudiante1", "password123", intereses);
        tarea = new Tarea("Tarea 1", "Resolver ejercicios", "Problemas matemáticos", "Básico", 60, true, "PDF");
    	quiz = new Quiz("Quiz Historia Colombia", "Demostrar conocimiento sobre Colombia", "Opcion multiple", "Principiante", 30, false, 60);
    	encuesta = new Encuesta("Encuesta 1", "Evaluar satisfacción", "Preguntas abiertas", "Básico", 15, true);
    	recurso = new Recurso("Lectura sobre Napoleón", "Aprender sobre el inicio de la conquista", "En esta lectura aprenderas sobre Napoleon y el inicio de su conquista.", "Fácil", 40, true, "RecursoNapoleon.PDF" );
    	quizV = new QuizVerdad("Quiz Java", "Demostrar conocimiento en Java a traves de preguntas verdad o falso", "Preguntas Verdadero o Falso", "basico", 15, true, 80);
    	examen = new Examen("Examen Final", "Evaluar el aprendizaje del curso", "Examen escrito", "Avanzado", 60, true);
    
    	lp1 = new LearningPath("Curso de programar en Java", "Aprender conceptos avanzados de Java", "Intermedio");
        lp2 = new LearningPath("Historia ", "Explorar los eventos clave del siglo XX", "Básico");
        lp3 = new LearningPath("Fútbol para Dummies", "Vuelvete experto en fútbol", "Básico");

    }

    @AfterEach
    void tearDown() {
        estudiante = null;
        tarea = null;
        quiz = null;
        encuesta = null;
        recurso = null;
        quizV = null;
        examen = null;
        
        lp1 = null;
        lp2 = null;
        lp3= null;
    }

    @Test
    void testBuscarLearningPathPorNombre() {
        learningPaths.put(lp1.getId(), lp1);
        learningPaths.put(lp2.getId(), lp2);
        
        LearningPath encontrado = estudiante.buscarLearningPathPorNombre("Curso de programar en Java");
        assertNotNull(encontrado, "El Learning Path no fue encontrado.");
        assertEquals("Curso de programar en Java", encontrado.getTitulo(), "El learningPath encontrado es distinto al esperado.");
    }
    @Test
    void testBuscarLearningPathPorNombreNoEncontro() {
        learningPaths.put(lp1.getId(), lp1);
        learningPaths.put(lp2.getId(), lp2);
        
        LearningPath encontrado = estudiante.buscarLearningPathPorNombre("Aprender C++");
        assertNull(encontrado, "El resultado no fue null al recorrer todos los learning Paths y no encontrar uno");
    }
    @Test
    void testBuscarLearningPathPorNombreNull() {
    	assertNull(estudiante.buscarLearningPathPorNombre("Aprender Java"), "El programa devuelve incorrectamente en caso de que no haya learningPaths");
    }

    @Test
    void testBuscarActividadPorNombre() {
        actividades.put(tarea.getId(), tarea);
        
        Actividad actividad = estudiante.buscarActividadPorNombre("Tarea 1");
        assertNotNull(actividad, "La actividad no fue encontrada.");
        assertEquals("Tarea 1", actividad.getTitulo());
    }
    @Test
    void testBuscarActividadPorNombreCasosIfyWhile() {
    	assertNull(estudiante.buscarActividadPorNombre("Lectura sobre Raquel Bernal"), "Encontro una lectura cuando no existen actividades.");
    	actividades.put(quiz.getId(), quiz);
    	actividades.put(tarea.getId(), tarea);
    	actividades.put(recurso.getId(), recurso);
    	
    	
    	assertNull(estudiante.buscarActividadPorNombre("Lectura sobre Raquel Bernal"), "Encontro una lectura cuando no existe nada de Raquel.");
    	
    }
    
    @Test
    void testRealizarQuiz() throws Exception {
    	quiz.agregarPregunta("¿Qué año comenzó la guerra civil?", Opcion.A);
        quiz.agregarOpcion("¿Qué año comenzó la guerra civil?", "Todas Las Anteriores", Opcion.A, "Han habido varias guerras civiles en Colombia.");
        
        quiz.agregarPregunta("¿Quién escribió 'Cien años de soledad'?", Opcion.B);
        quiz.agregarOpcion("¿Quién escribió 'Cien años de soledad'?", "Gabriel García Márquez", Opcion.B, "Es un famoso autor Colombiano, ganador de un premio Nobel.");
        
        ArrayList<Opcion> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(Opcion.A);
        respuestasEstudiante.add(Opcion.B);
        assertEquals(Estado.EXITOSA,estudiante.realizarQuiz(quiz,respuestasEstudiante));
        assertTrue(estudiante.getRegistroActividades().containsKey(quiz.getId()));
    }

    @Test
    void testEmpezarLearningPath() {
        boolean resultado = estudiante.empezarLearningPath(lp1);
        assertTrue(resultado, "El Learning Path no fue comenzado.");
        assertEquals(lp1, estudiante.getLearningPathEnCurso());
        assertFalse(estudiante.empezarLearningPath(lp2),"Se empezo un learning Path cuando ya habia otro creado");
    }

    @Test
    void testFinalizarLearningPath() {
        estudiante.empezarLearningPath(lp1);
        estudiante.finalizarLearningPath();
        assertNull(estudiante.getLearningPathEnCurso(), "El Learning Path no fue finalizado");
    }

	@Test
    void testRecomendarLearningPaths() {
        learningPaths.put(lp1.getId(), lp1);
        learningPaths.put(lp2.getId(), lp2);
        learningPaths.put(lp3.getId(), lp3);

        List<LearningPath> recomendaciones = estudiante.recomendarLearningPaths(learningPaths);
        assertEquals(3, recomendaciones.size(), "La cantidad de recomendaciones no es la esperada.");
        assertEquals(lp1.getTitulo(),recomendaciones.get(0).getTitulo(), "No se encontro la recomendacion esperada");
        assertEquals(lp3.getTitulo(), recomendaciones.get(2).getTitulo(),"No se encontro la recomendacion esperada");
    }

    @Test
    void testRealizarEncuesta() {
        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("Muy satisfecho");
        
        estudiante.realizarEncuesta(encuesta, respuestas);
        HashMap<String, Estado> registro = estudiante.getRegistroActividades();
        assertEquals(Estado.EXITOSA, registro.get(encuesta.getId()), "La encuesta no fue registrada correctamente.");
    }

    @Test
    void testRealizarExamen() {
        
        examen.agregarPregunta("¿Cuantos paises juegan las eliminatorias de conmebol?");
        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("10");
        
        estudiante.realizarExamen(examen, respuestas);
        HashMap<String, Estado> registro = estudiante.getRegistroActividades();
        assertEquals(Estado.PENDIENTE, registro.get(examen.getId()), "El examen no fue registrado correctamente.");
    }
    
    @Test
    void testRealizarRecurso() {
        estudiante.realizarRecurso(recurso);
        HashMap<String, Estado> registro = estudiante.getRegistroActividades();
        assertEquals(Estado.EXITOSA, registro.get(recurso.getId()), "El recurso no fue registrado correctamente.");
    }

    @Test
    void testRealizarTarea() {
        estudiante.realizarTarea(tarea, "Entrega en PDF");
        HashMap<String, Estado> registro = estudiante.getRegistroActividades();
        assertEquals(Estado.ENVIADA, registro.get(tarea.getId()), "La tarea no fue registrada correctamente.");
    }
    @Test
    void testRealizarQuizVerdad() throws Exception {
    	quizV.agregarPregunta("Java se usó para crear Minecraft.", VerdaderoFalso.Verdadero);
        quizV.agregarPregunta("Los comentarios en Java se escriben con '//'.", VerdaderoFalso.Verdadero);

       
        ArrayList<VerdaderoFalso> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(VerdaderoFalso.Verdadero);
        respuestasEstudiante.add(VerdaderoFalso.Verdadero); 
        
        assertEquals(Estado.EXITOSA, estudiante.realizarQuizVerdad(quizV, respuestasEstudiante),"El estado devuelto no fue el esperado");

    }
    @Test
    
    void getLearningPathById() {
        learningPaths.put(lp1.getId(), lp1);
        learningPaths.put(lp2.getId(), lp2);
        learningPaths.put(lp3.getId(), lp3);
        
        assertEquals(lp2.getTitulo(), estudiante.getLearningPathById(lp2.getId()).getTitulo(), "No se devolvio el learning path que se esperaba");
        assertNull(estudiante.getLearningPathById("LioMessi"),"El texto devolvio algo incorrectamente");
    }
    
    @Test
    void getActividadById() {
    	
    	assertNull(estudiante.getActividadById("SirPaul"),"No fue correcto y se devolvio algo, cuando debia ser null");
    	actividades.put(tarea.getId(),tarea);
    	actividades.put(quiz.getId(), quiz);
    	
    	assertEquals(quiz.getTitulo(),estudiante.getActividadById(quiz.getId()).getTitulo(),"No se devolvio la actividad esperada.");
    	
    	
    }
    
    @Test
    
    void testEmpezarYFinalizarActividad() {
    	boolean empezar = estudiante.empezarActividad(quiz);
    	assertTrue(empezar,"No se empezo correctamente la actividad cuando antes era null");
    	empezar = estudiante.empezarActividad(tarea);
    	assertFalse(empezar,"Se empezó la actividad cuando ya habia una empezada");
    	estudiante.finalizarActividad();
    	assertNull(estudiante.getActividadEnCurso(),"No se finalizo correctamente la actividad");
    	
    	
    }
    
    @Test
    
    void testRecomendarActividad() {
        
        actividades.put(quiz.getId(), quiz);
        actividades.put(quizV.getId(), quizV);
        actividades.put(tarea.getId(), tarea);

        
        
        lp1.agregarActividad(quiz);
        lp1.agregarActividad(quizV);
        lp1.agregarActividad(tarea);
        
        estudiante.getRegistroActividades().put(quiz.getId(), Estado.EXITOSA);
        estudiante.getRegistroActividades().put(quizV.getId(), Estado.NO_EXITOSA);
        estudiante.getRegistroActividades().put(tarea.getId(), Estado.ENVIADA);

        // Caso 1: Actividad exitosa - recomienda la siguiente
        Actividad recomendada = estudiante.recomendarActividad(quiz, lp1);
        assertNotNull(recomendada, "No se recomendó una actividad.");
        assertEquals(quizV.getId(), recomendada.getId(), "La actividad recomendada no es la siguiente en la lista.");

        // Caso 2: Actividad no exitosa - recomienda la anterior
        recomendada = estudiante.recomendarActividad(quizV, lp1);
        assertNotNull(recomendada, "No se recomendó una actividad.");
        assertEquals(quiz.getId(), recomendada.getId(), "La actividad recomendada no es la anterior en la lista.");


        // Caso 3: Última actividad exitosa, no hay actividad siguiente
        recomendada = estudiante.recomendarActividad(tarea, lp1);
        assertNull(recomendada, "No debería haber una actividad recomendada después de la última.");

        // Caso 4: Primera actividad no exitosa, no hay actividad anterior
        estudiante.getRegistroActividades().put(quiz.getId(), Estado.NO_EXITOSA);
        recomendada = estudiante.recomendarActividad(quiz, lp1);
        assertNull(recomendada, "No debería haber una actividad recomendada antes de la primera.");
    }
    
    @Test
    void testVerProgresoLearningPath() {
        
        
        lp1.agregarActividad(quiz);
        lp1.agregarActividad(quizV);
        lp1.agregarActividad(tarea);

        estudiante.empezarLearningPath(lp1);

        estudiante.getRegistroActividades().put(quiz.getId(), Estado.EXITOSA);
        estudiante.getRegistroActividades().put(quizV.getId(), Estado.ENVIADA);
        estudiante.getRegistroActividades().put(tarea.getId(), Estado.NO_EXITOSA);

        
        List<Double> progreso = estudiante.verProgresoLearningPath();
        
        
        assertEquals(66.67, progreso.get(0), 0.01, "El porcentaje de actividades completadas no es el esperado.");
        assertEquals(33.33, progreso.get(1), 0.01, "El porcentaje de actividades exitosas no es el esperado.");
    }
    
    @Test
    
    void testAgregarResena() {
    	String resena = "Actividad muy sencilla, la podria completar sin haber hecho el curso.";
    	
    	estudiante.agregarResenaActividad(quizV, resena);
    	
    	assertEquals(1,quizV.getResenas().size(),"La reseña no se añadio correctamente");
    	assertTrue(quizV.getResenas().contains("Actividad muy sencilla, la podria completar sin haber hecho el curso."), "No se guardo correctamente la reseña");
    	
    }
    
    @Test
    void testAgregarRating() throws Exception{
    	Float rating = 4.6f;
    	Float rating2 = 3.3f;
    	Float ratingEsperado = (Float)(rating +rating2) /2;
    	estudiante.agregarRatingActividad(quizV, rating);
    	estudiante.agregarRatingActividad(quizV, rating2);
    	
    	assertEquals(ratingEsperado,quizV.getRating(),0.1,"El rating no se añadio correctamente");
    	Exception exception = assertThrows(Exception.class, () -> {
            estudiante.agregarRatingActividad(quizV, 7.3f);
        });

        assertEquals("Rating debe estar entre 0 y 5.", exception.getMessage(), "El mensaje de excepción no es el esperado.");
    	
    }
    @Test
    void testVerProgresoLearningPathSinActividadesCompletadas() {
     
        lp1.agregarActividad(tarea);
        lp1.agregarActividad(quiz);

        estudiante.empezarLearningPath(lp1);

        List<Double> progreso = estudiante.verProgresoLearningPath();


        
        assertEquals(0.0, progreso.get(0), 0.01, "El porcentaje de actividades completadas no es el esperado.");
        assertEquals(0.0, progreso.get(1), 0.01, "El porcentaje de actividades exitosas no es el esperado.");
    }
   
    	
    @Test
    void testActualizarRegistroLearningPathActual() {
        
        lp1.agregarActividad(quiz);
        lp1.agregarActividad(tarea);
        lp1.agregarActividad(encuesta);
        estudiante.empezarLearningPath(lp1);
        
        estudiante.getRegistroActividades().put(quiz.getId(), Estado.EXITOSA);
        estudiante.getRegistroActividades().put(tarea.getId(), Estado.ENVIADA);
        estudiante.actualizarRegistroLearningPathActual();

        HashMap<String, Double> registroLearningPaths = estudiante.getRegistroLearningPaths();
        double progresoRegistrado = (double) registroLearningPaths.get(lp1.getId());    
        double progresoEsperado = (double) 2.0 / 3.0; 
        assertEquals(progresoEsperado, progresoRegistrado, 0.01, "El progreso registrado no es el esperado.");
    }

    @Test
    void testActualizarRegistroLearningPathActualSinActividades() {
        
        estudiante.empezarLearningPath(lp1);
        estudiante.actualizarRegistroLearningPathActual();
        
        HashMap<String, Double> registroLearningPaths = estudiante.getRegistroLearningPaths();
        double progresoRegistrado = registroLearningPaths.get(lp1.getId());
        assertEquals(0.0, progresoRegistrado, "El progreso registrado no es el esperado para un LearningPath sin actividades completadas.");
    }


}
