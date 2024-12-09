package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class VentanaVerEstadisticas extends JFrame implements ActionListener {
	private JButton botonMatriz;
	private JButton botonEstudiantes;
	private JButton botonLP;
	private VentanaMatrizActividades ventanaMatriz;
	private static final String MATRIZ = "Actividades realizadas estudiantes"; 
	private static final String ESTUDIANTES = "Estadisticas por estudiante";
	private static final String LP = "Estadisticas por Learning Path";
	
	
	public VentanaVerEstadisticas() {
		setLayout(null);
		JLabel titulo = new JLabel("Ver estadisticas");
		titulo.setBounds(10, 20, 165, 25);
		botonMatriz = new JButton(MATRIZ);
		botonMatriz.addActionListener(this);
		botonMatriz.setActionCommand(MATRIZ);
		botonMatriz.setBounds(10,50, 250, 40);
		
		botonEstudiantes = new JButton(ESTUDIANTES);
		botonEstudiantes.addActionListener(this);
		botonEstudiantes.setActionCommand(ESTUDIANTES);
		botonEstudiantes.setBounds(10,130, 250, 40);
		
		botonLP = new JButton(LP);
		botonLP.addActionListener(this);
		botonLP.setActionCommand(LP);
		botonLP.setBounds(10, 90, 250, 40);
		
		add(titulo);
		add(botonMatriz);
		add(botonLP);
		add(botonEstudiantes);
		
		setTitle( "Ver Estadisticas");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 300, 200 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}

	public void mostrarMatriz() {
		if(ventanaMatriz == null || !ventanaMatriz.isVisible()) {
			ventanaMatriz = new VentanaMatrizActividades();
			ventanaMatriz.setVisible(true);
		}
	}
	
	public void mostrarEstudiantes() {
		JOptionPane.showMessageDialog(this,"La ventana de ver Estadisticas no esta funcionando en estos momentos...\n"
				+ " espera a nuestras proximas actualizaciones para disfrutar de esta función.");
		
	}
	
	public void mostrarLP() {
		VentanaMostrarLPStats ventana = new VentanaMostrarLPStats();
		ventana.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if(comando.equals(MATRIZ)) {
			mostrarMatriz();
		} else if(comando.equals(ESTUDIANTES)) {
			mostrarEstudiantes();
		} else if(comando.equals(LP)) {
			mostrarLP();
		}
	}

	

}
