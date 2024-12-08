package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import usuarios.Estudiante;

public class VentanaVerProgresoLP extends JFrame implements ActionListener{
	private Estudiante estudianteActivo;
	private JPanel panelBotones;
	private JPanel panelContenido;
	private static final String COMPLETADAS = "Ver progreso de actividades completadas y exitosas";
	private static final String EXITOSAS = "Ver progreso de actividades exitosas";
	private static final String PRINCIPAL = "Volver al menú principal";
	
	public VentanaVerProgresoLP(Estudiante estudianteActual) {
		setLayout(new BorderLayout());
		
		estudianteActivo = estudianteActual;
		
		
		panelBotones = new JPanel(new GridLayout(3,1));
		panelContenido = new JPanel();
		
		JLabel titulo = new JLabel("Estadísticas de su progreso en el Learning Path en curso");
		
		JButton botonCompletadas = new JButton(COMPLETADAS);
		
		JButton botonExitosas = new JButton(EXITOSAS);
	
		JButton botonMenuPrincipal = new JButton(PRINCIPAL);
		
		
		JLabel contenido = new JLabel("Aquí aparecera el contenido esperado.");
		panelContenido.add(contenido);
		
		panelBotones.add(botonCompletadas);
		panelBotones.add(botonExitosas);
		panelBotones.add(botonMenuPrincipal);
		
		add(titulo, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.WEST);
		add(panelContenido, BorderLayout.CENTER);
		
		//List<Double> progreso = estudianteActivo.verProgresoLearningPath();
				//double porcentajeCompletadas = progreso.get(0);
				//double porcentajeExitosas = progreso.get(1);
		
		// TODO Auto-generated constructor stub
		setTitle( "Ver Progreso del Learning Path" );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public void mostrarCompletadasYExitosas() {
		
	}
	
	public void mostrarExitosas() {
		
		
	}
	
	public void salirMenuPrincipal() {
		this.dispose();
	}
	

}
