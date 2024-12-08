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

import learningPaths.LearningPath;
import usuarios.Estudiante;

public class VentanaVerOfertaLearningPath extends JFrame{

	/**
	 * 
	 */
	private JButton agregarButton;
	private Estudiante estudiante;
	private static final long serialVersionUID = 1L;
	private JList<LearningPath> listaPaths;
    private JTextArea detallesArea;
	public VentanaVerOfertaLearningPath(Estudiante estudianteActual){
		// TODO Auto-generated constructor stub
				this.estudiante = estudianteActual;
		        setTitle("Oferta de Learning Paths");
		        setSize(500, 400);
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		        ArrayList<LearningPath> paths = new ArrayList<>(VentanaPrincipal.learningPaths.values());

		        listaPaths = new JList<>(paths.toArray(new LearningPath[0]));
		        listaPaths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		        detallesArea = new JTextArea();
		        detallesArea.setEditable(false);
		        
		        agregarButton = new JButton("Agregar Learning Path");
		        agregarButton.setEnabled(false);
		        
		        setLayout(new BorderLayout());
		        add(new JScrollPane(listaPaths), BorderLayout.WEST);
		        add(new JScrollPane(detallesArea), BorderLayout.CENTER);
		        
		        
		        JPanel buttonPanel = new JPanel();
		        buttonPanel.add(agregarButton);
		        add(buttonPanel, BorderLayout.SOUTH);

		        listaPaths.addListSelectionListener(e -> {
		            if (!e.getValueIsAdjusting()) {
		                LearningPath selectedPath = listaPaths.getSelectedValue();
		                if (selectedPath != null) {
		                    mostrarDetalles(selectedPath);
		                    agregarButton.setEnabled(true);
		                    
		                }
		                else {
		                	agregarButton.setEnabled(false);
		                }
		            }
		        });
		    
			
		    agregarButton.addActionListener(e -> {
		        LearningPath pathSeleccionado = listaPaths.getSelectedValue();
		        if (pathSeleccionado != null) {
		            estudiante.setLearningPathEnCurso(pathSeleccionado);
		            JOptionPane.showMessageDialog(this, 
		                "Learning Path agregado correctamente a " + estudiante.getLogin(), 
		                "con éxito", 
		                JOptionPane.INFORMATION_MESSAGE);
		        }
		    });
		}


		    private void mostrarDetalles(LearningPath path) {
		        String detalles = "Nombre: " + path.getTitulo() + "\n" +
		                          "Descripción: " + path.getDescripcion() + "\n" +
		                          "Duración: " + path.getDuracion() +"\n"+
		                          "ID"+ path.getId()+"\n"+
		                          "Rating"+path.getRating()+"\n"+
		                          "Version"+path.getVersion()+"\n";
		        detallesArea.setText(detalles);
		    }
		    




	}

