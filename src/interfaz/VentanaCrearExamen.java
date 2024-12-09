package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import actividades.Examen;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaCrearExamen extends JFrame implements ActionListener {

	private Profesor profesorActual;
	private String titulo;
	private String objetivo;
	private String descripcion;
	private String nivel;
	private int duracionMinutos;
	private boolean obligatorio;
	private JCheckBox chkPreRequisitos;
	private JButton botonAgregarPreguntas;
	private JButton botonCrear;
	private static final String Crear = "Crear";
	private static final String AgregarPreguntas = "Agregar preguntas";
	private VentanaAgregarPrerequisitos ventanaPrerequisitos;
	private Examen examen;
	private ArrayList<String> preguntas;
	private VentanaAgregarPreguntasAbiertas ventanaAgregarPreguntas;
	
	public VentanaCrearExamen(Profesor profesorActual, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) {
		
		this.profesorActual = profesorActual;
		this.titulo = titulo;
		this.objetivo = objetivo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.duracionMinutos = duracionMinutos;
		this.obligatorio = obligatorio;
		
		setLayout(new GridLayout(3, 1));
		
		JPanel panelPrereq = new JPanel();
		panelPrereq.setLayout(new BorderLayout());
		chkPreRequisitos = new JCheckBox("¿Tiene prerequisitos? ");	
		chkPreRequisitos.setHorizontalTextPosition(SwingConstants.LEFT);
		panelPrereq.add(chkPreRequisitos, BorderLayout.CENTER);
		add(panelPrereq);
		
		botonAgregarPreguntas = new JButton(AgregarPreguntas);
		botonAgregarPreguntas.addActionListener(this);
		botonAgregarPreguntas.setActionCommand(AgregarPreguntas);
		add(botonAgregarPreguntas);
		
		botonCrear = new JButton(Crear);
		botonCrear.addActionListener(this);
		botonCrear.setActionCommand(Crear);
		add(botonCrear);
		
        // Termina de configurar la ventana
        setTitle( "Crear examen" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}

	public void setPreguntas(ArrayList<String> preguntas) {
		this.preguntas = preguntas;
	}
	
	public void crear() {
		try {
			if (preguntas != null) {
				examen = profesorActual.crearActividadExamen(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);    					
				examen.setPreguntas(preguntas);
				if (chkPreRequisitos.isSelected()) {
					mostrarVentanaPrerequisitos();
					this.dispose();
				} else {
					profesorActual.guardarActividad(examen);
					VentanaPrincipal.sistemaRegistro.guardarActividad(examen);
					VentanaPrincipal.sistemaRegistro.guardarProfesor(profesorActual);
					this.dispose();			
				}
			} else {
				JOptionPane.showMessageDialog(this,  "No puede crear un examen sin preguntas.", "Falta de preguntas", JOptionPane.ERROR_MESSAGE);    					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void mostrarVentanaAgregarPreguntas() {
		if( ventanaAgregarPreguntas == null || ! ventanaAgregarPreguntas.isVisible( ) )
		{
			ventanaAgregarPreguntas = new VentanaAgregarPreguntasAbiertas(this);
			ventanaAgregarPreguntas.setVisible(true);
		}
	}
	
	public void mostrarVentanaPrerequisitos() {
		if( ventanaPrerequisitos == null || ! ventanaPrerequisitos.isVisible( ) )
		{
			ventanaPrerequisitos = new VentanaAgregarPrerequisitos(examen, profesorActual);
			ventanaPrerequisitos.setVisible(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
	    if (comando.equals(Crear)) {
	    	crear();
	    } else if (comando.equals(AgregarPreguntas)){
	    	mostrarVentanaAgregarPreguntas();
	    }
	}
}