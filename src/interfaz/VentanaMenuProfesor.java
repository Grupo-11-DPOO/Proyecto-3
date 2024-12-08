package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaMenuProfesor extends JFrame implements ActionListener{

	private Profesor profesorActual;
	private JButton botonMenuCrearLearningPath;
	private JButton botonMenuVerYEditarLearningPath;
	private JButton botonCrearActividad;
	private VentanaCrearLearningPath ventanaCrearLearningPath;
	private VentanaVerYEditarLearningPath ventanaVerYEditarLearningPath;
	private VentanaCrearActividad ventanaCrearActividad;
	private static final String CREAR = "Crear Learning Path";
	private static final String VERYDITAR = "Ver y Editar Learning Path";
	private static final String CREAR_ACTIVIDAD = "Crear actividad";
	
	public VentanaMenuProfesor(Profesor profesorActual) {
		
		setLayout(new BorderLayout());
		this.profesorActual = profesorActual;
		
		JLabel titulo = new JLabel("Menú principal profesores");
		
		JPanel panelBotones = new JPanel();
		
		// Crear Learning Path
		botonMenuCrearLearningPath = new JButton(CREAR);
		botonMenuCrearLearningPath.addActionListener(this);
		botonMenuCrearLearningPath.setActionCommand(CREAR);
		panelBotones.add(botonMenuCrearLearningPath);
		
		// Ver y editar Learning Path
		botonMenuVerYEditarLearningPath = new JButton(VERYDITAR);
		botonMenuVerYEditarLearningPath.addActionListener(this);
		botonMenuVerYEditarLearningPath.setActionCommand(VERYDITAR);
		panelBotones.add(botonMenuVerYEditarLearningPath);
		
		// Crear actividad
		botonCrearActividad = new JButton(CREAR_ACTIVIDAD);
		botonCrearActividad.addActionListener(this);
		botonCrearActividad.setActionCommand(CREAR_ACTIVIDAD);
		panelBotones.add(botonCrearActividad);

		
		add(titulo, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.SOUTH);
		
		
        // Termina de configurar la ventana
        setTitle( "Menu Principal" );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
		
	}
	
	public void crearLearningPath() {
        if( ventanaCrearLearningPath== null || !ventanaCrearLearningPath.isVisible( ) )
        {
        	ventanaCrearLearningPath = new VentanaCrearLearningPath(profesorActual);
        	ventanaCrearLearningPath.setVisible( true );
        }
	}
	
	public void verYEditarLearningPath() {
        if( ventanaVerYEditarLearningPath== null || !ventanaVerYEditarLearningPath.isVisible( ) )
        {
        	ventanaVerYEditarLearningPath = new VentanaVerYEditarLearningPath(profesorActual);
        	ventanaVerYEditarLearningPath.setVisible( true );
        }
	}
	
	public void crearActividad() {
        if( ventanaCrearActividad== null || !ventanaCrearActividad.isVisible( ) )
        {
        	ventanaCrearActividad = new VentanaCrearActividad(profesorActual);
        	ventanaCrearActividad.setVisible( true );
        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals( CREAR ) )
        {
            crearLearningPath();
        } else if (comando.equals(VERYDITAR)) {
        	verYEditarLearningPath();
        } else if (comando.equals(CREAR_ACTIVIDAD)) {
        	crearActividad();
        }
	}
	
	
	
}
