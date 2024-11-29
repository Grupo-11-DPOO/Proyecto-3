package interfaz;

import java.awt.BorderLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VentanaRegistroEstudiantes extends JFrame{
	
	private VentanaPrincipal ventanaPrincipal;
	
	public VentanaRegistroEstudiantes(VentanaPrincipal ventanaPrincipal) {
		
		this.ventanaPrincipal = ventanaPrincipal;
		
		setLayout(new BorderLayout());
		
		
		
        // Termina de configurar la ventana
        setTitle( "Registro Profesores" );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( 400, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
		
	}

}
