package actividades;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import usuarios.Estudiante;

public class QuizVerdad extends Actividad {
	public float calificacionMinima;
	private ArrayList<String> preguntas; 
	private ArrayList<VerdaderoFalso> respuestasCorrectas;
	private HashMap<String, ArrayList<VerdaderoFalso>> respuestasEstudiantes;
	private List<JRadioButton> botonesVerdadero;
	private List<JRadioButton> botonesFalso;
	public QuizVerdad(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, float calificacionMinima) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
		this.calificacionMinima = calificacionMinima;
		this.respuestasCorrectas = new ArrayList<>();
		this.preguntas = new ArrayList<>();
		this.respuestasEstudiantes = new HashMap<>();
		this.tipoActividad = TipoActividades.QuizVerdad;
		this.botonesVerdadero = new ArrayList<>();
		this.botonesFalso = new ArrayList<>();
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
    public JPanel getContenido() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (int i = 0; i < preguntas.size(); i++) {
            panel.add(new JLabel((i + 1) + ". " + preguntas.get(i)));

            JRadioButton verdadero = new JRadioButton("Verdadero");
            JRadioButton falso = new JRadioButton("Falso");
            botonesVerdadero.add(verdadero);
            botonesFalso.add(falso);

            ButtonGroup grupo = new ButtonGroup();
            grupo.add(verdadero);
            grupo.add(falso);

            JPanel opcionesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            opcionesPanel.add(verdadero);
            opcionesPanel.add(falso);
            panel.add(opcionesPanel);

            panel.add(Box.createVerticalStrut(10));
        }

        return panel;
    }
    public Estado guardarRespuestas(Estudiante estudiante) {
        int correctas = 0;
        for (int i = 0; i < preguntas.size(); i++) {
            boolean respuestaEstudiante = botonesVerdadero.get(i).isSelected();
            VerdaderoFalso x ;
            if(respuestaEstudiante== true) {
            	x = VerdaderoFalso.Verdadero;
            }else {
            	x = VerdaderoFalso.Falso;
            }
            
            if (x== respuestasCorrectas.get(i)) {
                correctas++;
            }
        }

        float calificacion = (float) correctas / preguntas.size() * 100;
        Estado estado = (calificacion >= calificacionMinima) ? Estado.EXITOSA : Estado.NO_EXITOSA;
        estudiante.getRegistroActividades().put(this.getId(), estado);
        return estado;
    }

	@Override
	public QuizVerdad clone() {
		QuizVerdad copia = (QuizVerdad) super.clone();
		copia.crearId();
		return copia;
	}
}
