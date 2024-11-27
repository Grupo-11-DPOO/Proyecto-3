package actividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Examen extends Actividad{
	
	private List<String> preguntas;	
	private HashMap<String, ArrayList<String>> respuestasDeTodos;

	public Examen(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
		this.preguntas = new ArrayList<>();
		this.respuestasDeTodos = new HashMap<>();
		this.tipoActividad= TipoActividades.Examen;
	}
	
	public void agregarPregunta(String pregunta) {
		if(this.preguntas==null) {
			this.preguntas = new ArrayList<String>();
		}
		preguntas.add(pregunta);
	}
	
	public void eliminarPregunta(String pregunta) {
		boolean x = this.preguntas.contains(pregunta);
		if (x) {
			this.preguntas.remove(pregunta);
		}
	}
	
	public String verPreguntas() {
		int i = 0;
		StringBuilder preguntasString = new StringBuilder();
		
		if(!this.preguntas.isEmpty()) {
		
			for (String pregunta:preguntas) {
				i++;
				preguntasString.append(i+". "+pregunta+"\n");
			}
		}
		else {
			return "No hay preguntas que mostrar";
		}
		return preguntasString.toString();
	}	
	
	public List<String> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}

	public HashMap<String, ArrayList<String>> getRespuestas() {
		return respuestasDeTodos;
	}

	public void setRespuestas(HashMap<String, ArrayList<String>> respuestas) {
		this.respuestasDeTodos = respuestas;
	}

	public Estado contestarExamen(String id, ArrayList<String> respuesta) {
		this.respuestasDeTodos.put(id, respuesta);
		return Estado.PENDIENTE;	
	}
	
	@Override
	public Examen clone() {
		Examen copia = (Examen) super.clone();
		copia.crearId();
		return copia;
	}
}
	