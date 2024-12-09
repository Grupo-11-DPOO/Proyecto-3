package actividades;

import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import usuarios.Estudiante;

public class Tarea extends Actividad{
	
    private HashMap<String, String> respuestas; //Id del estudiante y como clave el string que dice por cual medio de entrega mando la tarea
    private String idActividadARealizar;
    private JTextArea areaEntrega;
	public Tarea(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio, String idActividadARealizar) {
		super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
    	this.tipoActividad = TipoActividades.Tarea;
    	respuestas = new HashMap<>();
    	this.idActividadARealizar = idActividadARealizar;
	}

	public HashMap<String, String> getRespuestas() {
		return respuestas;
	}

	public void setRespuestas(HashMap<String, String> respuestas) {
		this.respuestas = respuestas;
	}

	public String getIdActividadesARealizar() {
		return idActividadARealizar;
	}

	public Estado realizarTarea(String idEstudiante, String medioEntrega ) {
		respuestas.put(idEstudiante, medioEntrega);
		return Estado.ENVIADA;	
	}
    public JPanel getContenido() {
    	areaEntrega = new JTextArea();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelInstruccion = new JLabel("ID de la actividad a realizar: " + idActividadARealizar);
        panel.add(labelInstruccion);

        JLabel labelEntrega = new JLabel("Ingrese cómo entregará la actividad:");
        panel.add(labelEntrega);

        areaEntrega.setLineWrap(true);
        areaEntrega.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(areaEntrega);
        panel.add(scrollPane);

        return panel;
    }
    public void guardarRespuestas(Estudiante estudiante) {
        String respuesta = areaEntrega.getText();
        
        respuestas.put(estudiante.getLogin(), respuesta);
        
        
    }

	
	@Override
	public Tarea clone() {
		Tarea copia = (Tarea) super.clone();
		copia.crearId();
		return copia;
	}
}
