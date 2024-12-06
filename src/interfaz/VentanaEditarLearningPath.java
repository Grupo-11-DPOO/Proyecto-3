package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import learningPaths.LearningPath;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaEditarLearningPath extends JFrame implements ActionListener{

	private JButton botonTitulo;
	private JButton botonDescripcion;
	private JButton botonNivel;
	private JButton botonAgregarActividad;
	private static final String TITULO = "Editar título";
	private static final String DESCRIPCION = "Editar descripción";
	private static final String NIVEL = "Editar nivel";
	private static final String ACTIVIDAD = "Agregar actividad existente";
	
	private VentanaVerYEditarLearningPath ventanaPadre;
	private LearningPath learningPath;
	private VentanaAgregarActividadesPropias ventanaAgregarActividadesPropias;
	private Profesor profesorActual;
	
	public VentanaEditarLearningPath (VentanaVerYEditarLearningPath ventanaPadre, LearningPath learningPath, Profesor profesorActual) {
		
		setLayout( new BorderLayout());
		
		this.profesorActual = profesorActual;
		this.ventanaPadre = ventanaPadre;
		this.learningPath = learningPath;
		String titulo = learningPath.getTitulo();
		
		JLabel labelTitulo = new JLabel("Editar Learning Path "+titulo);
		add(labelTitulo, BorderLayout.NORTH);
		
		botonTitulo = new JButton(TITULO);
		botonTitulo.addActionListener(this);
		botonTitulo.setActionCommand(TITULO);
		add(botonTitulo, BorderLayout.WEST);
		
		botonDescripcion = new JButton(DESCRIPCION);
		botonDescripcion.addActionListener(this);
		botonDescripcion.setActionCommand(DESCRIPCION);
		add(botonDescripcion, BorderLayout.CENTER);
		
		botonNivel = new JButton(NIVEL);
		botonNivel.addActionListener(this);
		botonNivel.setActionCommand(NIVEL);
		add(botonNivel, BorderLayout.EAST);
		
		botonAgregarActividad = new JButton(ACTIVIDAD);
		botonAgregarActividad.addActionListener(this);
		botonAgregarActividad.setActionCommand(ACTIVIDAD);
		add(botonAgregarActividad, BorderLayout.SOUTH);
		
        // Termina de configurar la ventana
        setTitle( "Ver y Editar Learning Paths");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 400 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
		
	}
	
	public void actualizarTitulo() {
		String nuevoTitulo = JOptionPane.showInputDialog("Ingrese el nuevo título");
		// Actualizar info
		learningPath.setTitulo(nuevoTitulo);
		VentanaPrincipal.sistemaRegistro.guardarLearningPath(learningPath);
		VentanaPrincipal.sistemaRegistro.cargarProfesores(VentanaPrincipal.actividades, VentanaPrincipal.learningPaths);
		this.dispose();
		new VentanaEditarLearningPath(ventanaPadre, learningPath, profesorActual);
	}
	
	public void actualizarDescripcion() {
		String nuevaDescripcion = JOptionPane.showInputDialog("Ingrese la nueva descripción");
		// Actualizar info
		learningPath.setDescripcion(nuevaDescripcion);
		VentanaPrincipal.sistemaRegistro.guardarLearningPath(learningPath);
		VentanaPrincipal.sistemaRegistro.cargarProfesores(VentanaPrincipal.actividades, VentanaPrincipal.learningPaths);
		this.dispose();
		new VentanaEditarLearningPath(ventanaPadre, learningPath, profesorActual);
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
        learningPath.setNivel(nivel);
		VentanaPrincipal.sistemaRegistro.guardarLearningPath(learningPath);
		VentanaPrincipal.sistemaRegistro.cargarProfesores(VentanaPrincipal.actividades, VentanaPrincipal.learningPaths);
		this.dispose();
		new VentanaEditarLearningPath(ventanaPadre, learningPath, profesorActual);
	}
	
	public void agregarActividadExistente() {
		String cantidadString = JOptionPane.showInputDialog("Ingrese la cantidad de actividades (números enteros):");
		try {
            int cantidad = Integer.parseInt(cantidadString);
            if (cantidad > 0) {
            	mostrarVentanaAgregarActividadesPropias(cantidad, learningPath);
            	VentanaPrincipal.sistemaRegistro.guardarLearningPath(learningPath);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,  "Input inválido. Por favor solo ingrese enteros.", "Input error", JOptionPane.ERROR_MESSAGE);
        }
	}
	
	public void mostrarVentanaAgregarActividadesPropias(int cantidad, LearningPath learningPath) {
        if( ventanaAgregarActividadesPropias== null || !ventanaAgregarActividadesPropias.isVisible( ) )
        {
        	ventanaAgregarActividadesPropias = new VentanaAgregarActividadesPropias(profesorActual, cantidad, learningPath);
        	ventanaAgregarActividadesPropias.setVisible(true);
        }
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
        } else if (comando.equals(ACTIVIDAD)) {
        	agregarActividadExistente();
        }
	}

}
