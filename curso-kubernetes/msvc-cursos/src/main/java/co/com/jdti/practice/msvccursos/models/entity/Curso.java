package co.com.jdti.practice.msvccursos.models.entity;

import co.com.jdti.practice.msvccursos.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nombre;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "curso_id")
	private List<CursoUsuario> cursoUsuarios = new ArrayList<>();

	@Transient
	private List<Usuario> usuarios = new ArrayList<>();

	public void addCursoUsuario(CursoUsuario cursoUsuario) {
		this.cursoUsuarios.add(cursoUsuario);
	}

	public void removeCursoUsuario(CursoUsuario cursoUsuario) {
		this.cursoUsuarios.remove(cursoUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CursoUsuario)) {
			return false;
		}
		CursoUsuario o = (CursoUsuario) obj;
		return this.id != null && this.id.equals(o.getUsuarioId());
	}
}
