package interfaz;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PanelIngreso extends JPanel implements ActionListener{

	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JRadioButton radioProfesor;
	private JRadioButton radioEstudiante;
	private ButtonGroup grupoOpciones;
	private static final String PROFESOR = "Profesor";
	private static final String ESTUDIANTE = "Estudiante";
	
	
	public PanelIngreso() {
		
		setLayout( null);
		setBackground(new Color(255,255,255));
		JLabel labelUsuario = new JLabel("Login:");
		labelUsuario.setBounds(10, 20, 80, 25);
		txtUsuario = new JTextField();
		txtUsuario.setBounds(100,20,215,25);
		
		
		JLabel labelPassword = new JLabel("Contraseña:");
		labelPassword.setBounds(10, 50, 80, 25);
		txtPassword = new JPasswordField();
		txtPassword.setBounds(100,50,215,25);
		
		add(labelUsuario);
		add(txtUsuario);
		add(labelPassword);
		add(txtPassword);
		
		radioProfesor = new JRadioButton("Profesor");
		radioProfesor.addActionListener(this);
		radioProfesor.setActionCommand(PROFESOR);
		
		radioEstudiante = new JRadioButton("Estudiante");
		radioEstudiante.addActionListener(this);
		radioEstudiante.setActionCommand(ESTUDIANTE);
		
		grupoOpciones = new ButtonGroup();
        grupoOpciones.add(radioProfesor);
        grupoOpciones.add(radioEstudiante);
        
        radioProfesor.setBounds(80,80,100,25);
        radioEstudiante.setBounds(180,80,100,25);
        add(radioProfesor);
        add(radioEstudiante);

	}
	
	
	public String getLogin() {
		String login = txtUsuario.getText();
		return login;
	}
	
	@SuppressWarnings("deprecation")
	public String getPassword() {
		String password = txtPassword.getText();
		return password;
	}
	
	public String getTipoUsuario() {
	    ButtonModel seleccionado = grupoOpciones.getSelection();
	    if (seleccionado == null) {
	        return null;
	    }
	    return seleccionado.getActionCommand();
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
