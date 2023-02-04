package co.com.jdti.gui;

import co.com.jdti.model.Automovil;
import co.com.jdti.service.AutomovilService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificarAuto extends JFrame {

	private final AutomovilService automovilService;
	private JPanel mainPanel;
	private JLabel lblTitle;
	private JLabel logoImage;
	private JTextField tfMarca;
	private JTextField tfColor;
	private JTextField tfMotor;
	private JTextField tfPlaca;
	private JTextField tfCantidadPuertas;
	private JButton btnLimpiar;
	private JButton btnGuardar;
	private JTextField tfModelo;
	private Automovil automovil;

	public ModificarAuto(AutomovilService automovilService, int idAuto) {
		this.automovilService = automovilService;
		cargarAuto(idAuto);

		setTitle("Modificar Automovil");
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		btnGuardar.addActionListener(e -> {
			automovil.setModelo(tfModelo.getText());
			automovil.setMarca(tfModelo.getText());
			automovil.setMotor(tfModelo.getText());
			automovil.setColor(tfColor.getText());
			automovil.setPlaca(tfPlaca.getText());
			automovil.setCantidadPuertas(Integer.parseInt(tfCantidadPuertas.getText()));

			automovilService.agregarAutomovil(automovil);

			ConsultaAutomovil consultaAutomovil = new ConsultaAutomovil(automovilService);
			consultaAutomovil.setVisible(true);

			dispose();
		});

		pack();
		setVisible(false);
		btnLimpiar.addActionListener(e -> {
			ConsultaAutomovil consultaAutomovil = new ConsultaAutomovil(automovilService);
			consultaAutomovil.setVisible(true);
			dispose();
		});
	}

	private void cargarAuto(int idAuto) {
		automovil = automovilService.getById(idAuto);
		tfModelo.setText(automovil.getModelo());
		tfMarca.setText(automovil.getMarca());
		tfMotor.setText(automovil.getMotor());
		tfColor.setText(automovil.getColor());
		tfPlaca.setText(automovil.getPlaca());
		tfCantidadPuertas.setText("" + automovil.getCantidadPuertas());
	}
}
