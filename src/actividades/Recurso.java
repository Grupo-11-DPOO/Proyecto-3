package actividades;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Recurso extends Actividad{

	private String material;
	
	public Recurso(String titulo, String objetivo, String descripcion, String nivel, int duracionMinutos,
			boolean obligatorio, String material) {
			// Llamado al constructor padre
			super(titulo, objetivo, descripcion, nivel, duracionMinutos, obligatorio);
			// Agregamos material puesto que es una actividad.
			this.material = material;
			this.tipoActividad = TipoActividades.Recurso;
	}
	
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	public Estado realizarRecurso() {
		Estado estado = Estado.EXITOSA;
		return estado;
	}
    public JPanel getContenido() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextArea areaRecurso = new JTextArea(this.material);
        areaRecurso.setEditable(false);
        areaRecurso.setWrapStyleWord(true);
        areaRecurso.setLineWrap(true);

        JScrollPane scrollPane = new JScrollPane(areaRecurso);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));

        panel.add(scrollPane);

        return panel;
    }
	@Override
	public Recurso clone() {
		Recurso copia = (Recurso) super.clone();
		copia.crearId();
		return copia;
	}
}
