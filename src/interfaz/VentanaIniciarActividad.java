package interfaz;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import actividades.Actividad;
import learningPaths.LearningPath;
import usuarios.Estudiante;


public class VentanaIniciarActividad extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton agregarButton;
	private Estudiante estudiante;
	private LearningPath learningPath;
	private JList<Actividad> listaActividades;
	private JTextArea detallesArea;
	
	public VentanaIniciarActividad(Estudiante estudiante){
		
		this.estudiante = estudiante;
		setTitle( "Iniciar Actividad del Learning Path" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600 );
        
        this.learningPath = estudiante.getLearningPathEnCurso();
        
        detallesArea = new JTextArea();
        detallesArea.setEditable(false);
        
        agregarButton = new JButton("Iniciar Actividad");
        agregarButton.setEnabled(false);
        
        setLayout(new BorderLayout());
        add(new JScrollPane(detallesArea),BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        
        buttonPanel.add(agregarButton);
        add(buttonPanel,BorderLayout.SOUTH);
        
        if(learningPath== null) {
        	mostrarMensajeLearningPathNoSeleccionado();
        }
        else {
        	configurarComponentes();
        }
	}
	
	private void mostrarMensajeLearningPathNoSeleccionado() {
    	detallesArea.setText("Por favor, seleccione un LearningPath antes de iniciar una actividad");
    }
	
	private void configurarComponentes() {
		this.learningPath = estudiante.getLearningPathEnCurso();
		ArrayList<Actividad> act = new ArrayList<>(this.learningPath.getListaActividades());
		listaActividades = new JList<>(act.toArray(new Actividad[0]));
		listaActividades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(listaActividades),BorderLayout.WEST);
		listaActividades.setEnabled(true);
		System.out.println(this.learningPath.getListaActividades());
		listaActividades.addListSelectionListener(e ->{
			
			if(!e.getValueIsAdjusting()) {
				
				Actividad actividadSeleccionada = listaActividades.getSelectedValue();
				
				if(actividadSeleccionada != null) {
					mostrarDetalles(actividadSeleccionada);
					agregarButton.setEnabled(true);
					
				}
				else {
					agregarButton.setEnabled(false);
				}
			}
		});
		
		agregarButton.addActionListener(e ->{
			Actividad actividadSeleccionada = listaActividades.getSelectedValue();
			if(actividadSeleccionada != null) {
				estudiante.setActividadEnCurso(actividadSeleccionada);
				VentanaPrincipal.sistemaRegistro.guardarEstudiante(estudiante);
				JOptionPane.showMessageDialog(this,"Actividad agregada con éxito y lista para iniciar","Éxito",JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	private void mostrarDetalles(Actividad act) {
		String detalles = "Titulo: "+act.getTitulo()+ "\n"+
							"Descripción: "+act.getDescripcion()+ "\n"+
							"Duración en minutos: "+ act.duracionMinutos+ "\n"+
							"Tipo de Actividad: "+ act.getTipoActividad().name()+ "\n"+
							"Nivel: "+ act.getNivel()+ "\n"+
							"¿Es obligatorio?: "+ act.isObligatorio()+ "\n"+
							"Rating: "+ act.getRating()+ "\n"+
							"Objetivo: "+act.getObjetivo()+ "\n"+
							"Tiempo Limite: "+ act.getTiempoLimite();
		detallesArea.setText(detalles);
	}
}