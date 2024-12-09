package interfaz;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import actividades.Actividad;
import learningPaths.LearningPath;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaAgregarActividadesPropias extends JFrame implements ActionListener{
	
	private Profesor profesorActual;
	public int cantidad;
	public int contador = 0;
	private LearningPath learningPathActual;
	private List<Actividad> listaActividadesPropias;
	private JPanel panelActividades;
	private JButton botonAgregar;
	private static final String AGREGAR = "Agregar actividad con ID";
	private VentanaResenasActividadAgregar ventanaResenasActividadAgregar;


	public VentanaAgregarActividadesPropias(Profesor profesorActual, int cantidad, LearningPath learningPathActual) {
		
		this.profesorActual = profesorActual;
		this.cantidad = cantidad;
		this.learningPathActual = learningPathActual;
		this.listaActividadesPropias = VentanaPrincipal.sistemaRegistro.getActividadesPropiasProfesor(this.profesorActual);
		
		
		setLayout( new BorderLayout());
		
		// Panel central con la información de todas las actividades del profesor propias
		add(mostrarPanelActividades(), BorderLayout.CENTER);
		
		botonAgregar = new JButton(AGREGAR);
		botonAgregar.addActionListener(this);
		botonAgregar.setActionCommand(AGREGAR);
		add(botonAgregar, BorderLayout.SOUTH);
		
        // Termina de configurar la ventana
        setTitle( "Agregar actividad propia a LearningPath" );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 600, 500 );
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
        for (Actividad actividad : listaActividadesPropias) {
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
	
	public void mostrarVentanaResenasActividadAgregar(String idActividad) {
		// Crea una ventana donde se miran las reseñas de la actividad
		Actividad actividad = profesorActual.getActividadById(idActividad);
		if (actividad != null) {
			if( ventanaResenasActividadAgregar== null || !ventanaResenasActividadAgregar.isVisible( ) )
	        {
	        	ventanaResenasActividadAgregar = new VentanaResenasActividadAgregar(this, learningPathActual, actividad);
	        	ventanaResenasActividadAgregar.setVisible(true);
	        }
		} else {
			JOptionPane.showMessageDialog(this,  "No existe una actividad con ese ID", "ID incorrecto", JOptionPane.ERROR_MESSAGE);			
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals( AGREGAR ) )
        {
        	if (contador < cantidad) {
        		String idActividad = JOptionPane.showInputDialog("Ingrese el ID de la actividad a agregar:");
        		learningPathActual.agregarActividad(profesorActual.getActividadById(idActividad));
        		mostrarVentanaResenasActividadAgregar(idActividad);
        	} else {
        		JOptionPane.showMessageDialog(this,  "Se excedió del número de actividades a agregar", "Cantidad incorrecta", JOptionPane.ERROR_MESSAGE);
        		this.dispose();
        	}
        }
	}

}
