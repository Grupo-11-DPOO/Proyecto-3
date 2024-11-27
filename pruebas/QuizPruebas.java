package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import actividades.Estado;
import actividades.Opcion;
import actividades.Quiz;

class QuizPruebas {
    private Quiz quiz;

    @BeforeEach
    void setUp() {
        quiz = new Quiz(
                "Historia",
                "Aprender sobre historia mundial",
                "Contiene preguntas de múltiples opciones",
                "Avanzado",
                30,
                true,
                60.0f
        );
    }

    @AfterEach
    void tearDown() {
        quiz = null;
    }

    @Test
    void testAgregarPregunta() {
        quiz.agregarPregunta("¿Quién descubrió América?", Opcion.A);
        assertTrue(quiz.getPreguntas().containsKey("¿Quién descubrió América?"), "La pregunta no fue agregada correctamente.");
    }

    @Test
    void testAgregarOpcion() {
        quiz.agregarPregunta("¿Quién descubrió América?", Opcion.A);
        quiz.agregarOpcion("¿Quién descubrió América?", "Cristóbal Colón", Opcion.A, "Fue el primer europeo en llegar a América.");
        
        var opciones = quiz.getPreguntas().get("¿Quién descubrió América?");
        assertNotNull(opciones, "Las opciones no fueron agregadas correctamente.");
        assertTrue(opciones.containsKey(Opcion.A), "La opción A no fue agregada correctamente.");
    }

    @Test
    void testVerPreguntas() {
        quiz.agregarPregunta("¿Cuál es la capital de Francia?", Opcion.B);
        quiz.agregarOpcion("¿Cuál es la capital de Francia?", "Monaco", Opcion.A, "No es ");
        quiz.agregarOpcion("¿Cuál es la capital de Francia?", "París", Opcion.B, "París es la capital de Francia.");
        quiz.agregarOpcion("¿Cuál es la capital de Francia?", "Lyon", Opcion.C, "No es");
        String preguntasSinD = quiz.verPreguntas();
        quiz.agregarOpcion("¿Cuál es la capital de Francia?", "Champagne", Opcion.D, "No es una ciudad.");
        String preguntas = quiz.verPreguntas();
        assertTrue(preguntas.contains("Pregunta #1: ¿Cuál es la capital de Francia?"), "La pregunta no aparece en la lista.");
        assertTrue(preguntas.contains("A: Monaco"), "La opción A no aparece en la lista.");
        assertTrue(preguntas.contains("B: París"), "La opción B no aparece en la lista.");
        assertTrue(preguntas.contains("C: Lyon"), "La opción C no aparece en la lista.");
        assertTrue(preguntas.contains("D: Champagne"), "La opción D no aparece en la lista.");
        assertFalse(preguntasSinD.contains("D: Champagne"), "La opción D  aparece en la lista.");
    }

    @Test
    void testVerPreguntasConExplicaciones() {
        quiz.agregarPregunta("¿Cuál es el río más largo del mundo?", Opcion.C);
        quiz.agregarOpcion("¿Cuál es el río más largo del mundo?", "Nilo", Opcion.C, "El río Nilo es considerado el más largo del mundo.");

        String preguntasConExplicaciones = quiz.verPreguntasConExplicaciones();
        assertTrue(preguntasConExplicaciones.contains("Pregunta: ¿Cuál es el río más largo del mundo?"), "La pregunta no aparece en la lista.");
        assertTrue(preguntasConExplicaciones.contains("Explicación: \nEl río Nilo es considerado el más largo del mundo.\n"), "La explicación no aparece en la lista.");
    }
    
    @Test
    void testVerPreguntasConYSinExplicacionSinPreguntas(){
    	assertEquals("No hay preguntas que mostrar", quiz.verPreguntas(), "El mensaje es incorrecto cuando se ve la pregunta sin explicacion.");
    	assertEquals("No hay preguntas que mostrar", quiz.verPreguntasConExplicaciones(), "El mensaje es incorrecto cuando se muestran preguntas con explicaciones");
    }

    @Test
    void testCalificarRespuestasCorrectas() throws Exception {
        // Agregar preguntas y respuestas correctas
        quiz.agregarPregunta("¿Qué año comenzó la Segunda Guerra Mundial?", Opcion.A);
        quiz.agregarOpcion("¿Qué año comenzó la Segunda Guerra Mundial?", "1939", Opcion.A, "La guerra comenzó en 1939.");
        
        quiz.agregarPregunta("¿Quién escribió 'Cien años de soledad'?", Opcion.B);
        quiz.agregarOpcion("¿Quién escribió 'Cien años de soledad'?", "Gabriel García Márquez", Opcion.B, "Es un famoso autor colombiano.");

        // Respuestas del estudiante
        ArrayList<Opcion> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(Opcion.A);
        respuestasEstudiante.add(Opcion.B);

        Estado estado = quiz.calificar("12345", respuestasEstudiante);
        assertEquals(Estado.EXITOSA, estado, "La calificación debería ser EXITOSA.");
    }

    @Test
    void testCalificarRespuestasIncorrectas() throws Exception {
        // Agregar preguntas y respuestas correctas
        quiz.agregarPregunta("¿Cuál es la fórmula del agua?", Opcion.C);
        quiz.agregarOpcion("¿Cuál es la fórmula del agua?", "H2O", Opcion.C, "Es la fórmula química del agua.");
        
        quiz.agregarPregunta("¿Qué planeta es conocido como el planeta rojo?", Opcion.D);
        quiz.agregarOpcion("¿Qué planeta es conocido como el planeta rojo?", "Marte", Opcion.D, "Marte es conocido por su color rojo.");

        // Respuestas incorrectas del estudiante
        ArrayList<Opcion> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(Opcion.A);
        respuestasEstudiante.add(Opcion.B);

        Estado estado = quiz.calificar("67890", respuestasEstudiante);
        assertEquals(Estado.NO_EXITOSA, estado, "La calificación debería ser NO_EXITOSA.");
    }

    @Test
    void testCalificarConCantidadIncorrectaDeRespuestas() {
        quiz.agregarPregunta("¿Cuál es el océano más grande?", Opcion.A);
        quiz.agregarOpcion("¿Cuál es el océano más grande?", "Pacífico", Opcion.A, "El océano Pacífico es el más grande.");

        ArrayList<Opcion> respuestasEstudiante = new ArrayList<>();
        respuestasEstudiante.add(Opcion.A);
        respuestasEstudiante.add(Opcion.D);

        Exception exception = assertThrows(Exception.class, () -> {
            quiz.calificar("56789", respuestasEstudiante);
        });

        assertEquals("La cantidad de respuestas no coincide con el número de preguntas.", exception.getMessage(), "El mensaje de excepción no es el esperado.");
    }
    @Test
    void testClone() {
    	assertNotEquals(quiz.getId(),quiz.clone().getId(),"El id es el mismo en ambas");
    }
}
