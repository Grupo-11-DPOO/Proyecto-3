package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import actividades.Actividad;
import learningPaths.LearningPath;

@SuppressWarnings("serial")
public class VentanaResenasActividadAgregar extends JFrame implements ActionListener{

	private JButton agregar;
	private static final String AGREGAR = "Agregar";
	private JButton cerrar;
	private static final String CERRAR = "Cerrar";
	private VentanaAgregarActividadesPropias ventanaPadre;
	private LearningPath learningPathActual;
	private Actividad actividad;
	
	public VentanaResenasActividadAgregar(VentanaAgregarActividadesPropias ventanaPadre, LearningPath learningPathActual, Actividad actividad) {
		
		this.ventanaPadre = ventanaPadre;
		this.learningPathActual = learningPathActual;
		this.actividad = actividad;
		
		setLayout( new BorderLayout());
		
		// En el centro, la resena
        // Crear el área de texto
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false); // Hacer que no sea editable

        // Llenar el área de texto con los contenidos de la lista
        List<String> listaResenas = actividad.getResenas();
        for (String elemento : listaResenas) {
            textArea.append(elemento + "\n");
        }

        // Añadir el área de texto a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
		
		
		// Botones de agregar o no agregar
		agregar = new JButton(AGREGAR);
		agregar.addActionListener(this);
		agregar.setActionCommand(AGREGAR);
		cerrar = new JButton(CERRAR);
		cerrar.addActionListener(this);
		cerrar.setActionCommand(CERRAR);
		
		JPanel botones = new JPanel();
		botones.add(agregar);
		botones.add(cerrar);
		add(botones, BorderLayout.SOUTH);
		
        // Termina de configurar la ventana
        setTitle( "Ver reseñas para agregar actividad" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 900 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public void agregarActividad () {
		learningPathActual.agregarActividad(actividad);
		VentanaPrincipal.sistemaRegistro.guardarLearningPath(learningPathActual);
		ventanaPadre.contador ++;
		JOptionPane.showMessageDialog(this,"¡Actividad agregada al Learning Path!");
		if (ventanaPadre.contador == ventanaPadre.cantidad) {
			ventanaPadre.dispose();
		}
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals( AGREGAR ) )
        {
        	agregarActividad();
        } else if (comando.equals(CERRAR)) {
        	this.dispose();
        }
	}
}
