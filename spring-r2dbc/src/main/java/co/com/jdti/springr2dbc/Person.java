package co.com.jdti.springr2dbc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
	@Id
	private Long id;
	@Column("first_name")
	private String firstName;
	private Integer age;
	private String email;
}
