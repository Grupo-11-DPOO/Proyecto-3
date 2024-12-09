package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class VentanaAgregarPreguntasAbiertas extends JFrame implements ActionListener {
	
	private VentanaCrearExamen ventanaPadreExamen;
	private VentanaCrearEncuesta ventanaPadreEncuesta;
	private ArrayList<String> preguntas;
	private JButton agregarPregunta;
	private JButton volver;
	private final static String AGREGARPREGUNTA = "Agregar pregunta";
	private final static String VOLVER = "Guardar y volver";
	private JTextArea txtEnunciado;

	public VentanaAgregarPreguntasAbiertas (VentanaCrearExamen ventanaPadre) {
		
		this.ventanaPadreExamen = ventanaPadre;
		this.preguntas = new ArrayList<String>();
		
		setLayout(new GridLayout(2, 2));
		
		JLabel labelEnunciado = new JLabel("Enunciado pregunta");
		txtEnunciado = new JTextArea();
		txtEnunciado.setLineWrap(true);
		add(labelEnunciado);
		add(txtEnunciado);
		
		agregarPregunta = new JButton(AGREGARPREGUNTA);
		agregarPregunta.addActionListener(this);
		agregarPregunta.setActionCommand(AGREGARPREGUNTA);
		add(agregarPregunta);
		
		volver = new JButton(VOLVER);
		volver.addActionListener(this);
		volver.setActionCommand(VOLVER);
		add(volver);
		
        // Termina de configurar la ventana
        setTitle( "Crear preguntas abiertas" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 500, 300 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public VentanaAgregarPreguntasAbiertas (VentanaCrearEncuesta ventanaPadre) {
		
		this.ventanaPadreEncuesta = ventanaPadre;
		this.preguntas = new ArrayList<String>();
		
		setLayout(new GridLayout(2, 2));
		
		JLabel labelEnunciado = new JLabel("Enunciado pregunta");
		txtEnunciado = new JTextArea();
		txtEnunciado.setLineWrap(true);
		add(labelEnunciado);
		add(txtEnunciado);
		
		agregarPregunta = new JButton(AGREGARPREGUNTA);
		agregarPregunta.addActionListener(this);
		agregarPregunta.setActionCommand(AGREGARPREGUNTA);
		add(agregarPregunta);
		
		volver = new JButton(VOLVER);
		volver.addActionListener(this);
		volver.setActionCommand(VOLVER);
		add(volver);
		
		// Termina de configurar la ventana
		setTitle( "Crear preguntas abiertas" );
		setDefaultCloseOperation( DISPOSE_ON_CLOSE );
		setSize( 500, 300 );
		setResizable(false);
		setLocationRelativeTo( null );
		setVisible( true );
	}
	
	public void agregarPregunta() {
		String enunciado = txtEnunciado.getText();
		if (enunciado.length()>0) {
			preguntas.add(enunciado);
			limpiarCampos();
		} else {
			JOptionPane.showMessageDialog(this, "No puede poner el enunciado en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	public void limpiarCampos() {
		txtEnunciado.setText("");
	}
	
	public void volverYGuardar() {
		if (preguntas.size()>0) {
			if (ventanaPadreExamen != null) {
				ventanaPadreExamen.setPreguntas(preguntas);
			} else {
				ventanaPadreEncuesta.setPreguntas(preguntas);
			}
			
			this.dispose();
		} else {
			JOptionPane.showMessageDialog(this,  "No deberia salirse sin crear preguntas", "Debe crear al menos una pregunta", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals(AGREGARPREGUNTA)) {
        	agregarPregunta();
        } else if (comando.equals(VOLVER)) {
        	volverYGuardar();
        }
	}
}