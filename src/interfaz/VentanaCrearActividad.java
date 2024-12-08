package interfaz;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaCrearActividad extends JFrame implements ActionListener{
	
	private Profesor profesorActual;
	private String[] niveles = {"Bajo","Intermedio", "Avanzado"};
	private JButton botonRecurso;
	private JButton botonTarea;
	private JButton botonQuiz;
	private JButton botonExamen;
	private JButton botonEncuesta;
	private JButton botonQuizVerdadero;
	private JTextField txtTitulo;
	private JTextField txtObjetivo;
	private JTextField txtDescripcion;
	private JComboBox<String> cbbNivel;
	private JTextField txtDuracion;
	private JCheckBox chkObligatorio;
	private static final String RECURSO = "Crear recurso";
	private static final String TAREA = "Crear tarea";
	private static final String QUIZ = "Crear quiz (opción múltiple)";
	private static final String EXAMEN = "Crear examen";
	private static final String ENCUESTA = "Crear encuesta";
	private static final String QUIZ_VF = "Crear quiz (verdadero/falso)";
	private VentanaCrearRecurso ventanaCrearRecurso;
	private VentanaCrearTarea ventanaCrearTarea;
	private VentanaCrearQuizMultiple ventanaCrearQuizMultiple;
	private VentanaCrearExamen ventanaCrearExamen;
	private VentanaCrearEncuesta ventanaCrearEncuesta;
	private VentanaCrearQuizVF ventanaCrearQuizVF;

	public VentanaCrearActividad(Profesor profesorActual) {
		
		this.profesorActual = profesorActual;
		
		setLayout(new GridLayout(6, 2));
		// Lado izquierdo parametros generales de actividad
		// Lado derecho botones crear actividad
		
		// Panel titulo
		JPanel panelTitulo = new JPanel();
		JLabel labelTitulo = new JLabel("Título");
		txtTitulo = new JTextField(20);
		panelTitulo.add(labelTitulo);
		panelTitulo.add(txtTitulo);
		add(panelTitulo);
		
		// Boton crear Recurso
		botonRecurso = new JButton(RECURSO);
		botonRecurso.addActionListener(this);
		botonRecurso.setActionCommand(RECURSO);
		add(botonRecurso);
		
		// Panel objetivo
		JPanel panelObjetivo = new JPanel();
		JLabel labelObjetivo = new JLabel("Objetivo");
		txtObjetivo = new JTextField(25);
		panelObjetivo.add(labelObjetivo);
		panelObjetivo.add(txtObjetivo);
		add(panelObjetivo);
		
		// Boton crear Tarea
		botonTarea = new JButton(TAREA);
		botonTarea.addActionListener(this);
		botonTarea.setActionCommand(TAREA);
		add(botonTarea);
		
		// Panel descripción
		JPanel panelDescripcion = new JPanel();
		JLabel labelDescripcion = new JLabel("Descripción");
		txtDescripcion = new JTextField(25);
		panelDescripcion.add(labelDescripcion);
		panelDescripcion.add(txtDescripcion);
		add(panelDescripcion);
		
		// Boton crear Quiz
		botonQuiz = new JButton(QUIZ);
		botonQuiz.addActionListener(this);
		botonQuiz.setActionCommand(QUIZ);
		add(botonQuiz);
		
		// Panel nivel
		JPanel panelNivel = new JPanel();
		JLabel labelNivel = new JLabel("Nivel");
		cbbNivel = new JComboBox<String>(niveles);
		panelNivel.add(labelNivel);
		panelNivel.add(cbbNivel);
		add(panelNivel);
		
		// Boton crear Examen
		botonExamen = new JButton(EXAMEN);
		botonExamen.addActionListener(this);
		botonExamen.setActionCommand(EXAMEN);
		add(botonExamen);
		
		// Panel duracion
		JPanel panelDuracion = new JPanel();
		JLabel labelDuracion = new JLabel("Duración en minutos");
		txtDuracion = new JTextField(10);
		txtDuracion.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) { // Bloquear caracteres no numéricos
                    e.consume();
                }
            }
        });
		panelDuracion.add(labelDuracion);
		panelDuracion.add(txtDuracion);
		add(panelDuracion);
		
		// Boton crear Encuesta
		botonEncuesta = new JButton(ENCUESTA);
		botonEncuesta.addActionListener(this);
		botonEncuesta.setActionCommand(ENCUESTA);
		add(botonEncuesta);
		
		// Panel obligatorio 
		JPanel panelObligatorio = new JPanel();
		chkObligatorio = new JCheckBox("Obligatorio ");	
		chkObligatorio.setHorizontalTextPosition(SwingConstants.LEFT);
		panelObligatorio.add(chkObligatorio);
		add(panelObligatorio);
		
		// Boton crear Quiz V/F
		botonQuizVerdadero = new JButton(QUIZ_VF);
		botonQuizVerdadero.addActionListener(this);
		botonQuizVerdadero.setActionCommand(QUIZ_VF);
		add(botonQuizVerdadero);
		
        // Termina de configurar la ventana
        setTitle( "Crear actividad" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public void crearRecurso() {
		String titulo = txtTitulo.getText();
		String objetivo = txtObjetivo.getText();
		String descripcion = txtDescripcion.getText();
		String nivel = (String) cbbNivel.getSelectedItem();
		String duracionString = txtDuracion.getText();
		int duracion = 0;
		try {
	        // Intentar convertir el texto a un entero
			duracion = Integer.parseInt(duracionString);
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido para duración en minutos", "Error de formato", JOptionPane.ERROR_MESSAGE);
	    }
		boolean obligatorio = chkObligatorio.isSelected();
		if (titulo.length() > 0 && objetivo.length() > 0 && descripcion.length() > 0 && nivel.length() > 0 && duracion > 0) {
			mostrarVentanaCrearRecurso(titulo, objetivo, descripcion, nivel, duracion, obligatorio);
		} else {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	public void mostrarVentanaCrearRecurso(String titulo, String objetivo, String descripcion, String nivel, int duracion, boolean obligatorio) {
		if( ventanaCrearRecurso == null || ! ventanaCrearRecurso.isVisible( ) )
        {
			ventanaCrearRecurso = new VentanaCrearRecurso(profesorActual, titulo, objetivo, descripcion, nivel, duracion, obligatorio);
			ventanaCrearRecurso.setVisible(true);
			this.dispose();
        }
	}
	
	public void crearTarea() {
		String titulo = txtTitulo.getText();
		String objetivo = txtObjetivo.getText();
		String descripcion = txtDescripcion.getText();
		String nivel = (String) cbbNivel.getSelectedItem();
		String duracionString = txtDuracion.getText();
		int duracion = 0;
		try {
	        // Intentar convertir el texto a un entero
			duracion = Integer.parseInt(duracionString);
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido para duración en minutos", "Error de formato", JOptionPane.ERROR_MESSAGE);
	    }
		boolean obligatorio = chkObligatorio.isSelected();
		if (titulo.length() > 0 && objetivo.length() > 0 && descripcion.length() > 0 && nivel.length() > 0 && duracion > 0) {
			mostrarVentanaCrearTarea(titulo, objetivo, descripcion, nivel, duracion, obligatorio);
		} else {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	public void mostrarVentanaCrearTarea(String titulo, String objetivo, String descripcion, String nivel, int duracion, boolean obligatorio) {
		if( ventanaCrearTarea == null || ! ventanaCrearTarea.isVisible( ) )
		{
			ventanaCrearTarea = new VentanaCrearTarea(profesorActual, titulo, objetivo, descripcion, nivel, duracion, obligatorio);
			ventanaCrearTarea.setVisible(true);
			this.dispose();
		}
	}
	
	public void crearQuizMultiple() {
		String titulo = txtTitulo.getText();
		String objetivo = txtObjetivo.getText();
		String descripcion = txtDescripcion.getText();
		String nivel = (String) cbbNivel.getSelectedItem();
		String duracionString = txtDuracion.getText();
		int duracion = 0;
		try {
	        // Intentar convertir el texto a un entero
			duracion = Integer.parseInt(duracionString);
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido para duración en minutos", "Error de formato", JOptionPane.ERROR_MESSAGE);
	    }
		boolean obligatorio = chkObligatorio.isSelected();
		if (titulo.length() > 0 && objetivo.length() > 0 && descripcion.length() > 0 && nivel.length() > 0 && duracion > 0) {
			mostrarVentanaCrearQuizMultiple(titulo, objetivo, descripcion, nivel, duracion, obligatorio);
		} else {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	public void mostrarVentanaCrearQuizMultiple(String titulo, String objetivo, String descripcion, String nivel, int duracion, boolean obligatorio) {
		if( ventanaCrearQuizMultiple == null || ! ventanaCrearQuizMultiple.isVisible( ) )
		{
			ventanaCrearQuizMultiple = new VentanaCrearQuizMultiple(profesorActual, titulo, objetivo, descripcion, nivel, duracion, obligatorio);
			ventanaCrearQuizMultiple.setVisible(true);
			this.dispose();
		}
	}
	
	public void crearExamen() {
		String titulo = txtTitulo.getText();
		String objetivo = txtObjetivo.getText();
		String descripcion = txtDescripcion.getText();
		String nivel = (String) cbbNivel.getSelectedItem();
		String duracionString = txtDuracion.getText();
		int duracion = 0;
		try {
	        // Intentar convertir el texto a un entero
			duracion = Integer.parseInt(duracionString);
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido para duración en minutos", "Error de formato", JOptionPane.ERROR_MESSAGE);
	    }
		boolean obligatorio = chkObligatorio.isSelected();
		if (titulo.length() > 0 && objetivo.length() > 0 && descripcion.length() > 0 && nivel.length() > 0 && duracion > 0) {
			mostrarVentanaCrearExamen(titulo, objetivo, descripcion, nivel, duracion, obligatorio);
		} else {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	public void mostrarVentanaCrearExamen(String titulo, String objetivo, String descripcion, String nivel, int duracion, boolean obligatorio) {
		if( ventanaCrearExamen == null || ! ventanaCrearExamen.isVisible( ) )
		{
			ventanaCrearExamen = new VentanaCrearExamen(profesorActual, titulo, objetivo, descripcion, nivel, duracion, obligatorio);
			ventanaCrearExamen.setVisible(true);
			this.dispose();
		}
	}
	
	public void crearEncuesta() {
		String titulo = txtTitulo.getText();
		String objetivo = txtObjetivo.getText();
		String descripcion = txtDescripcion.getText();
		String nivel = (String) cbbNivel.getSelectedItem();
		String duracionString = txtDuracion.getText();
		int duracion = 0;
		try {
	        // Intentar convertir el texto a un entero
			duracion = Integer.parseInt(duracionString);
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido para duración en minutos", "Error de formato", JOptionPane.ERROR_MESSAGE);
	    }
		boolean obligatorio = chkObligatorio.isSelected();
		if (titulo.length() > 0 && objetivo.length() > 0 && descripcion.length() > 0 && nivel.length() > 0 && duracion > 0) {
			mostrarVentanaCrearEncuesta(titulo, objetivo, descripcion, nivel, duracion, obligatorio);
		} else {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	public void mostrarVentanaCrearEncuesta(String titulo, String objetivo, String descripcion, String nivel, int duracion, boolean obligatorio) {
		if( ventanaCrearEncuesta == null || ! ventanaCrearEncuesta.isVisible( ) )
		{
			ventanaCrearEncuesta = new VentanaCrearEncuesta(profesorActual, titulo, objetivo, descripcion, nivel, duracion, obligatorio);
			ventanaCrearEncuesta.setVisible(true);
			this.dispose();
		}
	}
	
	public void crearQuizVF() {
		String titulo = txtTitulo.getText();
		String objetivo = txtObjetivo.getText();
		String descripcion = txtDescripcion.getText();
		String nivel = (String) cbbNivel.getSelectedItem();
		String duracionString = txtDuracion.getText();
		int duracion = 0;
		try {
	        // Intentar convertir el texto a un entero
			duracion = Integer.parseInt(duracionString);
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido para duración en minutos", "Error de formato", JOptionPane.ERROR_MESSAGE);
	    }
		boolean obligatorio = chkObligatorio.isSelected();
		if (titulo.length() > 0 && objetivo.length() > 0 && descripcion.length() > 0 && nivel.length() > 0 && duracion > 0) {
			mostrarVentanaCrearQuizVF(titulo, objetivo, descripcion, nivel, duracion, obligatorio);
		} else {
			JOptionPane.showMessageDialog(this, "No puede dejar los campos en blanco", "Campos inválidos", JOptionPane.OK_OPTION);
		}
	}
	
	public void mostrarVentanaCrearQuizVF(String titulo, String objetivo, String descripcion, String nivel, int duracion, boolean obligatorio) {
		if( ventanaCrearQuizVF == null || ! ventanaCrearQuizVF.isVisible( ) )
		{
			ventanaCrearQuizVF = new VentanaCrearQuizVF(profesorActual, titulo, objetivo, descripcion, nivel, duracion, obligatorio);
			ventanaCrearQuizVF.setVisible(true);
			this.dispose();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if (comando.equals(RECURSO)) {
        	crearRecurso();
        } else if (comando.equals(TAREA)) {
        	crearTarea();
        } else if (comando.equals(QUIZ)) {
        	crearQuizMultiple();
        } else if (comando.equals(EXAMEN)) {
        	crearExamen();
        } else if (comando.equals(ENCUESTA)) {
        	crearEncuesta();
        } else if (comando.equals(QUIZ_VF)) {
        	crearQuizVF();
        }
	}

}
