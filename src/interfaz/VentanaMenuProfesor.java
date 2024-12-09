package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaMenuProfesor extends JFrame implements ActionListener{

	private Profesor profesorActual;
	private JButton botonMenuCrearLearningPath;
	private JButton botonMenuVerYEditarLearningPath;
	private JButton botonCrearActividad;
	private JButton botonMenuClonarActividad;
	private JButton botonMenuVerYEditarActividad;
	private JButton botonMenuAgregar;
	private JButton botonMenuVerEstadisticas;
	private JButton botonMenuSalir;
	private VentanaCrearLearningPath ventanaCrearLearningPath;
	private VentanaVerYEditarLearningPath ventanaVerYEditarLearningPath;
	private VentanaCrearActividad ventanaCrearActividad;
	private VentanaClonar ventanaClonar;
	private VentanaAgregarResenaORating ventanaAgregarRating;
	private static final String CREAR = "Crear Learning Path";
	private static final String VERYDITAR = "Ver y Editar Learning Path";
	private static final String CREAR_ACTIVIDAD = "Crear Actividad";
	private static final String VERYEDITARACTI = "Ver y Editar Actividades";
	private static final String CLONAR = "Clonar una Actividad";
	private static final String AGREGAR = "Agregar reseñas y/o rating (Actividad)";
	private static final String VERSTATS = "Ver Estadísticas";
	private static final String SALIR = "Salir";
	public VentanaMenuProfesor(Profesor profesorActual) {
		
		setLayout(new BorderLayout());
		this.profesorActual = profesorActual;
		
		JLabel titulo = new JLabel("Menú principal profesores");
		
		JPanel panelBotones = new JPanel(new GridLayout(8,1));
		
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
		
		//Clonar actividad
		botonMenuClonarActividad = new JButton(CLONAR);
		botonMenuClonarActividad.addActionListener(this);
		botonMenuClonarActividad.setActionCommand(CLONAR);
		panelBotones.add(botonMenuClonarActividad);
		
		//Ver y editar Actividad
		botonMenuVerYEditarActividad= new JButton(VERYEDITARACTI);
		botonMenuVerYEditarActividad.addActionListener(this);
		botonMenuVerYEditarActividad.setActionCommand(VERYEDITARACTI);
		panelBotones.add(botonMenuVerYEditarActividad);
		//Agregar reseña o Rating
		botonMenuAgregar = new JButton(AGREGAR);
		botonMenuAgregar.addActionListener(this);
		botonMenuAgregar.setActionCommand(AGREGAR);
		panelBotones.add(botonMenuAgregar);
		//VerEstadisticas
		botonMenuVerEstadisticas = new JButton(VERSTATS);
		botonMenuVerEstadisticas.addActionListener(this);
		botonMenuVerEstadisticas.setActionCommand(VERSTATS);
		panelBotones.add(botonMenuVerEstadisticas);
		//Salir
		botonMenuSalir = new JButton(SALIR);
		botonMenuSalir.addActionListener(this);
		botonMenuSalir.setActionCommand(SALIR);
		panelBotones.add(botonMenuSalir);
		
		add(titulo, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.WEST);
		// Usuario
		JPanel panelNombre = new JPanel();
		JLabel labelProfesor = new JLabel("Bienvenid@ "+profesorActual.getLogin()+"   ");
		panelNombre.add(labelProfesor);
		add(panelNombre, BorderLayout.EAST);
        // Termina de configurar la ventana
        setTitle( "Menu Principal" );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize( 600, 600 );
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
	
	public void clonarActividad() {
		if (ventanaClonar == null || !ventanaClonar.isVisible()) {
			ventanaClonar = new VentanaClonar(profesorActual);
			ventanaClonar.setVisible(true);
		}
	}
	
	public void agregarResenaORating() {
		if (ventanaAgregarRating == null || !ventanaAgregarRating.isVisible()) {
			ventanaAgregarRating = new VentanaAgregarResenaORating();
			ventanaAgregarRating.setVisible(true);
		}
	}
	
	public void verEstadisticas() {
		JOptionPane.showMessageDialog(this,"La ventana de ver Estadisticas no esta funcionando en estos momentos...\n"
				+ " espera a nuestras proximas actualizaciones para disfrutar de esta función.");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if (comando.equals(CREAR)) {
            crearLearningPath();
        } else if (comando.equals(VERYDITAR)) {
        	verYEditarLearningPath();
        } else if (comando.equals(CREAR_ACTIVIDAD)) {
        	crearActividad();
        } else if (comando.equals(CLONAR)) {
        	clonarActividad();
        } else if(comando.equals(AGREGAR)) {
        	agregarResenaORating();
        } else if(comando.equals(VERSTATS)) {
        	verEstadisticas();
        } else if (comando.equals(SALIR)) {
        	System.exit(0);
        }
	}
}