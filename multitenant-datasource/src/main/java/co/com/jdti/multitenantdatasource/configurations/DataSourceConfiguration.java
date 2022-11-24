package co.com.jdti.multitenantdatasource.configurations;

import co.com.jdti.multitenantdatasource.utils.MultitenantDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class DataSourceConfiguration {
	@Bean
	@Primary
	DataSource multitenantDataSource(Map<String, DataSource> dataSources) {
		var prefix = "ds";
		var map = dataSources
			.entrySet()
			.stream()
			.filter(e -> e.getKey().startsWith(prefix))
			.collect(Collectors.toMap(
				e -> (Object) Integer.parseInt(e.getKey().substring(prefix.length())),
				e -> (Object) e.getValue()
			));
		map.forEach((tenantId, ds) -> {
			var initializer = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"),
				new ClassPathResource(prefix + tenantId + "-data.sql"));
			initializer.execute((DataSource) ds);
			System.out.println("initialized datasource for " + tenantId);
		});
		var mds = new MultitenantDataSource();
		mds.setTargetDataSources(map);
		return mds;
	}

	@Bean
	DataSource ds1() {
		return dataSource("db_one");
	}

	@Bean
	DataSource ds2() {
		return dataSource("db_two");
	}

	private static DataSource dataSource(String dbName) {
		var dsp = new DataSourceProperties();
		dsp.setPassword("admin-root");
		dsp.setUsername("root");
		dsp.setUrl("jdbc:mysql://localhost:3306/" + dbName + "?allowPublicKeyRetrieval=true&useConfigs=maxPerformance&characterEncoding=utf8&useSSL=false&serverTimezone=America/Bogota&createDatabaseIfNotExist=true");
		return dsp.initializeDataSourceBuilder()
			.type(HikariDataSource.class)
			.build();
	}
}
