package persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import actividades.Actividad;
import learningPaths.LearningPath;
import usuarios.Profesor;

public class PersistenciaProfesores {
	private File archivo;
	private final static String ruta = "data/profesores.JSON";
	public JSONArray profesoresArray = new JSONArray();
	
	public PersistenciaProfesores() {	
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
    
    public JSONArray leerProfesores() {
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
                	profesoresArray = new JSONArray();
                } else {

                	try {
                		profesoresArray = new JSONArray(jsonString);
                    } catch (JSONException e) {
                        System.err.println("El contenido del archivo no es un JSONArray válido.");
                        e.printStackTrace();
                        profesoresArray = new JSONArray(); 
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("El archivo no se encontró.");
                e.printStackTrace();
                profesoresArray = new JSONArray(); 
            } catch (IOException e) {
                System.err.println("Error al leer el archivo.");
                e.printStackTrace();
                profesoresArray = new JSONArray(); 
            }
        } else {
        	profesoresArray = new JSONArray();
        }
        return profesoresArray;
    }
    
    public HashMap<String,Profesor> cargarProfesores(HashMap<String,Actividad> actividades, HashMap<String, LearningPath> learningPaths){
    	HashMap<String,Profesor> profesores = new HashMap<>();
    	JSONArray profesoresJsonArray = leerProfesores();
    	for(int i =0; i<profesoresJsonArray.length();i++) {
    		JSONObject profesorJson = profesoresJsonArray.getJSONObject(i);
    		Profesor profesor = convertirJsonToProfesor(profesorJson, actividades, learningPaths);
    		profesores.put(profesor.getLogin(), profesor);
    	}
    	return profesores;
    }
    
    public JSONObject convertirProfesorToJson(Profesor profesor) {
    	JSONObject profesorJson = new JSONObject();
    	String login = profesor.getLogin();
    	String passWord = profesor.getPassword();
    	ArrayList<String> idActividadesCreadas = profesor.getIdActividadesCreadas();
    	ArrayList<String> idLearningPathsCreados = profesor.getIdLearningPathsCreados();
    	profesorJson.put("login", login);
    	profesorJson.put("passWord", passWord);
    	profesorJson.put("idActividadesCreadas", idActividadesCreadas != null ? new JSONArray(idActividadesCreadas) : new JSONArray());
    	profesorJson.put("idLearningPathsCreados", idLearningPathsCreados != null ? new JSONArray(idLearningPathsCreados) : new JSONArray() );
    	return profesorJson;
    }
    
    public Profesor convertirJsonToProfesor(JSONObject profesorJson, HashMap<String,Actividad> actividades, HashMap<String,LearningPath> learningPaths) {
    	Profesor profesor = null;
    	String login = profesorJson.getString("login");
    	String passWord = profesorJson.getString("passWord");
    	JSONArray idActividadesCreadasArray = profesorJson.getJSONArray("idActividadesCreadas");
    	JSONArray idLearningPathsCreadosArray = profesorJson.getJSONArray("idLearningPathsCreados");
    	ArrayList<String> idLearningPathsCreados = new ArrayList<>();
        ArrayList<String> idActividadesCreadas = new ArrayList<>();
        for (int i = 0; i < idActividadesCreadasArray.length(); i++) {
        	idActividadesCreadas.add(idActividadesCreadasArray.getString(i));
        }
        for (int i = 0; i < idLearningPathsCreadosArray.length(); i++) {
        	idLearningPathsCreados.add(idLearningPathsCreadosArray.getString(i));
        }
        profesor = new Profesor(actividades,learningPaths, login, passWord);
        profesor.setIdActividadesCreadas(idActividadesCreadas);
        profesor.setIdLearningPathsCreados(idLearningPathsCreados);
    	return profesor;
    }
    
    public void guardarProfesor(Profesor profesor) {
        try {
            JSONArray profesoresArray = leerProfesores();
            
            JSONObject nuevoProfesor = convertirProfesorToJson(profesor);
            
            boolean encontrada = false;
            for (int i = 0; i < profesoresArray.length(); i++) {
                JSONObject profesorExistente = profesoresArray.getJSONObject(i);
                if (profesorExistente.getString("login").equals(profesor.getLogin())) {
                	profesoresArray.put(i, nuevoProfesor);
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
            	profesoresArray.put(nuevoProfesor);
            }
            try (FileWriter fileWriter = new FileWriter(ruta)) {
                fileWriter.write(profesoresArray.toString(4));
            }  
        } catch (Exception e) {
            System.err.println("Error al guardar el profesor: " + e.getMessage());
        }
    }
}
