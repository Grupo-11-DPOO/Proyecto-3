package interfaz;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import actividades.Actividad;
import actividades.Estado;
import actividades.Examen;
import actividades.TipoActividades;
import usuarios.Estudiante;

public class VentanaCompletarActividad extends JFrame {
	
	private Estudiante estudiante;
	private Actividad actividad;
	private JTextArea detallesArea;
	private JButton botonEnviar;
	private JLabel labelCalificacion;
	private JPanel panelContenido;
	
	public VentanaCompletarActividad(Estudiante estudiante) {
		this.estudiante= estudiante;
		actividad = this.estudiante.getActividadEnCurso();
		// TODO Auto-generated constructor stub
		setTitle( "Completar actividad en curso." );
        setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        setSize( 800, 600 );
        setResizable(false);
        setLocationRelativeTo( null );
        setVisible( true );
        setLayout(new BorderLayout());
        
        detallesArea = new JTextArea();
        detallesArea.setEditable(false);
        
        panelContenido = new JPanel();
        JScrollPane scrollPane = new JScrollPane(panelContenido);
        add(scrollPane,BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel();
        botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				botonEnviar.setEnabled(false);
				TipoActividades tipoAct = actividad.getTipoActividad();
				
				if(tipoAct == TipoActividades.Examen) {
					Examen examen = (Examen) actividad;
					
					labelCalificacion.setText("Estado de la Actividad: "+Estado.PENDIENTE.name());
					examen.guardarRespuestas(estudiante);
					estudiante.getRegistroActividades().put(actividad.getId(),Estado.PENDIENTE);
					VentanaPrincipal.sistemaRegistro.guardarEstudiante(estudiante);
					VentanaPrincipal.sistemaRegistro.guardarActividad(examen);
				}
			}
        	
        });
        panelBotones.add(botonEnviar);
        
        labelCalificacion = new JLabel();
        panelBotones.add(labelCalificacion);
        
        add(panelBotones,BorderLayout.SOUTH);
        
        
        if(actividad== null) {
        	
        	mostrarMensajeActividadNoSeleccionada();
        }
        else {
        	mostrarInfo(actividad);
        	TipoActividades tipoAct = actividad.getTipoActividad();
        	
        	if(tipoAct == TipoActividades.Examen) {
        		Examen examen = (Examen) actividad;
        		panelContenido.add(examen.getContenido());
      
        		
        	}
        	
        }
        
	}
	
	private void mostrarMensajeActividadNoSeleccionada() {
		
		detallesArea.setText("Por favor selecciona una actividad para poder comenzarla...");
		
	}
	
	private void mostrarInfo(Actividad act) {
		
		JPanel panelInfo = new JPanel(new GridLayout(5,1));
		panelInfo.add(new JLabel("Título: "+act.getTitulo()));
		panelInfo.add(new JLabel("Descripción: "+ act.getDescripcion()));
		panelInfo.add(new JLabel("Objetivo:"+ act.getObjetivo()));
		panelInfo.add(new JLabel("Duración: "+act.getDuracionMinutos()));
		panelInfo.add(new JLabel("Nivel: "+act.getNivel()));
		add(panelInfo,BorderLayout.NORTH);
		
		
		
	}
	
}
