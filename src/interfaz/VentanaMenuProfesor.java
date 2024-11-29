package interfaz;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaMenuProfesor extends JFrame{

	private Profesor profesorActual;
	
	public VentanaMenuProfesor(Profesor profesorActual) {
		
		this.profesorActual = profesorActual;
		
		setLayout( new BorderLayout());
		
		
        // Termina de configurar la ventana
        setTitle( "Menu Principal" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
		
	}
	
	
	
}
