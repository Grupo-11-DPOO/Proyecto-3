package usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import actividades.Actividad;
import actividades.Encuesta;
import actividades.Estado;
import actividades.Examen;
import actividades.Opcion;
import actividades.Quiz;
import actividades.QuizVerdad;
import actividades.Recurso;
import actividades.Tarea;
import actividades.VerdaderoFalso;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;


public class Estudiante extends Usuario {
	private Actividad actividadEnCurso;
	private LearningPath learningPathEnCurso; //Lista con los learningPaths en curso, donde se almacenan sus id
	private HashMap<String, Estado> registroActividades; //llave idActividades, valor Estado
	private HashMap<String, Double> registroLearningPaths; //llave idLearningPaths, valor double del progeso 0 a 1
	private List<String> intereses;
	
	public Estudiante(HashMap<String, Actividad> actividades, HashMap<String,LearningPath> learningPaths, String nLogin, String nPassword, List<String> intereses) {
		super(nLogin, nPassword, actividades, learningPaths);
		this.intereses = intereses;
		this.actividadEnCurso = null;
		this.learningPathEnCurso = null;
		this.registroActividades = new HashMap<String, Estado>() ;
		this.registroLearningPaths = new HashMap<String, Double>(); 
	}
	
	public LearningPath buscarLearningPathPorNombre(String nombreLearningPath) {
		
		if(!getDataLearningPaths().isEmpty()) {
			Iterator<LearningPath> iteradorClaves = getDataLearningPaths().values().iterator();
			 while (iteradorClaves.hasNext()) {
				 	LearningPath learningPath = iteradorClaves.next();
			        if (learningPath.getTitulo().equalsIgnoreCase(nombreLearningPath)) {
			            return learningPath;
			        }
			    }
			}
		return null;
	}
	
	public LearningPath getLearningPathById(String id) {
		boolean x= getDataLearningPaths().containsKey(id);
		if (x) {
			return getDataLearningPaths().get(id);
		}
		else return null;
		}
	
	public Actividad buscarActividadPorNombre(String nombreActividad) {
		if(!getDataActividades().isEmpty()) {
			Iterator<Actividad> iteradorClaves = getDataActividades().values().iterator();
			 while (iteradorClaves.hasNext()) {
				 	Actividad actividad = iteradorClaves.next();
			        if (actividad.getTitulo().equalsIgnoreCase(nombreActividad)) {
			            return actividad;
			        }
			    }
			}
		return null;
	}
	
	public Actividad getActividadById(String id) {
		boolean x= getDataActividades().containsKey(id);
		if (x) {
			return getDataActividades().get(id);
		}
		else return null;
		}
	
	public boolean empezarLearningPath(LearningPath learningPath) {
		if(learningPathEnCurso == null) {
			learningPathEnCurso = learningPath;
			return true;
		}
			else {
				return false;
			}
	}
	
	public void finalizarLearningPath() {
		this.learningPathEnCurso = null;
	}

	public Actividad getActividadEnCurso() {
		return actividadEnCurso;
	}

	public void setActividadEnCurso(Actividad actividadEnCurso) {
		this.actividadEnCurso = actividadEnCurso;
	}

	public LearningPath getLearningPathEnCurso() {
		return learningPathEnCurso;
	}

	public void setLearningPathEnCurso(LearningPath learningPathEnCurso) {
		this.learningPathEnCurso = learningPathEnCurso;
	}

	public List<String> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<String> intereses) throws UsuarioExistenteException {
		this.intereses = intereses;
		//usuarios.cargarEstudiante(login, password, intereses, registro);
	}
	
	public boolean empezarActividad(Actividad actividad) {
		if (actividadEnCurso==null) {
			actividadEnCurso = actividad;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void finalizarActividad() {
		actividadEnCurso = null; 
	}
	
	public List<Double> verProgresoLearningPath() {
		LearningPath learningPathEnCurso = getLearningPathEnCurso();
    	List<Actividad> actividadesLearningPath = learningPathEnCurso.getListaActividades();
    	int cantidadActividades = actividadesLearningPath.size();
    	HashMap<String, Estado> registroActividadesEstudiante = getRegistroActividades();
    	int contadorCompletadas = 0;
    	int contadorExitosas = 0;
    	List<Double> listaRetorno = new ArrayList<>();
    	for (Actividad actividad: actividadesLearningPath) {
    		String idActividad = actividad.getId();
    		Estado estado = registroActividadesEstudiante.get(idActividad);
    		if (estado != null) {
    			if (estado.equals(Estado.ENVIADA) || estado.equals(Estado.EXITOSA)) {
    				contadorCompletadas += 1;
    				if (estado == Estado.EXITOSA) {
    					contadorExitosas += 1;
    				}
    			}
    		}
    	}
    	double porcentajeCompletadas = ((double)contadorCompletadas/cantidadActividades)*100;
    	double porcentajeExitosas = ((double)contadorExitosas/cantidadActividades)*100;
    	listaRetorno.add(porcentajeCompletadas);
    	listaRetorno.add(porcentajeExitosas);
    	return listaRetorno;
	}
	
	public HashMap<String, Estado> getRegistroActividades() {
		return registroActividades;
	}

	public void setRegistroActividades(HashMap<String, Estado> registroActividades) {
		this.registroActividades = registroActividades;
	}

	public HashMap<String, Double> getRegistroLearningPaths() {
		return registroLearningPaths;
	}

	public void setRegistroLearningPaths(HashMap<String, Double> registroLearningPaths) {
		this.registroLearningPaths = registroLearningPaths;
	}
	
	public void actualizarRegistroLearningPathActual() {
		List<Actividad> listaActividades = getLearningPathEnCurso().getListaActividades();
		int cantidadActividadesLearningActual = listaActividades.size();
		int contador = 0;
		HashMap<String, Estado> registroActividades = getRegistroActividades();
		for (Actividad actividad: listaActividades) {
			String idActividad = actividad.getId();
			if (registroActividades.containsKey(idActividad)) {
				contador += 1;
			}
		}
		double progreso = 0;
		if(cantidadActividadesLearningActual!= 0) {
		progreso = (double) contador/cantidadActividadesLearningActual;
		}
		String idLearningActual = getLearningPathEnCurso().getId();
		registroLearningPaths.put(idLearningActual, progreso);
	}
	
	public void realizarEncuesta(Encuesta encuesta, ArrayList<String> respuestas) {
		Estado estado = encuesta.contestarEncuesta(login, respuestas);
		String id= encuesta.getId();
		registroActividades.put(id, estado);
	}

	public void realizarExamen(Examen examen, ArrayList<String> respuestas) {
		String idActividad = examen.getId();
		Estado estado = examen.contestarExamen(login, respuestas);
		registroActividades.put(idActividad, estado);
	}
	
	public Estado realizarQuizVerdad(QuizVerdad quiz, ArrayList<VerdaderoFalso> respuestas) throws Exception {
		String idActividad = quiz.getId();
		Estado estado = quiz.calificar(login, respuestas);
		registroActividades.put(idActividad, estado);
		return estado;
	}
	
	public Estado realizarQuiz(Quiz quiz, ArrayList<Opcion> respuestas) throws Exception {
		String idActividad = quiz.getId();
		Estado estado = quiz.calificar(login, respuestas);
		registroActividades.put(idActividad, estado);
		return estado;
	}
	
	public void realizarRecurso(Recurso recurso) {
		Estado estado = recurso.realizarRecurso();
		String idActividad = recurso.getId();
		registroActividades.put(idActividad, estado);
	}
	
	public void realizarTarea(Tarea tarea, String medioEntrega) {
		String idActividad = tarea.getId();
		Estado estado = tarea.realizarTarea(login, medioEntrega);
		registroActividades.put(idActividad, estado);
	}
	
	public int calcularSimilitud(LearningPath learningPath) {
        int puntuacion = 0;
        List<String> intereses = getIntereses().stream().map(String::toLowerCase).collect(Collectors.toList());
        String titulo = learningPath.getTitulo().toLowerCase();
        String descripcion = learningPath.getDescripcion().toLowerCase();

        for (String interes : intereses) {
            if (titulo.contains(interes) || descripcion.contains(interes)) {
                puntuacion++;
            }
        }
        return puntuacion;
    }

    // Método principal que ordena los Learning Paths en orden de preferencia al estudiante.
    public List<LearningPath> recomendarLearningPaths(HashMap<String, LearningPath> totalLearningPaths) {
        // Crear lista a partir del HashMap
        List<LearningPath> listaCompletaLearningPaths = new ArrayList<>(totalLearningPaths.values());

        // Ordenar según la similitud
        listaCompletaLearningPaths.sort((lp1, lp2) -> {
            int similitud1 = calcularSimilitud(lp1);
            int similitud2 = calcularSimilitud(lp2);
            return Integer.compare(similitud2, similitud1); });

        return listaCompletaLearningPaths;
    }
    
    public void agregarResenaActividad(Actividad actividad, String resena) {
    	actividad.agregarResena(resena);
    }
    
    public void agregarRatingActividad(Actividad actividad, Float rating) throws Exception {
    	actividad.agregarRating(rating);	
    }
    
    // Recomienda una actividad con respecto al estado de la finalizada
    @SuppressWarnings("incomplete-switch")
	public Actividad recomendarActividad(Actividad actividadFinalizada, LearningPath learningPath) {
    	Estado estado = registroActividades.get(actividadFinalizada.getId());
    	List<Actividad> listaActividades = learningPath.getListaActividades();
    	int indiceActual = listaActividades.indexOf(actividadFinalizada);
    	Actividad actividadSugerida = null;
    	switch (estado) {
        case ENVIADA:
        case EXITOSA:
            // Verificar si hay una actividad siguiente
            if (indiceActual + 1 < listaActividades.size()) {
                actividadSugerida = listaActividades.get(indiceActual + 1);
            }
            break;

        case NO_EXITOSA:
            // Verificar si hay una actividad anterior
            if (indiceActual - 1 >= 0) {
            	actividadSugerida = listaActividades.get(indiceActual - 1);
            }
            break;
    	}
    	return actividadSugerida;
    }
}