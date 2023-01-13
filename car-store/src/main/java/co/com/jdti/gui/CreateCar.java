package co.com.jdti.gui;

import co.com.jdti.model.Automovil;
import co.com.jdti.service.AutomovilService;

import javax.swing.*;

public class CreateCar extends JFrame {
	private JPanel mainPanel;
	private JLabel lblTitle;
	private JLabel logoImage;
	private JTextField tfModelo;
	private JTextField tfMarca;
	private JButton btnLimpiar;
	private JButton btnGuardar;
	private JTextField tfColor;
	private JTextField tfMotor;
	private JTextField tfPlaca;
	private JTextField tfCantidadPuertas;

	public CreateCar(AutomovilService automovilService) {
		setTitle("Dar alta a automovil");
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		btnGuardar.addActionListener(e -> {
			String modelo = tfModelo.getText();
			String marca = tfMarca.getText();
			String motor = tfMotor.getText();
			String color = tfColor.getText();
			String placa = tfPlaca.getText();
			int cantPuertas = Integer.parseInt(tfCantidadPuertas.getText());

			Automovil automovil = new Automovil(null, modelo, marca, motor, color, placa, cantPuertas);

			automovilService.agregarAutomovil(automovil);

			dispose();
		});
		btnLimpiar.addActionListener(e -> {
			tfModelo.setText("");
			tfMarca.setText("");
			tfMotor.setText("");
			tfColor.setText("");
			tfPlaca.setText("");
			tfCantidadPuertas.setText("");
		});

		pack();
		setVisible(false);
	}
}
