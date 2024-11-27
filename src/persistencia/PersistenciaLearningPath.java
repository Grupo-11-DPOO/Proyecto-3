package persistencia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import actividades.Actividad;
import learningPaths.LearningPath;

public class PersistenciaLearningPath {

	private File archivo;
    private final static String ruta = "data/learningPaths.JSON";
    public JSONArray learningPathArray = new JSONArray();
    
    public PersistenciaLearningPath() {
	    }
    
    public void crearArchivo() {
        archivo = new File(ruta);
	    File carpeta = archivo.getParentFile();
		if (!carpeta.exists()) {
			 	carpeta.mkdirs();
			}
		 try {
		        // Si el archivo no existe, lo crea
		        if (!archivo.exists()) {
		            archivo.createNewFile();
		        } else {
		        }
		    } catch (IOException e) {
		        System.err.println("Error al crear o utilizar el archivo: " + e.getMessage());
		    }
	    }
    
    public JSONArray leerLearningPaths() {
    	crearArchivo();
        if (archivo.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
                StringBuilder stringBuilder = new StringBuilder();
                String linea;
                while ((linea = reader.readLine()) != null) {
                    stringBuilder.append(linea);
                }
                String jsonString = stringBuilder.toString().trim();
                if (jsonString.isEmpty()) {
                	learningPathArray = new JSONArray();
                } else {

                	try {
                		learningPathArray = new JSONArray(jsonString);
                    } catch (JSONException e) {
                        System.err.println("El contenido del archivo no es un JSONArray válido.");
                        e.printStackTrace();
                        learningPathArray = new JSONArray(); 
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("El archivo no se encontró.");
                e.printStackTrace();
                learningPathArray = new JSONArray(); 
            } catch (IOException e) {
                System.err.println("Error al leer el archivo.");
                e.printStackTrace();
                learningPathArray = new JSONArray(); 
            }
        } else {
        	learningPathArray = new JSONArray();
        }
        return learningPathArray;
    }
    
    public HashMap<String, LearningPath> cargarLearningPaths(HashMap<String,Actividad> actividades ){
    	JSONArray learningPathsJsonArray = leerLearningPaths();
    	HashMap<String,LearningPath> learningPaths= new HashMap<>();
    	for(int i = 0; i<learningPathsJsonArray.length();i++) {
    		JSONObject learningPathJson = learningPathsJsonArray.getJSONObject(i);
    		LearningPath learningPath = convertirJsonToLearningPath(learningPathJson,actividades);
    		learningPaths.put(learningPath.getId(), learningPath);
    	}
    	return learningPaths;
    }
    
    public JSONObject convertirLearningPathToJson(LearningPath learningPath) {
    	ArrayList<String> idActividades = new ArrayList<>();
    	for(Actividad actividad: learningPath.getListaActividades()) {
    		idActividades.add(actividad.getId());
    	}
    	JSONObject jsonObject = new JSONObject();
    	JSONArray actividadesArray = new JSONArray(idActividades);
    	String titulo = learningPath.getTitulo();
    	String id = learningPath.getId();
    	String descripcion = learningPath.getDescripcion();
    	String nivel = learningPath.getNivel();
    	String fechaCreacion = learningPath.getFechaCreacion().toString();
    	String fechaModificacion = learningPath.getFechaModificacion().toString();
    	double rating = learningPath.getRating();
    	int duracion = learningPath.getDuracion();
    	int version = learningPath.getVersion();
    	jsonObject.put("id", id);
    	jsonObject.put("titulo", titulo);
    	jsonObject.put("actividades",actividadesArray );
    	jsonObject.put("descripcion", descripcion);
    	jsonObject.put("nivel", nivel);
    	jsonObject.put("fechaCreacion", fechaCreacion);
    	jsonObject.put("fechaModificacion", fechaModificacion);
    	jsonObject.put("rating", rating);
    	jsonObject.put("duracion", duracion);
    	jsonObject.put("version", version);
    	return jsonObject;
    }
    
    public LearningPath convertirJsonToLearningPath(JSONObject jsonObject, HashMap<String, Actividad> actividades) {
        // Recuperar los atributos básicos del LearningPath
        String id = jsonObject.getString("id");
        String titulo = jsonObject.getString("titulo");
        String descripcion = jsonObject.getString("descripcion");
        String nivel = jsonObject.getString("nivel");
        String fechaCreacionStr = jsonObject.getString("fechaCreacion");
        String fechaModificacionStr = jsonObject.getString("fechaModificacion");
        double rating = jsonObject.getDouble("rating");
        int duracion = jsonObject.getInt("duracion");
        int version = jsonObject.getInt("version");
        // Parsear fechas desde String
        LocalDateTime fechaCreacion = LocalDateTime.parse(fechaCreacionStr);
        LocalDateTime fechaModificacion = LocalDateTime.parse(fechaModificacionStr);
        // Recuperar la lista de actividades
        JSONArray actividadesArray = jsonObject.getJSONArray("actividades");
        ArrayList<Actividad> listaActividades = new ArrayList<>();
        for (int i = 0; i < actividadesArray.length(); i++) {
            String actividadId = actividadesArray.getString(i);
            Actividad actividad = actividades.get(actividadId); 
            if (actividad != null) {
                listaActividades.add(actividad);
            } else {
                System.err.println("Advertencia: No se encontró la actividad con ID: " + actividadId);
            }
        }
        LearningPath learningPath = new LearningPath(titulo,descripcion,nivel);
        learningPath.setActividades(listaActividades);
        learningPath.setDuracion(duracion);
        learningPath.setFechaCreacion(fechaCreacion);
        learningPath.setFechaModificacion(fechaModificacion);
        learningPath.setId(id);
        learningPath.setDuracion(duracion);
        learningPath.setVersion(version);
        learningPath.setRating(rating);
        return learningPath;
        // Crear y retornar el objeto LearningPath
    }
    
    public void guardarLearningPath(LearningPath learningPath) {
        try {
            JSONArray learningPathArray = leerLearningPaths();
            JSONObject nuevoLearnignPathJson = convertirLearningPathToJson(learningPath);
            boolean encontrada = false;
            for (int i = 0; i < learningPathArray.length(); i++) {
                JSONObject learningPathExistente = learningPathArray.getJSONObject(i);
                if (learningPathExistente.getString("id").equals(learningPath.getId())) {
                	learningPathArray.put(i, nuevoLearnignPathJson);
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
            	learningPathArray.put(nuevoLearnignPathJson);
            }
            try (FileWriter fileWriter = new FileWriter(ruta)) {
                fileWriter.write(learningPathArray.toString(4)); 
            }
        } catch (Exception e) {
            System.err.println("Error al guardar el learningPath: " + e.getMessage());
        }
    }
}