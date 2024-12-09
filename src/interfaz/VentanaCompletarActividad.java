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
import actividades.Encuesta;
import actividades.Estado;
import actividades.Examen;
import actividades.Quiz;
import actividades.QuizVerdad;
import actividades.Recurso;
import actividades.Tarea;
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
				
				if(tipoAct == TipoActividades.Encuesta) {
					Encuesta encuesta = (Encuesta) actividad;
					encuesta.guardarRespuestas(estudiante);
					estudiante.getRegistroActividades().put(actividad.getId(), Estado.EXITOSA);
					labelCalificacion.setText("Estado de la Actividad:"+ Estado.EXITOSA.name());
					VentanaPrincipal.sistemaRegistro.guardarActividad(encuesta);
					VentanaPrincipal.sistemaRegistro.guardarEstudiante(estudiante);
					
					
				}
				else if (tipoAct == TipoActividades.Quiz) {
		            Quiz quiz = (Quiz) actividad;
		            Estado estado = quiz.guardarRespuestas(estudiante);
		            estudiante.getRegistroActividades().put(actividad.getId(), estado);
		            labelCalificacion.setText("Estado de la Actividad: " + estado.name());
		            VentanaPrincipal.sistemaRegistro.guardarActividad(quiz);
		            VentanaPrincipal.sistemaRegistro.guardarEstudiante(estudiante);
		        }
				 else if (tipoAct == TipoActividades.QuizVerdad) {
		                QuizVerdad quizVF = (QuizVerdad) actividad;
		                Estado estado = quizVF.guardarRespuestas(estudiante);
		                labelCalificacion.setText("Estado de la Actividad: " + estado.name());
		                VentanaPrincipal.sistemaRegistro.guardarActividad(quizVF);
		                VentanaPrincipal.sistemaRegistro.guardarEstudiante(estudiante);
		            }
				 else if (tipoAct == TipoActividades.Recurso) {
                Recurso recurso = (Recurso) actividad;
                labelCalificacion.setText("Estado de la Actividad: Exitosa" );
                estudiante.getRegistroActividades().put(actividad.getId(), Estado.EXITOSA);
                VentanaPrincipal.sistemaRegistro.guardarActividad(recurso);
                VentanaPrincipal.sistemaRegistro.guardarEstudiante(estudiante);
            }
				 else if (tipoAct == TipoActividades.Tarea) {
		                Tarea tarea = (Tarea) actividad;
		                tarea.guardarRespuestas(estudiante);
		                labelCalificacion.setText("Estado de la Actividad: ENVIADA" );
		                estudiante.getRegistroActividades().put(actividad.getId(), Estado.ENVIADA);
		                VentanaPrincipal.sistemaRegistro.guardarActividad(tarea);
		                VentanaPrincipal.sistemaRegistro.guardarEstudiante(estudiante);
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
        	if (tipoAct == TipoActividades.Encuesta) {
        		
        		Encuesta encuesta = (Encuesta) actividad;
        		panelContenido.add(encuesta.getContenido());
        	}
        	if( tipoAct == TipoActividades.Quiz) {
        		
        		Quiz quiz = (Quiz) actividad;
        		panelContenido.add(quiz.getContenido());
        	
        	
        }
        	if (tipoAct == TipoActividades.QuizVerdad) {
                QuizVerdad quizVF = (QuizVerdad) actividad;
                panelContenido.add(quizVF.getContenido());
            }
        	if (tipoAct == TipoActividades.Tarea) {
                Tarea tarea = (Tarea) actividad;
                panelContenido.add(tarea.getContenido());
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
