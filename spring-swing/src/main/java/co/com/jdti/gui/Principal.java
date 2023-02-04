package co.com.jdti.gui;

import co.com.jdti.service.AutomovilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class Principal extends JFrame {
	private JButton btnNewCar;
	private JButton btnCheckCar;
	private JButton btnExit;
	private JLabel lblTitle;
	private JLabel logoImage;
	private JPanel mainPanel;

	@Autowired
	private AutomovilService automovilService;

	public Principal() {
		setTitle("Principal");
		setContentPane(mainPanel);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		btnNewCar.addActionListener(e -> {
			CreateCar createCar = new CreateCar(automovilService);
			createCar.setVisible(true);
		});
		btnCheckCar.addActionListener(e -> {
			ConsultaAutomovil consultaAutomovil = new ConsultaAutomovil(automovilService);
			consultaAutomovil.setVisible(true);
		});
		btnExit.addActionListener(e -> {
		});

		pack();
		setVisible(true);
	}
}
