package usuarios;

import java.util.HashMap;

import actividades.Actividad;
import learningPaths.LearningPath;

public abstract class Usuario {
	
	protected String login;
	protected String password;
	protected HashMap <String, Actividad> dataActividades; // mapa con las actividades que existen
	protected HashMap <String, LearningPath> dataLearningPaths; // mapa con los learningPath que existen
	
	public Usuario(String nLogin, String nPassword, HashMap<String,Actividad> actividades, HashMap<String,LearningPath> learningPaths) {
		this.login = nLogin;
		this.password = nPassword;
		this.setDataActividades(actividades);
		this.setDataLearningPaths(learningPaths);
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public HashMap <String, LearningPath> getDataLearningPaths() {
		return dataLearningPaths;
	}

	public void setDataLearningPaths(HashMap <String, LearningPath> dataLearningPaths) {
		this.dataLearningPaths = dataLearningPaths;
	}

	public HashMap <String, Actividad> getDataActividades() {
		return dataActividades;
	}

	public void setDataActividades(HashMap <String, Actividad> dataActividades) {
		this.dataActividades = dataActividades;
	}
}
