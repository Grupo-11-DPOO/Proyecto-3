package interfaz;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import learningPaths.LearningPath;

public class VentanaVerOfertaLearningPath extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<LearningPath> listaPaths;
    private JTextArea detallesArea;
	public VentanaVerOfertaLearningPath(){
		// TODO Auto-generated constructor stub
		        setTitle("Oferta de Learning Paths");
		        setSize(500, 400);
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		        ArrayList<LearningPath> paths = new ArrayList<>(VentanaPrincipal.learningPaths.values());

		        listaPaths = new JList<>(paths.toArray(new LearningPath[0]));
		        listaPaths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		        detallesArea = new JTextArea();
		        detallesArea.setEditable(false);

		        setLayout(new BorderLayout());
		        add(new JScrollPane(listaPaths), BorderLayout.WEST);
		        add(new JScrollPane(detallesArea), BorderLayout.CENTER);

		        listaPaths.addListSelectionListener(e -> {
		            if (!e.getValueIsAdjusting()) {
		                LearningPath selectedPath = listaPaths.getSelectedValue();
		                if (selectedPath != null) {
		                    mostrarDetalles(selectedPath);
		                }
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

