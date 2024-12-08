package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import actividades.Opcion;

@SuppressWarnings("serial")
public class VentanaAgregarPreguntas extends JFrame implements ActionListener{

	private HashMap<String, HashMap<Opcion, HashMap<String,String>>> preguntas;
	private ArrayList<Opcion> respuestasCorrectas;
	private VentanaCrearQuizMultiple ventanaPadre;
	private JTextField txtEnunciado;
	private JTextField txtOpcionA;
	private JTextField txtExplicacionA;
	private JTextField txtOpcionB;
	private JTextField txtExplicacionB;
	private JTextField txtOpcionC;
	private JTextField txtExplicacionC;
	private JTextField txtOpcionD;
	private JTextField txtExplicacionD;
	private JRadioButton radioOpcionA;
	private JRadioButton radioOpcionB;
	private JRadioButton radioOpcionC;
	private JRadioButton radioOpcionD;
	private ButtonGroup grupoOpciones;
	private final static String A = "A";
	private final static String B = "B";
	private final static String C = "C";
	private final static String D = "D";
	private JButton agregarPregunta;
	private JButton volver;
	private final static String AGREGARPREGUNTA = "Agregar pregunta";
	private final static String VOLVER = "Guardar y volver";
	
	
	public VentanaAgregarPreguntas(VentanaCrearQuizMultiple ventanaPadre) {
		
		this.ventanaPadre = ventanaPadre;
		this.preguntas = new HashMap<String, HashMap<Opcion, HashMap<String,String>>>();
		this.respuestasCorrectas = new ArrayList<>();
		
		setLayout(new GridLayout(7, 3));
		
		JLabel labelEnunciado = new JLabel("Enunciado pregunta");
		txtEnunciado = new JTextField(50);
		add(labelEnunciado);
		add(txtEnunciado);
		JLabel labelVacio = new JLabel("");
		add(labelVacio);
		
		JLabel labelOpcionA = new JLabel("Opción A");
		txtOpcionA = new JTextField(50);
		txtExplicacionA = new JTextField(50);
		add(labelOpcionA);
		add(txtOpcionA);
		add(txtExplicacionA);
		
		JLabel labelOpcionB = new JLabel("Opción B");
		txtOpcionB = new JTextField(50);
		txtExplicacionB = new JTextField(50);
		add(labelOpcionB);
		add(txtOpcionB);
		add(txtExplicacionB);
		
		JLabel labelOpcionC = new JLabel("Opción C");
		txtOpcionC = new JTextField(50);
		txtExplicacionC = new JTextField(50);
		add(labelOpcionC);
		add(txtOpcionC);
		add(txtExplicacionC);
		
		JLabel labelOpcionD = new JLabel("Opción D");
		txtOpcionD = new JTextField(50);
		txtExplicacionD = new JTextField(50);
		add(labelOpcionD);
		add(txtOpcionD);
		add(txtExplicacionD);
		
		JLabel labelOpcionCorrecta = new JLabel("Opción correcta");
		radioOpcionA = new JRadioButton("A");
		radioOpcionA.addActionListener(this);
		radioOpcionA.setActionCommand(A);
		
		radioOpcionB = new JRadioButton("B");
		radioOpcionB.addActionListener(this);
		radioOpcionB.setActionCommand(B);
		
		radioOpcionC = new JRadioButton("C");
		radioOpcionC.addActionListener(this);
		radioOpcionC.setActionCommand(C);
		
		radioOpcionD = new JRadioButton("D");
		radioOpcionD.addActionListener(this);
		radioOpcionD.setActionCommand(D);
		
		JPanel opcionCorrecta = new JPanel();
		grupoOpciones = new ButtonGroup();
		grupoOpciones.add(radioOpcionA);
		grupoOpciones.add(radioOpcionB);
		grupoOpciones.add(radioOpcionC);
		grupoOpciones.add(radioOpcionD);
		opcionCorrecta.add(radioOpcionA);
		opcionCorrecta.add(radioOpcionB);
		opcionCorrecta.add(radioOpcionC);
		opcionCorrecta.add(radioOpcionD);
		// Por defecto, la respuesta correcta es la D de Dios
		radioOpcionD.setSelected(true);
		add(labelOpcionCorrecta);
		add(opcionCorrecta);
		
		agregarPregunta = new JButton(AGREGARPREGUNTA);
		agregarPregunta.addActionListener(this);
		agregarPregunta.setActionCommand(AGREGARPREGUNTA);
		add(agregarPregunta);
		
		volver = new JButton(VOLVER);
		volver.addActionListener(this);
		volver.setActionCommand(VOLVER);
		add(volver);
		
        // Termina de configurar la ventana
        setTitle( "Crear preguntas" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 700, 900 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public void agregarPregunta() {
		String enunciado = txtEnunciado.getText();
		String opcionA = txtOpcionA.getText();
		String explicacionA = txtExplicacionA.getText();
		String opcionB = txtOpcionB.getText();
		String explicacionB = txtExplicacionB.getText();
		String opcionC = txtOpcionC.getText();
		String explicacionC = txtExplicacionC.getText();
		String opcionD = txtOpcionD.getText();
		String explicacionD = txtExplicacionD.getText();
		ButtonModel opcionCorrecta = grupoOpciones.getSelection();
		String opcionCorrectaValor= opcionCorrecta.getActionCommand();
		if (enunciado.length()>0 && opcionA.length()>0 && opcionB.length()>0 && opcionC.length()>0 && opcionD.length()>0) {
			HashMap<Opcion, HashMap<String,String>> valoresPregunta = new HashMap<Opcion, HashMap<String,String>>();
			HashMap<String, String> valoresA = new HashMap<String, String>();
			HashMap<String, String> valoresB = new HashMap<String, String>();
			HashMap<String, String> valoresC = new HashMap<String, String>();
			HashMap<String, String> valoresD = new HashMap<String, String>();
			valoresA.put(opcionA, explicacionA);
			valoresB.put(opcionB, explicacionB);
			valoresC.put(opcionC, explicacionC);
			valoresD.put(opcionD, explicacionD);
			valoresPregunta.put(Opcion.A, valoresA);
			valoresPregunta.put(Opcion.B, valoresB);
			valoresPregunta.put(Opcion.C, valoresC);
			valoresPregunta.put(Opcion.D, valoresD);
			preguntas.put(enunciado, valoresPregunta);
			Opcion opcionCorrectaEnum;
			if (opcionCorrectaValor.equals("A")) {
				opcionCorrectaEnum = Opcion.A;
			} else if (opcionCorrectaValor.equals("B")) {
				opcionCorrectaEnum = Opcion.B;
			} else if (opcionCorrectaValor.equals("C")) {
				opcionCorrectaEnum = Opcion.C;
			} else {
				opcionCorrectaEnum = Opcion.D;
			}
			respuestasCorrectas.add(opcionCorrectaEnum);
			limpiarCampos();
		} else {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	public void limpiarCampos() {
		txtEnunciado.setText("");
		txtOpcionA.setText("");
		txtExplicacionA.setText("");
		txtOpcionB.setText("");
		txtExplicacionB.setText("");
		txtOpcionC.setText("");
		txtExplicacionC.setText("");
		txtOpcionD.setText("");
		txtExplicacionD.setText("");
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
