package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import exceptions.UsuarioExistenteException;

@SuppressWarnings("serial")
public class VentanaRegistroProfesor extends JFrame implements ActionListener{

	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JButton confirmar;
	private static final String REGISTRAR = "Registrar";
	
	public VentanaRegistroProfesor() {
		
		
		setLayout(null);

		JLabel labelUsuario = new JLabel("Login:");
		
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
		
		
		// Boton agregar
		confirmar = new JButton();
		confirmar.setText("Registrar");
		confirmar.setBounds(50,90, 165, 25);
		confirmar.addActionListener(this);
		confirmar.setActionCommand(REGISTRAR);
		add(confirmar);
		
		
        // Termina de configurar la ventana
        setTitle( "Registro Profesores" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 300, 160 );
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
	
	public void registrar() throws UsuarioExistenteException {
		String login = getLogin();
		String password = getPassword();
		if (!login.isEmpty() && !password.isEmpty()) {
			VentanaPrincipal.sistemaRegistro.registrarProfesor(login, password);
			JOptionPane.showMessageDialog(this,"¡Profesor registrado correctamente!");
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
        if( comando.equals( REGISTRAR ) )
        {
            try {
				registrar();
			} catch (UsuarioExistenteException e1) {
				e1.printStackTrace();
			}
        }
    }
	
}
