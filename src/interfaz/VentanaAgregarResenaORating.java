package interfaz;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


public class VentanaAgregarResenaORating extends JFrame implements ActionListener{
	private JButton agregarButton;
	private JButton agregarRating;
	
	private static final long serialVersionUID = 1L;
	private JList<Actividad> listaPaths;
    private JTextArea detallesArea;
    public static final String RESENA = "Agregar Reseña";
    public static final String RATING = "Agregar Rating";
	public VentanaAgregarResenaORating() {
		
        setTitle("Oferta de Learning Paths");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ArrayList<Actividad> paths = new ArrayList<>(VentanaPrincipal.actividades.values());

        listaPaths = new JList<>(paths.toArray(new Actividad[0]));
        listaPaths.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        detallesArea = new JTextArea();
        detallesArea.setEditable(false);
        
        agregarButton = new JButton(RESENA);
        agregarButton.addActionListener(this);
        agregarButton.setActionCommand(RESENA);
        
        
        agregarRating = new JButton(RATING);
        agregarRating.addActionListener(this);
        agregarRating.setActionCommand(RATING);
        
        setLayout(new BorderLayout());
        add(new JScrollPane(listaPaths), BorderLayout.WEST);
        add(new JScrollPane(detallesArea), BorderLayout.CENTER);
        
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(agregarButton);
        buttonPanel.add(agregarRating);
        add(buttonPanel, BorderLayout.SOUTH);

        listaPaths.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Actividad selectedPath = listaPaths.getSelectedValue();
                if (selectedPath != null) {
                    mostrarDetalles(selectedPath);
                    agregarButton.setEnabled(true);
                    
                }
                else {
                	agregarButton.setEnabled(false);
                }
            }
        });
	}
	public void agregarResena(Actividad path) {
		if(path != null) {
			path.agregarResena(JOptionPane.showInputDialog("Ingrese una reseña para la actividad seleccionada: "));
		} else {
			JOptionPane.showMessageDialog(this, "No hay ninguna actividad seleccionada.");
		}
	}
	
	public void agregarRatingActividad(Actividad path) throws Exception {
		if(path != null) {
			float rating =-0.5f;
			while(rating<0 || rating>5) {
				rating =  Float.parseFloat(JOptionPane.showInputDialog("Ingrese un rating para la actividad seleccionada entre 0 y 5: "));
				if(rating<0 ||rating>5) {
					JOptionPane.showMessageDialog(this, "El numero "+rating+" no esta dentro del rango esperado.");
				}
			}
			path.agregarRating(rating);;
			mostrarDetalles(path);
		} else {
			JOptionPane.showMessageDialog(this, "No hay ninguna actividad seleccionada.");
		}
	}
	private void mostrarDetalles(Actividad path) {
        String detalles = "Nombre: " + path.getTitulo() + "\n" +
                          "Descripción: " + path.getDescripcion() + "\n" +
                          "Duración: " + path.getDuracionMinutos() +"\n"+
                          "ID"+ path.getId()+"\n"+
                          "Rating"+path.getRating()+"\n";
                          
        detallesArea.setText(detalles);
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comando = e.getActionCommand();
		Actividad selectedPath = listaPaths.getSelectedValue();
		if(comando.equals(RESENA)) {
			agregarResena(selectedPath);
		} else if (comando.equals(RATING)){
			try {
				agregarRatingActividad(selectedPath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
}
