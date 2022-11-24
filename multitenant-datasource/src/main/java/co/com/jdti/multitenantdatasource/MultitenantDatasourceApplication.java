package co.com.jdti.multitenantdatasource;

import co.com.jdti.multitenantdatasource.models.Customer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.web.servlet.function.RouterFunctions.route;

@SpringBootApplication
public class MultitenantDatasourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultitenantDatasourceApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routes(JdbcTemplate template) {
		return route()
			.GET("/customers", request -> {
				var results = template.query("SELECT * FROM customers",
					(rs, rowNum) -> new Customer(rs.getInt("id"),
						rs.getString("name")));
				return ServerResponse.ok().body(results);
			})
			.build();
	}
}
