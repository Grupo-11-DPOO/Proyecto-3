package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import actividades.Opcion;
import actividades.Quiz;
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
	private JButton botonAgregarPreguntas;
	private JButton botonCrear;
	private JTextField txtCalificacionMinima;
	private static final String Crear = "Crear";
	private static final String AgregarPreguntas = "Agregar preguntas";
	private VentanaAgregarPrerequisitos ventanaPrerequisitos;
	private Quiz quizMultiple;
	private HashMap<String, HashMap<Opcion, HashMap<String,String>>> preguntas;
	private ArrayList<Opcion> respuestasCorrectas;
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
		panelCalificacion.setLayout(new GridLayout(1,2));
		JLabel labelCalificacion = new JLabel("Calificación mínima (entre 0 y 100)");
		txtCalificacionMinima = new JTextField(20);
		panelCalificacion.add(labelCalificacion);
		panelCalificacion.add(txtCalificacionMinima);
		add(panelCalificacion);
		
		JPanel panelPrereq = new JPanel();
		panelPrereq.setLayout(new BorderLayout());
		chkPreRequisitos = new JCheckBox("¿Tiene prerequisitos? ");	
		chkPreRequisitos.setHorizontalTextPosition(SwingConstants.LEFT);
		panelPrereq.add(chkPreRequisitos, BorderLayout.CENTER);
		add(panelPrereq);
		
		botonAgregarPreguntas = new JButton(AgregarPreguntas);
		botonAgregarPreguntas.addActionListener(this);
		botonAgregarPreguntas.setActionCommand(AgregarPreguntas);
		add(botonAgregarPreguntas);
		
		botonCrear = new JButton(Crear);
		botonCrear.addActionListener(this);
		botonCrear.setActionCommand(Crear);
		add(botonCrear);
		
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
	
	public void setRespuestasCorrectas(ArrayList<Opcion> respuestasCorrectas) {
		this.respuestasCorrectas = respuestasCorrectas;
	}
	
	public void crear() {
		String stringCalificacionMinima = txtCalificacionMinima.getText();
		try {
            int cantidad = Integer.parseInt(stringCalificacionMinima);
            if (cantidad > 0 && cantidad <= 100) {
    			try {
    				if (preguntas != null) {
    					quizMultiple = profesorActual.crearActividadQuiz(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, cantidad);    					
	    				quizMultiple.setPreguntas(preguntas);
	    				quizMultiple.setRespuestasCorrectas(respuestasCorrectas);
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
    					JOptionPane.showMessageDialog(this,  "No puede crear un quiz sin preguntas.", "Falta de preguntas", JOptionPane.ERROR_MESSAGE);    					
    				}
    				
    			} catch (Exception e) {
    				e.printStackTrace();
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
        } else if (comando.equals(AgregarPreguntas)){
        	mostrarVentanaAgregarPreguntas();
        }
	}
}
