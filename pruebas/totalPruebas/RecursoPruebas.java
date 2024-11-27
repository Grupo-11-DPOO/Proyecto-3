package totalPruebas;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import actividades.Estado;
import actividades.Recurso;

class RecursoPruebas {
    private Recurso recurso;

    @BeforeEach
    void setUp() {
        // Inicializamos un nuevo recurso antes de cada prueba
        recurso = new Recurso(
                "Introduccion a Java",
                "Aprender los conceptos básicos de Java",
                "Material de referencia para aprender Java",
                "Principiante",
                60,
                true,
                "PDF con conceptos de Java"
        );
    }

    @AfterEach
    void tearDown() {
        recurso = null;
    }

    @Test
    void testGetMaterial() {
        assertEquals("PDF con conceptos de Java", recurso.getMaterial(), "El material no es el esperado.");
    }

    @Test
    void testSetMaterial() {
        recurso.setMaterial("Video tutorial de Java");
        assertEquals("Video tutorial de Java", recurso.getMaterial(), "El material no se actualizó correctamente.");
    }

    @Test
    void testRealizarRecurso() {
        Estado estado = recurso.realizarRecurso();
        assertEquals(Estado.EXITOSA, estado, "El estado debería ser EXITOSA después de realizar el recurso.");
    }

    @Test
    void testCorrectamenteCreado() {
        assertEquals("Introduccion a Java", recurso.getTitulo(), "El título no es el esperado.");
        assertEquals("Aprender los conceptos básicos de Java", recurso.getObjetivo(), "El objetivo no es el esperado.");
        assertEquals("Material de referencia para aprender Java", recurso.getDescripcion(), "La descripción no es la esperada.");
        assertEquals(60, recurso.getDuracionMinutos(), "La duración no es la esperada.");
        assertTrue(recurso.isObligatorio(), "El recurso debería ser obligatorio.");
    }
    @Test
    void testClone() {
    	assertNotEquals(recurso.getId(),recurso.clone().getId(),"El id es el mismo en ambas");
    }
}