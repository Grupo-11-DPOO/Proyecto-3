package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import actividades.Actividad;
import controller.SistemaRegistro;
import learningPaths.LearningPath;
import usuarios.Estudiante;
import usuarios.Profesor;


@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {

	public static SistemaRegistro sistemaRegistro;
	private static HashMap<String, Profesor> datosProfesor;
	private static HashMap<String, Estudiante> datosEstudiante;
	public static HashMap<String, Actividad> actividades;
	public static HashMap<String, LearningPath> learningPaths;
	
	PanelIngreso pIngreso;
	PanelBotones pBotones;
	
	
	public VentanaPrincipal() {
		
		setLayout( new BorderLayout( ) );
		
		
		// Panel nombre de app
		JTextPane paneTitulo = new JTextPane();
		paneTitulo.setText("Bienvenido al Sistema Operativo de Learning Paths!");
		
		// TODO centrar y poner bonito
        add( paneTitulo, BorderLayout.NORTH );
        
        // TODO tener un panel central
        pIngreso = new PanelIngreso();
        add(pIngreso, BorderLayout.CENTER);

        // TODO panel de botones iniciar sesion registrarse 
        pBotones = new PanelBotones(this);

        add(pBotones, BorderLayout.SOUTH);
 
        
		
        // Termina de configurar la ventana
        setTitle( "Learning Paths G11" );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( 400, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
        
	}
	
	public void mostrarVentanaIniciarSesion() {
		System.out.println("INICIAR sesion");
	}
	
	
	public void mostrarVentanaRegistrarse() {
		
		System.out.println("registro");
	}

	

    /**
     * Inicia la aplicación
     * @param args
     */
    public static void main( String[] args )
    {
		sistemaRegistro = new SistemaRegistro();
		datosProfesor = sistemaRegistro.getDatosProfesores();
		datosEstudiante = sistemaRegistro.getDatosEstudiantes();
		actividades = sistemaRegistro.actividades;
		learningPaths = sistemaRegistro.learningPaths;
        
		new VentanaPrincipal();
     
    }
	
	
}
