package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import actividades.VerdaderoFalso;

@SuppressWarnings("serial")
public class VentanaAgregarPreguntasVF extends JFrame implements ActionListener {

	private VentanaCrearQuizVF ventanaPadre;
	private JButton agregarPregunta;
	private JButton volver;
	private final static String AGREGARPREGUNTA = "Agregar pregunta";
	private final static String VOLVER = "Guardar y volver";
	private JTextField txtEnunciado;
	private JRadioButton radioVerdadero;
	private JRadioButton radioFalso;
	private ButtonGroup grupoOpciones;
	private final static String VERDADERO = "Verdadero";
	private final static String FALSO = "Falso";
	private ArrayList<String> preguntas;
	private ArrayList<VerdaderoFalso> respuestasCorrectas;
	
	public VentanaAgregarPreguntasVF (VentanaCrearQuizVF ventanaPadre) {
		
		this.ventanaPadre = ventanaPadre;
		this.preguntas = new ArrayList<String>();
		this.respuestasCorrectas = new ArrayList<VerdaderoFalso>();
		
		setLayout(new GridLayout(3, 2));
		
		JLabel labelEnunciado = new JLabel("Enunciado pregunta");
		txtEnunciado = new JTextField(50);
		add(labelEnunciado);
		add(txtEnunciado);
		
		radioVerdadero = new JRadioButton(VERDADERO);
		radioVerdadero.addActionListener(this);
		radioVerdadero.setActionCommand(VERDADERO);
		
		radioFalso = new JRadioButton(FALSO);
		radioFalso.addActionListener(this);
		radioFalso.setActionCommand(FALSO);
		grupoOpciones = new ButtonGroup();
		grupoOpciones.add(radioVerdadero);
		grupoOpciones.add(radioFalso);
		// Por defecto, la respuesta correcta es verdadero
		radioVerdadero.setSelected(true);
		
		add(radioVerdadero);
		add(radioFalso);
		
		agregarPregunta = new JButton(AGREGARPREGUNTA);
		agregarPregunta.addActionListener(this);
		agregarPregunta.setActionCommand(AGREGARPREGUNTA);
		add(agregarPregunta);
		
		volver = new JButton(VOLVER);
		volver.addActionListener(this);
		volver.setActionCommand(VOLVER);
		add(volver);
		
        // Termina de configurar la ventana
        setTitle( "Crear preguntas (verdadero/falso)" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 700, 700 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public void agregarPregunta() {
		String enunciado = txtEnunciado.getText();
		ButtonModel opcionCorrecta = grupoOpciones.getSelection();
		String opcionCorrectaValor= opcionCorrecta.getActionCommand();
		if (enunciado.length()>0) {
			preguntas.add(enunciado);
			VerdaderoFalso opcionCorrectaEnum;
			if (opcionCorrectaValor.equals(VERDADERO)) {
				opcionCorrectaEnum = VerdaderoFalso.Verdadero;
			} else {
				opcionCorrectaEnum = VerdaderoFalso.Falso;
			}
			respuestasCorrectas.add(opcionCorrectaEnum);
			limpiarCampos();
		} else {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	public void limpiarCampos() {
		txtEnunciado.setText("");
	}
	
	public void volverYGuardar() {
		if (respuestasCorrectas.size()>0) {
			ventanaPadre.setPreguntas(preguntas);
			ventanaPadre.setRespuestasCorrectas(respuestasCorrectas);
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