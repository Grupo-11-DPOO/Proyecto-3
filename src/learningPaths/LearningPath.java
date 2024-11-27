package learningPaths;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import actividades.*;
import usuarios.Estudiante;

public class LearningPath implements Identificable{
	
	private String id;
	protected String titulo;
	protected String descripcion;
	protected String nivel;
	protected int duracion;
	private double rating;
    public LocalDateTime fechaCreacion;
	private LocalDateTime fechaModificacion;
	private int version;
	public List<Actividad> actividades;
	
	public LearningPath(String titulo, String descripcion, String nivel) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.fechaCreacion = LocalDateTime.now();
		this.fechaModificacion = LocalDateTime.now();
		this.actividades = new ArrayList<Actividad>();
		this.rating = 0;
		setDuracion();
		crearId();
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getNivel() {
		return nivel;
	}
	
	public float calcularRating(List<Actividad> listaActividades) {
		float suma = 0;
		int cantidad = 0;
		float rpt = 0;
		for (Actividad acti: listaActividades){
			suma += acti.getRating();
			cantidad ++;
		}
		if(cantidad != 0) {
			rpt = suma/cantidad;
		}
		this.rating = rpt;
		return rpt;
	}

	public int getDuracion() {
		return duracion;
	}

	public double getRating() {
		calcularRating(actividades);
		return rating;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}

	public int getVersion() {
		return version;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
		this.fechaModificacion = LocalDateTime.now();
		this.version += 1;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
		this.fechaModificacion = LocalDateTime.now();
		this.version += 1;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
		this.fechaModificacion = LocalDateTime.now();
		this.version += 1;
	}

	protected void setDuracion() {
		int duracionMinutosAcumulada = 0;
		for (Actividad actividad: actividades) {
			duracionMinutosAcumulada += actividad.getDuracionMinutos();
		}
		this.duracion = duracionMinutosAcumulada;
		this.fechaModificacion = LocalDateTime.now();
		this.version += 1;
	}
	
	public void agregarActividad(Actividad actividad) {
		actividades.add(actividad);
		this.fechaModificacion = LocalDateTime.now();
		this.version += 1;
		setDuracion();
	}
	
	public Actividad getActividad(List<Actividad> actividades, String id) {
		Actividad activ = null;
		for(Actividad actividad: actividades) {
			String id_activ = actividad.getId();
			if(id_activ == id) {
				activ = actividad;
			}
		}
		return activ;
	}
	
	public Actividad getActividad(String nombre, List<Actividad> actividades) {
		Actividad activ = null;
		for(Actividad actividad: actividades) {
			String tituloActiv = actividad.getTitulo();
			if(tituloActiv == nombre) {
				activ = actividad;
			}
		}
		return activ;
	}
	
	public List<Actividad> getListaActividades(){
		return actividades;
	}
	
	public List<Double> getProgreso(HashMap<String, Estudiante> datosEstudiante) {
		List<Double> listaRetorno = new ArrayList<>();
		List<Actividad> actividadesLearning = getListaActividades();
		int cantidadActividades = actividadesLearning.size();
		Collection<Estudiante> estudiantes = datosEstudiante.values();
    	int contadorCompletadas = 0;
    	int contadorExitosas = 0;
		for (Estudiante estudiante: estudiantes) {
			LearningPath learningEnCurso = estudiante.getLearningPathEnCurso();
			if (learningEnCurso != null) {
				if (learningEnCurso.getId()==this.getId()) {
					HashMap<String, Estado> registroAct = estudiante.getRegistroActividades();
					for (Actividad actividad: actividadesLearning) {
						String idActividad = actividad.getId();
						Estado estado = registroAct.get(idActividad);
			    		if (estado != null) {
			    			if (estado == Estado.ENVIADA || estado == Estado.EXITOSA) {
			    				contadorCompletadas += 1;
			    				if (estado == Estado.EXITOSA) {
			    					contadorExitosas += 1;
			    				}
			    			}
			    		}
					}
				}
			}
		}
		double porcentajeCompletadas = ((double)contadorCompletadas/cantidadActividades)*100;
    	double porcentajeExitosas = ((double)contadorExitosas/cantidadActividades)*100;
    	listaRetorno.add(porcentajeCompletadas);
    	listaRetorno.add(porcentajeExitosas);
    	return listaRetorno;
	}

	public void setActividades(List<Actividad> actividades) {
		this.actividades = actividades;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public String verLearningPath() {
        StringBuilder informacion = new StringBuilder();

        informacion.append("ID: ").append(id != null ? id : "N/A").append("\n");
        informacion.append("Título: ").append(titulo != null ? titulo : "N/A").append("\n");
        informacion.append("Descripción: ").append(descripcion != null ? descripcion : "N/A").append("\n");
        informacion.append("Nivel: ").append(nivel != null ? nivel : "N/A").append("\n");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
        String formattedDateCrea = fechaCreacion.format(formatter);
        String formattedDateModi = fechaModificacion.format(formatter);
        informacion.append("Fecha creación: ").append(formattedDateCrea).append("\n");
        informacion.append("Fecha modificación: ").append(formattedDateModi).append("\n");
        informacion.append("Duración (minutos): ").append(duracion).append("\n");
        informacion.append("Rating: ").append(rating).append("\n");
        informacion.append("Versión: ").append(version).append("\n");
        informacion.append("Cantidad acividades: ").append(getListaActividades().size());

        return informacion.toString();
	}

	@Override
	public void crearId() {
		String id = UUID.randomUUID().toString(); // Genera un ID único
	    this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}
}