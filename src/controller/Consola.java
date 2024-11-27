package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import actividades.Actividad;
import actividades.Encuesta;
import actividades.Estado;
import actividades.Examen;
import actividades.Opcion;
import actividades.Quiz;
import actividades.QuizVerdad;
import actividades.Recurso;
import actividades.Tarea;
import actividades.TipoActividades;
import actividades.VerdaderoFalso;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;
import usuarios.Estudiante;
import usuarios.Profesor;

public class Consola {
	
	public static SistemaRegistro sistemaRegistro = new SistemaRegistro();
	private static HashMap<String, Profesor> datosProfesor = sistemaRegistro.getDatosProfesores();
	private static HashMap<String, Estudiante> datosEstudiante = sistemaRegistro.getDatosEstudiantes();
	public static HashMap<String, Actividad> actividades = sistemaRegistro.actividades;
	public static HashMap<String, LearningPath> learningPaths = sistemaRegistro.learningPaths;
	private static Profesor profesorActual;
	private static Estudiante estudianteActual;
	private static String[] opciones = {"Iniciar sesión", "Registrarse", "Salir"};
	private static String[] opcionesRegistro = {"Crear usuario: Profesor", "Crear usuario: Estudiante", "Salir"};
	private static String[] opcionesMenuProfesor = {"Crear Learning Path", "Ver y Editar Learning Path", "Crear Actividad", "Clonar Actividad",
			"Ver y Editar Actividades", "Agregar reseñas y/o rating a actividad", "Ver estadísticas", "Salir"};
	private static String[] opcionesTipoActividad = {"Encuesta", "Examen", "Quiz múltiple", "Recurso", "Tarea", "Quiz V/F"};
	private static String[] opcionesClonar = {"Clonar con ID de actividad", "Volver"};
	private static String[] opcionesCrearLearningPath = {"Agregar actividad propia existente", "Crear nueva actividad"};
	private static String[] opcionesSiNo = {"Si", "No"};
	private static String[] opcionesLogin = {"Profesor", "Estudiante"};
	private static String[] opcionResenaRating = {"Reseña", "Rating"};
	private static String[] opcionesEditarLearningPath = {"Título", "Descripción", "Nivel", "Agregar actividades existentes"};
	private static String[] opcionesVerYEditarActividades = {"Ver prerequisitos", "Ver reseñas", "Editar título", "Editar objetivo",
			"Editar descripción", "Editar nivel", "Editar si es obligatorio", "Volver"};
	private static String[] opcionesMenuEstudiantes = {"Ver Learning Paths", "Iniciar actividad del Learning Path actual", 
			"Completar actividad en curso", "Agregar reseñas y/o rating a actividad", "Ver progreso del Learning Path actual",
			"Salirse del Learning Path o Actividad actual", "Salir"};
	private static String[] opcionesOfertaLearningPaths = {"Oferta total de Learning Paths", "Recomendación a partir de sus intereses.", "Volver"};
	private static String[] opcionSalirLearningOActividad = {"Salir Learning Path actual", "Salir Actividad actual", "Volver al menú principal"};
	private static String[] opcionesProgresoLearningPath = {"Ver progreso de actividades completadas y exitosas",
			"Ver progreso de actividades exitosas", "Volver al menú principal"};
	private static String[] opcionesVerEstadisticas = {"Ver estadísticas por estudiante", "Ver estadísticas por Learning Path",
			"Volver al menú principal"};
	
    /**
     * Le pide al usuario que ingrese una cadena de caracteres
     * @param mensaje El mensaje con el que se solicita la información
     * @return La cadena introducida por el usuario
     */
    protected static String pedirCadenaAlUsuario( String mensaje )
    {
        try
        {
            System.out.print( mensaje + ": " );
            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
            String input = reader.readLine( );
            return input;
        }
        catch( IOException e )
        {
            System.out.println( "Error leyendo de la consola" );
        }
        return "error";
    }

    /**
     * Le pide al usuario que ingrese un número que no puede tener cifras decimales
     * @param mensaje El mensaje con el que se solicita la información
     * @return El valor introducido por el usuario
     */
    protected static int pedirEnteroAlUsuario( String mensaje )
    {
        int valorResultado = Integer.MIN_VALUE;
        while( valorResultado == Integer.MIN_VALUE )
        {
            try
            {
                System.out.print( mensaje + ": " );
                BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
                String input = reader.readLine( );
                int numero = Integer.parseInt( input );
                valorResultado = numero;
            }
            catch( NumberFormatException nfe )
            {
                System.out.println( "El valor digitado no es un entero" );
            }
            catch( IOException e )
            {
                System.out.println( "Error leyendo de la consola" );
            }
        }
        return valorResultado;
    }

    /**
     * Le pide al usuario que ingrese un número que puede tener cifras decimales
     * @param mensaje El mensaje con el que se solicita la información
     * @return El valor introducido por el usuario
     */
    protected static float pedirNumeroAlUsuario( String mensaje )
    {
        float valorResultado = Integer.MIN_VALUE;
        while( valorResultado == Integer.MIN_VALUE )
        {
            try
            {
                System.out.print( mensaje + ": " );
                BufferedReader reader = new BufferedReader( new InputStreamReader( System.in ) );
                String input = reader.readLine( );
                float numero = Float.parseFloat( input );
                valorResultado = numero;
            }
            catch( NumberFormatException nfe )
            {
                System.out.println( "El valor digitado no es un número" );
            }
            catch( IOException e )
            {
                System.out.println( "Error leyendo de la consola" );
            }
        }
        return valorResultado;
    }

    /**
     * Le pide al usuario que seleccione una de las opciones que se le presenten
     * @param coleccionOpciones
     * @return Retorna la opción seleccionada (el valor, no su posición).
     */
    protected String pedirOpcionAlUsuario( Collection<? extends Object> coleccionOpciones )
    {
        String[] opciones = new String[coleccionOpciones.size( )];
        int pos = 0;
        for( Iterator<? extends Object> iterator = coleccionOpciones.iterator( ); iterator.hasNext( ); pos++ )
        {
            opciones[ pos ] = iterator.next( ).toString( );
        }

        System.out.println( "Seleccione una de las siguientes opciones:" );
        for( int i = 1; i <= opciones.length; i++ )
        {
            System.out.println( " " + i + ". " + opciones[ i - 1 ] );
        }

        String opcion = pedirCadenaAlUsuario( "\nEscriba el número que corresponde a la opción deseada" );
        try
        {
            int opcionSeleccionada = Integer.parseInt( opcion );
            if( opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length )
                return opciones[ opcionSeleccionada - 1 ];
            else
            {
                System.out.println( "Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length );
                return pedirOpcionAlUsuario( coleccionOpciones );
            }
        }
        catch( NumberFormatException nfe )
        {
            System.out.println( "Esa no es una opción válida. Digite solamente números." );
            return pedirOpcionAlUsuario( coleccionOpciones );
        }
    }
    
    protected static void crearUsuario(String[] opciones) {
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Registro de usuarios" );
        System.out.println( "------------------------------------------------------" );
        
        for( int i = 1; i <= opciones.length; i++ )
        {
            System.out.println( " " + i + ". " + opciones[ i - 1 ] );
        }
        int opcionSeleccionada = pedirEnteroAlUsuario( "Escoja la opción deseada" );
        try
        {
            if( opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length ) {

            	switch (opcionSeleccionada) {
            		case 3:
            			System.out.println("Saliendo del programa...");
    	                System.exit(0);
    	                
            		case 1:
            			crearProfesor();
            			System.out.println("¡Profesor registrado exitosamente!");
            			return;
            		case 2:
            			crearEstudiante();
            			System.out.println("¡Estudiante registrado exitosamente!");
            			return;
            	}
            } else {
                System.out.println( "Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length );
                crearUsuario( opciones );
            }
        }
        catch( NumberFormatException nfe )
        {
            System.out.println( "Esa no es una opción válida. Digite solamente números." );
            crearUsuario( opciones );
        }
    }
    
    protected static void crearProfesor() {
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Registar Profesor" );
        System.out.println( "------------------------------------------------------" );
    	
        String login = pedirCadenaAlUsuario("Nombre de usuario");
        String password = pedirCadenaAlUsuario("Contraseña");
        
        try {
			sistemaRegistro.registrarProfesor(login, password);
			return;
		} catch (UsuarioExistenteException e) {
			System.out.println( "Este nombre de usuario ya es existe." );
            crearProfesor();
		}	
    }
    
    protected static void crearEstudiante() {
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Registar Estudiante" );
        System.out.println( "------------------------------------------------------" );
    	
        String login = pedirCadenaAlUsuario("Nombre de usuario");
        String password = pedirCadenaAlUsuario("Contraseña");
        List<String> intereses = pedirIntereses();
        
        try {
        	sistemaRegistro.registrarEstudiante(login, password, intereses);
			return;
		} catch (UsuarioExistenteException e) {
			System.out.println( "Este nombre de usuario ya es existe." );
            crearEstudiante();
		}	
    }
    
    protected static List<String> pedirIntereses(){
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Agregar intereses" );
        System.out.println( "------------------------------------------------------" );
        System.out.println("Ingrese los 3 principales intereses que tenga (orden no importa). Por ejemplo: derecho, programar y arte.");
        List<String> intereses = new ArrayList<String>();
        for (int i = 1; i < 4; i++) {
        	String interes = pedirCadenaAlUsuario("Ingrese un interés: ");
        	intereses.add(interes);
        }
        return intereses;
    }
    
    protected static void iniciarSesion() {
        System.out.println( "------------------------------------------------------" );
        System.out.println( "Iniciar sesión" );
        int tipoUsuario = mostrarMenu("Tipo de usuario", opcionesLogin);
        String login = pedirCadenaAlUsuario("Nombre de usuario");
        String password = pedirCadenaAlUsuario("Contraseña");
        boolean resultado;
        if (tipoUsuario == 1) {
        	resultado = sistemaRegistro.iniciarSesionProfesor(login, password);
        } else {
        	resultado = sistemaRegistro.iniciarSesionEstudiante(login,password);
        } 
        if (resultado) {
        	System.out.println( "------------------------------------------------------" );
        	System.out.println("Bienvenid@ "+login+"!");
        	if (tipoUsuario == 1) {
        		profesorActual = datosProfesor.get(login);
        		menuProfesor();
        	} else {
        		estudianteActual = datosEstudiante.get(login);
        		menuEstudiante();
        	}
        } else {
        	System.out.println("Credenciales incorrectos.");
        	iniciarSesion();
        } 
    }

    /**
     * Muestra un menú y le pide al usuario que seleccione una opción
     * @param nombreMenu El nombre del menu
     * @param opciones Las opciones que se le presentan al usuario
     * @return El número de la opción seleccionada por el usuario, contando desde 1
     */
    protected static int mostrarMenu( String nombreMenu, String[] opciones )
    {
        System.out.println( "------------------------------------------------------" );
        System.out.println( nombreMenu );
        System.out.println( "------------------------------------------------------" );

        for( int i = 1; i <= opciones.length; i++ )
        {
            System.out.println( " " + i + ". " + opciones[ i - 1 ] );
        }
        int opcionSeleccionada = pedirEnteroAlUsuario( "Escoja la opción deseada" );
        try
        {
            if( opcionSeleccionada > 0 && opcionSeleccionada <= opciones.length ) {
                return opcionSeleccionada;
            }
            else
            {
                System.out.println( "Esa no es una opción válida. Digite solamente números entre 1 y " + opciones.length );
                return mostrarMenu( nombreMenu, opciones );
            }
        }
        catch( NumberFormatException nfe )
        {
            System.out.println( "Esa no es una opción válida. Digite solamente números." );
            return mostrarMenu( nombreMenu, opciones );
        }
    }
    
    public static void menuProfesor() {
    	try {
			int opcionSeleccionada = mostrarMenu("Menu Principal Profesores", opcionesMenuProfesor);
			
			switch (opcionSeleccionada) {
			case 8:
				System.out.println( "------------------------------------------------------" );
				System.out.println("Saliendo del programa.");
				System.out.println( "------------------------------------------------------" );
                System.exit(0);
			case 1:
				menuCrearLearningPath();
                break;
			case 2:
				menuVerYEditarLearningPath();
				break;
			case 3:
				menuCrearActividad();
				break;
			case 4:
				menuClonarActividad();
				break;
			case 5:
				menuVerYEditarActividad();
				break;
			case 6:
				menuAgregarResenaORating();
				break;
			case 7: 
				verEstadisticas();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
    }
    
    public static void menuCrearLearningPath() throws UsuarioExistenteException {
		System.out.println( "------------------------------------------------------" );
		System.out.println("Crear Learning Path");
		System.out.println( "------------------------------------------------------" );
		String titulo = pedirCadenaAlUsuario("Ingrese el titulo");
		String objetivo = pedirCadenaAlUsuario("Ingrese el objetivo");
		String nivel = pedirCadenaAlUsuario("Ingrese el nivel");
		LearningPath learningPath = profesorActual.crearLearningPath(titulo, objetivo, nivel);
		sistemaRegistro.guardarLearningPath(learningPath);
		sistemaRegistro.guardarProfesor(profesorActual);
		System.out.println("El Learning Path se ha creado exitosamente, sin embargo, está vacio.");
		int cantidadActividades = pedirEnteroAlUsuario("Ingrese la cantidad de actividades que tendrá");
		if (cantidadActividades > 0) {
			int opcion = mostrarMenu("Agregar actividades. Recuerde que van en orden.", opcionesCrearLearningPath);
			switch(opcion) {
			case 1:
				List<String> idActividadesPropias = profesorActual.getIdActividadesCreadas();
				int cantidadActividadesProfesor = idActividadesPropias.size();
				if (cantidadActividadesProfesor > 0) {
					imprimirActividadesPropiasProfesor(idActividadesPropias);
					int i = 0;
					while (i < cantidadActividades) {
						String idActividadAgregar = pedirCadenaAlUsuario("Ingrese el id de la actividad candidata (se muestran las reseñas)");
						Actividad actividad = actividades.get(idActividadAgregar);
						verResenasActividad(actividad);
						int agregar = mostrarMenu("¿Desea agregar la actividad?", opcionesSiNo);
						if (agregar == 1) {
							learningPath.agregarActividad(actividad);
							sistemaRegistro.guardarLearningPath(learningPath);
							System.out.println("Actividad agregada exitosamente.");						
						}
						i++;
					}
				} else {
					System.out.println("Usted no cuenta con actividades propias. El Learning Path se dejará vacio, cuando cree actividades podrá editarlo y agregarselas.");
					System.out.println("Redirigiendo al menu de crear actividades...");
					menuCrearActividad();
				}
				break;
				
			case 2:
				System.out.println("El Learning Path se dejará vacio, cuando cree actividades podrá editarlo y agregarselas.");
				System.out.println("Redirigiendo al menu de crear actividades...");
				menuCrearActividad();
				break;
			}
		}
		menuProfesor();
    }
    
    public static void verResenasActividad(Actividad actividad) {
    	String titulo = actividad.getTitulo();
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Reseñas de la actividad: "+titulo);
    	for (String resena: actividad.getResenas()) {
    		System.out.println( "------------------------------------------------------" );
    		System.out.println(resena);
    	}
    }
    
    public static void verPrerequisitosActividad(Actividad actividad) {
    	String titulo = actividad.getTitulo();
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Prerequisitos de la actividad "+titulo);
    	System.out.println( "------------------------------------------------------" );
    	for (Actividad preReq: actividad.getPrerequisitos()) {
    		String id = preReq.getId();
    		String titulo1 = preReq.getTitulo();
    		System.out.println("ID: "+id);
    		System.out.println("Título: "+titulo1);
    		System.out.println( "------------------------------------------------------" );
    	}
    }
    
    public static void imprimirLearningPathsPropios(List<String> idLearningPathPropios) {
		System.out.println( "Lista de sus LearningPaths" );
		for (String idLearning: idLearningPathPropios) {
			System.out.println( "------------------------------------------------------" );
			LearningPath learningPath = learningPaths.get(idLearning);
			if (learningPath != null) {
				System.out.println(learningPath.verLearningPath());
			}
		}
    }
    
    public static void menuVerYEditarLearningPath() {
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Ver y Editar Learning Paths");
    	System.out.println( "------------------------------------------------------" );
    	List<String> idLearningPathsPropios = profesorActual.getIdLearningPathsCreados();
    	imprimirLearningPathsPropios(idLearningPathsPropios);
    	int opcion = mostrarMenu("¿Desea editar algún Learning Path?", opcionesSiNo);
    	if (opcion == 1) {
    		String id = pedirCadenaAlUsuario("ID del Learning Path");
    		LearningPath learningPathEditar = learningPaths.get(id);
    		System.out.println( "------------------------------------------------------" );
    		System.out.println("Editar Learning Path "+learningPathEditar.getTitulo());
    		int opcionSeleccionada = mostrarMenu("Seleccione que desea editar", opcionesEditarLearningPath);
    		switch (opcionSeleccionada) {
    		case 1:
    			System.out.println( "------------------------------------------------------" );
    			System.out.println("Editar título");
    			System.out.println( "------------------------------------------------------" );
    			String nuevoTitulo = pedirCadenaAlUsuario("Ingrese el nuevo titulo");
    			learningPathEditar.setTitulo(nuevoTitulo);
    			break;
    		case 2:
    			System.out.println( "------------------------------------------------------" );
    			System.out.println("Editar descripción");
    			System.out.println( "------------------------------------------------------" );
    			String nuevaDescripcion = pedirCadenaAlUsuario("Ingrese la nueva descripción");
    			learningPathEditar.setDescripcion(nuevaDescripcion);
    			break;
    		case 3:
    			System.out.println( "------------------------------------------------------" );
    			System.out.println("Editar nivel");
    			System.out.println( "------------------------------------------------------" );
    			String nuevoNivel = pedirCadenaAlUsuario("Ingrese el nuevo nivel");
    			learningPathEditar.setNivel(nuevoNivel);
    			break;
    		case 4:
    			// Agregar actividades existentes
    			List<String> idActividadesPropias = profesorActual.getIdActividadesCreadas();
    			int cantidadActividadesProfesor = idActividadesPropias.size();
    			if (cantidadActividadesProfesor > 0) {
    				int cantidadActividades = pedirEnteroAlUsuario("Ingrese la cantidad de actividades existentes a agregar");
    				// Imprimir actividades propias
    				imprimirActividadesPropiasProfesor(idActividadesPropias);
    				int i = 0;
    				while (i < cantidadActividades) {
    					System.out.println( "------------------------------------------------------" );
    					String idActividadAgregar = pedirCadenaAlUsuario("Ingrese el id de la actividad candidata (se muestran las reseñas)");
    					Actividad actividad = actividades.get(idActividadAgregar);
    					verResenasActividad(actividad);
    					int agregar = mostrarMenu("¿Desea agregar la actividad?", opcionesSiNo);
    					if (agregar == 1) {
    						learningPathEditar.agregarActividad(actividad);
    						System.out.println("Actividad agregada exitosamente.");						
    					}
    					i++;
    				}
    			} else {
    				System.out.println("Usted no cuenta con actividades propias.");
    			}
    			break;
    		}
    		System.out.println("Se edito correctamente");
    		sistemaRegistro.cargarProfesores(actividades, learningPaths);
    		sistemaRegistro.guardarLearningPath(learningPathEditar);
    		menuProfesor();
    	}
    	else {
    		menuProfesor();
    	}
    }
    
    public static void imprimirActividadesPropiasProfesor(List<String> idActividadesPropias) {
		System.out.println( "Lista de sus actividades" );
		for (String idActividad: idActividadesPropias) {
			System.out.println( "------------------------------------------------------" );
			Actividad actividad = actividades.get(idActividad);
			if (actividad != null) {
				System.out.println(actividad.verActividad());
			}
		}
    }
    
    public static void imprimirActividadesTotales() {
		for (Map.Entry<String, Actividad> entry : actividades.entrySet()) {
			System.out.println( "------------------------------------------------------" );
			Actividad actividad = entry.getValue();
            System.out.println(actividad.verActividad());
        }
    }
    
    public static void menuCrearActividad() {
		System.out.println( "------------------------------------------------------" );
		System.out.println("Crear actividad");
		try {
			int opcionSeleccionada = mostrarMenu("Seleccione el tipo de actividad a crear", opcionesTipoActividad);
			String titulo;
			String objetivo;
			String descripcion;
			String nivel;
			int duracionMinutos;
			String obligatorioString;
			boolean obligatorio;
			// Parametros que todas las actividades tienen:
			titulo = pedirCadenaAlUsuario("Ingrese el título");
			objetivo = pedirCadenaAlUsuario("Ingrese el objetivo");
			descripcion = pedirCadenaAlUsuario("Ingrese la descripción");
			nivel = pedirCadenaAlUsuario("Ingrese el nivel (bajo, intermedio o avanzado)");
			duracionMinutos = pedirEnteroAlUsuario("Ingrese el tiempo estimado en minutos");
			obligatorioString = pedirCadenaAlUsuario("¿Es obligatorio?");
			obligatorioString = obligatorioString.toLowerCase();
			// Por defecto no es
			obligatorio = false;
			if (obligatorioString.equals("si")) {
				obligatorio = true;
			}
			String tienePrerequisitosString = pedirCadenaAlUsuario("¿Tiene prerequisitos?");
			tienePrerequisitosString = tienePrerequisitosString.toLowerCase();
			// Por defecto no tiene
			ArrayList<Actividad> listaPrerequisitos = new ArrayList<>();;
			if (tienePrerequisitosString.equals("si")) {
				// Entro al metodo para agregar prerequisitos
				listaPrerequisitos = crearPrerequisitos();
			}
			String idActividad;
			
			switch (opcionSeleccionada) {
			case 1:
				// Crear encuesta
				Encuesta encuesta = profesorActual.crearActividadEncuesta(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
				encuesta.setPrerequisitos(listaPrerequisitos);
				// Atributos propios de encuesta
				// Agregamos preguntas
				int cantidadPreguntas = pedirEnteroAlUsuario("Cantidad de preguntas de la encuesta");
				int i = 0;
				while (i < cantidadPreguntas) {
					String enunciado = pedirCadenaAlUsuario("Enunciado pregunta");
					encuesta.agregarPregunta(enunciado);
					i++;
				}
				profesorActual.guardarActividad(encuesta);
				sistemaRegistro.guardarActividad(encuesta);
				sistemaRegistro.guardarProfesor(profesorActual);
				idActividad = encuesta.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				menuProfesor();
				break;
				
			case 2:
				// Crear examen
				Examen examen = profesorActual.crearActividadExamen(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
				examen.setPrerequisitos(listaPrerequisitos);
				// Atributos propios de encuesta
				// Agregamos preguntas
				int cantidadPreguntas1 = pedirEnteroAlUsuario("Cantidad de preguntas del examen");
				int i1 = 0;
				while (i1 < cantidadPreguntas1) {
					String enunciado = pedirCadenaAlUsuario("Enunciado pregunta");
					examen.agregarPregunta(enunciado);
					i1++;
				}
				profesorActual.guardarActividad(examen);
				sistemaRegistro.guardarActividad(examen);
				sistemaRegistro.guardarProfesor(profesorActual);
				idActividad = examen.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				menuProfesor();
				break;
				
			case 3:
				// Crear quiz opciones multiples
				// Atributos propios de quiz
				float calificacionMinima = pedirNumeroAlUsuario("Calificación mínima (0 a 100)");
				
				Quiz quiz = profesorActual.crearActividadQuiz(titulo, objetivo, descripcion,nivel ,duracionMinutos, obligatorio, calificacionMinima);
				quiz.setPrerequisitos(listaPrerequisitos);
				// Agregamos preguntas
				int cantidadPreguntas2 = pedirEnteroAlUsuario("Cantidad de preguntas");
				int i2 = 0;
				while (i2 < cantidadPreguntas2) {
					String enunciado = pedirCadenaAlUsuario("Enunciado pregunta");
					String opcionCorrecta = pedirCadenaAlUsuario("Opción correcta (A-B-C-D)");
					opcionCorrecta = opcionCorrecta.toLowerCase();
					Opcion opcionCorrectaEnum;
					if (opcionCorrecta.equals("a")) {
						opcionCorrectaEnum = Opcion.A;
					} else if (opcionCorrecta.equals("b")) {
						opcionCorrectaEnum = Opcion.B;
					} else if (opcionCorrecta.equals("c")) {
						opcionCorrectaEnum = Opcion.C;
					} else {
						// Si el usuario digito mal, siempre será la D de Dios la respuesta correcta.
						opcionCorrectaEnum = Opcion.D;
					}
					quiz.agregarPregunta(enunciado, opcionCorrectaEnum);
					// Opcion A
					String opcionA = pedirCadenaAlUsuario("Opción A");
					String explicacionA = pedirCadenaAlUsuario("Explicación de la opción A");
					quiz.agregarOpcion(enunciado, opcionA, Opcion.A, explicacionA);
					// Opcion A
					String opcionB = pedirCadenaAlUsuario("Opción B");
					String explicacionB = pedirCadenaAlUsuario("Explicación de la opción B");
					quiz.agregarOpcion(enunciado, opcionB, Opcion.B, explicacionB);
					// Opcion A
					String opcionC = pedirCadenaAlUsuario("Opción C");
					String explicacionC = pedirCadenaAlUsuario("Explicación de la opción C");
					quiz.agregarOpcion(enunciado, opcionC, Opcion.C, explicacionC);
					// Opcion A
					String opcionD = pedirCadenaAlUsuario("Opción D");
					String explicacionD = pedirCadenaAlUsuario("Explicación de la opción D");
					quiz.agregarOpcion(enunciado, opcionD, Opcion.D, explicacionD);
					i2++;
				}
				profesorActual.guardarActividad(quiz);
				sistemaRegistro.guardarActividad(quiz);
				sistemaRegistro.guardarProfesor(profesorActual);
				idActividad = quiz.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				menuProfesor();
				break;
				
			case 4:
				// Crear recurso
				// Atributos propios de recurso
				String contenidoRecurso = pedirCadenaAlUsuario("Ingrese el link del contenido del recurso");
				Recurso recurso = profesorActual.crearActividadRecurso(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, contenidoRecurso);
				recurso.setPrerequisitos(listaPrerequisitos);
				// Guardamos
				profesorActual.guardarActividad(recurso);
				sistemaRegistro.guardarActividad(recurso);
				sistemaRegistro.guardarProfesor(profesorActual);
				idActividad = recurso.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				menuProfesor();
				break;
				
			case 5:
				// Crear tarea
				// Atributos propios de tarea
				String idActividadTarea = pedirCadenaAlUsuario("Ingrese el id de la actividad de la tarea");
				Tarea tarea = profesorActual.crearActividadTarea(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, idActividadTarea);
				tarea.setPrerequisitos(listaPrerequisitos);
				// Guardamos
				profesorActual.guardarActividad(tarea);
				sistemaRegistro.guardarActividad(tarea);
				sistemaRegistro.guardarProfesor(profesorActual);
				idActividadTarea = tarea.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividadTarea);
				menuProfesor();
				break;
				
			case 6:
				// Crear quiz verdadero falso
				// Atributos propios de quiz
				float calificacionMinima1 = pedirNumeroAlUsuario("Calificación mínima (0 a 100)");
				QuizVerdad quizVerdadero = profesorActual.crearQuizVerdaderoFalso(titulo, objetivo, descripcion,nivel ,duracionMinutos, obligatorio, calificacionMinima1);
				quizVerdadero.setPrerequisitos(listaPrerequisitos);
				// Agregamos preguntas
				int cantidadPreguntas21 = pedirEnteroAlUsuario("Cantidad de preguntas");
				int i21 = 0;
				while (i21 < cantidadPreguntas21) {
					String enunciado = pedirCadenaAlUsuario("Enunciado pregunta");
					String opcionCorrecta = pedirCadenaAlUsuario("Opción correcta (V o F)");
					opcionCorrecta = opcionCorrecta.toLowerCase();
					VerdaderoFalso opcionCorrectaEnum;
					if (opcionCorrecta.equals("v")) {
						opcionCorrectaEnum = VerdaderoFalso.Verdadero;
					} else {
						opcionCorrectaEnum = VerdaderoFalso.Falso;
					}
					quizVerdadero.agregarPregunta(enunciado, opcionCorrectaEnum);
					i21++;
				}
				profesorActual.guardarActividad(quizVerdadero);
				sistemaRegistro.guardarActividad(quizVerdadero);
				sistemaRegistro.guardarProfesor(profesorActual);
				idActividad = quizVerdadero.getId();
				System.out.println("La actividad fue cargada exitosamente con el id "+idActividad);
				menuProfesor();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static ArrayList<Actividad> crearPrerequisitos(){
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Agregar Prerequisitos");
    	System.out.println( "------------------------------------------------------" );
    	imprimirActividadesTotales();
		ArrayList<Actividad> listaPrerequisitos = new ArrayList<>();
    	int cantidadPrerequisitos = pedirEnteroAlUsuario("Ingrese la cantidad de prerequisitos");
    	int i = 0;
    	while (i < cantidadPrerequisitos) {
	    	String codigoActividad = pedirCadenaAlUsuario("Ingrese el id de la actividad a agregar");
	    	Actividad actividadAgregar = actividades.get(codigoActividad);
	    	listaPrerequisitos.add(actividadAgregar);
	    	i++;
    	}
		return listaPrerequisitos;
    }
    
    public static void menuClonarActividad() {
		System.out.println( "------------------------------------------------------" );
		System.out.println("Clonar actividad");
		System.out.println( "------------------------------------------------------" );
		System.out.println("Lista actividades:");
		// Imprime todas las actividades
		imprimirActividadesTotales();
		try {
			int opcionSeleccionada = mostrarMenu("¿Desea clonar?", opcionesClonar);
			
			switch (opcionSeleccionada) {
			case 1:
				String idActividadAClonar = pedirCadenaAlUsuario("Ingrese ID");
				Actividad actividad = actividades.get(idActividadAClonar);
				Actividad actividadClonada = profesorActual.clonarActividad(actividad);
				sistemaRegistro.guardarActividad(actividad);
				sistemaRegistro.guardarActividad(actividadClonada);
				sistemaRegistro.guardarProfesor(profesorActual);
				System.out.println("La actividad fue clonada exitosamente.");
				menuProfesor();
				break;
			case 2:
				menuProfesor();
				break;
			}
		}
			catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void menuVerYEditarActividad() {
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Ver y Editar Actividades");
    	System.out.println( "------------------------------------------------------" );
    	List<String> idActividadesPropias = profesorActual.getIdActividadesCreadas();
    	imprimirActividadesPropiasProfesor(idActividadesPropias);
    	int opcion = mostrarMenu("Opciones para las actividades mostradas", opcionesVerYEditarActividades);
    	if (opcion != 8) {
    		String id = pedirCadenaAlUsuario("ID de la actividad");
	    	Actividad actividadEditar = actividades.get(id);
	    	switch (opcion) {
	    	case 1: 
	    		// Ver prerequisitos
	    		verPrerequisitosActividad(actividadEditar);
	    		break;
	    	case 2:
	    		// Ver reseñas
	    		verResenasActividad(actividadEditar);
	    	case 3:
	    		System.out.println( "------------------------------------------------------" );
    			System.out.println("Editar titulo");
    			System.out.println( "------------------------------------------------------" );
    			String nuevoTitulo = pedirCadenaAlUsuario("Ingrese el nuevo titulo");
    			actividadEditar.setTitulo(nuevoTitulo);
    			break;
	    	case 4:
	    		System.out.println( "------------------------------------------------------" );
    			System.out.println("Editar objetivo");
    			System.out.println( "------------------------------------------------------" );
    			String nuevoObjetivo = pedirCadenaAlUsuario("Ingrese el nuevo objetivo");
    			actividadEditar.setObjetivo(nuevoObjetivo);
    			break;
	    	case 5:
	    		System.out.println( "------------------------------------------------------" );
    			System.out.println("Editar descripción");
    			System.out.println( "------------------------------------------------------" );
    			String nuevaDescripcion = pedirCadenaAlUsuario("Ingrese la nueva descripción");
    			actividadEditar.setDescripcion(nuevaDescripcion);
    			break;
	    	case 6:
	    		System.out.println( "------------------------------------------------------" );
    			System.out.println("Editar nivel");
    			System.out.println( "------------------------------------------------------" );
    			String nuevoNivel = pedirCadenaAlUsuario("Ingrese el nuevo nivel");
    			actividadEditar.setNivel(nuevoNivel);
    			break;
	    	case 7:
	    		int obligatorio = mostrarMenu("¿Es obligatorio?", opcionesSiNo);
	    		if (obligatorio == 1) {
	    			actividadEditar.setObligatorio(true);
	    		} else {
	    			actividadEditar.setObligatorio(false);
	    		}
    			break;
	    	}
    		sistemaRegistro.cargarProfesores(actividades, learningPaths);
    		sistemaRegistro.guardarActividad(actividadEditar);
	    	menuProfesor();
    	} else {
    		menuProfesor();
    	}
    }
    
    public static void menuAgregarResenaORating() {
    	int opcion = mostrarMenu("Agregar reseña o rating a una actividad", opcionResenaRating);
    	if (opcion == 1) {
    		System.out.println( "------------------------------------------------------" );
	    	System.out.println("Agregar reseña a una actividad");
	    	System.out.println( "------------------------------------------------------" );
	    	System.out.println( "Lista actividades:" );
	    	imprimirActividadesTotales();
	    	System.out.println( "------------------------------------------------------" );
	    	String idActividad = pedirCadenaAlUsuario("Ingrese el id de la actividad a dejar la reseña");
	    	Actividad actividad = actividades.get(idActividad);
	    	String resena = pedirCadenaAlUsuario("Ingrese la reseña");
	    	actividad.agregarResena(resena);
	    	System.out.println("Reseña agregada exitosamente.");
	    	sistemaRegistro.guardarActividad(actividad);
    	} else {
    		System.out.println( "------------------------------------------------------" );
	    	System.out.println("Agregar rating a una actividad");
	    	System.out.println( "------------------------------------------------------" );
	    	System.out.println( "Lista actividades:" );
	    	imprimirActividadesTotales();
	    	System.out.println( "------------------------------------------------------" );
	    	String idActividad = pedirCadenaAlUsuario("Ingrese el id de la actividad a dejar la reseña");
	    	Actividad actividad = actividades.get(idActividad);
	    	float rating = pedirNumeroAlUsuario("Ingrese el rating (0 a 5 exclusivamente)");
	    	try {
				actividad.agregarRating(rating);
				System.out.println("Reseña agregada exitosamente.");
				sistemaRegistro.guardarActividad(actividad);
			} catch (Exception e) {
				System.out.println("El número que ingreso no está dentro del rango permitido.");
				e.printStackTrace();
			}
    	}
    	if (profesorActual == null) {
    		menuEstudiante();
    	} else {
    		menuProfesor();    		
    	}
    }
    
    public static void verEstadisticas() {
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Bienvenido profesor al menú de estadísticas");
    	int opcion = mostrarMenu("Seleccione que desea visualizar", opcionesVerEstadisticas);
    	switch (opcion) {
    	case 1:
    		// Por estudiante
    		// Imprimimos lista estudiantes
    		Collection<Estudiante> estudiantes = datosEstudiante.values();
    		System.out.println( "------------------------------------------------------" );
    		System.out.println( "Lista de todos los estudiantes" );
    		System.out.println( "------------------------------------------------------" );
    		for (Estudiante estudiante: estudiantes) {
    			String loginEstudiante = estudiante.getLogin();
    			System.out.println("Nombre de usuario: "+loginEstudiante);
    		}
    		System.out.println( "------------------------------------------------------" );
    		String idEstudiante = pedirCadenaAlUsuario("Ingrese el login del estudiante a observar");
    		Estudiante estudiante = datosEstudiante.get(idEstudiante);
    		int opcionEstadistica = mostrarMenu("Seleccione lo que desea visualizar", opcionesProgresoLearningPath);
    		String tituloLearningPathEnCurso = estudiante.getLearningPathEnCurso().getTitulo();
    		List<Double> listaProgresos = estudiante.verProgresoLearningPath();
        	double porcentajeCompletadas = listaProgresos.get(0);
        	double porcentajeExitosas = listaProgresos.get(1);
        	System.out.println( "------------------------------------------------------" );
        	System.out.println( "Learning path de "+idEstudiante+" es "+tituloLearningPathEnCurso );
    		switch (opcionEstadistica) {
    		case 1:
    			System.out.println( "------------------------------------------------------" );
        		System.out.println( "Porcentaje de actividades completadas y/o exitosas: "+porcentajeCompletadas+"%" );
        		System.out.println( "------------------------------------------------------" );    		
        		break;
        	case 2:
        		System.out.println( "------------------------------------------------------" );
        		System.out.println( "Porcentaje de actividades exitosas: "+porcentajeExitosas+"%" );
        		System.out.println( "------------------------------------------------------" );    		
        		break;
        	case 3:
        		menuProfesor();
        		break;
    		}
    		break;
    		
    	case 2:
    		// Estadisticas por Learning Path
    		// Imprimir todos los Learning Paths
    		System.out.println( "------------------------------------------------------" );
    		System.out.println( "Lista de todos los Learning Paths" );
    		verLearningPathsTotales();
    		System.out.println( "------------------------------------------------------" );
    		String idLearning = pedirCadenaAlUsuario("Ingrese el id del Learning Path a observar");
    		LearningPath learningPath = learningPaths.get(idLearning);
    		int opcionEstadistica1 = mostrarMenu("Seleccione lo que desea visualizar", opcionesProgresoLearningPath);
    		String tituloLearningPath = learningPath.getTitulo();
    		List<Double> listaProgresos1 = learningPath.getProgreso(datosEstudiante);
        	double porcentajeCompletadas1 = listaProgresos1.get(0);
        	double porcentajeExitosas1 = listaProgresos1.get(1);
        	System.out.println( "------------------------------------------------------" );
        	System.out.println( "Learning path "+tituloLearningPath );
    		switch (opcionEstadistica1) {
    		case 1:
    			System.out.println( "------------------------------------------------------" );
        		System.out.println( "Porcentaje de actividades completadas y/o exitosas: "+porcentajeCompletadas1+"%" );
        		System.out.println( "------------------------------------------------------" );    		
        		break;
        	case 2:
        		System.out.println( "------------------------------------------------------" );
        		System.out.println( "Porcentaje de actividades exitosas: "+porcentajeExitosas1+"%" );
        		System.out.println( "------------------------------------------------------" );   
        		break;
        	case 3:
        		menuProfesor();
        		break;
    		}
    		break;
    	case 3:
    		menuProfesor();
    		break;
    	}
    	menuProfesor();
    }
    
    public static void menuEstudiante() {
    	try {
			int opcionSeleccionada = mostrarMenu("Menu Principal Estudiantes", opcionesMenuEstudiantes);
			
			switch (opcionSeleccionada) {
			case 7:
				System.out.println( "------------------------------------------------------" );
				System.out.println("Saliendo del programa.");
				System.out.println( "------------------------------------------------------" );
                System.exit(0);
			case 1:
				verOfertaLearningPaths();
                break;
			case 2:
				iniciarActividadLearningPathActual();
				break;
			case 3:
				// Terminar actividad en curso es lo mismo que iniciarla. No se pueden dejar actividades a medias.
				Actividad actividadEnCurso = estudianteActual.getActividadEnCurso();
				if (actividadEnCurso != null) {
					iniciarActividadEnCurso(actividadEnCurso);
				} else {
					System.out.println("No tiene una actividad en curso.");
					menuEstudiante();
				}
				break;
			case 4:
				menuAgregarResenaORating();
				break;
			case 5:
				verProgresoLearningPath();
			case 6:
				salirLearningPathOActividad();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
    }
    
    public static void salirLearningPathOActividad() {
    	int opcion = mostrarMenu("Salir del Learning Path o Actividad actual", opcionSalirLearningOActividad);
    	switch (opcion) {
    	case 1:
    		if (estudianteActual.getLearningPathEnCurso()==null) {
    			System.out.println("No se encuentra en un Learning Path. No se puede salir de algo que no está.");
    		} else {
    		estudianteActual.finalizarLearningPath();
    		sistemaRegistro.guardarEstudiante(estudianteActual);
    		System.out.println("Se salió del Learning Path actual exitosamente.");
    		}
    		menuEstudiante();
    		break;
    	case 2:
    		if (estudianteActual.getActividadEnCurso()==null) {
    			System.out.println("No tiene actividad en curso. No se puede salir de algo que no está.");
    		} else {
    		estudianteActual.finalizarActividad();
    		sistemaRegistro.guardarEstudiante(estudianteActual);
    		System.out.println("Se salió de la actividad actual exitosamente.");
    		}
    		menuEstudiante();
    		break;
    	case 3:
    		menuEstudiante();
    		break;
    	}
    }
    
    public static void verProgresoLearningPath() {
    	List<Double> listaProgresos = estudianteActual.verProgresoLearningPath();
    	double porcentajeCompletadas = listaProgresos.get(0);
    	double porcentajeExitosas = listaProgresos.get(1);
    	int opcion = mostrarMenu("Estadísticas de su progreso en el Learning Path en curso", opcionesProgresoLearningPath);
    	switch (opcion) {
    	case 1:
    		System.out.println( "------------------------------------------------------" );
    		System.out.println( "Porcentaje de actividades completadas y/o exitosas: "+porcentajeCompletadas+"%" );   		
    		break;
    	case 2:
    		System.out.println( "------------------------------------------------------" );
    		System.out.println( "Porcentaje de actividades exitosas: "+porcentajeExitosas+"%" );   		
    		break;
    	case 3:
    		menuEstudiante();
    	}
    	menuEstudiante();
    }
    
    public static void verOfertaLearningPaths() throws Exception {
    	int opcion = mostrarMenu("Ver", opcionesOfertaLearningPaths);
    	switch (opcion) {
    	case 1:
    		System.out.println( "------------------------------------------------------" );
    		System.out.println( "Lista de todos los Learning Paths disponibles:" );		
    		verLearningPathsTotales();
    		inscribirseLearningPath();
    		int arrancarActividad = mostrarMenu("¿Desea empezar alguna actividad del Learning Path?", opcionesSiNo);
    		if (arrancarActividad==1) {
    			iniciarActividadLearningPathActual();
    		}
    		menuEstudiante();
    		break;
    	case 2:
    		System.out.println( "------------------------------------------------------" );
    		System.out.println( "Lista de los Learning Paths organizados para usted:" );
    		System.out.println( "------------------------------------------------------" ); 
    		verLearningPathsIntereses();
    		inscribirseLearningPath();
    		int arrancarActividad1 = mostrarMenu("¿Desea empezar alguna actividad del Learning Path?", opcionesSiNo);
    		if (arrancarActividad1==1) {
    			iniciarActividadLearningPathActual();
    		}
    		menuEstudiante();
    		break;
    	case 3:
    		menuEstudiante();
    		break;
    	}
    }
    
    public static void verLearningPathsTotales() {
    	for (LearningPath learningPath : learningPaths.values()) {
    		System.out.println( "------------------------------------------------------" );
    		System.out.println(learningPath.verLearningPath());
        }
    }
    
    public static void verLearningPathsIntereses() {
    	List<LearningPath> listaOrganizadaIntereses = estudianteActual.recomendarLearningPaths(learningPaths);
        System.out.println(String.format("%-10s | %-20s | %-30s | %-10s | %-20s | %-20s | %-10s | %-10s | %-10s", 
                "ID", "Título", "Descripción", "Nivel", "Fecha Creación", "Fecha Modificación", "Duración", "Rating", "Versión"));
        System.out.println("=".repeat(160));
        // Iterar y formatear cada LearningPath
        for (int i = 0; i < listaOrganizadaIntereses.size(); i++) {
            LearningPath learningPath = listaOrganizadaIntereses.get(i);
            System.out.println(String.format("%-10s | %-20s | %-30s | %-10s | %-20s | %-20s | %-10s | %-10s | %-10s", 
			learningPath.getId(), 
			learningPath.getTitulo(), 
			learningPath.getDescripcion(), 
			learningPath.getNivel(), 
			learningPath.getFechaCreacion(), 
			learningPath.getFechaModificacion(), 
			learningPath.getDuracion(), 
			learningPath.getRating(), 
			learningPath.getVersion()));
            System.out.println( "------------------------------------------------------" );
        }
    }
    
    public static void verLearningPathEspecifico(LearningPath learningPath) {
    	System.out.println("ID: "+learningPath.getId());
    	System.out.println("Título: "+learningPath.getTitulo());
    	System.out.println("Descripción: "+learningPath.getDescripcion());
    	System.out.println("Nivel: "+learningPath.getNivel());
    	System.out.println("Fecha creación: "+learningPath.getFechaCreacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    	System.out.println("Fecha modificación: "+learningPath.getFechaModificacion().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    	System.out.println("Cantidad de actividades: "+learningPath.getListaActividades().size());
    	System.out.println("Duración: "+learningPath.getDuracion());
    	System.out.println("Rating: "+learningPath.getRating());
    	System.out.println("Versión: "+learningPath.getVersion());
    	int opcion = mostrarMenu("¿Desea ver la estructura de actividades?", opcionesSiNo);
    	if (opcion == 1) {
        	System.out.println( "------------------------------------------------------" );
    		System.out.println( "Lista de las actividades del Learning Path" );
    		for (Actividad actividad : learningPath.getListaActividades()) {
    			System.out.println( "------------------------------------------------" );
    			System.out.println("ID: "+actividad.getId());
    	    	System.out.println("Título: "+actividad.getTitulo());
    	    	System.out.println("Tipo actividad: "+actividad.getTipoActividad());
    	    	System.out.println("Objetivo: "+actividad.getObjetivo());
    	    	System.out.println("Descripción: "+actividad.getDescripcion());
    	    	System.out.println("Nivel: "+actividad.getNivel());
    	    	System.out.println("Duración en minutos: "+actividad.getDuracionMinutos());
    	    	System.out.println("Obligatorio: "+actividad.isObligatorio());
    	    	System.out.println("Rating: "+actividad.getRating());
    	    	System.out.println("Cantidad prerequisitos: "+actividad.getPrerequisitos().size());
            }
    	} else {
    		return;
    	}
    }
    
    public static void inscribirseLearningPath() {
    	System.out.println( "------------------------------------------------------" );    		    		
		String idLearning = pedirCadenaAlUsuario( "Ingrese el id del Learning Path que quiere ver detalladamente" );
		LearningPath learningPath = learningPaths.get(idLearning);
		System.out.println( "------------------------------------------------------" );    		    		
		verLearningPathEspecifico(learningPath);
		int inscribirse = mostrarMenu("¿Desea inscribirse en el Learning Path "+learningPath.getTitulo()+"?", opcionesSiNo);
		if (inscribirse == 1) {
			// Verificar que no tenga un LearningPath actual
			if (estudianteActual.getLearningPathEnCurso() != null) {
				System.out.println("Usted ya está en un Learning Path en este momento. Solo puede tener uno a la vez.");
			} else {
				estudianteActual.setLearningPathEnCurso(learningPath);
				estudianteActual.actualizarRegistroLearningPathActual();
				sistemaRegistro.guardarEstudiante(estudianteActual);
				System.out.println("Ha sido inscrito en el Learning Path "+learningPath.getTitulo()+" exitosamente.");
				return;
			}
			
		} else {
			int volverEmpezarOSalir = mostrarMenu("¿Desea inscribirse a otro? Si no, vuelve al menú principal.", opcionesSiNo);
			if (volverEmpezarOSalir == 1) {
				// Vuelve recursivamente a esta función
				inscribirseLearningPath();
			} else {
				menuEstudiante();
			}
		}
    }
    
    public static void iniciarActividadLearningPathActual() throws Exception {
    	// Se revisa primero que no tenga una actividad en curso
    	if (estudianteActual.getLearningPathEnCurso()!=null) {
    		if (estudianteActual.getActividadEnCurso()!=null) {
    		System.out.println("Ya tiene una actividad en curso.");
    		System.out.println("Debe terminar primero la actividad para empezar una nueva.");
    		
    	} else {
    		String nombreLearning = estudianteActual.getLearningPathEnCurso().getTitulo();
	    	System.out.println( "------------------------------------------------------" );
			System.out.println( "Actividades disponibles en orden del Learning Path: "+nombreLearning );
			System.out.println( "------------------------------------------------------" );  
			LearningPath learningPath = estudianteActual.getLearningPathEnCurso();
			for (Actividad actividad : learningPath.getListaActividades()) {
				System.out.println("ID: "+actividad.getId());
		    	System.out.println("Título: "+actividad.getTitulo());
		    	System.out.println("Tipo actividad: "+actividad.getTipoActividad());
		    	System.out.println("Objetivo: "+actividad.getObjetivo());
		    	System.out.println("Descripción: "+actividad.getDescripcion());
		    	System.out.println("Nivel: "+actividad.getNivel());
		    	System.out.println("Duración en minutos: "+actividad.getDuracionMinutos());
		    	System.out.println("Obligatorio: "+actividad.isObligatorio());
		    	System.out.println("Rating: "+actividad.getRating());
		    	System.out.println("Cantidad prerequisitos: "+actividad.getPrerequisitos().size());
		    	System.out.println( "------------------------------------------------------" );
	        }
			System.out.println("Se recomienda que las ejecute en orden. Sin embargo, no es obligatorio hacerlo.");
			String idActividad = pedirCadenaAlUsuario("Ingrese el id de la actividad a ejecutar");
			Actividad actividad = actividades.get(idActividad);
			if (actividades.containsKey(idActividad)) {
				verPrerequisitosActividad(actividad);
				boolean apto = actividad.advertenciaPrerequisitos(estudianteActual);
				if (apto) {
					System.out.println("Usted comple con todos los prerequisitos, felicitaciones.");
				} else {
					System.out.println("A pesar que no cumple con los prerequisitos, puede continuar bajo su propia responsabilidad.");
				}
				// Iniciar actividad. Se llama iniciar actividad en curso ya que oficialmente esta en curso
				iniciarActividadEnCurso(actividad);
			} else {
				System.out.println("No existe actividad con ese ID.");
			}
    	}
    	menuEstudiante();
    	} else {
    		System.out.println("No está activo en ningún Learning Path.");
    		menuEstudiante();
    	}
    }
    
    public static void iniciarActividadEnCurso(Actividad actividad) throws Exception {
    	// Dependiendo del tipo de actividad empieza un metodo distinto
    	TipoActividades tipoActividad = actividad.getTipoActividad();
    	// Calculamos fecha limite
    	actividad.establecerFechaLimite(estudianteActual);
    	estudianteActual.setActividadEnCurso(actividad);
    	sistemaRegistro.guardarEstudiante(estudianteActual);
    	if (tipoActividad == TipoActividades.Encuesta) {
    		// Inciar actividad encuesta
    		Encuesta encuesta = (Encuesta) actividad;
    		realizarEncuesta(encuesta);
  
    	} else if (tipoActividad == TipoActividades.Examen) {
    		// Iniciar actividad examen
    		Examen examen = (Examen) actividad;
    		realizarExamen(examen);
    		
    	} else if (tipoActividad == TipoActividades.Quiz) {
    		// Iniciar actividad quiz opciones multiples
    		Quiz quiz = (Quiz) actividad;
    		realizarQuiz(quiz);
    		
    	} else if (tipoActividad == TipoActividades.Recurso) {
    		// Iniciar actividad recurso
    		Recurso recurso = (Recurso) actividad;
    		realizarRecurso(recurso);

    	} else if (tipoActividad == TipoActividades.Tarea){
    		// Iniciar actividad tarea
    		Tarea tarea = (Tarea) actividad;
    		realizarTarea(tarea);
    	} else if (tipoActividad == TipoActividades.QuizVerdad) {
    		// Iniciar actividad quiz verdadero o falso
    		QuizVerdad quiz = (QuizVerdad) actividad;
    		realizarQuizVerdad(quiz);
    	}
    	System.out.println("Recuerde que puede dejar una reseña o rating de la actividad si desea!");
    	// Recomendar actividad con respecto al estado
    	if (estudianteActual.recomendarActividad(actividad, estudianteActual.getLearningPathEnCurso()) != null) {
    		Actividad actividadRecomendada = estudianteActual.recomendarActividad(actividad, estudianteActual.getLearningPathEnCurso());
    		System.out.println( "------------------------------------------------------" );
    		System.out.println("Se recomienda que continue con la actividad "+actividadRecomendada.getTitulo());
    	} else {
    		System.out.println("No hay actividades anteriores para sugerir.");
    	}
    	// Marcamos actividad actual como vacia.
    	estudianteActual.actualizarRegistroLearningPathActual();
    	estudianteActual.setActividadEnCurso(null);
    	sistemaRegistro.guardarActividad(actividad);
    	menuEstudiante();
    }
    
    public static void realizarEncuesta(Encuesta encuesta) {
    	System.out.println( "------------------------------------------------------" );
    	System.out.println(encuesta.verActividad());
    	int cantidadPreguntas = encuesta.getPreguntas().size();
    	System.out.println("Cantidad de preguntas: "+cantidadPreguntas);
    	System.out.println( "------------------------------------------------------" );
    	// Revisar fechaLimite
    	System.out.println("Preguntas: ");
    	System.out.println(encuesta.verPreguntas());
    	// Realizar
    	int i = 1;
    	ArrayList<String> respuestas = new ArrayList<>();
    	while (i <= cantidadPreguntas) {
    		String respuesta = pedirCadenaAlUsuario("Respuesta a la pregunta #"+i);
    		respuestas.add(respuesta);
    		i++;
    	}
    	// Manda las respuestas...
    	estudianteActual.realizarEncuesta(encuesta,respuestas);
    	sistemaRegistro.guardarEstudiante(estudianteActual);
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Se ha marcado "+encuesta.getTitulo()+" como exitosa.");
    	System.out.println( "------------------------------------------------------" );
    }
    
    public static void realizarExamen(Examen examen) {
    	System.out.println( "------------------------------------------------------" );
    	System.out.println(examen.verActividad());
    	int cantidadPreguntas = examen.getPreguntas().size();
    	System.out.println("Cantidad de preguntas: "+cantidadPreguntas);
    	System.out.println( "------------------------------------------------------" );
    	// Revisar fechaLimite
    	System.out.println("Preguntas: ");
    	System.out.println(examen.verPreguntas());
    	// Realizar
    	int i = 1;
    	ArrayList<String> respuestas = new ArrayList<>();
    	while (i <= cantidadPreguntas) {
    		String respuesta = pedirCadenaAlUsuario("Respuesta a la pregunta #"+i);
    		respuestas.add(respuesta);
    		i++;
    	}
    	// Manda las respuestas...
    	estudianteActual.realizarExamen(examen,respuestas);
    	sistemaRegistro.guardarEstudiante(estudianteActual);
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Se ha marcado la actividad "+examen.getTitulo()+" enviada.");
    	System.out.println("Este pendiente a la calificación del profesor.");
    	System.out.println( "------------------------------------------------------" );
    }
    
    public static void realizarQuiz(Quiz quiz) throws Exception {
    	System.out.println( "------------------------------------------------------" );
    	System.out.println(quiz.verActividad());
    	int cantidadPreguntas = quiz.getPreguntas().size();
    	System.out.println("Cantidad de preguntas: "+cantidadPreguntas);
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Preguntas");
    	System.out.println("---------");
    	System.out.println(quiz.verPreguntas());
    	// Realizar
    	System.out.println( "------------------------------------------------------" );
    	System.out.println( "Respuestas:" );
    	int i = 1;
    	ArrayList<Opcion> respuestas = new ArrayList<>();
    	while (i <= cantidadPreguntas) {
    		String respuesta = pedirCadenaAlUsuario("Respuesta (A-B-C-D) a la pregunta #"+i);
    		respuesta = respuesta.toLowerCase();
			Opcion respuestaEnum;
			if (respuesta.equals("a")) {
				respuestaEnum = Opcion.A;
			} else if (respuesta.equals("b")) {
				respuestaEnum = Opcion.B;
			} else if (respuesta.equals("c")) {
				respuestaEnum = Opcion.C;
			} else {
				// Si el usuario digito mal, siempre será la D de Dios la respuesta correcta.
				respuestaEnum = Opcion.D;
			}
    		respuestas.add(respuestaEnum);
    		i++;
    	}
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Ahora, se va a calificar... ");
    	System.out.println( "------------------------------------------------------" );
    	// Manda las respuestas
    	Estado resultado = estudianteActual.realizarQuiz(quiz,respuestas);
    	sistemaRegistro.guardarEstudiante(estudianteActual);
    	// Vuelven calificadas
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("El quiz "+quiz.getTitulo()+" ha sido "+resultado);
    	System.out.println( "------------------------------------------------------" );
    }
    
    public static void realizarQuizVerdad(QuizVerdad quiz) throws Exception {
    	System.out.println( "------------------------------------------------------" );
    	System.out.println(quiz.verActividad());
    	int cantidadPreguntas = quiz.getPreguntas().size();
    	System.out.println("Cantidad de preguntas: "+cantidadPreguntas);
    	System.out.println( "------------------------------------------------------" );
    	System.out.println(quiz.verPreguntas());
    	// Realizar
    	System.out.println( "------------------------------------------------------" );
    	System.out.println( "Respuestas:\n" );
    	int i = 1;
    	ArrayList<VerdaderoFalso> respuestas = new ArrayList<>();
    	while (i <= cantidadPreguntas) {
    		String respuesta = pedirCadenaAlUsuario("(V o F) a la pregunta #"+i);
    		respuesta = respuesta.toLowerCase();
    		VerdaderoFalso respuestaEnum;
			if (respuesta.equals("v")) {
				respuestaEnum = VerdaderoFalso.Verdadero;
			} else {
				respuestaEnum = VerdaderoFalso.Falso;
			}
    		respuestas.add(respuestaEnum);
    		i++;
    	}
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Ahora, se va a calificar... ");
    	System.out.println( "------------------------------------------------------" );
    	// Manda las respuestas
    	System.out.println(respuestas);
    	System.out.println(quiz.getRespuestasCorrectas());
    	Estado resultado = estudianteActual.realizarQuizVerdad(quiz,respuestas);
    	sistemaRegistro.guardarEstudiante(estudianteActual);
    	// Vuelven calificadas
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("El quiz "+quiz.getTitulo()+" ha sido "+resultado);
    	System.out.println( "------------------------------------------------------" );
    }
    
    public static void realizarRecurso(Recurso recurso) {
    	System.out.println( "------------------------------------------------------" );
    	System.out.println(recurso.verActividad());
    	System.out.println( "------------------------------------------------------" );
    	// Revisar fechaLimite
    	// Imprimir el material
    	System.out.println("Material");
    	System.out.println( "------------------------------------------------------" );
    	System.out.println(recurso.getMaterial());
    	// Realizar
    	estudianteActual.realizarRecurso(recurso);
    	sistemaRegistro.guardarEstudiante(estudianteActual);
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Se ha marcado la actividad "+recurso.getTitulo()+" enviada.");
    	System.out.println("Este pendiente a la calificación del profesor.");
    	System.out.println( "------------------------------------------------------" );
    }
    
    public static void realizarTarea(Tarea tarea) {
    	System.out.println( "------------------------------------------------------" );
    	System.out.println(tarea.verActividad());
    	System.out.println( "------------------------------------------------------" );
    	// Imprimir el idActividadTarea
    	System.out.println("ID de la actividad a realizar en la tarea:");
    	System.out.println(tarea.getIdActividadesARealizar());
    	System.out.println( "------------------------------------------------------" );
    	String medioEntrega = pedirCadenaAlUsuario("Ingrese el medio de entrega");
    	// Realizar
    	// Tiene que mandarle el medio de entrega
    	estudianteActual.realizarTarea(tarea, medioEntrega);
    	sistemaRegistro.guardarEstudiante(estudianteActual);
    	System.out.println( "------------------------------------------------------" );
    	System.out.println("Se ha marcado la actividad "+tarea.getTitulo()+" enviada.");
    	System.out.println( "------------------------------------------------------" );
    }
    	
	public static void main(String[] args)  {		
		// Tengo que cargar todos los datos. Se hace desde los parametros statics.
		String tituloConsola = "Bienvenido al Sistema Operativo de LearningPaths G11!";
		int opcionSeleccionada = 0;
		
		while (true) {
		try {
			opcionSeleccionada = mostrarMenu(tituloConsola, opciones);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		switch (opcionSeleccionada) {
			case 3:
				System.out.println( "------------------------------------------------------" );
				System.out.println("Saliendo del programa.");
				System.out.println( "------------------------------------------------------" );
                System.exit(0);
			case 1:
				iniciarSesion();
				break;
			case 2:
				crearUsuario(opcionesRegistro);
			}
		}
	}
}