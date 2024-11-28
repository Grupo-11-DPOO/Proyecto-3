package interfaz;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PanelIngreso extends JPanel{

	JTextField txtUsuario;
	JTextField txtPassword;
	
	
	public PanelIngreso() {
		
		setLayout( new GridLayout(2,2));
		
        JLabel labelUsuario = new JLabel("Login:");
        txtUsuario = new JTextField();
        
        JLabel labelPassword = new JLabel("Contraseña:");
        txtPassword = new JTextField();
        
        add(labelUsuario);
        add(txtUsuario);
        add(labelPassword);
        add(txtPassword);
        
	}
	
	
	public String getLogin() {
		String login = txtUsuario.getText();
		return login;
	}
	
}
