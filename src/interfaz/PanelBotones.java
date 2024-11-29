package interfaz;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelBotones extends JPanel implements ActionListener{
	
	private static final String INICIAR = "Iniciar sesión";
	private static final String REGISTRARSE = "Registrarse";
	private VentanaPrincipal ventanaPrincipal;
	
	public PanelBotones(VentanaPrincipal ventanaPrincipal) {
		
		this.ventanaPrincipal = ventanaPrincipal;
        setLayout( new FlowLayout( ) );
        
        JButton bIniciar = new JButton(INICIAR);
        bIniciar.addActionListener(this);
        bIniciar.setActionCommand(INICIAR);
        bIniciar.setVisible(true);
        add(bIniciar);
        
        JButton bRegistrarse = new JButton(REGISTRARSE);
        bRegistrarse.addActionListener(this);
        bRegistrarse.setActionCommand(REGISTRARSE);
        bRegistrarse.setVisible(true);
        add(bRegistrarse);
	}
	
	
    public void actionPerformed( ActionEvent e )
    {
        String comando = e.getActionCommand( );
        if( comando.equals( INICIAR ) )
        {
           ventanaPrincipal.iniciarSesion( );
        }
        else if( comando.equals( REGISTRARSE ) )
        {
            ventanaPrincipal.escogerTipoUsuario( );
        }
    }
	
}
