package persistencia;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import actividades.Actividad;
import actividades.Encuesta;
import actividades.Examen;
import actividades.Opcion;
import actividades.Quiz;
import actividades.QuizVerdad;
import actividades.Recurso;
import actividades.Tarea;
import actividades.TipoActividades;
import actividades.VerdaderoFalso;

public class PersistenciaActividades {

	private File archivo;
    private final static String ruta = "data/actividades.JSON";
    public JSONArray actividadesArray = new JSONArray();
    
    public PersistenciaActividades() {
	    }

    public Actividad convertirJsonToActividad(JSONObject jsonObject) throws JSONException, ParseException {
        Actividad actividad;
        TipoActividades tipoActividad = TipoActividades.valueOf(jsonObject.getString("tipoActividad"));
        String id = jsonObject.getString("id");
        String titulo = jsonObject.getString("titulo");
        String objetivo = jsonObject.getString("objetivo");
        String descripcion = jsonObject.getString("descripcion");
        String nivel = jsonObject.getString("nivel");
        int duracionMinutos = jsonObject.getInt("duracionMinutos");
        boolean obligatorio = jsonObject.getBoolean("obligatorio");
        
        SimpleDateFormat formateador = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        // Convertir el String a Date
        Date tiempoLimite = formateador.parse(jsonObject.getString("tiempoLimite"));
        
        //Date tiempoLimite = new Date(jsonObject.getLong("tiempoLimite"));
        float rating = (float) jsonObject.getDouble("rating");
        int cantidadRating = jsonObject.getInt("cantidadRating");
        List<String> resenas = new ArrayList<>();
        JSONArray resenasArray = jsonObject.getJSONArray("resenas");
        for (int i = 0; i < resenasArray.length(); i++) {
            resenas.add(resenasArray.getString(i));
        }
        ArrayList<Actividad> prerrequisitos = new ArrayList<>();
        JSONArray prerequisitosArray = jsonObject.getJSONArray("prerrequisitos");
        for (int i = 0; i < prerequisitosArray.length(); i++) {
            prerrequisitos.add(convertirJsonToActividad(prerequisitosArray.getJSONObject(i)));
        }
        switch (tipoActividad) {
            case Encuesta:
                actividad = new Encuesta(titulo,objetivo,descripcion,nivel,duracionMinutos,obligatorio);
                ArrayList<String> preguntas = new ArrayList<>();
                JSONArray preguntasArray = jsonObject.getJSONArray("preguntas");
                for (int i = 0; i < preguntasArray.length(); i++) {
                    preguntas.add(preguntasArray.getString(i));
                }
                ((Encuesta) actividad).setPreguntas(preguntas);
                HashMap<String, ArrayList<String>> respuestas = convertirJSONAHashMapConLista(jsonObject.getJSONObject("respuestas"));
                ((Encuesta) actividad).setRespuestas(respuestas);
                break;
            case Examen:
                actividad = new Examen(titulo,objetivo,descripcion,nivel,duracionMinutos,obligatorio);
                preguntas = new ArrayList<>();
                preguntasArray = jsonObject.getJSONArray("preguntas");
                for (int i = 0; i < preguntasArray.length(); i++) {
                    preguntas.add(preguntasArray.getString(i));
                }
                ((Examen) actividad).setPreguntas(preguntas);
                respuestas = convertirJSONAHashMapConLista(jsonObject.getJSONObject("respuestas"));
                ((Examen) actividad).setRespuestas(respuestas);
                break;
            case Quiz:
                float calificacionMinima = (float) jsonObject.getDouble("calificacionMinima");
                actividad = new Quiz(titulo,objetivo,descripcion,nivel,duracionMinutos,obligatorio,calificacionMinima);
                ArrayList<Opcion> respuestasCorrectas = new ArrayList<>();
                JSONArray respuestasCorrectasArray = jsonObject.getJSONArray("respuestasCorrectas");
                for (int i = 0; i < respuestasCorrectasArray.length(); i++) {
                    respuestasCorrectas.add(Opcion.valueOf(respuestasCorrectasArray.getString(i)));
                }
                ((Quiz) actividad).setRespuestasCorrectas(respuestasCorrectas);
                HashMap<String, ArrayList<Opcion>> respuestasEstudiantes = new HashMap<>();
                JSONObject respuestasEstudiantesJson = jsonObject.getJSONObject("respuestasEstudiantes");
                for (String clave : respuestasEstudiantesJson.keySet()) {
                    ArrayList<Opcion> opciones = new ArrayList<>();
                    JSONArray opcionesArray = respuestasEstudiantesJson.getJSONArray(clave);
                    for (int i = 0; i < opcionesArray.length(); i++) {
                        opciones.add(Opcion.valueOf(opcionesArray.getString(i)));
                    }
                    respuestasEstudiantes.put(clave, opciones);
                }
                ((Quiz) actividad).setRespuestasEstudiantes(respuestasEstudiantes);
                HashMap<String, HashMap<Opcion, HashMap<String, String>>> preguntasQuiz = new HashMap<>();
                JSONObject preguntasJson = jsonObject.getJSONObject("preguntas");
                for (String pregunta : preguntasJson.keySet()) {
                    JSONObject opcionesJson = preguntasJson.getJSONObject(pregunta);
                    HashMap<Opcion, HashMap<String, String>> opciones = new HashMap<>();
                    for (String opcionStr : opcionesJson.keySet()) {
                        Opcion opcion = Opcion.valueOf(opcionStr);
                        JSONObject detallesJson = opcionesJson.getJSONObject(opcionStr);
                        HashMap<String, String> detalles = new HashMap<>();
                        for (String detalleClave : detallesJson.keySet()) {
                            detalles.put(detalleClave, detallesJson.getString(detalleClave));
                        }
                        opciones.put(opcion, detalles);
                    }
                    preguntasQuiz.put(pregunta, opciones);
                }
                ((Quiz) actividad).setPreguntas(preguntasQuiz);
                break;
            case QuizVerdad:
                calificacionMinima = (float) jsonObject.getDouble("calificacionMinima");
                actividad = new QuizVerdad(titulo,objetivo,descripcion,nivel,duracionMinutos,obligatorio,calificacionMinima);
                ArrayList<String> preguntasQuizVerdad = new ArrayList<>();
                preguntasArray = jsonObject.getJSONArray("preguntas");
                for (int i = 0; i < preguntasArray.length(); i++) {
                    preguntasQuizVerdad.add(preguntasArray.getString(i));
                }
                ((QuizVerdad) actividad).setPreguntas(preguntasQuizVerdad);
                ArrayList<VerdaderoFalso> respuestasQuizVerdad = new ArrayList<>();
                JSONArray respuestasArray = jsonObject.getJSONArray("respuestas");
                for (int i = 0; i < respuestasArray.length(); i++) {
                    respuestasQuizVerdad.add(VerdaderoFalso.valueOf(respuestasArray.getString(i)));
                }
                ((QuizVerdad) actividad).setRespuestasCorrectas(respuestasQuizVerdad);
                HashMap<String, ArrayList<VerdaderoFalso>> respuestasEstudiantesQuizVerdad = new HashMap<>();
                respuestasEstudiantesJson = jsonObject.getJSONObject("respuestasEstudiantes");
                for (String clave : respuestasEstudiantesJson.keySet()) {
                    ArrayList<VerdaderoFalso> valores = new ArrayList<>();
                    JSONArray valoresArray = respuestasEstudiantesJson.getJSONArray(clave);
                    for (int i = 0; i < valoresArray.length(); i++) {
                        valores.add(VerdaderoFalso.valueOf(valoresArray.getString(i)));
                    }
                    respuestasEstudiantesQuizVerdad.put(clave, valores);
                }
                ((QuizVerdad) actividad).setRespuestasEstudiantes(respuestasEstudiantesQuizVerdad);
                break;
            case Recurso:
                String material = jsonObject.getString("material");
                actividad = new Recurso(titulo,objetivo,descripcion,nivel,duracionMinutos,obligatorio,material);
                break;
            case Tarea:
                String idActividadRealizar = jsonObject.getString("idActividadRealizar");
                actividad = new Tarea(titulo,objetivo,descripcion,nivel,duracionMinutos,obligatorio,idActividadRealizar);
                HashMap<String, String> respuestasTarea = new HashMap<>();
                JSONObject respuestasJson = jsonObject.getJSONObject("respuestas");
                for (String clave : respuestasJson.keySet()) {
                    respuestasTarea.put(clave, respuestasJson.getString(clave));
                }
                ((Tarea) actividad).setRespuestas(respuestasTarea);
                break;
            default:
                throw new IllegalArgumentException("Tipo de actividad no reconocido: " + tipoActividad);
        }
        actividad.setId(id);
        actividad.setTiempoLimite(tiempoLimite);
        actividad.setRating(rating);
        actividad.setCantidadRating(cantidadRating);
        actividad.setResenas(resenas);
        actividad.setPrerequisitos(prerrequisitos);
        actividad.setTipoActividad(tipoActividad);
        return actividad;
    }

    private HashMap<String, ArrayList<String>> convertirJSONAHashMapConLista(JSONObject jsonObject) {
        HashMap<String, ArrayList<String>> resultado = new HashMap<>();
        for (String clave : jsonObject.keySet()) {
            ArrayList<String> lista = new ArrayList<>();
            JSONArray jsonArray = jsonObject.getJSONArray(clave);
            for (int i = 0; i < jsonArray.length(); i++) {
                lista.add(jsonArray.getString(i));
            }
            resultado.put(clave, lista);
        }
        return resultado;
    }
    
    public JSONObject convertirActividadToJson(Actividad actividad) {
    	TipoActividades tipoActividad = actividad.getTipoActividad();
        String id = actividad.getId();
        String titulo = actividad.getTitulo();
        String objetivo = actividad.getObjetivo();
        String descripcion = actividad.getDescripcion();
        String nivel = actividad.getNivel();
        int duracionMinutos = actividad.getDuracionMinutos();
        boolean obligatorio = actividad.isObligatorio();
        String tiempoLimite = actividad.getTiempoLimite().toString();
        float rating = actividad.getRating();
        int cantidadRating = actividad.getCantidadRating();
        List<String> resenas = actividad.getResenas();
        ArrayList<Actividad> prerrequisitos = actividad.getPrerequisitos();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("titulo", titulo);
        jsonObject.put("objetivo", objetivo);
        jsonObject.put("descripcion", descripcion);
        jsonObject.put("nivel", nivel);
        jsonObject.put("duracionMinutos", duracionMinutos);
        jsonObject.put("obligatorio", obligatorio);
        jsonObject.put("tiempoLimite", tiempoLimite); 
        jsonObject.put("rating", rating);
        jsonObject.put("cantidadRating",cantidadRating );
        jsonObject.put("resenas", resenas != null ? new JSONArray(resenas) : new JSONArray());
        jsonObject.put("tipoActividad", tipoActividad != null ? tipoActividad.name() : null);
        JSONArray prerequisitosArray = new JSONArray();
        if (prerrequisitos != null) {
            for (Actividad act : prerrequisitos) {
            	JSONObject prerrequisitoJson = convertirActividadToJson(act);
                prerequisitosArray.put(prerrequisitoJson);
            }
        }
        jsonObject.put("prerrequisitos", prerequisitosArray);
        if(tipoActividad== TipoActividades.Encuesta) {
        	Encuesta encuesta = (Encuesta) actividad;
        	List<String> preguntas = encuesta.getPreguntas();
        	HashMap<String, ArrayList<String>> respuestas = encuesta.getRespuestas();
        	jsonObject.put("preguntas", preguntas != null ? new JSONArray(preguntas): new JSONArray());
        	JSONObject objectRespuestas = convertirHashMapConListaAJSON(respuestas);
        	jsonObject.put("respuestas", objectRespuestas);
        }
        else if(tipoActividad== TipoActividades.Examen) {
        	Examen examen = (Examen) actividad;
        	List<String> preguntas = examen.getPreguntas();
        	HashMap<String, ArrayList<String>> respuestas = examen.getRespuestas();
        	jsonObject.put("preguntas", preguntas != null ? new JSONArray(preguntas): new JSONArray());
        	JSONObject objectRespuestas = convertirHashMapConListaAJSON(respuestas);
        	jsonObject.put("respuestas", objectRespuestas);
        }
        else if(tipoActividad == TipoActividades.Quiz) {
        	Quiz quiz = (Quiz) actividad;
        	float calificacionMinima = quiz.getCalificacionMinima();
        	ArrayList<Opcion> respuestasCorrectas = quiz.getRespuestasCorrectas();
        	HashMap<String,HashMap<Opcion,HashMap<String,String>>> preguntas = quiz.getPreguntas();
        	HashMap<String,ArrayList<Opcion>> respuestasEstudiantes = quiz.getRespuestasEstudiantes();
        	JSONArray jsonRespuestasCorrectas = new JSONArray();
        	for(Opcion opcion: respuestasCorrectas) {
        		jsonRespuestasCorrectas.put(opcion.name());
        	}
        	jsonObject.put("calificacionMinima", calificacionMinima);
        	jsonObject.put("respuestasCorrectas", jsonRespuestasCorrectas);
        	JSONObject jsonRespuestasEstudiantes = new JSONObject();        	
        	for (String clave: respuestasEstudiantes.keySet()) {
        		ArrayList<Opcion> valores = respuestasEstudiantes.get(clave);
        		JSONArray jsonArray = new JSONArray();
        		for(Opcion opcion: valores) {
            		jsonArray.put(opcion.name());
            	}
        		jsonRespuestasEstudiantes.put(clave, jsonArray);
        	}
        	jsonObject.put("respuestasEstudiantes", jsonRespuestasEstudiantes);
        	JSONObject preguntasJson = new JSONObject();
            for (String pregunta : preguntas.keySet()) {
                HashMap<Opcion, HashMap<String, String>> opciones = preguntas.get(pregunta);
                JSONObject opcionesJson = new JSONObject();
                for (Opcion opcion : opciones.keySet()) {
                    HashMap<String, String> detalles = opciones.get(opcion);
                    JSONObject detallesJson = new JSONObject(detalles);
                    opcionesJson.put(opcion.name(), detallesJson);
                }
                preguntasJson.put(pregunta, opcionesJson);
            }
            jsonObject.put("preguntas", preguntasJson);
        }
        else if (tipoActividad == TipoActividades.QuizVerdad) {
        	QuizVerdad quizVerdad = (QuizVerdad) actividad;
        	float calificacionMinima = quizVerdad.getCalificacionMinima();
        	ArrayList<String> preguntas = quizVerdad.getPreguntas();
        	ArrayList<VerdaderoFalso> respuestas = quizVerdad.getRespuestasCorrectas();
        	HashMap<String, ArrayList<VerdaderoFalso>> respuestasEstudiantes = quizVerdad.getRespuestasEstudiantes();
        	jsonObject.put("calificacionMinima", calificacionMinima);
        	jsonObject.put("preguntas", preguntas != null ? new JSONArray(preguntas): new JSONArray());
        	JSONArray jsonRespuestas = new JSONArray();
        	for(VerdaderoFalso verdad: respuestas) {
        		jsonRespuestas.put(verdad.name());
        	}
        	jsonObject.put("respuestas", jsonRespuestas);
        	JSONObject jsonRespuestasEstudiantes = new JSONObject();        	
        	for (String clave: respuestasEstudiantes.keySet()) {
        		ArrayList<VerdaderoFalso> valores = respuestasEstudiantes.get(clave);
        		JSONArray jsonArray = new JSONArray();
        		for(VerdaderoFalso verdad: valores) {
            		jsonArray.put(verdad.name());
            	}
        		jsonRespuestasEstudiantes.put(clave, jsonArray);
        	}
        	jsonObject.put("respuestasEstudiantes", jsonRespuestasEstudiantes);
        }
        else if(tipoActividad == TipoActividades.Recurso) {
        	Recurso recurso = (Recurso) actividad;
        	String material = recurso.getMaterial();
        	jsonObject.put("material", material);
        }
        else if (tipoActividad == TipoActividades.Tarea) {
        	Tarea tarea = (Tarea) actividad;
        	String idActividadRealizar = tarea.getIdActividadesARealizar();
        	HashMap<String,String> respuestas = tarea.getRespuestas();
        	jsonObject.put("idActividadRealizar", idActividadRealizar);
        	JSONObject respuestasJson  = new JSONObject(respuestas);
        	jsonObject.put("respuestas", respuestasJson);
        }
        return jsonObject;
    }
    
    public HashMap<String,Actividad> cargarActividades() throws JSONException, ParseException{
    	JSONArray actividadesJsonArray = leerActividades();
    	HashMap<String,Actividad> actividades = new HashMap<>();
    	for(int i =0; i<actividadesJsonArray.length(); i++) {
    		JSONObject actividadJson = actividadesJsonArray.getJSONObject(i);
    		Actividad actividad = convertirJsonToActividad(actividadJson);
    		actividades.put(actividad.getId(), actividad);
    	}
    	return actividades; 
    }
    
    public JSONObject convertirHashMapConListaAJSON(HashMap<String,ArrayList<String>> respuestas) {
        JSONObject jsonObject = new JSONObject();
        for (String clave : respuestas.keySet()) {
            ArrayList<String> valores = respuestas.get(clave);
            JSONArray jsonArray = new JSONArray(valores);
            jsonObject.put(clave, jsonArray);
        }
        return jsonObject;
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

    public JSONArray leerActividades() {
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
                    actividadesArray = new JSONArray();
                } else {
                	try {
                        actividadesArray = new JSONArray(jsonString);
                    } catch (JSONException e) {
                        System.err.println("El contenido del archivo no es un JSONArray válido.");
                        e.printStackTrace();
                        actividadesArray = new JSONArray(); 
                    }
                }
            } catch (FileNotFoundException e) {
                System.err.println("El archivo no se encontró.");
                e.printStackTrace();
                actividadesArray = new JSONArray(); 
            } catch (IOException e) {
                System.err.println("Error al leer el archivo.");
                e.printStackTrace();
                actividadesArray = new JSONArray(); 
            }
        } else {
            actividadesArray = new JSONArray();
        }
        return actividadesArray;
    }

    public void guardarActividad(Actividad actividad) {
        try {
            JSONArray actividadesArray = leerActividades();
            JSONObject nuevaActividadJson = convertirActividadToJson(actividad);
            boolean encontrada = false;
            for (int i = 0; i < actividadesArray.length(); i++) {
                JSONObject actividadExistente = actividadesArray.getJSONObject(i);
                if (actividadExistente.getString("id").equals(actividad.getId())) {
                    actividadesArray.put(i, nuevaActividadJson);
                    encontrada = true;
                    break;
                }
            }
            if (!encontrada) {
                actividadesArray.put(nuevaActividadJson);
            }
            try (FileWriter fileWriter = new FileWriter(ruta)) {
                fileWriter.write(actividadesArray.toString(4)); // Formato bonito con indentación de 4 espacios
            }
            System.out.println("Actividad guardada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error al guardar la actividad: " + e.getMessage());
        }
    }
}