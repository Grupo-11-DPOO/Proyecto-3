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
	private VentanaCrearLearningPath ventanaCrearLearningPath;
	private VentanaVerYEditarLearningPath ventanaVerYEditarLearningPath;
	private static final String CREAR = "Crear Learning Path";
	private static final String VERYDITAR = "Ver y Editar Learning Path";
	
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

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals( CREAR ) )
        {
            crearLearningPath();
        } else if (comando.equals(VERYDITAR)) {
        	verYEditarLearningPath();
        }
	}
	
	
	
}
