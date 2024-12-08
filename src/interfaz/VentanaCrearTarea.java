package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import usuarios.Profesor;

public class VentanaCrearTarea extends JFrame implements ActionListener {

	
	
	public VentanaCrearTarea(Profesor profesorActual, String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos, boolean obligatorio) {
		
        // Termina de configurar la ventana
        setTitle( "Crear tarea" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
