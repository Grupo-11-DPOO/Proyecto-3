package actividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import usuarios.Estudiante;

public class Examen extends Actividad{
	
	private List<String> preguntas;	
	private HashMap<String, ArrayList<String>> respuestasDeTodos;
	private List<JTextArea> areasRespuestas = new ArrayList<>();;

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
	
	public JPanel getContenido() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		for(int i=0; i<preguntas.size();i++) {
			String pregunta= preguntas.get(i); 
			
			panel.add(new JLabel((i+1)+". "+ pregunta));
			
			JTextArea areaRespuesta = new JTextArea(5,30);
			areasRespuestas.add(areaRespuesta);
			panel.add(new JScrollPane(areaRespuesta));
			panel.add(Box.createVerticalStrut(10));
				
		}
		
		return panel;	
		
		
	}
	
	public void guardarRespuestas(Estudiante estudiante) {
		
		String login = estudiante.getLogin();
		ArrayList<String> respuestas = new ArrayList<>();
		
		for (int i = 0; i< preguntas.size(); i++) {
			String respuesta = areasRespuestas.get(i).getText().toLowerCase();
			respuestas.add(respuesta);
		}
		respuestasDeTodos.put(login, respuestas);
	}

	@Override
	public Examen clone() {
		Examen copia = (Examen) super.clone();
		copia.crearId();
		return copia;
	}
}
	