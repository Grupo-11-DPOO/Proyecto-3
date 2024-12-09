package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import exceptions.UsuarioExistenteException;

@SuppressWarnings("serial")
public class VentanaRegistroEstudiante extends JFrame implements ActionListener{
	
	
	private static final String REGISTRAREST = "Registrar";
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JTextField interes1;
	private JTextField interes2;
	private JTextField interes3;
	private JButton confirmar;
	
	public VentanaRegistroEstudiante() {
		
		setLayout(null);
		
		JLabel labelUsuario = new JLabel("Login: ");
		labelUsuario.setBounds(10, 20, 80, 25);
		txtUsuario = new JTextField();
		txtUsuario.setBounds(100,20,165,25);
		
		
		JLabel labelPassword = new JLabel("Contraseña:");
		labelPassword.setBounds(10, 50, 80, 25);
		txtPassword = new JPasswordField();
		txtPassword.setBounds(100,50,165,25);
		
		add(labelUsuario);
		add(txtUsuario);
		add(labelPassword);
		add(txtPassword);
		
		JLabel labelDesc = new JLabel("Escriba tres temas de su interes.");
		labelDesc.setBounds(10, 80, 250, 25);
		JLabel labelInt1 = new JLabel("Interes 1: ");
		labelInt1.setBounds(10, 100, 80, 25);
		interes1 = new JTextField();
		interes1.setBounds(100, 100, 165, 25);
		
		JLabel labelInt2 = new JLabel("Interes 2: ");
		labelInt2.setBounds(10, 130, 80, 25);
		interes2 = new JTextField();
		interes2.setBounds(100, 130, 165, 25);
		
		JLabel labelInt3 = new JLabel("Interes 3: ");
		labelInt3.setBounds(10, 160, 80, 25);
		interes3 = new JTextField();
		interes3.setBounds(100, 160, 165, 25);
		
		add(labelDesc);
		add(labelInt1);
		add(interes1);
		add(labelInt2);
		add(interes2);
		add(labelInt3);
		add(interes3);
		
		// Boton agregar
		confirmar = new JButton();
		confirmar.setText("Registrar");
		confirmar.setBounds(100,190, 100, 25);
		confirmar.addActionListener(this);
		confirmar.setActionCommand(REGISTRAREST);
		add(confirmar);
		
        // Termina de configurar la ventana
        setTitle( "Registro Estudiantes" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 300, 250 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
		
	}
	
	public String getLogin() {
		String login = txtUsuario.getText();
		return login;
	}
	
	public String getPassword() {
		String password = txtPassword.getText();
		return password;
	}
	public ArrayList<String> getIntereses(){
		ArrayList<String> interes = new ArrayList<String>();
		if(!interes1.getText().isBlank()) {
			interes.add(interes1.getText());
		}
		if(!interes2.getText().isBlank()) {
			interes.add(interes2.getText());
		}
		if(!interes3.getText().isBlank()) {
			interes.add(interes3.getText());
		}
		return interes;
	}
	
	public void registrar() throws UsuarioExistenteException {
		String login = getLogin();
		String password = getPassword();
		ArrayList<String> interes = getIntereses();
		if (!login.isEmpty() && !password.isEmpty() && !interes.isEmpty()) {
			VentanaPrincipal.sistemaRegistro.registrarEstudiante(login, password, interes);
			JOptionPane.showMessageDialog(this,"¡Estudiante registrado correctamente!");
			dispose();
		}
		else {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	@Override
    public void actionPerformed( ActionEvent e )
    {
        String comando = e.getActionCommand( );
        if( comando.equals( REGISTRAREST ) )
        {
            try {
				registrar();
			} catch (UsuarioExistenteException e1) {
				e1.printStackTrace();
			}
        }
    }
}