package interfaz;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import actividades.Actividad;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaClonar extends JFrame implements ActionListener{

	private Profesor profesorActual;
	private JPanel panelActividades;
	private JButton botonClonar;
	private JButton botonCerrar;
	private Collection<Actividad> listaActividades;
	private static final String CLONAR = "Clonar actividad con ID";
	private static final String CERRAR = "Cerrar";
	
	public VentanaClonar (Profesor profesorActual) {
		
		this.profesorActual = profesorActual;
		this.listaActividades = VentanaPrincipal.actividades.values();
		JLabel labelTitulo = new JLabel("Clonar actividades para "+profesorActual.getLogin());
		add(labelTitulo, BorderLayout.NORTH);
		
		// Panel central con la información de todas las actividades
		add(mostrarPanelActividades(), BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel();
		botonClonar = new JButton(CLONAR);
		botonClonar.addActionListener(this);
		botonClonar.setActionCommand(CLONAR);
		botonCerrar = new JButton(CERRAR);
		botonCerrar.addActionListener(this);
		botonCerrar.setActionCommand(CERRAR);
		panelBotones.add(botonClonar);
		panelBotones.add(botonCerrar);
		add(panelBotones, BorderLayout.SOUTH);
		
        // Termina de configurar la ventana
        setTitle( "Clonar actividades" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 580, 550 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
	public JPanel mostrarPanelActividades() {
		panelActividades = new JPanel();
        String[] columnas = { "ID", "Título", "Objetivo", "Descripción", "Nivel", "Duración (min)", "Obligatorio", "Rating", "Tipo Actividad" };
        // Modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        // Llenar el modelo con los datos de la lista
        for (Actividad actividad : listaActividades) {
            modelo.addRow(new Object[] {
                actividad.getId(),
                actividad.getTitulo(),
                actividad.getObjetivo(),
                actividad.getDescripcion(),
                actividad.getNivel(),
                actividad.getDuracionMinutos(),
                actividad.isObligatorio() ? "Sí" : "No",
                actividad.getRating(),
                actividad.getTipoActividad()
            });
        }
        // Crear la tabla
        JTable tabla = new JTable(modelo);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setFillsViewportHeight(true);
        // Añadir funcionalidad para copiar la celda de ID
        tabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tabla.getSelectedRow();
                int columnaSeleccionada = tabla.getSelectedColumn();
                // Verificar que se seleccionó la columna "ID"
                if (columnaSeleccionada == 0 && filaSeleccionada >= 0) {
                    // Obtener el valor de la celda seleccionada
                    Object valorCelda = tabla.getValueAt(filaSeleccionada, columnaSeleccionada);
                    // Copiar el valor al portapapeles
                    StringSelection seleccion = new StringSelection(valorCelda.toString());
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(seleccion, null);
                    // Mostrar mensaje de confirmación
                    JOptionPane.showMessageDialog(null, "ID copiado al portapapeles: " + valorCelda);
                }
            }
        });
        // Añadir la tabla a un JScrollPane
        JScrollPane scrollPane = new JScrollPane(tabla);
        panelActividades.add(scrollPane, BorderLayout.CENTER);
		return panelActividades;
	}
	
	private void clonarActividad() throws Exception {
		String idActividad = JOptionPane.showInputDialog("Ingrese el ID de la actividad a agregar:");
		Actividad actividad = VentanaPrincipal.actividades.get(idActividad);
		if (actividad != null) {
			Actividad actividadClonada = profesorActual.clonarActividad(actividad);
			VentanaPrincipal.sistemaRegistro.guardarActividad(actividadClonada);
			VentanaPrincipal.sistemaRegistro.guardarProfesor(profesorActual);
		} else {
			JOptionPane.showMessageDialog(this,  "No existe una actividad con ese ID", "ID incorrecto", JOptionPane.ERROR_MESSAGE);	
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals( CLONAR ) ) {
        	try {
				clonarActividad();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        } else if (comando.equals(CERRAR)) {
        	this.dispose();
        }
	}
}
