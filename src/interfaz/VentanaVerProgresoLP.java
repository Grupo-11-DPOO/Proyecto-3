package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import usuarios.Estudiante;

public class VentanaVerProgresoLP extends JFrame implements ActionListener{
	private Estudiante estudianteActivo;
	private double porcentajeCompletadas;
	private double porcentajeExitosas;
	private JPanel panelBotones;
	private JPanel panelContenido;
	private JTextArea datos;
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
		botonCompletadas.addActionListener(this);
		botonCompletadas.setActionCommand(COMPLETADAS);
		JButton botonExitosas = new JButton(EXITOSAS);
		botonExitosas.addActionListener(this);
		botonExitosas.setActionCommand(EXITOSAS);
		JButton botonMenuPrincipal = new JButton(PRINCIPAL);
		botonMenuPrincipal.addActionListener(this);
		botonMenuPrincipal.setActionCommand(PRINCIPAL);

		datos = new JTextArea("Aquí aparecera el contenido esperado.");
		panelContenido.add(datos);
		
		panelBotones.add(botonCompletadas);
		panelBotones.add(botonExitosas);
		panelBotones.add(botonMenuPrincipal);
		
		add(titulo, BorderLayout.NORTH);
		add(panelBotones, BorderLayout.WEST);
		add(panelContenido, BorderLayout.CENTER);
		
		if (estudianteActivo.getLearningPathEnCurso() != null) {
			List<Double> progreso = estudianteActivo.verProgresoLearningPath();
			porcentajeCompletadas = progreso.get(0);
			porcentajeExitosas = progreso.get(1);
			
		} else {
			porcentajeCompletadas = -1;
			porcentajeExitosas = -1;
		}
		
		
		
		// TODO Auto-generated constructor stub
		setTitle( "Ver Progreso del Learning Path" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 700, 300 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public void mostrarCompletadasYExitosas() {
		String detalles = null;
		if (porcentajeCompletadas == -1) {
			detalles = "No hay ningún learning path en curso,"+"\n"+ "por lo que esta funcionalidad no sirve";
			datos.setText(detalles);
		}else{
			detalles = "El porcentaje de actividades completadas es: "+ porcentajeCompletadas+"%\n"
					+ "El porcentaje de actividades exitosas es: "+ porcentajeExitosas+"%";
			datos.setText(detalles);
		}
	}
	
	public void mostrarExitosas() {
		String detalles = null;
		if (porcentajeCompletadas == -1) {
			detalles = "No hay ningún learning path en curso,\n por lo que esta funcionalidad no sirve";
			datos.setText(detalles);
		}else {
			detalles = "El porcentaje de actividades exitosas es: "+ porcentajeExitosas+"%";
			datos.setText(detalles);
		}
		
	}
	
	public void salirMenuPrincipal() {
		this.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(COMPLETADAS)) {
			mostrarCompletadasYExitosas();
		} else if(comando.equals(EXITOSAS)) {
			mostrarExitosas();
		} else if(comando.equals(PRINCIPAL)) {
			salirMenuPrincipal();
		}
	}
}
