package co.jdti.example.microserviciocommons.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
@Data
public class SubjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column
    private String name;

    @JsonIgnoreProperties(value = "sons", allowSetters = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private SubjectEntity father;

    @JsonIgnoreProperties(value = "father", allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "father", cascade = CascadeType.ALL)
    private List<SubjectEntity> sons;

    public SubjectEntity() {
        this.sons = new ArrayList<>();
    }
}
