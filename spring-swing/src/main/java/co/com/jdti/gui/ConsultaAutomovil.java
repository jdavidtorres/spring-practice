package co.com.jdti.gui;

import co.com.jdti.model.Automovil;
import co.com.jdti.service.AutomovilService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ConsultaAutomovil extends JFrame {

	private JPanel mainPanel;
	private JPanel tablePanel;
	private JTable table;
	private JButton btnModificar;
	private JButton btnEliminar;
	private final AutomovilService automovilService;

	public ConsultaAutomovil(AutomovilService automovilService) {
		this.automovilService = automovilService;
		setTitle("Consulta Automovil");
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(720, 480);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				cargarTabla();
			}
		});
		btnEliminar.addActionListener(e -> {
			if (table.getSelectedRow() != -1) {
				int idAuto = (int) table.getValueAt(table.getSelectedRow(), 0);
				automovilService.delete(idAuto);
				cargarTabla();
			} else {
				mostrarMensaje("Favor seleccionar una fila", "Error", "Aviso");
			}
		});
		btnModificar.addActionListener(e -> {
			if (table.getSelectedRow() != -1) {
				int idAuto = (int) table.getValueAt(table.getSelectedRow(), 0);
				ModificarAuto modificarAuto = new ModificarAuto(automovilService, idAuto);
				modificarAuto.setVisible(true);
				dispose();
			} else {
				mostrarMensaje("Favor seleccionar una fila", "Error", "Aviso");
			}
		});
		pack();
		setVisible(false);
	}

	private List<Automovil> listarAutos() {
		return automovilService.findAll();
	}

	private void cargarTabla() {
		DefaultTableModel defaultTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		String[] titulos = {"Id", "Modelo", "Marca", "Motor", "Color", "Placa", "Cantidad de puertas"};
		defaultTableModel.setColumnIdentifiers(titulos);

		if (!listarAutos().isEmpty()) {
			for (Automovil automovil : listarAutos()) {
				Object[] objects = {automovil.getId(), automovil.getModelo(), automovil.getMarca(), automovil.getMotor(), automovil.getColor(), automovil.getPlaca(), automovil.getCantidadPuertas()};
				defaultTableModel.addRow(objects);
			}
		}

		table.setModel(defaultTableModel);
	}

	private void mostrarMensaje(String mensaje, String tipo, String titulo) {
		JOptionPane optionPane = new JOptionPane(mensaje);
		if (tipo.equals("Info")) {
			optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
		} else if (tipo.equals("Error")) {
			optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
		}
		JDialog dialog = optionPane.createDialog(titulo);
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}
