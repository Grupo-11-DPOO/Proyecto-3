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
import actividades.Actividad;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaEditarActividad extends JFrame implements ActionListener{
	private JPanel panelBotones;
	private JButton botonTitulo;
	private JButton botonDescripcion;
	private JButton botonNivel;
	private JButton botonEditarObjetivo;
	private static final String TITULO = "Editar título";
	private static final String OBJETIVO = "Editar Objetivo";
	private static final String DESCRIPCION = "Editar descripción";
	private static final String NIVEL = "Editar nivel";
	
	private VentanaVerYEditarActividades ventanaPadre;
	private Actividad actividad;
	private Profesor profesorActual;
	
	public VentanaEditarActividad(VentanaVerYEditarActividades papa, Actividad activ, Profesor profesorActivo) {
		setLayout( new BorderLayout());
		panelBotones = new JPanel(new GridLayout(4,1));
		this.profesorActual = profesorActivo;
		this.ventanaPadre = papa;
		this.actividad = activ;
		String titulo = actividad.getTitulo();
		
		JLabel labelTitulo = new JLabel("Editar actividad "+titulo);
		add(labelTitulo, BorderLayout.NORTH);
		
		botonTitulo = new JButton(TITULO);
		botonTitulo.addActionListener(this);
		botonTitulo.setActionCommand(TITULO);
		panelBotones.add(botonTitulo);
		
		botonDescripcion = new JButton(DESCRIPCION);
		botonDescripcion.addActionListener(this);
		botonDescripcion.setActionCommand(DESCRIPCION);
		panelBotones.add(botonDescripcion);
		
		botonNivel = new JButton(NIVEL);
		botonNivel.addActionListener(this);
		botonNivel.setActionCommand(NIVEL);
		panelBotones.add(botonNivel);
		
		
		botonEditarObjetivo = new JButton(OBJETIVO);
		botonEditarObjetivo.addActionListener(this);
		botonEditarObjetivo.setActionCommand(OBJETIVO);
		panelBotones.add(botonEditarObjetivo);
		
		add(panelBotones, BorderLayout.WEST);
		
        // Termina de configurar la ventana
        setTitle( "Ver y Editar Actividades");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 400 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
		
	}
	
	public void actualizarTitulo() {
		String nuevoTitulo = JOptionPane.showInputDialog("Ingrese el nuevo título");
		// Actualizar info
		actividad.setTitulo(nuevoTitulo);
		VentanaPrincipal.sistemaRegistro.guardarActividad(actividad);
		VentanaPrincipal.sistemaRegistro.cargarProfesores(VentanaPrincipal.actividades, VentanaPrincipal.learningPaths);
		this.dispose();
		new VentanaEditarActividad(ventanaPadre, actividad, profesorActual);
	}
	
	public void actualizarDescripcion() {
		String nuevaDescripcion = JOptionPane.showInputDialog("Ingrese la nueva descripción");
		// Actualizar info
		actividad.setDescripcion(nuevaDescripcion);
		VentanaPrincipal.sistemaRegistro.guardarActividad(actividad);
		VentanaPrincipal.sistemaRegistro.cargarProfesores(VentanaPrincipal.actividades, VentanaPrincipal.learningPaths);
		this.dispose();
		new VentanaEditarActividad(ventanaPadre, actividad, profesorActual);
	}
	
	public void actualizarNivel() {
		String[] niveles = {"Bajo", "Intermedio", "Avanzado"};
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione un nuevo nivel:", "Opciones de Nivel", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, niveles, niveles[0]);
        String nivel = null;
        switch (seleccion) {
        case 0:
        	// Bajo
        	nivel = "Bajo";
        	break;
        case 1:
        	// Intermedio
        	nivel = "Intermedio";
        	break;
        case 2:
        	// Avanzado
        	nivel = "Avanzado";
        	break;
        }
        actividad.setNivel(nivel);
		VentanaPrincipal.sistemaRegistro.guardarActividad(actividad);
		VentanaPrincipal.sistemaRegistro.cargarProfesores(VentanaPrincipal.actividades, VentanaPrincipal.learningPaths);
		this.dispose();
		new VentanaEditarActividad(ventanaPadre, actividad, profesorActual);
	}
	
	public void actualizarObjetivo() {
		String nuevoObjetivo = JOptionPane.showInputDialog("Ingrese el nuevo obejtivo de la actividad.");
		// Actualizar info
		actividad.setObjetivo(nuevoObjetivo);
		VentanaPrincipal.sistemaRegistro.guardarActividad(actividad);
		VentanaPrincipal.sistemaRegistro.cargarProfesores(VentanaPrincipal.actividades, VentanaPrincipal.learningPaths);
		this.dispose();
		new VentanaEditarActividad(ventanaPadre, actividad, profesorActual);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals(TITULO)) {
        	actualizarTitulo();
        } else if (comando.equals(DESCRIPCION)) {
        	actualizarDescripcion();
        } else if (comando.equals(NIVEL)) {
        	actualizarNivel();
        } else if (comando.equals(OBJETIVO)) {
        	actualizarObjetivo();
        }
	}

	

}
