package interfaz;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	
	private VentanaRegistroProfesor ventanaRegistroProfesores;
	private VentanaRegistroEstudiante ventanaRegistroEstudiantes;
	private VentanaMenuProfesor ventanaMenuProfesor;
	
	private PanelIngreso pIngreso;
	private PanelBotones pBotones;
	
	
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
	
	public void iniciarSesion() {
		if (pIngreso.getTipoUsuario() == null) {
			JOptionPane.showMessageDialog(this, "Tiene que escoger un tipo de usuario", "Usuario indefinido", JOptionPane.ERROR_MESSAGE);
		} else {
			// Sacamos la info del usuario
			String login = pIngreso.getLogin();
			String password = pIngreso.getPassword();
			
			if (pIngreso.getTipoUsuario() == "Profesor") {
				if (sistemaRegistro.iniciarSesionProfesor(login, password)) {
					// Menu profesor
					// TODO Ventana profesor
					Profesor profesor = datosProfesor.get(login);
					mostrarVentanaMenuProfesor(profesor);
					this.dispose();
					
				} else {
					// Datos incorrectos
					JOptionPane.showMessageDialog(this, "Su login y/o contraseña son incorrectos.", "Credenciales incorrectos", JOptionPane.OK_OPTION);
				}
			} else {
				if (sistemaRegistro.iniciarSesionEstudiante(login, password)) {
					// Menu estudiante
					// TODO Ventana estudiante
					System.out.println("ENTRA VENTANA ESTUDIANTE");
				} else {
					// Datos incorrectos
					JOptionPane.showMessageDialog(this, "Su login y/o contraseña son incorrectos.", "Credenciales incorrectos", JOptionPane.OK_OPTION);
				}
			}
		}
	}
	
	public void escogerTipoUsuario() {
		String[] opcionesTipoUsuario = {"Profesor", "Estudiante", "Volver"};
		
		int seleccion = JOptionPane.showOptionDialog(
			    this,                              
			    "Seleccione una opción",         
			    "Selector de tipo de usuario",
			    JOptionPane.DEFAULT_OPTION,
			    JOptionPane.INFORMATION_MESSAGE,
			    null,                            
			    opcionesTipoUsuario,   
			    opcionesTipoUsuario[0]      
			);
		
		switch (seleccion) {
	    case 0:
	        mostrarVentanaRegistroProfesores();
	        break;
	    case 1:
	        mostrarVentanaRegistroEstudiantes();
	        break;
	    case 2:
	        break;
	    default:
	        break;
		}
	}
	
    public void mostrarVentanaRegistroProfesores( )
    {
        if( ventanaRegistroProfesores== null || !ventanaRegistroProfesores.isVisible( ) )
        {
        	ventanaRegistroProfesores = new VentanaRegistroProfesor( this );
        	ventanaRegistroProfesores.setVisible( true );
        }
	}
    
    
    public void mostrarVentanaRegistroEstudiantes( )
    {
    	if( ventanaRegistroEstudiantes== null || !ventanaRegistroEstudiantes.isVisible( ) )
    	{
    		ventanaRegistroEstudiantes = new VentanaRegistroEstudiante( this );
    		ventanaRegistroEstudiantes.setVisible( true );
    	}
    }

	public void mostrarVentanaMenuProfesor(Profesor profesor) {
    	if( ventanaMenuProfesor== null || !ventanaMenuProfesor.isVisible( ) )
    	{
    		ventanaMenuProfesor = new VentanaMenuProfesor( profesor );
    		ventanaMenuProfesor.setVisible( true );
    	}
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
