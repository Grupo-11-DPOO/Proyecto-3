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

import usuarios.Estudiante;

public class VentanaMenuEstudiante extends JFrame implements ActionListener{
	private Estudiante estudianteActual;
	private JButton botonMenuVerOferta;
	private JButton botonIniciarActividad;
	private JButton botonIniciarEnCurso;
	private JButton botonMenuAgregar;
	private JButton botonVerProgreso;
	private JButton botonSalirLPOAct;
	private JButton botonSalir;
	private static final String VEROFERTA = "Ver oferta de Learning Path";
	private static final String INICIAR = "Iniciar Actividad del Learning Path actual";
	private static final String COMPLETAR = "Completar actividad en Curso";
	private static final String AGREGAR = "Agregar reseñas y/o rating a actividad";
	private static final String PROGRESO = "Ver progreso del Learning Path actual";
	private static final String SALIRLP = "Salirse del Learning Path o Actividad actual";
	private static final String EXIT = "Cerrar sesión";
	
	private VentanaVerOfertaLearningPath verOfertaVentana;
	private VentanaIniciarActividad ventanaIniciarActividad;
	private VentanaCompletarActividad ventanaCompletarActividad;
	private VentanaAgregarResenaORating ventanaAgregar;
	private VentanaVerProgresoLP ventanaVerProgreso;
	private VentanaSalirLPOAct ventanaSalirLPOAct;
	
	
	public VentanaMenuEstudiante(Estudiante est) {
		estudianteActual = est;
		JLabel titulo = new JLabel("Menú Principal Estudiantes");
		
		JPanel panelBotones = new JPanel(new GridLayout(8,1));
		
		//Ver Oferta de Learning Paths
		botonMenuVerOferta = new JButton(VEROFERTA);
		botonMenuVerOferta.addActionListener(this);
		botonMenuVerOferta.setActionCommand(VEROFERTA);
		panelBotones.add(botonMenuVerOferta);
		
		//Iniciar actividad
		botonIniciarActividad= new JButton(INICIAR);
		botonIniciarActividad.addActionListener(this);
		botonIniciarActividad.setActionCommand(INICIAR);
		panelBotones.add(botonIniciarActividad);
		
		//Completar actividad
		botonIniciarEnCurso= new JButton(COMPLETAR);
		botonIniciarEnCurso.addActionListener(this);
		botonIniciarEnCurso.setActionCommand(COMPLETAR);
		panelBotones.add(botonIniciarEnCurso);
		//Agregar reseña y/o rating a una actividad
		botonMenuAgregar= new JButton(AGREGAR);
		botonMenuAgregar.addActionListener(this);
		botonMenuAgregar.setActionCommand(AGREGAR);
		panelBotones.add(botonMenuAgregar);
		//Ver el progreso del LearningPath
		botonVerProgreso= new JButton(PROGRESO);
		botonVerProgreso.addActionListener(this);
		botonVerProgreso.setActionCommand(PROGRESO);
		panelBotones.add(botonVerProgreso);
		//Salirse del LP o act en curso
		botonSalirLPOAct= new JButton(SALIRLP);
		botonSalirLPOAct.addActionListener(this);
		botonSalirLPOAct.setActionCommand(SALIRLP);
		panelBotones.add(botonSalirLPOAct);
		//Salir
		botonSalir = new JButton(EXIT);
		botonSalir.addActionListener(this);
		botonSalir.setActionCommand(EXIT);
		panelBotones.add(botonSalir);
		
		
		add(titulo, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.WEST);
		
		setTitle( "Menu Principal" );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public void verOfertaLP() {
		if(verOfertaVentana == null || !verOfertaVentana.isVisible()) {
			verOfertaVentana = new VentanaVerOfertaLearningPath(this.estudianteActual);
			verOfertaVentana.setVisible(true);
		}
	}
	
	public void iniciarActividad() {
		
		if(estudianteActual.getLearningPathEnCurso()== null) {
			JOptionPane.showMessageDialog(this,"Por favor, seleccione un LearningPath antes de iniciar una actividad");
			
		} else if(ventanaIniciarActividad == null||!ventanaIniciarActividad.isVisible()) {
			ventanaIniciarActividad = new VentanaIniciarActividad(this.estudianteActual);
			ventanaIniciarActividad.setVisible(true);
		}
	}
	public void completarActividad() {
		if(estudianteActual.getLearningPathEnCurso()== null) {
			JOptionPane.showMessageDialog(this,"No hay ninguna actividad en curso para completar.");
			
		} else if(ventanaCompletarActividad == null||!ventanaCompletarActividad.isVisible()) {
			ventanaCompletarActividad = new VentanaCompletarActividad();
			ventanaCompletarActividad.setVisible(true);
		}
	}
	
	public void agregarROR() {
		if(ventanaAgregar == null||!ventanaAgregar.isVisible()) {
			ventanaAgregar = new VentanaAgregarResenaORating();
			ventanaAgregar.setVisible(true);
		}
	}
	
	public void verProgreso() {
		if(ventanaVerProgreso == null||!ventanaVerProgreso.isVisible()) {
			ventanaVerProgreso = new VentanaVerProgresoLP(estudianteActual);
			ventanaVerProgreso.setVisible(true);
		}
	}
	
	public void salirLearningPath() {
		if(estudianteActual.getLearningPathEnCurso()== null) {
			JOptionPane.showMessageDialog(this,"No hay ningun Learning Path ni actividad en curso!");
		} else if(ventanaSalirLPOAct == null||!ventanaSalirLPOAct.isVisible()) {
			ventanaSalirLPOAct = new VentanaSalirLPOAct(estudianteActual);
			ventanaSalirLPOAct.setVisible(true);
		}
	}
	
	public void salirVentana() {
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(VEROFERTA)) {
			verOfertaLP();
		} else if(comando.equals(INICIAR)) {
			iniciarActividad();
		} else if(comando.equals(COMPLETAR)) {
			completarActividad();
		} else if(comando.equals(AGREGAR)) {
			agregarROR();
		} else if(comando.equals(PROGRESO)) {
			verProgreso();
		} else if(comando.equals(SALIRLP)) {
			salirLearningPath();
		} else if(comando.equals(EXIT)) {
			salirVentana();
		}
		
	}
	
	
}
