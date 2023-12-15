package co.com.jdti.walletapp;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

public class TestMYSQLContainerConnection {

	@Container
	@ServiceConnection
	public static MySQLContainer<?> mysqlDB = new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
}
