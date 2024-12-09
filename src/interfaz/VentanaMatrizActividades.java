package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VentanaMatrizActividades extends JFrame {

	private static final long serialVersionUID = 1L;
	private JMenuBar menuBar;
    private JPanel leftPanel;
    private YearlyActivityPanel activityPanel;
    private JComboBox<String> studentComboBox;
    private JTextField startDateField;
    private JTextField endDateField;
    private JButton filterButton;


    private Map<LocalDate, Integer> activitiesMap;

    public VentanaMatrizActividades() {
        super("Intento de copiar al github");
        
        activitiesMap = generateSampleData();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createMenuBar();

        createLeftPanel();

        activityPanel = new YearlyActivityPanel(activitiesMap);
        add(activityPanel, BorderLayout.CENTER);

        JLabel statusBar = new JLabel("Listo.");
        add(statusBar, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem exitItem = new JMenuItem("Salir");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        JMenu studentsMenu = new JMenu("Estudiantes");
        JMenuItem addStudentItem = new JMenuItem("Añadir Estudiante");
        studentsMenu.add(addStudentItem);

        JMenu activitiesMenu = new JMenu("Actividades");
        JMenuItem addActivityItem = new JMenuItem("Registrar Actividad");
        // Aquí agregar lógica para registrar actividad
        activitiesMenu.add(addActivityItem);

        JMenu reportsMenu = new JMenu("Reportes");
        JMenuItem generateReportItem = new JMenuItem("Generar Reporte");
        // Aquí lógica para generar reportes
        reportsMenu.add(generateReportItem);

        menuBar.add(fileMenu);
        menuBar.add(studentsMenu);
        menuBar.add(activitiesMenu);
        menuBar.add(reportsMenu);

        setJMenuBar(menuBar);
    }

    private void createLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(250, 600));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; 
        gbc.gridy = 0; 
        gbc.insets = new Insets(5,5,5,5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel studentLabel = new JLabel("Estudiante:");
        leftPanel.add(studentLabel, gbc);

        gbc.gridy++;
        studentComboBox = new JComboBox<>(new String[]{"Todos", "Estudiante A", "Estudiante B"});
        leftPanel.add(studentComboBox, gbc);

        gbc.gridy++;
        JLabel startDateLabel = new JLabel("Fecha Inicio (YYYY-MM-DD):");
        leftPanel.add(startDateLabel, gbc);

        gbc.gridy++;
        startDateField = new JTextField(10);
        leftPanel.add(startDateField, gbc);

        gbc.gridy++;
        JLabel endDateLabel = new JLabel("Fecha Fin (YYYY-MM-DD):");
        leftPanel.add(endDateLabel, gbc);

        gbc.gridy++;
        endDateField = new JTextField(10);
        leftPanel.add(endDateField, gbc);

        gbc.gridy++;
        filterButton = new JButton("Filtrar");
        filterButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                activityPanel.setData(activitiesMap);
            }
        });
        leftPanel.add(filterButton, gbc);

        add(leftPanel, BorderLayout.WEST);
    }

    private Map<LocalDate, Integer> generateSampleData() {
        Map<LocalDate, Integer> map = new HashMap<>();
        LocalDate start = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        LocalDate end = LocalDate.of(LocalDate.now().getYear(), 12, 31);
        Random random = new Random();

        LocalDate date = start;
        while(!date.isAfter(end)) {
            map.put(date, random.nextInt(30)); 
            date = date.plusDays(1);
        }

        return map;
    }

   
}


@SuppressWarnings("serial")
class YearlyActivityPanel extends JPanel {
    private Map<LocalDate, Integer> data;
    private int cellSize = 15; 
    private int cellGap = 2;   

    public YearlyActivityPanel(Map<LocalDate, Integer> data) {
        this.data = data;
        setPreferredSize(new Dimension(700, 400));
        setBackground(Color.WHITE);
    }

    public void setData(Map<LocalDate, Integer> data) {
        this.data = data;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(data == null || data.isEmpty()) {
            g.drawString("No hay datos para mostrar.", 50, 50);
            return;
        }

       
        LocalDate start = LocalDate.of(LocalDate.now().getYear(), 1, 1);
        int dayOfYear = 0;

        for(int week = 0; week < 53; week++) {
            for(int dayOfWeek = 0; dayOfWeek < 7; dayOfWeek++) {
                LocalDate currentDate = start.plusDays(dayOfYear);
                if(currentDate.getYear() != start.getYear()) {
                    break;
                }
                Integer value = data.get(currentDate);
                if(value == null) value = 0;

                Color cellColor = getColorForValue(value);
                int x = 50 + week * (cellSize + cellGap);
                int y = 50 + dayOfWeek * (cellSize + cellGap);
                g.setColor(cellColor);
                g.fillRect(x, y, cellSize, cellSize);


                dayOfYear++;
            }
        }
    }

    private Color getColorForValue(int value) {
        if (value == 0) return new Color(235, 245, 235);
        else if (value <= 5) return new Color(200, 230, 200);
        else if (value <= 10) return new Color(150, 210, 150);
        else if (value <= 20) return new Color(100, 190, 100);
        else return new Color(50, 170, 50);
    }

}