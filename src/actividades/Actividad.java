package actividades;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import learningPaths.Identificable;
import learningPaths.LearningPath;
import usuarios.Estudiante;

public abstract class Actividad implements Identificable, Cloneable {
	
	protected String id;
	public String titulo;
	public String objetivo;
	public String descripcion;
	public String nivel;
	public int duracionMinutos;
	public boolean obligatorio;
	public Date tiempoLimite;
	private float rating;
	protected ArrayList<Actividad> prerequisitos;
	public int cantidadRating;
	public List<String> resenas; // las resenas que un estudiante ingresa a la actividad y pueda visualizar un profesor
	protected TipoActividades tipoActividad;
	
	public Actividad(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) {
		this.titulo = titulo;
		this.objetivo = objetivo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.duracionMinutos = duracionMinutos;
		this.obligatorio = obligatorio;
		this.prerequisitos = new ArrayList<Actividad>();
		this.resenas = new ArrayList<String>();
		this.rating = 0;
		this.cantidadRating = 0;
		crearId();
		this.tiempoLimite = new Date();
	}

	public Date getTiempoLimite() {
		return tiempoLimite;
	}

	public void setTiempoLimite(Date tiempoLimite) {
		this.tiempoLimite = tiempoLimite;
	}

	public int getCantidadRating() {
		return cantidadRating;
	}

	public void setCantidadRating(int cantidadRating) {
		this.cantidadRating = cantidadRating;
	}

	public void setRating(float d) {
		this.rating = d;
	}

	public void setResenas(List<String> resenas) {
		this.resenas = resenas;
	}

	public void setTipoActividad(TipoActividades tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public int getDuracionMinutos() {
		return duracionMinutos;
	}

	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}
	

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}


	public ArrayList<Actividad> getPrerequisitos() {
		return prerequisitos;
	}

	public void setPrerequisitos(ArrayList<Actividad> prerequisitos) {
		this.prerequisitos = prerequisitos;
	}
	
	// Los ratings deben estar entre 0 y 5.
	public void agregarRating(float ratingPersonal) throws Exception{
		if (ratingPersonal <= 0 || ratingPersonal > 5) {
			throw new Exception("Rating debe estar entre 0 y 5.");
		} else {
			
			float nuevoRating = ((this.rating*this.cantidadRating)+ratingPersonal);
			this.cantidadRating+=1;
			this.rating = nuevoRating/this.cantidadRating;
		}
	}
	
	public float getRating() {
		return rating;
	}
	
	public void agregarResena(String resenaInput) {
		resenas.add(resenaInput);
	}
	
	public List<String> getResenas() {
		return resenas;
	}
	
	public boolean advertenciaPrerequisitos(Estudiante estudiante) {
		boolean valido = true;
		if (!prerequisitos.isEmpty()) {
			for (Actividad act:prerequisitos) {
				String codigoAct = act.getId();
				HashMap<String, Estado> registroActividadesEstudiante = estudiante.getRegistroActividades();
				if (!registroActividadesEstudiante.containsKey(codigoAct)) {
					valido = false;
				}
			}
		}
		return valido;
	}
	
    public void establecerFechaLimite(Estudiante estudianteActual) {
    	// Buscamos actividad anterior
    	LearningPath learningPath = estudianteActual.getLearningPathEnCurso();
    	List<Actividad> listaActividades = learningPath.getListaActividades();
    	int index = listaActividades.indexOf(this);
    	if (index > 0) {
    		Actividad actividadAnterior = listaActividades.get(index-1);
			if (actividadAnterior == null || actividadAnterior.getDuracionMinutos() <= 0) {
				this.tiempoLimite = null;
			} else {
		        Calendar calendario = Calendar.getInstance();
		        calendario.setTime(new Date());
		        calendario.add(Calendar.MINUTE, actividadAnterior.getDuracionMinutos());
		        calendario.add(Calendar.HOUR, 1);        
		        // Establece la nueva fecha límite
		        this.tiempoLimite = calendario.getTime();
	        }
    	} else {
    		this.tiempoLimite = null;
    	}
    }
    
    // Método para verificar si la actividad se está realizando después de la fecha límite
    protected boolean revisarFechaLimiteExcedida() {
        if (tiempoLimite != null) {
            Date fechaActual = new Date();
            if (fechaActual.after(tiempoLimite)) {
                System.out.println("Advertencia: Está realizando la actividad después de la fecha límite recomendada.");
                return true;
            }
            return false;
        }
        return false;
    }
    
    // Se clona la actividad por si un profesor desea poder ser el nuevo dueño y modificarla.
	public void agregarPrerrequisito(Actividad actividad) {
		this.prerequisitos.add(actividad);
	}
	
	@Override
    public Actividad clone() {
        try {
            return(Actividad) super.clone();
            
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String verActividad() {
        StringBuilder informacion = new StringBuilder();
        informacion.append("ID: ").append(id != null ? id : "N/A").append("\n");
        informacion.append("Título: ").append(titulo != null ? titulo : "N/A").append("\n");
        informacion.append("Objetivo: ").append(objetivo != null ? objetivo : "N/A").append("\n");
        informacion.append("Descripción: ").append(descripcion != null ? descripcion : "N/A").append("\n");
        informacion.append("Nivel: ").append(nivel != null ? nivel : "N/A").append("\n");
        informacion.append("Duración (minutos): ").append(duracionMinutos).append("\n");
        informacion.append("Obligatorio: ").append(obligatorio ? "Sí" : "No").append("\n");
        informacion.append("Tiempo Límite: ").append(tiempoLimite != null ? tiempoLimite.toString() : "Sin límite").append("\n");
        informacion.append("Rating: ").append(rating).append(" (").append(cantidadRating).append(" calificaciones)\n");
        informacion.append("Tipo de Actividad: ").append(tipoActividad != null ? tipoActividad : "No especificado");
        return informacion.toString();
    }
	
	@Override
	public void crearId() {
		// Genera el ID
    	String id = UUID.randomUUID().toString();
        this.id= id;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TipoActividades getTipoActividad() {
		return tipoActividad;
	}
}