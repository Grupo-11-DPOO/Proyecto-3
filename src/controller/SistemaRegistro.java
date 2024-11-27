package controller;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import actividades.Actividad;
import exceptions.UsuarioExistenteException;
import learningPaths.LearningPath;
import persistencia.PersistenciaActividades;
import persistencia.PersistenciaEstudiantes;
import persistencia.PersistenciaLearningPath;
import persistencia.PersistenciaProfesores;
import usuarios.Estudiante;
import usuarios.Profesor;

public class SistemaRegistro {
	private PersistenciaEstudiantes persistenciaEstudiantes;
	private PersistenciaProfesores persistenciaProfesores;
	private PersistenciaActividades persistenciaActividades;
	private PersistenciaLearningPath persistenciaLearningPath;
	private HashMap<String,Profesor>datosProfesores;
	private HashMap<String,Estudiante>datosEstudiantes;
	public HashMap<String,LearningPath> learningPaths;
	public HashMap<String, Actividad> actividades;
	
	public SistemaRegistro() throws JSONException {
		this.persistenciaEstudiantes = new PersistenciaEstudiantes();
		this.persistenciaProfesores = new PersistenciaProfesores();
		this.persistenciaActividades= new PersistenciaActividades();
		this.persistenciaLearningPath = new PersistenciaLearningPath();
		this.datosEstudiantes = new HashMap<>();
		this.datosProfesores = new HashMap<>();
		this.learningPaths = new HashMap<>();
		this.actividades = new HashMap<>();
		try {
			cargarDatos();
	} catch (UsuarioExistenteException e) {
			e.printStackTrace();
		} catch (ParseException e) {
		e.printStackTrace();
		}
	}
	
	public HashMap<String, Actividad> cargarActividades() throws JSONException, ParseException {
		HashMap<String, Actividad> actividades = persistenciaActividades.cargarActividades();
		return actividades;
	}
	
	public void guardarActividad(Actividad actividad) {
		persistenciaActividades.guardarActividad(actividad);
	}
	
	public HashMap<String, LearningPath> cargarLearningPaths(HashMap<String,Actividad> actividades){
		HashMap<String,LearningPath> learningPaths = persistenciaLearningPath.cargarLearningPaths(actividades);
		return learningPaths;
	}
	
	public void guardarLearningPath(LearningPath learningPath) {
		persistenciaLearningPath.guardarLearningPath(learningPath);
	}
	
	public HashMap<String,Estudiante> cargarEstudiantes(HashMap<String,Actividad> actividades, HashMap<String,LearningPath> learningPaths) {
		HashMap<String,Estudiante> estudiantes = persistenciaEstudiantes.cargarEstudiantes(actividades, learningPaths);
		return estudiantes;
	}
	
	public void guardarEstudiante(Estudiante estudiante) {
		persistenciaEstudiantes.guardarEstudiante(estudiante);
	}
	
	public HashMap<String,Profesor> cargarProfesores(HashMap<String,Actividad> actividades, HashMap<String,LearningPath> learningPaths){
		HashMap<String,Profesor> profesores = persistenciaProfesores.cargarProfesores(actividades, learningPaths);
		return profesores;
	}
	
	public void guardarProfesor(Profesor profesor) {
		persistenciaProfesores.guardarProfesor(profesor);
	}
	
	public void cargarDatos() throws UsuarioExistenteException, JSONException, ParseException{
		this.actividades = cargarActividades();
		this.learningPaths = cargarLearningPaths(actividades);
		this.datosEstudiantes = cargarEstudiantes(actividades,learningPaths);
		this.datosProfesores = cargarProfesores(actividades,learningPaths);
	}
		
	public Profesor registrarProfesor(String login, String passWord) throws UsuarioExistenteException {
		boolean x = datosProfesores.containsKey(login);
		if(x) {
			return null;
		}
		else {
			Profesor profesor = new Profesor(this.actividades,this.learningPaths,login, passWord);
			this.datosProfesores.put(login, profesor);
			persistenciaProfesores.guardarProfesor(profesor);
			return profesor;
		}	
	}
	
	public Estudiante registrarEstudiante(String login, String passWord, List<String> intereses) throws UsuarioExistenteException {
		boolean x = datosEstudiantes.containsKey(login);
		if(x) {
			return null;
	}
		else {
			Estudiante estudiante = new Estudiante(actividades,learningPaths,login,passWord,intereses);
			this.datosEstudiantes.put(login, estudiante);
			persistenciaEstudiantes.guardarEstudiante(estudiante);
			return estudiante;
		}
	}
	
	public boolean iniciarSesionProfesor(String login, String password) {
	    for (Map.Entry<String, Profesor> entry : datosProfesores.entrySet()) {
	        Profesor profesor = entry.getValue();
	        if (profesor.getLogin().equals(login) && profesor.getPassword().equals(password)) {
	            return true;
	        }
	    }
	    return false;  
	}
	
	public boolean iniciarSesionEstudiante(String login, String password) {
	    for (Map.Entry<String, Estudiante> entry : datosEstudiantes.entrySet()) {
	        Estudiante estudiante = entry.getValue();
	        if (estudiante.getLogin().equals(login) && estudiante.getPassword().equals(password)) {
	            return true;
	        }
	    }
	    return false;  
	}

	public HashMap<String, Profesor> getDatosProfesores() {
		return datosProfesores;
	}

	public void setDatosProfesores(HashMap<String, Profesor> datosProfesores) {
		this.datosProfesores = datosProfesores;
	}

	public HashMap<String, Estudiante> getDatosEstudiantes() {
		return datosEstudiantes;
	}

	public void setDatosEstudiantes(HashMap<String, Estudiante> datosEstudiantes) {
		this.datosEstudiantes = datosEstudiantes;
	}
}