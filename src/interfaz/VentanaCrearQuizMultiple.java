package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import actividades.Opcion;
import actividades.Quiz;
import actividades.Recurso;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaCrearQuizMultiple extends JFrame implements ActionListener {

	private Profesor profesorActual;
	private String titulo;
	private String objetivo;
	private String descripcion;
	private String nivel;
	private int duracionMinutos;
	private boolean obligatorio;
	private JCheckBox chkPreRequisitos;
	private JButton botonCrear;
	private JTextField txtCalificacionMinima;
	private static final String Crear = "Crear";
	private VentanaAgregarPrerequisitos ventanaPrerequisitos;
	private Quiz quizMultiple;
	private HashMap<String, HashMap<Opcion, HashMap<String,String>>> preguntas;
	private VentanaAgregarPreguntas ventanaAgregarPreguntas;
	
	public VentanaCrearQuizMultiple(Profesor profesorActual, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) {
		
		this.profesorActual = profesorActual;
		this.titulo = titulo;
		this.objetivo = objetivo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.duracionMinutos = duracionMinutos;
		this.obligatorio = obligatorio;
		
		setLayout(new GridLayout(4, 1));
		
		JPanel panelCalificacion = new JPanel();
		JLabel labelCalificacion = new JLabel("Calificación mínima (valores enteros entre 0 y 100 exclusivamente)");
		txtCalificacionMinima = new JTextField(20);
		panelCalificacion.add(labelCalificacion);
		panelCalificacion.add(txtCalificacionMinima);
		add(panelCalificacion);
		
		
		
		
        // Termina de configurar la ventana
        setTitle( "Crear quiz (opción múltiple)" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 500, 800 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
		
	}
	
	public void setPreguntas(HashMap<String, HashMap<Opcion, HashMap<String,String>>> preguntas) {
		this.preguntas = preguntas;
	}
	
	public void crear() {
		String stringCalificacionMinima = txtCalificacionMinima.getText();
		try {
            int cantidad = Integer.parseInt(stringCalificacionMinima);
            if (cantidad > 0 && cantidad <= 100) {
    			try {
    				if (!preguntas.isEmpty()) {
    					quizMultiple = profesorActual.crearActividadQuiz(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, cantidad);    					
    				} else {
    					JOptionPane.showMessageDialog(this,  "No puede crear un quiz sin preguntas.", "Falta de preguntas", JOptionPane.ERROR_MESSAGE);    					
    				}
    				
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    			if (chkPreRequisitos.isSelected()) {
    				mostrarVentanaPrerequisitos();
    				this.dispose();
    			} else {
    				profesorActual.guardarActividad(quizMultiple);
    				VentanaPrincipal.sistemaRegistro.guardarActividad(quizMultiple);
    				VentanaPrincipal.sistemaRegistro.guardarProfesor(profesorActual);
    				this.dispose();			
    			}
            	
            } else {
            	JOptionPane.showMessageDialog(this,  "Input inválido. Por favor solo ingrese valores entre 0 y 100", "Input error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,  "Input inválido. Por favor solo ingrese enteros para calificación mínima.", "Input error", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void mostrarVentanaAgregarPreguntas() {
		if( ventanaAgregarPreguntas == null || ! ventanaAgregarPreguntas.isVisible( ) )
		{
			ventanaAgregarPreguntas = new VentanaAgregarPreguntas(this);
			ventanaAgregarPreguntas.setVisible(true);
		}
	}
	
	public void mostrarVentanaPrerequisitos() {
		if( ventanaPrerequisitos == null || ! ventanaPrerequisitos.isVisible( ) )
		{
    		ventanaPrerequisitos = new VentanaAgregarPrerequisitos(quizMultiple, profesorActual);
    		ventanaPrerequisitos.setVisible(true);
    		
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if (comando.equals(Crear)) {
        	crear();
        }
	}
}
