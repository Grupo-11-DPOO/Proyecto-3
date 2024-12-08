package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class VentanaAgregarPreguntas extends JFrame implements ActionListener{

	private VentanaCrearQuizMultiple ventanaPadre;
	
	public VentanaAgregarPreguntas(VentanaCrearQuizMultiple ventanaPadre) {
		
		this.ventanaPadre = ventanaPadre;
		
		
		
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
