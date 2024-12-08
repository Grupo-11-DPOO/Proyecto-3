package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import actividades.Tarea;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaCrearTarea extends JFrame implements ActionListener {

	private Profesor profesorActual;
	private String titulo;
	private String objetivo;
	private String descripcion;
	private String nivel;
	private int duracionMinutos;
	private boolean obligatorio;
	private JCheckBox chkPreRequisitos;
	private JButton botonCrear;
	private JTextField txtIdActividadTarea;
	private static final String Crear = "Crear";
	private VentanaAgregarPrerequisitos ventanaPrerequisitos;
	private Tarea tarea;
	
	public VentanaCrearTarea(Profesor profesorActual, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) {
		
		this.profesorActual = profesorActual;
		this.titulo = titulo;
		this.objetivo = objetivo;
		this.descripcion = descripcion;
		this.nivel = nivel;
		this.duracionMinutos = duracionMinutos;
		this.obligatorio = obligatorio;
		
		setLayout( new GridLayout(2,2));
		
		JLabel labelRecurso = new JLabel("ID de la tarea a realizar o la instrucción a realizar");
		txtIdActividadTarea = new JTextField(50);
		add(labelRecurso);
		add(txtIdActividadTarea);
		
		chkPreRequisitos = new JCheckBox("¿Tiene prerequisitos? ");	
		chkPreRequisitos.setHorizontalTextPosition(SwingConstants.LEFT);
		add(chkPreRequisitos);
		
		
		botonCrear = new JButton(Crear);
		botonCrear.addActionListener(this);
		botonCrear.setActionCommand(Crear);
		add(botonCrear);
		
        // Termina de configurar la ventana
        setTitle( "Crear tarea" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}

	public void crear() {
		String stringIdActividadTarea = txtIdActividadTarea.getText();
		if (stringIdActividadTarea.length() > 0) {
			try {
				tarea = profesorActual.crearActividadTarea(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio, stringIdActividadTarea);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (chkPreRequisitos.isSelected()) {
				mostrarVentanaPrerequisitos();
				this.dispose();
			} else {
				profesorActual.guardarActividad(tarea);
				VentanaPrincipal.sistemaRegistro.guardarActividad(tarea);
				VentanaPrincipal.sistemaRegistro.guardarProfesor(profesorActual);
				this.dispose();		
			}
		}
	}
	
	public void mostrarVentanaPrerequisitos() {
		if( ventanaPrerequisitos == null || ! ventanaPrerequisitos.isVisible( ) )
		{
    		ventanaPrerequisitos = new VentanaAgregarPrerequisitos(tarea, profesorActual);
    		ventanaPrerequisitos.setVisible(true);
    		
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if (comando.equals(Crear)) {
        	crear();
        }
	}
}
