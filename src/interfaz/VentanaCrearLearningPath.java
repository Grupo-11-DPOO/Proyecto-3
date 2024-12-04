package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import learningPaths.LearningPath;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaCrearLearningPath extends JFrame implements ActionListener{
	
	private Profesor profesorActual;
	private JTextField txtTitulo;
	private JTextField txtObjetivo;
	private JComboBox<String> cbbNivel;
	private String[] niveles = {"Bajo","Intermedio", "Avanzado"};
	private JButton botonCrear;
	private static final String CREAR = "Crear";
	private VentanaAgregarActividadesPropias ventanaAgregarActividadesPropias;


	public VentanaCrearLearningPath(Profesor profesorActual) {
		
		this.profesorActual = profesorActual;
		
		setLayout(new GridLayout(4, 2));

		JLabel labelTitulo = new JLabel("Título:");
		txtTitulo = new JTextField();
		
		JLabel labelObjetivo = new JLabel("Objetivo:");
		txtObjetivo = new JTextField();
		
		JLabel labelNivel = new JLabel("Nivel:");
		cbbNivel = new JComboBox<String>(niveles);
		
		add(labelTitulo);
		add(txtTitulo);
		add(labelObjetivo);
		add(txtObjetivo);
		add(labelNivel);
		add(cbbNivel);
		
		botonCrear = new JButton(CREAR);
		botonCrear.addActionListener(this);
		botonCrear.setActionCommand(CREAR);
		
		add(botonCrear);
		
        // Termina de configurar la ventana
        setTitle( "Crear Learning Path" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 900 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public String getTitulo() {
		String titulo = txtTitulo.getText();
		return titulo;
	}
	
	public String getObjetivo() {
		String objetivo = txtObjetivo.getText();
		return objetivo;
	}
	
	public String getNivel() {
    	String opcion = (String) cbbNivel.getSelectedItem();
    	return opcion;
	}
	
	public void crearVacio() {
		String titulo = getTitulo();
		String objetivo = getObjetivo();
		String nivel = getNivel();
		if (titulo.isEmpty() || objetivo.isEmpty() || nivel.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		} else {
			LearningPath learningPath = profesorActual.crearLearningPath(titulo, objetivo, nivel);
			VentanaPrincipal.sistemaRegistro.guardarLearningPath(learningPath);
			VentanaPrincipal.sistemaRegistro.guardarProfesor(profesorActual);
			int opcion = JOptionPane.showConfirmDialog(this, "Learning Path creado exitosamente. Está vacío, ¿desea agregar actividades?","Información importante. ", JOptionPane.YES_NO_OPTION);
			if (opcion == JOptionPane.YES_OPTION) {
				// Proceso agregar actividades
				String cantidadString = JOptionPane.showInputDialog("Ingrese la cantidad de actividades (números enteros):");
				try {
		            int cantidad = Integer.parseInt(cantidadString);
		            if (cantidad > 0) {
		            	agregarActividades(cantidad, learningPath);
		            	VentanaPrincipal.sistemaRegistro.guardarLearningPath(learningPath);
		            }
		            
		        } catch (NumberFormatException e) {
		            JOptionPane.showMessageDialog(this,  "Input inválido. Por favor solo ingrese enteros.", "Input error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		}
		
		this.dispose();
	}
	
	public void agregarActividades(int cantidad, LearningPath learningPath) {
        String[] opciones = { "Agregar actividad propia existente", "Crear nueva actividad" };
        int eleccion = JOptionPane.showOptionDialog(this, "Seleccione una opción:", "Gestión de Actividades", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
        switch (eleccion) {
            case 0: 
                // Actividad propia
            	mostrarVentanaAgregarActividadesPropias(cantidad, learningPath);
                break;
            case 1: 
            	// Crear actividad
                // TODO
            	// Cuando la llama dentro de ese menu esta la opcion para agregar al learningPath
            	
                break;
        }
	}
	
	public void mostrarVentanaAgregarActividadesPropias(int cantidad, LearningPath learningPath) {
        if( ventanaAgregarActividadesPropias== null || !ventanaAgregarActividadesPropias.isVisible( ) )
        {
        	ventanaAgregarActividadesPropias = new VentanaAgregarActividadesPropias(profesorActual, cantidad, learningPath);
        	ventanaAgregarActividadesPropias.setVisible(true);
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals( CREAR ) )
        {
            crearVacio();
        }
	}
}
