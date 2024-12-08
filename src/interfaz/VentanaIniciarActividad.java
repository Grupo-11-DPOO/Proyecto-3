package interfaz;

import javax.swing.JFrame;

public class VentanaIniciarActividad extends JFrame {

	public VentanaIniciarActividad(){
		// TODO Auto-generated constructor stub
		setTitle( "Iniciar Actividad del Learning Path" );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}

}
