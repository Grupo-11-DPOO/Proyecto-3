package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import learningPaths.LearningPath;

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
	
	public VentanaEditarLearningPath (VentanaVerYEditarLearningPath ventanaPadre, LearningPath learningPath) {
		
		setLayout( new BorderLayout());
		
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
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals( TITULO ) )
        {
        	String nuevoTitulo = JOptionPane.showInputDialog("Ingrese el nuevo título");
        	// actualizar info
        } else if (comando.equals(DESCRIPCION)) {
        	String nuevaDescripcion = JOptionPane.showInputDialog("Ingrese la nueva descripción");
        	// actualizar info
        } else if (comando.equals(NIVEL)) {
        	// String nuevoNivel = JOptionPane.showInputDialog("Ingrese la nueva descripción");
        	// Debe ser de la opcion existente.
        	// actualizar info
        } else if (comando.equals(ACTIVIDAD)) {
        	
        }
	}

}
