package actividades;

import java.util.HashMap;

public class Tarea extends Actividad{
	
    private HashMap<String, String> respuestas; //Id del estudiante y como clave el string que dice por cual medio de entrega mando la tarea
    private String idActividadARealizar;
    
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
	
	@Override
	public Tarea clone() {
		Tarea copia = (Tarea) super.clone();
		copia.crearId();
		return copia;
	}
}
