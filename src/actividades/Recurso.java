package actividades;


public class Recurso extends Actividad{

	private String material;
	
	public Recurso(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, String material) {
			// Llamado al constructor padre
			super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
			// Agregamos material puesto que es una actividad.
			this.material = material;
			this.tipoActividad = TipoActividades.Recurso;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public Estado realizarRecurso() {
		Estado estado = Estado.EXITOSA;
		return estado;
	}
	
	@Override
	public Recurso clone() {
		Recurso copia = (Recurso) super.clone();
		copia.crearId();
		return copia;
	}
}
