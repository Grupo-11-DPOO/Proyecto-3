package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import actividades.Actividad;
import actividades.Estado;
import learningPaths.LearningPath;
import usuarios.Estudiante;

public class PersistenciaEstudiantes {
	
	private File archivo;
	private final static String ruta = "data/estudiantes.JSON";
	public JSONArray estudiantesArray = new JSONArray();
	
	public PersistenciaEstudiantes(){
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
    
    public JSONArray leerEstudiantes() {
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
                	estudiantesArray = new JSONArray();
                } else {

                	try {
                		estudiantesArray = new JSONArray(jsonString);
                    } catch (JSONException e) {
                        System.err.println("El contenido del archivo no es un JSONArray válido.");
                        e.printStackTrace();
                        estudiantesArray = new JSONArray(); 
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("El archivo no se encontró.");
                e.printStackTrace();
                estudiantesArray = new JSONArray(); 
            } catch (IOException e) {
                System.err.println("Error al leer el archivo.");
                e.printStackTrace();
                estudiantesArray = new JSONArray(); 
            }
        } else {
        	estudiantesArray = new JSONArray();
        }
        return estudiantesArray;
    }
    
    public HashMap<String,Estudiante> cargarEstudiantes(HashMap<String,Actividad> actividades, HashMap<String, LearningPath> learningPaths){
    	HashMap<String,Estudiante> estudiantes = new HashMap<>();
    	JSONArray estudiantesJsonArray = leerEstudiantes();
    	for(int i =0; i<estudiantesJsonArray.length();i++) {
    		JSONObject estudianteJson = estudiantesJsonArray.getJSONObject(i);
    		Estudiante estudiante = convertirJsonToEstudiante(estudianteJson, actividades,learningPaths);
    		estudiantes.put(estudiante.getLogin(), estudiante);
    	}
    	return estudiantes;
    }
    
    public JSONObject convertirEstudianteToJson(Estudiante estudiante) {
    	JSONObject estudianteJson = new JSONObject();
    	String login = estudiante.getLogin();
    	String passWord = estudiante.getPassword();
    	List<String> intereses = estudiante.getIntereses();
    	String idActividadEnCurso = "";
    	if(estudiante.getActividadEnCurso()!= null) {
	    	idActividadEnCurso = estudiante.getActividadEnCurso().getId();
    	}
    	String idLearningPathEnCurso = "";
    	if(estudiante.getLearningPathEnCurso()!=null) {
    	idLearningPathEnCurso = estudiante.getLearningPathEnCurso().getId();
    	}
    	HashMap<String, Estado> registroActividades = estudiante.getRegistroActividades();
    	HashMap<String, Double> registroLearningPaths = estudiante.getRegistroLearningPaths();
    	JSONObject registroJson = new JSONObject();
    	JSONObject registroLearningPathJson = new JSONObject();
        for (Map.Entry<String, Double> entrada : registroLearningPaths.entrySet()) {
            String clave = entrada.getKey();
            Double valor = entrada.getValue();  
            registroLearningPathJson.put(clave, valor); 
        }
	    for (Map.Entry<String, Estado> entrada : registroActividades.entrySet()) {
	        String clave = entrada.getKey();
	        Estado valor = entrada.getValue(); 
	        registroJson.put(clave, valor.name());
	    }
	    estudianteJson.put("registroLearningPaths", registroLearningPathJson);
	    estudianteJson.put("registroActividades", registroJson);
	    estudianteJson.put("login", login);
	    estudianteJson.put("passWord", passWord);
	    estudianteJson.put("intereses", intereses != null? new JSONArray(intereses): new JSONArray());
	    estudianteJson.put("idActividadEnCurso", idActividadEnCurso);
	    estudianteJson.put("idLearningPathEnCurso", idLearningPathEnCurso);	
    	return estudianteJson;
    }

    public Estudiante convertirJsonToEstudiante(JSONObject estudianteJson, HashMap<String,Actividad> actividades, HashMap<String,LearningPath> learningPaths) {
    	Estudiante estudiante = null;
    	String login = estudianteJson.getString("login");
        String passWord = estudianteJson.getString("passWord");
        JSONArray interesesArray = estudianteJson.getJSONArray("intereses");
        List<String> intereses = new ArrayList<>();
        for (int i = 0; i < interesesArray.length(); i++) {
            intereses.add(interesesArray.getString(i));
        }
        String idActividadEnCurso = estudianteJson.optString("idActividadEnCurso", null);
        String idLearningPathEnCurso = estudianteJson.optString("idLearningPathEnCurso", null);
        JSONObject registroJson = estudianteJson.getJSONObject("registroActividades");
        HashMap<String, Estado> registroActividades = new HashMap<>();
        for (String clave : registroJson.keySet()) {
            String estadoString = registroJson.getString(clave);
            Estado estado = Estado.valueOf(estadoString); 
            registroActividades.put(clave, estado);
        }
        JSONObject registroLearningPathsJson = estudianteJson.getJSONObject("registroLearningPaths");
        HashMap<String, Double> registroLearningPaths = new HashMap<>();
        for (String clave : registroLearningPathsJson.keySet()) {
            Double valor = registroLearningPathsJson.getDouble(clave);
            registroLearningPaths.put(clave, valor);
        }   
        Actividad actividadEnCurso = null;
        if (idActividadEnCurso != null && !idActividadEnCurso.isEmpty()) {
            actividadEnCurso = actividades.get(idActividadEnCurso); 
        }
        LearningPath learningPathEnCurso = null;
        if (idLearningPathEnCurso != null && !idLearningPathEnCurso.isEmpty()) {
            learningPathEnCurso = learningPaths.get(idLearningPathEnCurso);
        }
        estudiante = new Estudiante(actividades, learningPaths,login,passWord,intereses);
        estudiante.setActividadEnCurso(actividadEnCurso);
        estudiante.setRegistroActividades(registroActividades);
        estudiante.setRegistroLearningPaths(registroLearningPaths);
        estudiante.setLearningPathEnCurso(learningPathEnCurso);
        estudiante.setActividadEnCurso(actividadEnCurso);
        return estudiante;
    }
    
    public void guardarEstudiante(Estudiante estudiante) {
        try {
            JSONArray estudiantesArray = leerEstudiantes();
            JSONObject nuevoEstudiante = convertirEstudianteToJson(estudiante);
            boolean encontrada = false;
            for (int i = 0; i < estudiantesArray.length(); i++) {
                JSONObject estudianteExistente = estudiantesArray.getJSONObject(i);
                if (estudianteExistente.getString("login").equals(estudiante.getLogin())) {
                	estudiantesArray.put(i, nuevoEstudiante);
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
            	estudiantesArray.put(nuevoEstudiante);
            }
            try (FileWriter fileWriter = new FileWriter(ruta)) {
                fileWriter.write(estudiantesArray.toString(4)); // Formato bonito con indentación de 4 espacios
            }
            System.out.println("Estudiante guardado exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al guardar el Estudiante: " + e.getMessage());
        }
    }  
}