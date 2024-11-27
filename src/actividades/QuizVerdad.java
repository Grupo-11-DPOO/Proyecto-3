package actividades;

import java.util.ArrayList;
import java.util.HashMap;

public class QuizVerdad extends Actividad {
	public float calificacionMinima;
	private ArrayList<String>preguntas; 
	private ArrayList<VerdaderoFalso> respuestasCorrectas;
	private HashMap<String, ArrayList<VerdaderoFalso>> respuestasEstudiantes;
	
	public QuizVerdad(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, float calificacionMinima) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
		this.calificacionMinima = calificacionMinima;
		this.respuestasCorrectas = new ArrayList<>();
		this.preguntas = new ArrayList<>();
		this.respuestasEstudiantes = new HashMap<>();
		this.tipoActividad = TipoActividades.QuizVerdad;
	}
	
	public ArrayList<String> getPreguntas() {
		return preguntas;
	}
	
	public void setPreguntas(ArrayList<String> preguntas) {
		this.preguntas = preguntas;
	}

	public HashMap<String, ArrayList<VerdaderoFalso>> getRespuestasEstudiantes() {
		return respuestasEstudiantes;
	}
	public void setRespuestasEstudiantes(HashMap<String, ArrayList<VerdaderoFalso>> respuestasEstudiantes) {
		this.respuestasEstudiantes = respuestasEstudiantes;
	}
	
	public float getCalificacionMinima() {
		return calificacionMinima;
	}
	
	public void setCalificacionMinima(float calificacionMin) {
		this.calificacionMinima = calificacionMin;
	}
	
	public ArrayList<VerdaderoFalso> getRespuestasCorrectas() {
		return respuestasCorrectas;
	}
	
	public void setRespuestasCorrectas(ArrayList<VerdaderoFalso> respuestasCorrectas) {
		this.respuestasCorrectas = respuestasCorrectas;
	}
	
	public void agregarPregunta(String enunciado, VerdaderoFalso opcionCorrecta) {
		preguntas.add(enunciado);
		respuestasCorrectas.add(opcionCorrecta);
	}
	
	public String verPreguntas() {
		
		StringBuilder sb = new StringBuilder();
	    sb.append("Preguntas:\n\n");
	    int contador = 1; // Para numerar las preguntas
	    if(!preguntas.isEmpty()) {
	    	for (String pregunta : preguntas) {
	    		sb.append(contador).append(". ").append(pregunta).append("\n");
	    		contador++;
	    }
	    } else {
	    	return "No hay Preguntas por mostrar";
	    }
	    return sb.toString();
	}
	
	public Estado calificar(String idEstudiante, ArrayList<VerdaderoFalso> respuestas) throws Exception{
		if (this.respuestasCorrectas.size()==respuestas.size()) {
			respuestasEstudiantes.put(idEstudiante, respuestas);
			int contadorCorrectas = 0;
			int contador= 0;
			for (VerdaderoFalso respuesta: this.respuestasCorrectas) {
				VerdaderoFalso respuestaSeleccionada = respuestas.get(contador);
				if (respuestaSeleccionada.equals(respuesta)) {
					contadorCorrectas ++;
				}
				contador++;
			}
			float calificacion = ((float) contadorCorrectas / this.respuestasCorrectas.size()) * 100;

			if (calificacion >= calificacionMinima) {
				return Estado.EXITOSA;
			} else {
				return Estado.NO_EXITOSA;
			}
		} else {
			throw new Exception("La cantidad de respuestas no coincide con el número de preguntas.");
		}
	}
	
	@Override
	public QuizVerdad clone() {
		QuizVerdad copia = (QuizVerdad) super.clone();
		copia.crearId();
		return copia;
	}
}
