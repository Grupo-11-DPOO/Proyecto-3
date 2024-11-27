package actividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Quiz extends Actividad{
	
	public float calificacionMinima;
	private HashMap<String, HashMap<Opcion, HashMap<String,String>>> preguntas;//Mapa de las preguntas con sus opciones, la llave es la preg¿unta, la clave es un mapa donde la llave es la opcion y su valor otro mapa donde ya este ultimo mapa contiene como llave el enunciado de la opcion y como valor la explicación del porque es correcta
	private ArrayList<Opcion> respuestasCorrectas; // Mapa donde la llave es la pregunta y la clave es la respuesta correcta
	private HashMap<String,ArrayList<Opcion>> respuestasEstudiantes;

	public Quiz(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio,float calificacionMinima) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
		this.calificacionMinima = calificacionMinima;
		this.preguntas = new HashMap<>();
		this.respuestasCorrectas = new ArrayList<>();
		this.respuestasEstudiantes = new HashMap<>();
		this.tipoActividad = TipoActividades.Quiz;
	}
	
	public HashMap<String, ArrayList<Opcion>> getRespuestasEstudiantes() {
		return respuestasEstudiantes;
	}

	public void setRespuestasEstudiantes(HashMap<String, ArrayList<Opcion>> respuestasEstudiantes) {
		this.respuestasEstudiantes = respuestasEstudiantes;
	}

	public float getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(float calificacionMin) {
		this.calificacionMinima = calificacionMin;
	}

	public HashMap<String, HashMap<Opcion, HashMap<String, String>>> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(HashMap<String, HashMap<Opcion, HashMap<String, String>>> preguntas) {
		this.preguntas = preguntas;
	}

	public ArrayList<Opcion> getRespuestasCorrectas() {
		return respuestasCorrectas;
	}

	public void setRespuestasCorrectas(ArrayList<Opcion> respuestasCorrectas) {
		this.respuestasCorrectas = respuestasCorrectas;
	}
	
	public void agregarPregunta(String enunciado, Opcion opcionCorrecta) {
		HashMap<Opcion,HashMap<String,String>> entryOpcion = new HashMap<>();
		preguntas.put(enunciado, entryOpcion);
		respuestasCorrectas.add(opcionCorrecta);
	}
	
	public void agregarOpcion(String enunciadoPregunta, String enunciadoRespuesta, Opcion opcion, String explicacionRespuesta) {
			HashMap<Opcion, HashMap<String,String>> entry = preguntas.get(enunciadoPregunta); 
			HashMap<String,String> enunciadoExplicacion = new HashMap<>();
			enunciadoExplicacion.put(enunciadoRespuesta, explicacionRespuesta);
			entry.put(opcion, enunciadoExplicacion);
	}

	public String verPreguntas() {
	    StringBuilder resultado = new StringBuilder();
	    
	    if (!this.preguntas.isEmpty()) {
	    	int i = 1;
	        for (Map.Entry<String, HashMap<Opcion, HashMap<String, String>>> pregunta : preguntas.entrySet()) {
	            // Imprimir el enunciado de la pregunta
	            resultado.append("\nPregunta #"+i+": " + pregunta.getKey()).append("\n");
	            // Recorrer las opciones en orden definido por el enum
	            for (Opcion opcion : Opcion.values()) { // Asegura el orden A, B, C, D
	                if (pregunta.getValue().containsKey(opcion)) {
	                	 resultado.append(opcion+": ");
	                    // Imprimir el primer String del HashMap interno
	                    for (String texto : pregunta.getValue().get(opcion).keySet()) {
	                    	 resultado.append(texto).append("\n");
	                    }
	                }
	            }
	            resultado.append("------------");
	            i++;
	        }
	    } else {
	        return "No hay preguntas que mostrar";
	    }
	    return resultado.toString(); 
	}
	
	public String verPreguntasConExplicaciones() {
	    StringBuilder resultado = new StringBuilder();
	    
	    if (!this.preguntas.isEmpty()) {
	        for (Map.Entry<String, HashMap<Opcion, HashMap<String,String>>> entradaPregunta : preguntas.entrySet()) {
	            String enunciadoPregunta = entradaPregunta.getKey();
	            HashMap<Opcion, HashMap<String,String>> opciones = entradaPregunta.getValue();

	            resultado.append("Pregunta: ").append(enunciadoPregunta).append("\nOpciones:\n");
	            if (!opciones.isEmpty()) {
	                for (Map.Entry<Opcion, HashMap<String,String>> entradaOpciones : opciones.entrySet()) {
	                    String opcion = entradaOpciones.getKey().name();
	                    HashMap<String,String> entradaOpcion = entradaOpciones.getValue();
	                    if(!entradaOpcion.isEmpty()) {
	                    	for(Map.Entry<String, String> entry: entradaOpcion.entrySet()) {
	                    		String enunciado = entry.getKey();
	                    		String explicacion = entry.getValue();
	    	                    resultado.append(opcion).append("): ").append(enunciado).append("\n");
	    	                    resultado.append("\nExplicación: \n"+explicacion+"\n");
	                    	}
	                    }
	                }
	            }
	        }
	    } else {
	        return "No hay preguntas que mostrar";
	    }
	    return resultado.toString();
	}
		
	public Estado calificar(String idEstudiante, ArrayList<Opcion> respuestas) throws Exception{
		if (respuestasCorrectas.size()==respuestas.size()) {
			respuestasEstudiantes.put(idEstudiante, respuestas);
			int contadorCorrectas = 0;
			int contador= 0;
			for (Opcion opcion: respuestasCorrectas) {
				Opcion respuestaSeleccionada = respuestas.get(contador);
				Opcion correcta = opcion;
				if (respuestaSeleccionada.equals(correcta)) {
					contadorCorrectas ++;
				}
				contador++;
			}
			float calificacion = ((float) contadorCorrectas / respuestasCorrectas.size()) * 100;

			verPreguntasConExplicaciones();
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
	public Quiz clone() {
		Quiz copia = (Quiz) super.clone();
		copia.crearId();
		return copia;
	}
}
