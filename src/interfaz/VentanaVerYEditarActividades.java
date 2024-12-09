package interfaz;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
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
public class VentanaVerYEditarActividades extends JFrame implements ActionListener {
	private Profesor profesorActual;
	private JButton botonSi;
	private JButton botonNo;
	private static final String SI = "Si";
	private static final String NO = "No";
	private JPanel panelActividades;
	private List<Actividad> listaActividadesPropias;
	private VentanaEditarActividad ventanaEditarActividad;
	
	public VentanaVerYEditarActividades(Profesor profesorActivo) {
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		this.profesorActual = profesorActivo;
		this.listaActividadesPropias = VentanaPrincipal.sistemaRegistro.getActividadesPropiasProfesor(profesorActual);
		
		// Titulo arriba
		JLabel titulo = new JLabel("Lista Actividades");
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(titulo);
		
		// En el centro el panel con la info de los learning paths
		add(mostrarPanelActividades());
		
		// Abajo botones si y no sobre editar
		
		botonSi = new JButton(SI);
		botonSi.addActionListener(this);
		botonSi.setActionCommand(SI);
		botonNo = new JButton(NO);
		botonNo.addActionListener(this);
		botonNo.setActionCommand(NO);
		
		JPanel botones = new JPanel();
		botones.setLayout(new BorderLayout());
		JLabel tituloOpciones = new JLabel("¿Desea editar algúna actividad?");
		tituloOpciones.setHorizontalAlignment(JLabel.CENTER);
		botones.add(tituloOpciones, BorderLayout.NORTH);
		botones.add(botonSi, BorderLayout.WEST);
		botones.add(botonNo, BorderLayout.EAST);
		add(botones);
		
		
        // Termina de configurar la ventana
        setTitle( "Ver y Editar Actividades");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 400 );
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
	
	public void mostrarVentanaEditar(String idActividad) {
		// Crea una ventana donde se miran las reseñas de la actividad
		Actividad actividad = profesorActual.getActividadById(idActividad);
		if (actividad != null) {
			if( ventanaEditarActividad== null || !ventanaEditarActividad.isVisible( ) )
	        {
				ventanaEditarActividad = new VentanaEditarActividad(this, actividad, profesorActual);
				ventanaEditarActividad.setVisible(true);
				this.dispose();
	        }
		} else {
			JOptionPane.showMessageDialog(this,  "No existe una actividad con ese ID", "ID incorrecto", JOptionPane.ERROR_MESSAGE);			
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if( comando.equals( SI ) )
        {
        	String idActividad = JOptionPane.showInputDialog("Ingrese el ID de la actividad a editar");
        	mostrarVentanaEditar(idActividad);
        } else if (comando.equals(NO)) {
        	this.dispose();
        }
		
	}

}
