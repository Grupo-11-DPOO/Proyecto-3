package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


import usuarios.Estudiante;

@SuppressWarnings("serial")
public class VentanaSalirLPOAct extends JFrame implements ActionListener{
	private Estudiante estudianteActual;
	private JButton botonSalirLP;
	private JButton botonSalirActi;
	private static final String LP = "Salir del Learning Path en curso";
	private static final String ACT = "Salir de la Actividad en curso";
	
	public VentanaSalirLPOAct(Estudiante estudianteActivo) {
		estudianteActual = estudianteActivo;
		setLayout(null);
		botonSalirLP = new JButton(LP);
		botonSalirLP.addActionListener(this);
		botonSalirLP.setActionCommand(LP);
		botonSalirLP.setBounds(10, 20, 315, 60);
			
		botonSalirActi = new JButton(ACT);
		botonSalirActi.addActionListener(this);
		botonSalirActi.setActionCommand(ACT);
		botonSalirActi.setBounds(10, 100, 315, 60);
			
		add(botonSalirLP);
		add(botonSalirActi);
		
		setTitle( "Salir Learning Path o Actividad" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 350, 240 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public void salirLP() {
		estudianteActual.finalizarLearningPath();
		VentanaPrincipal.sistemaRegistro.guardarEstudiante(estudianteActual);
		JOptionPane.showMessageDialog(this,"LearningPath cerrado correctamente!");
	}
	
	public void salirActi() {
		estudianteActual.finalizarActividad();
		VentanaPrincipal.sistemaRegistro.guardarEstudiante(estudianteActual);
		JOptionPane.showMessageDialog(this,"Actividad cerrada correctamente!");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(LP)) {
			salirLP();
			this.dispose();
		} else if(comando.equals(ACT)) {
			salirActi();
			this.dispose();
		}
	}
}
