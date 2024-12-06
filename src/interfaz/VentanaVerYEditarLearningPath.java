package interfaz;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import learningPaths.LearningPath;
import usuarios.Profesor;

@SuppressWarnings("serial")
public class VentanaVerYEditarLearningPath extends JFrame implements ActionListener{

	private Profesor profesorActual;
	private JButton botonSi;
	private JButton botonNo;
	private static final String SI = "Sí";
	private static final String NO = "No";
	private JPanel panelInfo;
	private List<LearningPath> listaLearningPathPropios;
	private VentanaEditarLearningPath ventanaEditarLearningPath;
		
	public VentanaVerYEditarLearningPath(Profesor profesorActual) {
		
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		this.profesorActual = profesorActual;
		this.listaLearningPathPropios = VentanaPrincipal.sistemaRegistro.getLearningPathsPropiosProfesor(this.profesorActual);
		
		// Titulo arriba
		JLabel titulo = new JLabel("Lista Learning Paths");
		titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(titulo);
		
		// En el centro el panel con la info de los learning paths
		add(mostrarPanelInfoLearningPaths());
		
		// Abajo botones si y no sobre editar
		
		botonSi = new JButton(SI);
		botonSi.addActionListener(this);
		botonSi.setActionCommand(SI);
		botonNo = new JButton(NO);
		botonNo.addActionListener(this);
		botonNo.setActionCommand(NO);
		
		JPanel botones = new JPanel();
		botones.setLayout(new BorderLayout());
		JLabel tituloOpciones = new JLabel("¿Desea editar algún Learning Path?");
		tituloOpciones.setHorizontalAlignment(JLabel.CENTER);
		botones.add(tituloOpciones, BorderLayout.NORTH);
		botones.add(botonSi, BorderLayout.WEST);
		botones.add(botonNo, BorderLayout.EAST);
		add(botones);
		
		
        // Termina de configurar la ventana
        setTitle( "Ver y Editar Learning Paths");
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 400 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
		
		
	}
	
	public JPanel mostrarPanelInfoLearningPaths() {
		panelInfo = new JPanel();
        String[] columnas = { "ID", "Título", "Descripción", "Nivel", "Fecha creación", "Fecha modificación", "Duración (min)", "Rating", "Versión", "Cantidad Actividades" };

        // Modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        

        // Llenar el modelo con los datos de la lista
        for (LearningPath learningPath : listaLearningPathPropios) {
        	LocalDateTime fechaCreacion = learningPath.getFechaCreacion();
        	LocalDateTime fechaModificacion = learningPath.getFechaModificacion();
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
        	String formattedDateCrea = fechaCreacion.format(formatter);
        	String formattedDateModi = fechaModificacion.format(formatter);
            modelo.addRow(new Object[] {
                learningPath.getId(),
                learningPath.getTitulo(),
                learningPath.getDescripcion(),
                learningPath.getNivel(),
                formattedDateCrea,
                formattedDateModi,
                learningPath.getDuracion(),
                learningPath.getRating(),
                learningPath.getVersion(),
                learningPath.getListaActividades().size(),
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

        panelInfo.add(scrollPane);
		
		return panelInfo;
	}
	
	public void mostrarVentanaEditar(String idLearningPath) {
		// Crea una ventana donde se miran las reseñas de la actividad
		LearningPath learningPath = profesorActual.getLearningPathById(idLearningPath);
		if (learningPath != null) {
			if( ventanaEditarLearningPath== null || !ventanaEditarLearningPath.isVisible( ) )
	        {
				ventanaEditarLearningPath = new VentanaEditarLearningPath(this, learningPath, profesorActual);
				ventanaEditarLearningPath.setVisible(true);
				this.dispose();
	        }
		} else {
			JOptionPane.showMessageDialog(this,  "No existe un Learning Path con ese ID", "ID incorrecto", JOptionPane.ERROR_MESSAGE);			
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand( );
        if( comando.equals( SI ) )
        {
        	String idLearningPath = JOptionPane.showInputDialog("Ingrese el ID del Learning Path a editar");
        	mostrarVentanaEditar(idLearningPath);
        } else if (comando.equals(NO)) {
        	this.dispose();
        }
	}
}
