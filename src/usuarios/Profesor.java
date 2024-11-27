package usuarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import actividades.Actividad;
import actividades.Encuesta;
import actividades.Examen;
import actividades.Quiz;
import actividades.QuizVerdad;
import actividades.Recurso;
import actividades.Tarea;
import actividades.TipoActividades;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;

public class Profesor extends Usuario {

	private ArrayList<String> idActividadesCreadas;
	private ArrayList<String> idLearningPathsCreados;
	
	public Profesor(HashMap<String,Actividad> actividades, HashMap<String,LearningPath> learningPaths, String Login, String Password) {
		super(Login, Password, actividades, learningPaths);
		this.idActividadesCreadas = new ArrayList<>();
		this.idLearningPathsCreados = new ArrayList<>();
		}

	public ArrayList<String> getIdActividadesCreadas() {
		return idActividadesCreadas;
	}

	public void setIdActividadesCreadas(ArrayList<String> idActividadesCreadas) {
		this.idActividadesCreadas = idActividadesCreadas;
	}

	public ArrayList<String> getIdLearningPathsCreados() {
		return idLearningPathsCreados;
	}

	public void setIdLearningPathsCreados(ArrayList<String> idLearningPathsCreadas) {
		this.idLearningPathsCreados = idLearningPathsCreadas;
	}

	public void agregarLearningPath(LearningPath learnPath) throws UsuarioExistenteException {
		getDataLearningPaths().put(learnPath.getId(),learnPath);
		//learningPathExistentes.cargarLearningPath(learnPath);
		this.idLearningPathsCreados.add(learnPath.getId());
		//usuarios.cargarProfesor(login, password, idLearningPaths, idActividades);
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
	public Actividad clonarActividad(Actividad actividad) throws Exception {
		// Metodo madre
		TipoActividades tipo = actividad.getTipoActividad();
		Actividad actividadClonada = null;
		switch (tipo) {
		case Recurso:
			Recurso recurso = (Recurso) actividad;
			actividadClonada = clonarRecurso(recurso);
			break;
		case Tarea:
			Tarea tarea = (Tarea) actividad;
			actividadClonada = clonarTarea(tarea);
			break;
		case Quiz:
			Quiz quiz = (Quiz) actividad;
			actividadClonada = clonarQuiz(quiz);
			break;
		case Examen:
			Examen examen = (Examen) actividad;
			actividadClonada = clonarExamen(examen);
			break;
		case Encuesta:
			Encuesta encuesta = (Encuesta) actividad;
			actividadClonada = clonarEncuesta(encuesta);
			break;
		case QuizVerdad:
			QuizVerdad quizVerdad = (QuizVerdad) actividad;
			actividadClonada = clonarQuizVerdad(quizVerdad);
			break;
		}
		return actividadClonada;
	}	
	
	public Actividad getActividadById(String id) {
		boolean x= getDataActividades().containsKey(id);
		
		if (x) {
			
			return getDataActividades().get(id);
		} else return null;
	}
	
	public Actividad clonarTarea(Tarea tarea) throws Exception {
	    Tarea actiClonada = tarea.clone();
	    idActividadesCreadas.add(actiClonada.getId());
	    getDataActividades().put(actiClonada.getId(), actiClonada);
	    return actiClonada;
	}

	public Actividad clonarRecurso(Recurso recurso) throws Exception {
	    Recurso actiClonada = recurso.clone();
	    idActividadesCreadas.add(actiClonada.getId());
	    getDataActividades().put(actiClonada.getId(), actiClonada);
	    return actiClonada;
	}

	public Actividad clonarQuizVerdad(QuizVerdad quizVerdad) throws Exception {
	    QuizVerdad actiClonada = quizVerdad.clone();
	    idActividadesCreadas.add(actiClonada.getId());
	    getDataActividades().put(actiClonada.getId(), actiClonada);
	    return actiClonada;
	}

	public Actividad clonarQuiz(Quiz quiz) throws Exception {
	    Quiz actiClonada = quiz.clone();
	    idActividadesCreadas.add(actiClonada.getId());
	    getDataActividades().put(actiClonada.getId(), actiClonada);
	    return actiClonada;
	}

	public Actividad clonarExamen(Examen examen) throws Exception {
	    Examen actiClonada = examen.clone();
	    idActividadesCreadas.add(actiClonada.getId());
	    getDataActividades().put(actiClonada.getId(), actiClonada);
	    return actiClonada;
	}

	public Actividad clonarEncuesta(Encuesta encuesta) throws Exception {
	    Encuesta actiClonada = encuesta.clone();
	    idActividadesCreadas.add(actiClonada.getId());
	    getDataActividades().put(actiClonada.getId(), actiClonada);
	    return actiClonada;
	}

	public Recurso crearActividadRecurso(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, String material) throws Exception {
			Recurso recurso = new Recurso(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, material);
			System.out.println("Su actividad de tipo recurso ha sido creada.");
			getDataActividades().put(recurso.getId(), recurso);
			return recurso;
	}
	
	public void agregarActividadALearningPath(LearningPath learningPath, Actividad actividad) {
		learningPath.agregarActividad(actividad);
		getDataLearningPaths().put(learningPath.getId(), learningPath);
	}
	
	public Tarea crearActividadTarea(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio,
			String idActividadTarea) throws Exception {
			Tarea tarea = new Tarea(titulo, objetivo,descripcion, nivel, duracionMinutos, obligatorio, idActividadTarea);
			System.out.println("Su actividad de tipo tarea ha sido creada.");
			return tarea;
		}

	public Examen crearActividadExamen (String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) throws Exception {
			Examen examen= new Examen(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
			System.out.println("Su actividad de tipo examen ha sido creada.");
			getDataActividades().put(examen.getId(), examen);
			return examen;
		}
	
	public Encuesta crearActividadEncuesta (String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) throws Exception {
			Encuesta encuesta= new Encuesta(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
			System.out.println("Su actividad de tipo encuesta ha sido creada.");
			getDataActividades().put(encuesta.getId(), encuesta);
			return encuesta;
		}
	
	public QuizVerdad crearQuizVerdaderoFalso(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, float calificacionMinima) {
		QuizVerdad quiz= new QuizVerdad(titulo, objetivo, descripcion,nivel ,duracionMinutos, obligatorio, calificacionMinima);
		System.out.println("Su actividad de tipo quiz de verdadero o falso ha sido creada.");
		return quiz;
	}
	
	public Quiz crearActividadQuiz (String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, float calificacionMinima) {	
		Quiz quiz= new Quiz(titulo, objetivo, descripcion,nivel ,duracionMinutos, obligatorio, calificacionMinima);
		System.out.println("Su actividad de tipo quiz de selección multiple ha sido creada.");
		return quiz;
	}
	
	public String mostrarRespuestasExamen(Examen examen) {
		 StringBuilder sb = new StringBuilder();
		 if(!examen.getRespuestas().entrySet().isEmpty()) {
		    for (Map.Entry<String, ArrayList<String>> entry : examen.getRespuestas().entrySet()) {
		        String login = entry.getKey();
		        ArrayList<String> respuestas = entry.getValue();
		        
		        sb.append("Estudiante: ").append(login).append("\n");
		        sb.append("Respuestas al examen: ");
		        for (String valor : respuestas) {
		            sb.append(valor).append(", ");
		        }
		        if (!respuestas.isEmpty()) {
		            sb.setLength(sb.length() - 2);
		        }
		        sb.append("\n");
		    }
		    return sb.toString();
		 }else {
			 return "No hay respuestas por mostrar.";
		 }
	}
	
	public String mostrarRespuestasEncuesta(Encuesta encuesta) {
		
		 StringBuilder sb = new StringBuilder();
		    for (Map.Entry<String, ArrayList<String>> entry : encuesta.getRespuestas().entrySet()) {
		        String login = entry.getKey();
		        ArrayList<String> respuestas = entry.getValue();
		        
		        sb.append("Estudiante: ").append(login).append("\n");
		        sb.append("Respuestas al examen: ");
		        for (String valor : respuestas) {
		            sb.append(valor).append(", ");
		        }
		        if (!respuestas.isEmpty()) {
		            sb.setLength(sb.length() - 2);
		        }
		        sb.append("\n");
		    }
		    return sb.toString();
	}
	
	public String mostrarRespuestasTarea(Tarea tarea) {
		
		 StringBuilder sb = new StringBuilder();
		    for (Map.Entry<String, String> entry : tarea.getRespuestas().entrySet()) {
		        String login = entry.getKey();
		        String medio = entry.getValue();
		        
		        sb.append("Estudiante: ").append(login).append("\n");
		        sb.append("Medio de entrega de la tarea: ").append(medio);
		        sb.append("\n");
		    }
		    return sb.toString();
	}

	public void guardarActividad(Actividad actividad) {
		if(idActividadesCreadas.contains(actividad.getId())) {
			
		}else {
			idActividadesCreadas.add(actividad.getId());
		}
		getDataActividades().put(actividad.getId(),actividad); // Mapa id, Actividad TOTALES
	}
	
	public LearningPath crearLearningPath(String titulo, String objetivo, String nivel) {
		LearningPath learningPath = new LearningPath(titulo, objetivo, nivel);
		getDataLearningPaths().put(learningPath.getId(), learningPath);
		idLearningPathsCreados.add(learningPath.getId());
		return learningPath;
	}
	
	public void guardarLearningPath(LearningPath learningPath) throws UsuarioExistenteException{
		if(idLearningPathsCreados.contains(learningPath.getId())) {
			
		}
		else {
			idLearningPathsCreados.add(learningPath.getId());
		}
		getDataLearningPaths().put(learningPath.getId(),learningPath);
	}

	public void agregarActividad(Actividad actividad, LearningPath learnPath) throws UsuarioExistenteException {
		List<Actividad> actividadesLearn = learnPath.getListaActividades();
		actividadesLearn.add(actividad);
		guardarActividad(actividad);
	}
	
	public String mostrarResenas(LearningPath learnPath) {
		StringBuilder sb = new StringBuilder();
		List<Actividad> actividades  = learnPath.getListaActividades();
		if(!actividades.isEmpty()) {
			for(Actividad actividad :actividades) {
				List<String> resenas = actividad.getResenas();
				sb.append("Titulo: "+ actividad.getTitulo());
				int counter = 1;
				for(String resena: resenas){
			
					sb.append("Resena "+ counter+": " + resena);
				
					sb.append("---------------------------------------");
					counter++;
				}
			}
		} else {
			return "No hay reseñas por mostrar.";
		}
		return sb.toString();
	}
}
