package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import actividades.*;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;
import usuarios.Profesor;

class ProfesorPruebas {
    private Profesor profesor;
    private HashMap<String, Actividad> actividades;
    private HashMap<String, LearningPath> learningPaths;

    @BeforeEach
    void setUp() {
        actividades = new HashMap<>();
        learningPaths = new HashMap<>();
        profesor = new Profesor(actividades, learningPaths, "profesor1", "password123");
    }

    @AfterEach
    void tearDown() {
        profesor = null;
    }

    @Test
    void testCrearActividadRecurso() throws Exception {
        Recurso recurso = profesor.crearActividadRecurso("Lectura Java", "Aprender conceptos básicos", "PDF sobre Java", "Intermedio", 20, true, "Material.pdf");
        assertNotNull(recurso, "El recurso no fue creado correctamente.");
        assertEquals("Lectura Java", recurso.getTitulo(), "El título del recurso no es el esperado.");
    }

    @Test
    void testCrearActividadTarea() throws Exception {
        Tarea tarea = profesor.crearActividadTarea("Tarea 1", "Resolver ejercicios", "Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");
        assertNotNull(tarea, "La tarea no fue creada correctamente.");
        assertEquals("Tarea 1", tarea.getTitulo(), "El título de la tarea no es el esperado.");
    }

    @Test
    void testCrearActividadExamen() throws Exception {
        Examen examen = profesor.crearActividadExamen("Examen Final", "Evaluar conocimientos", "Examen escrito", "Avanzado", 60, true);
        assertNotNull(examen, "El examen no fue creado correctamente.");
        assertEquals("Examen Final", examen.getTitulo(), "El título del examen no es el esperado.");
        
    }

    @Test
    void testCrearActividadEncuesta() throws Exception {
        Encuesta encuesta = profesor.crearActividadEncuesta("Encuesta de Satisfacción", "Evaluar satisfacción", "Preguntas abiertas", "Básico", 15, true);
        assertNotNull(encuesta, "La encuesta no fue creada correctamente.");
        assertEquals("Encuesta de Satisfacción", encuesta.getTitulo(), "El título de la encuesta no es el esperado.");
    }

    @Test
    void testBuscarLearningPathPorNombre() throws UsuarioExistenteException {
        LearningPath lp1 = new LearningPath("Java Avanzado", "Aprender Java a fondo", "Intermedio");
        profesor.agregarLearningPath(lp1);

        LearningPath encontrado = profesor.buscarLearningPathPorNombre("Java Avanzado");
        assertNotNull(encontrado, "El Learning Path no fue encontrado.");
        assertEquals("Java Avanzado", encontrado.getTitulo(), "El Learning Path encontrado no tiene el título esperado.");
    }

    @Test
    void testBuscarLearningPathPorNombreNoEncontrado() throws Exception {
    	LearningPath lp1 = new LearningPath("Java Avanzado", "Aprender Java a fondo", "Intermedio");
        LearningPath encontrado = profesor.buscarLearningPathPorNombre("Python Básico");
       
        assertNull(encontrado, "No debería haber encontrado un Learning Path.");
        profesor.agregarLearningPath(lp1);
        LearningPath encontrado2 = profesor.buscarLearningPathPorNombre("Python Básico");
        
        assertNull(encontrado2, "No debería haber encontrado un Learning Path.");
    }

    @Test
    void testGetLearningPathById() throws UsuarioExistenteException {
        LearningPath lp = new LearningPath("Historia Mundial", "Explorar eventos históricos", "Avanzado");
        profesor.agregarLearningPath(lp);
        assertNull(profesor.getLearningPathById("UniandesLover23"),"No se retorno null al buscar un id inexistente.");
        LearningPath encontrado = profesor.getLearningPathById(lp.getId());
        assertNotNull(encontrado, "El Learning Path no fue encontrado por ID.");
        assertEquals(lp.getTitulo(), encontrado.getTitulo(), "El Learning Path encontrado no tiene el título esperado.");
    }

    @Test
    void testClonarTarea() throws Exception {
    	Tarea tarea = profesor.crearActividadTarea("Tarea 1", "Resolver ejercicios", 
    			"Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");
        assertNotNull(tarea, "La tarea no fue creada correctamente.");
        Actividad idClon = profesor.clonarTarea(tarea);

        Tarea actividadClonada = (Tarea) profesor.getActividadById(idClon.getId());
        assertNotNull(actividadClonada, "La actividad clonada no fue encontrada.");
        assertNotEquals(tarea.getId(), idClon, 
        		"El ID de la actividad clonada no debería ser igual al original.");
    }
    
    @Test
    void testClonarRecurso() throws Exception {
        Recurso recurso = profesor.crearActividadRecurso("Lectura Java", "Aprender conceptos básicos", 
                "PDF sobre Java", "Intermedio", 20, true, "Material.pdf");
        assertNotNull(recurso, "El recurso no fue creado correctamente.");
        Actividad idClon = profesor.clonarRecurso(recurso);

        Recurso recursoClonado = (Recurso) profesor.getActividadById(idClon.getId());
        assertNotNull(recursoClonado, "El recurso clonado no fue encontrado.");
        assertNotEquals(recurso.getId(), idClon, 
                "El ID del recurso clonado no debería ser igual al original.");
    }
    
    @Test
    void testClonarQuizVerdad() throws Exception {
        QuizVerdad quizVerdad = profesor.crearQuizVerdaderoFalso("Quiz Java", 
                "Evaluar conocimientos sobre Java", "Preguntas Verdadero o Falso", 
                "Fácil", 15, true, 80);
        assertNotNull(quizVerdad, "El quiz de verdadero o falso no fue creado correctamente.");
        Actividad idClon = profesor.clonarQuizVerdad(quizVerdad);

        QuizVerdad quizClonado = (QuizVerdad) profesor.getActividadById(idClon.getId());
        assertNotNull(quizClonado, "El quiz clonado no fue encontrado.");
        assertNotEquals(quizVerdad.getId(), idClon, 
                "El ID del quiz clonado no debería ser igual al original.");
    }
    
    @Test
    void testClonarQuiz() throws Exception {
        Quiz quiz = profesor.crearActividadQuiz("Quiz Historia", "Demostrar conocimiento sobre historia", 
                "Preguntas de opción múltiple", "Intermedio", 30, true, 60);
        assertNotNull(quiz, "El quiz no fue creado correctamente.");
        Actividad idClon = profesor.clonarQuiz(quiz);

        Quiz quizClonado = (Quiz) profesor.getActividadById(idClon.getId());
        assertNotNull(quizClonado, "El quiz clonado no fue encontrado.");
        assertNotEquals(quiz.getId(), idClon, 
                "El ID del quiz clonado no debería ser igual al original.");
    }
    
    @Test
    void testClonarExamen() throws Exception {
        Examen examen = profesor.crearActividadExamen("Examen Final", "Evaluar conocimientos adquiridos", 
                "Examen escrito sobre conceptos clave", "Avanzado", 45, true);
        assertNotNull(examen, "El examen no fue creado correctamente.");
        Actividad idClon = profesor.clonarExamen(examen);

        Examen examenClonado = (Examen) profesor.getActividadById(idClon.getId());
        assertNotNull(examenClonado, "El examen clonado no fue encontrado.");
        assertNotEquals(examen.getId(), idClon, 
                "El ID del examen clonado no debería ser igual al original.");
    }
    
    @Test
    void testClonarEncuesta() throws Exception {
        Encuesta encuesta = profesor.crearActividadEncuesta("Encuesta de Satisfacción", 
                "Medir satisfacción del estudiante", "Preguntas abiertas", 
                "Intermedio", 20, true);
        assertNotNull(encuesta, "La encuesta no fue creada correctamente.");
        Actividad idClon = profesor.clonarEncuesta(encuesta);

        Encuesta encuestaClonada = (Encuesta) profesor.getActividadById(idClon.getId());
        assertNotNull(encuestaClonada, "La encuesta clonada no fue encontrada.");
        assertNotEquals(encuesta.getId(), idClon, 
                "El ID de la encuesta clonada no debería ser igual al original.");
    }

    @Test
    void testMostrarRespuestasEncuesta() throws Exception {
        Encuesta encuesta = profesor.crearActividadEncuesta("Encuesta de Satisfacción", "Evaluar satisfacción", "Preguntas abiertas sin resultado", "Fácil", 10, true);
        ArrayList<String> respuestas = new ArrayList<>();
        respuestas.add("Muy satisfecho");
        respuestas.add("Buena experiencia");

        encuesta.contestarEncuesta("estudiante1", respuestas);

        String resultado = profesor.mostrarRespuestasEncuesta(encuesta);
        assertTrue(resultado.contains("Muy satisfecho"), "Las respuestas no fueron mostradas correctamente.");
        assertTrue(resultado.contains("estudiante1"), "El nombre del estudiante no fue mostrado correctamente.");
    }
    
    @Test
    void testAgregarActividadALearningPath() throws Exception{
    	LearningPath lp = new LearningPath("DPOO", "Clase de Diseño Y programación orientado a objetos", "Intermedio");
    	assertNull(profesor.buscarActividadPorNombre("JujutsuParcial"),"Se encontro una actividad cuando la lista era vacia.");
    	Encuesta encuesta = profesor.crearActividadEncuesta("Encuesta de Satisfacción", "Evaluar satisfacción", "Preguntas abiertas sin resultado sobre el LearningPath", "Fácil", 10, true);
    	profesor.agregarActividadALearningPath(lp, encuesta);
    	Actividad buscar = profesor.getActividadById(encuesta.getId());
    	assertNotNull(buscar, "No se encontro la actividad despues de ser agregada");
    	assertEquals("Encuesta de Satisfacción", buscar.getTitulo(),"El titulo de la actividad no es el esperado.");
    }
    
    @Test
    void testGetActividadByIdCasoNull() {
    	Actividad acti = profesor.getActividadById("UniandesLover23");
    	assertNull(acti, "Al buscar un Id inexistente no se retorno null");
    	
    }
    
    @Test
    void testBuscarActividadPorNombre() throws Exception {
    	assertNull(profesor.buscarActividadPorNombre("JujutsuParcial"),"Se encontro una actividad cuando la lista era vacia.");
    	Encuesta encuesta = profesor.crearActividadEncuesta("Encuesta de Satisfacción", "Evaluar satisfacción", "Preguntas abiertas sin resultado sobre el LearningPath", "Fácil", 10, true);
    	Actividad buscar = profesor.buscarActividadPorNombre(encuesta.getTitulo());
    	assertNotNull(buscar, "No se encontro la actividad despues de ser agregada");
    	assertEquals("Encuesta de Satisfacción", buscar.getTitulo(),"El titulo de la actividad no es el esperado.");
    	assertNull(profesor.buscarActividadPorNombre("JujutsuParcial"),"Se encontro una actividad inexistente cuando la lista no era vacia.");
    }
    
    @Test
    void testMostrarResenas() {
    	
    	LearningPath lp = new LearningPath("DPOO", "Clase de Diseño Y programación orientado a objetos", "Intermedio");
		assertEquals("No hay reseñas por mostrar.",profesor.mostrarResenas(lp),"No se mostro el mensaje esperado.");
		QuizVerdad quiz = new QuizVerdad("Quiz Java", "Demostrar conocimiento en Java a traves de preguntas verdad o falso", "Preguntas Verdadero o Falso", "Fácil", 15, true, 80);
    	String resena = "Actividad muy sencilla, la podria completar sin haber hecho el curso.";
    	String resena2 = "Complicado, se me dificulto mucho.";
    	quiz.agregarResena(resena);
    	quiz.agregarResena(resena2);
    	profesor.agregarActividadALearningPath(lp, quiz);
		StringBuilder sb = new StringBuilder();
		sb.append("Titulo: Quiz Java");
		sb.append("Resena "+ 1+": " + resena);
		sb.append("---------------------------------------");
		sb.append("Resena "+ 2+": " + resena2);
		sb.append("---------------------------------------");
		assertEquals(sb.toString(), profesor.mostrarResenas(lp),"El mensaje no es el esperado.");	
    }
    
    @Test
    void testMostrarRespuestasExamen() throws Exception {
        
        Examen examen = profesor.crearActividadExamen("Examen Final", "Evaluar conocimientos adquiridos", 
                "Examen escrito sobre conceptos clave", "Avanzado", 45, true);
        
        assertEquals("No hay respuestas por mostrar.", profesor.mostrarRespuestasExamen(examen),"Se mostro el mensaje equivocado cuando no hay respuestas");
        
        ArrayList<String> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add("Respuesta 1");
        respuestasEstudiante.add("Respuesta 2");
        respuestasEstudiante.add("Respuesta 3");
        examen.contestarExamen("IdEstudiante23", respuestasEstudiante);
        
        StringBuilder sb=new StringBuilder();
        sb.append("Estudiante: IdEstudiante23").append("\n");
        sb.append("Respuestas al examen: ");
        sb.append("Respuesta 1, ").append("Respuesta 2, ").append("Respuesta 3");
        sb.append("\n");
        String resultado = profesor.mostrarRespuestasExamen(examen);
        assertEquals(sb.toString(),resultado,"El mensaje no es el esperado");
    }
    
    @Test
    void testMostrarRespuestasTarea() throws Exception {
        
        Tarea tarea = profesor.crearActividadTarea("Tarea 1", "Resolver ejercicios", 
                "Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");

        tarea.getRespuestas().put("estudiante1", "Entrega en PDF");
        tarea.getRespuestas().put("estudiante2", "Entrega en línea");
        StringBuilder sb = new StringBuilder();

        sb.append("Estudiante: estudiante1").append("\n");
        sb.append("Medio de entrega de la tarea: Entrega en PDF").append("\n");

     
        sb.append("Estudiante: estudiante2").append("\n");
        sb.append("Medio de entrega de la tarea: Entrega en línea").append("\n");

        String resultadoEsperado = sb.toString();

        String resultado = profesor.mostrarRespuestasTarea(tarea);

        assertEquals(resultadoEsperado, resultado, "El mensaje devuelto no es el esperado");
    }
    
    @Test
    void testClonarActividad() throws Exception {
        
        Tarea tarea = profesor.crearActividadTarea("Tarea 1", "Resolver ejercicios", 
                "Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");
        Recurso recurso = profesor.crearActividadRecurso("Recurso 1", "Aprender conceptos básicos", 
                "PDF sobre matemáticas", "Intermedio", 20, true, "Material.pdf");
        Quiz quiz = profesor.crearActividadQuiz("Quiz 1", "Evaluar conocimientos", 
                "Preguntas de opción múltiple", "Principiante", 15, true, 60);
        Examen examen = profesor.crearActividadExamen("Examen Final", "Evaluar conocimientos adquiridos", 
                "Examen escrito", "Avanzado", 45, true);
        Encuesta encuesta = profesor.crearActividadEncuesta("Encuesta de Satisfacción", 
                "Evaluar satisfacción", "Preguntas abiertas", "Básico", 10, true);
        QuizVerdad quizVerdad = profesor.crearQuizVerdaderoFalso("Quiz Verdad", 
                "Preguntas de verdadero o falso", "Evaluar conocimiento rápido", 
                "Fácil", 10, true, 80);

        Actividad idTareaClonada = (Actividad) profesor.clonarActividad(tarea);
        Actividad idRecursoClonada = (Actividad) profesor.clonarActividad(recurso);
        Actividad idQuizClonada = (Actividad) profesor.clonarActividad(quiz);
        Actividad idExamenClonada = (Actividad) profesor.clonarActividad(examen);
        Actividad idEncuestaClonada = (Actividad) profesor.clonarActividad(encuesta);
        Actividad idQuizVerdadClonada = (Actividad) profesor.clonarActividad(quizVerdad);

        assertNotNull(profesor.getActividadById(idTareaClonada.getId()), "La tarea clonada no fue encontrada.");
        assertNotNull(profesor.getActividadById(idRecursoClonada.getId()), "El recurso clonado no fue encontrado.");
        assertNotNull(profesor.getActividadById(idQuizClonada.getId()), "El quiz clonado no fue encontrado.");
        assertNotNull(profesor.getActividadById(idExamenClonada.getId()), "El examen clonado no fue encontrado.");
        assertNotNull(profesor.getActividadById(idEncuestaClonada.getId()), "La encuesta clonada no fue encontrada.");
        assertNotNull(profesor.getActividadById(idQuizVerdadClonada.getId()), "El quiz de verdadero o falso clonado no fue encontrado.");
        
        assertNotEquals(tarea.getId(), idTareaClonada.getId(), "El ID de la tarea clonada no debería ser igual al original.");
        assertNotEquals(recurso.getId(), idRecursoClonada.getId(), "El ID del recurso clonado no debería ser igual al original.");
        assertNotEquals(quiz.getId(), idQuizClonada.getId(), "El ID del quiz clonado no debería ser igual al original.");
        assertNotEquals(examen.getId(), idExamenClonada.getId(), "El ID del examen clonado no debería ser igual al original.");
        assertNotEquals(encuesta.getId(), idEncuestaClonada.getId(), "El ID de la encuesta clonada no debería ser igual al original.");
        assertNotEquals(quizVerdad.getId(), idQuizVerdadClonada.getId(), "El ID del quiz de verdadero o falso clonado no debería ser igual al original.");
    }
    
    @Test
    void testGuardarActividad() throws Exception {
        Tarea tarea = profesor.crearActividadTarea("Tarea 1", "Resolver ejercicios", 
                "Matemáticas básicas", "Intermedio", 30, true, "Entrega en PDF");
        profesor.guardarActividad(tarea);

        assertNotNull(profesor.getDataActividades().get(tarea.getId()), "La actividad no fue guardada en el mapa de actividades.");
        assertTrue(profesor.getIdActividadesCreadas().contains(tarea.getId()), "El ID de la actividad no fue añadido a la lista de actividades creadas.");
    }

    @Test
    void testCrearLearningPath() {
        LearningPath learningPath = profesor.crearLearningPath("Java Basics", "Aprender Java desde cero", "Principiante");

        assertNotNull(learningPath, "El Learning Path no fue creado correctamente.");
        assertEquals("Java Basics", learningPath.getTitulo(), "El título del Learning Path no es el esperado.");
        assertEquals("Aprender Java desde cero", learningPath.getDescripcion(), "El objetivo del Learning Path no es el esperado.");
        assertEquals("Principiante", learningPath.getNivel(), "El nivel del Learning Path no es el esperado.");
        assertTrue(profesor.getDataLearningPaths().containsKey(learningPath.getId()), "El Learning Path no fue añadido al mapa de Learning Paths.");
    }

    @Test
    void testGuardarLearningPath() throws Exception {
        LearningPath learningPath = profesor.crearLearningPath("Python Advanced", "Aprender conceptos avanzados de Python", "Avanzado");
        profesor.guardarLearningPath(learningPath);

        assertNotNull(profesor.getDataLearningPaths().get(learningPath.getId()), "El Learning Path no fue guardado en el mapa de Learning Paths.");
        assertTrue(profesor.getIdLearningPathsCreados().contains(learningPath.getId()), "El ID del Learning Path no fue añadido a la lista de Learning Paths creados.");
    }

    @Test
    void testAgregarActividad() throws Exception {
        LearningPath learningPath = profesor.crearLearningPath("Java Advanced", "Dominio avanzado de Java", "Avanzado");
        Tarea tarea = profesor.crearActividadTarea("Tarea 1", "Resolver ejercicios avanzados", 
                "Matemáticas avanzadas", "Avanzado", 45, true, "Entrega en PDF");
        
        profesor.agregarActividad(tarea, learningPath);

        assertTrue(learningPath.getListaActividades().contains(tarea), "La actividad no fue añadida al Learning Path.");
        assertTrue(profesor.getDataLearningPaths().containsKey(learningPath.getId()), "El Learning Path no fue guardado en el mapa de Learning Paths.");
    }
}
